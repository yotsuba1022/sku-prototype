package idv.clu.sku.prototype.service;

import idv.clu.sku.prototype.model.SkuDetails;
import idv.clu.sku.prototype.model.SkuDetailsKey;
import idv.clu.sku.prototype.repository.SkuDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Carl Lu
 */
@Service
public class SkuServiceImpl implements SkuService {

    private final SkuDetailsRepository skuDetailsRepository;

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public SkuServiceImpl(final SkuDetailsRepository skuDetailsRepository) {
        this.skuDetailsRepository = skuDetailsRepository;
    }

    @Override
    public SkuDetails createSkuDetails() {
        SkuDetailsKey skuDetailsKey = new SkuDetailsKey();
        skuDetailsKey.setChannelId("DEV");
        skuDetailsKey.setClientId("Client1");
        skuDetailsKey.setSkuId("sku1");

        SkuDetails skuDetails = new SkuDetails();
        skuDetails.setSkuDetailsKey(skuDetailsKey);
        skuDetails.setName("MBPR 13 inch");
        skuDetails.setEccn("ECCN1");
        skuDetails.setPartNumber("partNum1");
        skuDetails.setTaxCode("Tax123");
        skuDetails.setId(UUID.randomUUID().toString());
        skuDetails.setCreatedTime(Timestamp.from(Instant.now()));
        skuDetails.setUpdatedTime(Timestamp.from(Instant.now()));

        return skuDetailsRepository.save(skuDetails);
    }

    @Override
    public SkuDetails get(String channelId, String skuId, String clientId) {
        SkuDetailsKey skuDetailsKey = new SkuDetailsKey();
        skuDetailsKey.setChannelId(channelId);
        skuDetailsKey.setClientId(clientId);
        skuDetailsKey.setSkuId(skuId);
        Optional<SkuDetails> skuDetails = skuDetailsRepository.findById(skuDetailsKey);
        return skuDetails.get();
        //return skuDetailsRepository.findOneByChannelIdAndSkuIdAndClientId(channelId, skuId, clientId);
        //return skuDetailsRepository.findAllById(Collections.singletonList(skuDetailsKey));
    }

}
