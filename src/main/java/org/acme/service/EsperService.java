package org.acme.service;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.acme.dto.AssetDTO;
import org.acme.entity.KnownAsset;

import java.util.List;

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
        // epService.getEPAdministrator().getConfiguration().addEventType("AssetDTO", AssetDTO.class);
        // epService.getEPAdministrator().createEPL("create schema AssetEvent as AssetDTO");
        // epService.getEPAdministrator().createEPL("create schema KnownAsset as AssetDTO");

        // Uni<List<KnownAsset>> knownAssetsUni = Panache.withTransaction(KnownAsset::listAll);
        // Uni<List<AssetDTO>> allAssetsUni = bitvavoService.getAllAssets();

        // Uni.combine().all().unis(knownAssetsUni, allAssetsUni).asTuple()
        //         .onItem().invoke(tuple -> {
        //             List<KnownAsset> knownAssets = tuple.getItem1();
        //             List<AssetDTO> allAssets = tuple.getItem2();

        //             // Populate Esper with known assets
        //             for (KnownAsset knownAsset : knownAssets) {
        //                 epService.getEPRuntime().sendEvent(new AssetDTO(knownAsset.symbol), "KnownAsset");
        //             }

        //             // Create EPL statement
        //             EPStatement statement = epService.getEPAdministrator().createEPL(
        //                     "select a.symbol from AssetEvent a where not exists (select 1 from KnownAsset b where b.symbol = a.symbol)"
        //             );

        //             // Add listener
        //             statement.addListener((newData, oldData) -> {
        //                 String newSymbol = (String) newData[0].get("symbol");
        //                 Log.info("New asset detected: " + newSymbol);
        //                 Panache.withTransaction(() -> new KnownAsset(newSymbol).persist())
        //                         .onItem().invoke(() -> {
        //                             Log.info("Persisted new asset: " + newSymbol);
        //                             epService.getEPRuntime().sendEvent(new AssetDTO(newSymbol), "KnownAsset");
        //                         })
        //                         .subscribe().with(
        //                                 v -> {},
        //                                 failure -> Log.error("Failed to persist new asset " + newSymbol, failure)
        //                         );
        //             });

        //             // Initial scan
        //             allAssets.forEach(this::sendEvent);
        //         })
        //         .subscribe().with(
        //                 v -> Log.info("EsperService started and initialized."),
        //                 failure -> Log.error("Failed to initialize EsperService", failure)
        //         );
    }


    public void sendEvent(AssetDTO asset) {
        // epService.getEPRuntime().sendEvent(asset, "AssetEvent");
    }
}
