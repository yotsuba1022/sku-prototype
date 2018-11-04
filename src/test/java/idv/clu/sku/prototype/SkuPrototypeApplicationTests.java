package idv.clu.sku.prototype;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import idv.clu.sku.prototype.model.SkuDetails;
import idv.clu.sku.prototype.model.SkuDetailsKey;
import idv.clu.sku.prototype.repository.SkuDetailsDataAccess;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.cql.CqlIdentifier;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkuPrototypeApplicationTests {

    private static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS test_key_space "
            + "WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

    private static final String KEYSPACE_ACTIVATE_QUERY = "USE test_key_space;";

    private static final String SKU_DETAILS_TABLE_NAME = "sku_details";

    @Autowired
    private SkuDetailsDataAccess skuDetailsDataAccess;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private CassandraAdminOperations cassandraAdminOperations;

    @BeforeClass
    public static void startCassandraEmbedded() throws InterruptedException, TTransportException, IOException {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
        final Cluster cluster =
                Cluster.builder().withoutJMXReporting().addContactPoints("localhost").withPort(9142).build();
        final Session session = cluster.connect();
        session.execute(KEYSPACE_CREATION_QUERY);
        session.execute(KEYSPACE_ACTIVATE_QUERY);
        Thread.sleep(5000);
    }

    /**
     * This functionality still has bug, for more details, please refer to:
     * (https://github.com/jsevellec/cassandra-unit/issues/278)
     * <p>
     * The workaround is the add the following line of code before invoke cleanEmbeddedCassandra:
     * EmbeddedCassandraServerHelper.getSession();
     */
    @AfterClass
    public static void stopCassandraEmbedded() {
        EmbeddedCassandraServerHelper.getSession();
        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    }

    @Before
    public void createTable() {
        cassandraAdminOperations.createTable(true, CqlIdentifier.of(SKU_DETAILS_TABLE_NAME), SkuDetails.class,
                                             new HashMap<>());
    }

    @After
    public void dropTable() {
        cassandraAdminOperations.dropTable(CqlIdentifier.of(SKU_DETAILS_TABLE_NAME));
    }

    @Test
    public void testReadSkuDetails() {
        final String skuId = "sku-test";
        SkuDetails createdSkuDetails = skuDetailsDataAccess.createSkuDetailsByTemplate(skuId);
        SkuDetailsKey key = createdSkuDetails.getSkuDetailsKey();
        SkuDetails fetchedSkuDetails =
                skuDetailsDataAccess.getByTemplate(key.getChannelId(), key.getSkuId(), key.getClientId());

        assertEquals(createdSkuDetails.getId(), fetchedSkuDetails.getId());
    }

}
