package idv.clu.sku.prototype.repository;

import idv.clu.sku.prototype.model.SkuDetails;
import idv.clu.sku.prototype.model.SkuDetailsKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Carl Lu
 */
@Repository
public interface SkuDetailsRepository extends CassandraRepository<SkuDetails, SkuDetailsKey> {

    //SkuDetails findOneByChannelIdAndSkuIdAndClientId(String channelId, String skuId, String clientId);

}
