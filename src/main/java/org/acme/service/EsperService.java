package org.acme.service;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.acme.dto.AssetDTO;
import org.acme.entity.KnownAsset;

@ApplicationScoped
public class EsperService {

    private final EPServiceProvider epService;
    private final BitvavoService bitvavoService;

    @Inject
    public EsperService(BitvavoService bitvavoService) {
        this.epService = EPServiceProviderManager.getDefaultProvider();
        this.bitvavoService = bitvavoService;
    }

    void onStart(@Observes StartupEvent ev) {
        this.epService.getEPAdministrator().createEPL("create schema AssetEvent as (symbol string)");

        Panache.withTransaction(() -> KnownAsset.<KnownAsset>listAll()
                .onItem().transformToUni(knownAssets -> {
                    this.epService.getEPAdministrator().createEPL("create schema KnownAsset as (symbol string)");
                    for (KnownAsset knownAsset : knownAssets) {
                        epService.getEPRuntime().sendEvent(new AssetDTO(knownAsset.symbol), "KnownAsset");
                    }
                    return bitvavoService.getAllAssets();
                })
                .onItem().invoke(assets -> {
                    EPStatement statement = this.epService.getEPAdministrator().createEPL(
                            "select a.symbol from AssetEvent a where not exists (select 1 from KnownAsset b where b.symbol = a.symbol)"
                    );

                    statement.setSubscriber((newData, oldData) -> {
                        String newSymbol = (String) newData[0].get("symbol");
                        Log.info("New asset detected: " + newSymbol);
                        Panache.withTransaction(() -> new KnownAsset(newSymbol).persist())
                                .onItem().invoke(() -> epService.getEPRuntime().sendEvent(new AssetDTO(newSymbol), "KnownAsset"))
                                .subscribe().with(v -> {});
                    });

                    // Initial scan
                    assets.forEach(this::sendEvent);
                })
        ).subscribe().with(v -> {});
    }

    public void sendEvent(AssetDTO asset) {
        epService.getEPRuntime().sendEvent(asset, "AssetEvent");
    }
}
