package idv.clu.sku.prototype.controller;

import idv.clu.sku.prototype.model.SkuDetails;
import idv.clu.sku.prototype.model.SkuDetailsKey;
import idv.clu.sku.prototype.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PostMapping("/create/{skuId}")
    public SkuDetails create(@PathVariable(name = "skuId") String skuId) {
        return skuService.createSkuDetails(skuId);
    }

    @GetMapping("/get/{skuId}")
    public SkuDetails get(@PathVariable(name = "skuId") String skuId) {
        return skuService.get("DEV", skuId, "Client1");
    }

    @GetMapping("/getByTemplate/{skuId}")
    public SkuDetails getByTemplate(@PathVariable(name = "skuId") String skuId) {
        return skuService.getByTemplate("DEV", skuId, "Client1");
    }

    @GetMapping("/list")
    public List<SkuDetails> getByQuery(
            @RequestParam(value = "limit", required = false, defaultValue = "10") final String limit,
            @RequestParam(value = "page", required = false, defaultValue = "1") final String page) {
        return skuService.getByQuery(Integer.valueOf(limit), Integer.valueOf(page));
    }

    @PutMapping("/update")
    public SkuDetails update(@RequestBody SkuDetails skuDetails) {
        return skuService.update(skuDetails);
    }

    @DeleteMapping("/{channelId}/{skuId}/{clientId}")
    public boolean deleteById(@PathVariable(name = "channelId") String channelId,
                              @PathVariable(name = "skuId") String skuId,
                              @PathVariable(name = "clientId") String clientId) {
        SkuDetailsKey key = skuService.getByTemplate(channelId, skuId, clientId).getSkuDetailsKey();
        return skuService.deleteByKey(key);
    }

}
