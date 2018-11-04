package idv.clu.sku.prototype.repository;

import idv.clu.sku.prototype.model.SkuDetails;
import idv.clu.sku.prototype.model.SkuDetailsKey;

import java.util.List;

/**
 * @author Carl Lu
 */
public interface SkuDetailsDataAccess {

    SkuDetails createSkuDetails(String skuId);

    SkuDetails createSkuDetailsByTemplate(String skuId);

    SkuDetails get(String channelId, String skuId, String clientId);

    SkuDetails getByTemplate(String channelId, String skuId, String clientId);

    List<SkuDetails> getByQuery(int limit, int page);

    SkuDetails update(SkuDetails skuDetails);

    boolean deleteByKey(SkuDetailsKey key);

}
