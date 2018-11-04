package idv.clu.sku.prototype.repository;

import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import idv.clu.sku.prototype.model.SkuDetails;
import idv.clu.sku.prototype.model.SkuDetailsKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Carl Lu
 */
@Component
public class SkuDetailsDataAccessImpl implements SkuDetailsDataAccess {

    private final CassandraTemplate cassandraTemplate;
    private final SkuDetailsRepository skuDetailsRepository;

    @Autowired
    public SkuDetailsDataAccessImpl(final CassandraTemplate cassandraTemplate,
                                    final SkuDetailsRepository skuDetailsRepository) {
        this.cassandraTemplate = cassandraTemplate;
        this.skuDetailsRepository = skuDetailsRepository;
    }

    /**
     * Implement with Spring Data Cassandra Repository.
     *
     * @param skuId sku id
     * @return skuDetails
     */
    @Override
    public SkuDetails createSkuDetails(final String skuId) {
        return skuDetailsRepository.save(create(skuId));
    }

    /**
     * Implement with Spring Data Cassandra Template
     *
     * @param skuId sku id
     * @return skuDetails
     */
    @Override
    public SkuDetails createSkuDetailsByTemplate(final String skuId) {
        return cassandraTemplate.insert(create(skuId));
    }

    /**
     * Implement with Spring Data Cassandra Repository.
     *
     * @param channelId channel id
     * @param skuId     sku id
     * @param clientId  client id
     * @return sku details
     */
    @Override
    public SkuDetails get(final String channelId, final String skuId, final String clientId) {
        SkuDetailsKey skuDetailsKey = new SkuDetailsKey();
        skuDetailsKey.setChannelId(channelId);
        skuDetailsKey.setClientId(clientId);
        skuDetailsKey.setSkuId(skuId);
        Optional<SkuDetails> skuDetails = skuDetailsRepository.findById(skuDetailsKey);
        return skuDetails.get();
    }

    /**
     * Implement with Spring Data Cassandra Template
     *
     * @param channelId channel id
     * @param skuId     sku id
     * @param clientId  client id
     * @return sku details
     */
    @Override
    public SkuDetails getByTemplate(final String channelId, final String skuId, final String clientId) {
        Select selectQuery = QueryBuilder.select().from("sku_details");
        Select.Where where = selectQuery.where();
        Clause channelClause = QueryBuilder.eq("channel_id", channelId);
        Clause skuIdClause = QueryBuilder.eq("sku_id", skuId);
        Clause clientIdClause = QueryBuilder.eq("client_id", clientId);
        where.and(channelClause).and(skuIdClause).and(clientIdClause);
        return cassandraTemplate.selectOne(selectQuery, SkuDetails.class);
    }

    /**
     * Implement with Spring Data Cassandra Template
     *
     * @param limit limit
     * @param page  page
     * @return list of sku details
     */
    @Override
    public List<SkuDetails> getByQuery(final int limit, final int page) {
        int iterations = 0;
        Query query = Query.empty().pageRequest(CassandraPageRequest.of(iterations++, limit));
        Slice<SkuDetails> slice = cassandraTemplate.slice(query, SkuDetails.class);

        while (iterations < page) {
            if (slice.hasNext()) {
                slice = cassandraTemplate.slice(query.pageRequest(slice.nextPageable()), SkuDetails.class);
                iterations++;
            } else {
                break;
            }
        }

        return slice.getContent();
    }

    /**
     * Implement with Spring Data Cassandra Template
     *
     * @param skuDetails sku details
     * @return sku details
     */
    @Override
    public SkuDetails update(final SkuDetails skuDetails) {
        return cassandraTemplate.update(skuDetails);
    }

    /**
     * Implement with Spring Data Cassandra Template
     *
     * @param key key
     * @return boolean
     */
    @Override
    public boolean deleteByKey(final SkuDetailsKey key) {
        return cassandraTemplate.deleteById(key, SkuDetails.class);
    }

    private SkuDetails create(final String skuId) {
        SkuDetailsKey skuDetailsKey = new SkuDetailsKey();
        skuDetailsKey.setChannelId("DEV");
        skuDetailsKey.setClientId("Client1");
        skuDetailsKey.setSkuId(skuId);

        SkuDetails skuDetails = new SkuDetails();
        skuDetails.setSkuDetailsKey(skuDetailsKey);
        skuDetails.setName("MBPR 15 inch by " + skuId);
        skuDetails.setEccn("ECCN1");
        skuDetails.setPartNumber("partNum by " + skuId);
        skuDetails.setTaxCode("Tax123");

        Map<String, String> metaData = new HashMap<>();
        metaData.put("primaryKey", "DEV_Client1_" + skuId);
        metaData.put("eccn", "ECCN1");
        skuDetails.setMetadata(metaData);

        skuDetails.setId(UUID.randomUUID().toString());
        skuDetails.setCreatedTime(Timestamp.from(Instant.now()));

        Map<String, String> updatedBy = new HashMap<>();
        updatedBy.put("invoker", "clu");
        updatedBy.put("host", "127.0.0.1");
        skuDetails.setUpdatedBy(updatedBy);

        skuDetails.setUpdatedTime(Timestamp.from(Instant.now()));

        return skuDetails;
    }

}
