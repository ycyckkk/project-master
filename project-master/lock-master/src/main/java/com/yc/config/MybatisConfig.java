package com.yc.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 *
 * @author yuche
 * @since 2020/4/18 12:11
 */
@Configuration
public class MybatisConfig {

    private Resource[] resolveMapperLocations(String[] mapperLocations) {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<Resource>();
        if (mapperLocations != null) {
            for (String mapperLocation : mapperLocations) {
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
    }


    @Bean("userDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource userDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("userSqlSessionFactory")
    public SqlSessionFactory userSqlSessionFactory(
            @Qualifier("userDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage("com.yc.entity");
        String[] mapperLocations = {"classpath:mapper/*.xml"};
        factory.setMapperLocations(resolveMapperLocations(mapperLocations));
        return factory.getObject();
    }

    @Bean("userSqlSessionTemplate")
    public SqlSessionTemplate userSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("userTransactionManager")
    public DataSourceTransactionManager userTransactionManager(@Qualifier("userDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @MapperScan(basePackages = "com.yc.mapper", sqlSessionTemplateRef = "userSqlSessionTemplate")
    private class userMapperScan {
    }
}
