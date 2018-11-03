package idv.clu.sku.prototype.service;

import idv.clu.sku.prototype.model.SkuDetails;

/**
 * @author Carl Lu
 */
public interface SkuService {

    SkuDetails createSkuDetails();

    SkuDetails get(String channelId, String skuId, String clientId);

}
