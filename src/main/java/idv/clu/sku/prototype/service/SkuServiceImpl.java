package idv.clu.sku.prototype.service;

import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import idv.clu.sku.prototype.model.SkuDetails;
import idv.clu.sku.prototype.model.SkuDetailsKey;
import idv.clu.sku.prototype.repository.SkuDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Carl Lu
 */
@Service
public class SkuServiceImpl implements SkuService {

    private final CassandraTemplate cassandraTemplate;
    private final SkuDetailsRepository skuDetailsRepository;

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public SkuServiceImpl(final SkuDetailsRepository skuDetailsRepository, final CassandraTemplate cassandraTemplate) {
        this.skuDetailsRepository = skuDetailsRepository;
        this.cassandraTemplate = cassandraTemplate;
    }

    @Override
    public SkuDetails createSkuDetails(String skuId) {
        return skuDetailsRepository.save(create(skuId));
    }

    @Override
    public SkuDetails createSkuDetailsByTemplate(String skuId) {
        return cassandraTemplate.insert(create(skuId));
    }

    @Override
    public SkuDetails get(String channelId, String skuId, String clientId) {
        SkuDetailsKey skuDetailsKey = new SkuDetailsKey();
        skuDetailsKey.setChannelId(channelId);
        skuDetailsKey.setClientId(clientId);
        skuDetailsKey.setSkuId(skuId);
        Optional<SkuDetails> skuDetails = skuDetailsRepository.findById(skuDetailsKey);
        return skuDetails.get();
    }

    @Override
    public SkuDetails getByTemplate(String channelId, String skuId, String clientId) {
        Select selectQuery = QueryBuilder.select().from("sku_details");
        Select.Where where = selectQuery.where();
        Clause channelClause = QueryBuilder.eq("channel_id", channelId);
        Clause skuIdClause = QueryBuilder.eq("sku_id", skuId);
        Clause clientIdClause = QueryBuilder.eq("client_id", clientId);
        where.and(channelClause).and(skuIdClause).and(clientIdClause);
        return cassandraTemplate.selectOne(selectQuery, SkuDetails.class);
    }

    @Override
    public List<SkuDetails> getByQuery(int limit, int page) {
        int iterations = 0;
        Query query = Query.empty().pageRequest(CassandraPageRequest.of(iterations++, limit));
        Slice<SkuDetails> skuDetailsPage = cassandraTemplate.slice(query, SkuDetails.class);

        while (iterations < page) {
            if (skuDetailsPage.hasNext()) {
                skuDetailsPage =
                        cassandraTemplate.slice(query.pageRequest(skuDetailsPage.nextPageable()), SkuDetails.class);
                iterations++;
            } else {
                break;
            }
        }

        return skuDetailsPage.getContent();
    }

    private SkuDetails create(String skuId) {
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
        skuDetails.setId(UUID.randomUUID().toString());
        skuDetails.setCreatedTime(Timestamp.from(Instant.now()));
        skuDetails.setUpdatedTime(Timestamp.from(Instant.now()));

        return skuDetails;
    }

}
