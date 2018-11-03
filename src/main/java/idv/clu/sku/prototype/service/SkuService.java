package idv.clu.sku.prototype.service;

import idv.clu.sku.prototype.model.SkuDetails;

import java.util.List;

/**
 * @author Carl Lu
 */
public interface SkuService {

    SkuDetails createSkuDetails(String skuId);

    SkuDetails createSkuDetailsByTemplate(String skuId);

    SkuDetails get(String channelId, String skuId, String clientId);

    SkuDetails getByTemplate(String channelId, String skuId, String clientId);

    List<SkuDetails> getByQuery(int limit, int page);

}
