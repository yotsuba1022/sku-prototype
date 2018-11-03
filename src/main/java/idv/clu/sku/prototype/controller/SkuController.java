package idv.clu.sku.prototype.controller;

import idv.clu.sku.prototype.model.SkuDetails;
import idv.clu.sku.prototype.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Carl Lu
 */
@RestController
public class SkuController {

    private final SkuService skuService;

    @Autowired
    public SkuController(final SkuService skuService) {
        this.skuService = skuService;
    }

    @GetMapping("/get")
    public SkuDetails get() {
        skuService.createSkuDetails();
        return skuService.get("DEV", "sku1", "Client1");
    }

}
