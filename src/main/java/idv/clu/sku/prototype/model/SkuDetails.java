package idv.clu.sku.prototype.model;

import com.datastax.driver.core.DataType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Carl Lu
 */
@Table(value = "sku_details")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SkuDetails implements Serializable {

    @PrimaryKey
    private SkuDetailsKey skuDetailsKey;
    private String name;
    private String eccn;
    @Column(value = "part_number")
    private String partNumber;
    @Column(value = "tax_code")
    private String taxCode;
    // private Map<String, String> metadata;

    @CassandraType(type = DataType.Name.UUID)
    private String id;

    @Column(value = "created_time")
    private Date createdTime;
    @Column(value = "updated_time")
    private Date updatedTime;

}
