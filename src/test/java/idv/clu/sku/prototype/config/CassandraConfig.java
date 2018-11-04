package idv.clu.sku.prototype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraEntityClassScanner;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;

/**
 * @author Carl Lu
 */
@Configuration
public class CassandraConfig {

    private final static String KEY_SPACE = "test_key_space";

    @Bean
    public CassandraClusterFactoryBean cluster() {
        final CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setJmxReportingEnabled(false);
        cluster.setContactPoints("localhost");
        cluster.setPort(9142);
        return cluster;
    }

    @Bean
    public CassandraMappingContext mappingContext() throws ClassNotFoundException {
        CassandraMappingContext mappingContext = new CassandraMappingContext();
        mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cluster().getObject(), KEY_SPACE));
        mappingContext.setInitialEntitySet(CassandraEntityClassScanner.scan("idv.clu.sku.prototype.model"));
        return mappingContext;
    }

    @Bean
    public CassandraConverter converter() throws ClassNotFoundException {
        return new MappingCassandraConverter(mappingContext());
    }

    @Bean
    public CassandraSessionFactoryBean session() throws Exception {
        CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
        session.setCluster(cluster().getObject());
        session.setKeyspaceName(KEY_SPACE);
        session.setConverter(converter());
        session.setSchemaAction(SchemaAction.CREATE_IF_NOT_EXISTS);
        return session;

    }

    @Bean
    public CassandraTemplate cassandraTemplate() throws Exception {
        return new CassandraTemplate(session().getObject());
    }

    @Bean
    public CassandraAdminOperations cassandraAdminOperations() throws Exception {
        return new CassandraAdminTemplate(session().getObject(), converter());
    }

}
