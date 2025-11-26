package org.acme.service;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dto.AssetDTO;

@ApplicationScoped
public class AssetScannerService {

    @Inject
    BitvavoService bitvavoService;

    @Inject
    EsperService esperService;

    @Scheduled(every = "10m")
    void scanForNewAssets() {
        bitvavoService.getAllAssets()
                .subscribe().with(assets -> {
                    assets.forEach(assetDTO -> {
                        esperService.sendEvent(assetDTO);
                    });
                });
    }
}