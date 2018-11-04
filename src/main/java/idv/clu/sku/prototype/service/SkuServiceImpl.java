package idv.clu.sku.prototype.service;

import idv.clu.sku.prototype.model.SkuDetails;
import idv.clu.sku.prototype.model.SkuDetailsKey;
import idv.clu.sku.prototype.repository.SkuDetailsDataAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Carl Lu
 */
@Service
public class SkuServiceImpl implements SkuService {

    private final SkuDetailsDataAccess skuDetailsDataAccess;

    @Autowired
    public SkuServiceImpl(final SkuDetailsDataAccess skuDetailsDataAccess) {
        this.skuDetailsDataAccess = skuDetailsDataAccess;
    }

    @Override
    public SkuDetails createSkuDetails(final String skuId) {
        return skuDetailsDataAccess.createSkuDetails(skuId);
    }

    @Override
    public SkuDetails createSkuDetailsByTemplate(final String skuId) {
        return skuDetailsDataAccess.createSkuDetailsByTemplate(skuId);
    }

    @Override
    public SkuDetails get(final String channelId, final String skuId, final String clientId) {
        return skuDetailsDataAccess.get(channelId, skuId, clientId);
    }

    @Override
    public SkuDetails getByTemplate(final String channelId, final String skuId, final String clientId) {
        return skuDetailsDataAccess.getByTemplate(channelId, skuId, clientId);
    }

    @Override
    public List<SkuDetails> getByQuery(final int limit, final int page) {
        return skuDetailsDataAccess.getByQuery(limit, page);
    }

    @Override
    public SkuDetails update(final SkuDetails skuDetails) {
        return skuDetailsDataAccess.update(skuDetails);
    }

    @Override
    public boolean deleteByKey(final SkuDetailsKey key) {
        return skuDetailsDataAccess.deleteByKey(key);
    }

}
