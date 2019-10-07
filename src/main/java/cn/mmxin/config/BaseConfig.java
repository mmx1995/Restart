package cn.mmxin.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


import javax.sql.DataSource;

@Configuration
public class BaseConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource baseDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public SqlSessionFactory baseSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean  = new SqlSessionFactoryBean();
        bean.setDataSource(baseDataSource());
        bean.setConfigLocation(new ClassPathResource("BaseMyBatisConfig.xml"));
        return bean.getObject();
    }
}
