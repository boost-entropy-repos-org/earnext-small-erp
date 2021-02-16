package top.dongxibao.erp.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import top.dongxibao.erp.plugin.AutoIdInterceptor;

/**
 * @ClassName MybatisConfig
 * @description Mybatis配置类
 * @author Dongxibao
 * @date 2020-11-27
 * @Version 1.0
 */
@EnableTransactionManagement
@Configuration
@MapperScan({"top.dongxibao.erp.mapper"})
public class MybatisConfig {

    @Bean
    public PlatformTransactionManager transactionManager(HikariDataSource hikariDataSource) {
        return new DataSourceTransactionManager(hikariDataSource);
    }

    /**
     * 插件实体
     */
    @Bean
    AutoIdInterceptor autoIdInterceptor() {
        return new AutoIdInterceptor();
    }

    /**
     * SqlSessionFactory 实体
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(HikariDataSource hikariDataSource) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(hikariDataSource);
        sessionFactory.setFailFast(true);
        sessionFactory.setMapperLocations(resolver.getResources("classpath:/mapper/**/*.xml"));
        /**
         * 添加插件信息(因为插件采用责任链模式所有可以有多个，所以采用数组)
         */
        Interceptor[] interceptors = new Interceptor[1];
        interceptors[0] = autoIdInterceptor();
        sessionFactory.setPlugins(interceptors);
        return sessionFactory.getObject();
    }
}