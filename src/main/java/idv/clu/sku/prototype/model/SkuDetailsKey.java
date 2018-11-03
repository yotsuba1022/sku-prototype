package idv.clu.sku.prototype.model;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;

/**
 * @author Carl Lu
 */
@PrimaryKeyClass
@Data
public class SkuDetailsKey implements Serializable {

    @PrimaryKeyColumn(name = "channel_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String channelId;
    @PrimaryKeyColumn(name = "sku_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String skuId;
    @PrimaryKeyColumn(name = "client_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private String clientId;

}
