package com.zhang.datamanagementplatform.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @Primary 标记配置，优先实现
 * @Author ztc
 * @Date Created in 19:01 2018/11/7
 * @Description:
 */
@Configuration
@Primary
public class DruidConfiguration {
    /**
     * 此处最好设置bean名，如果是单数据源倒无所谓，但是如果是多数据源很有可能发生冲突，特别是spring内使用的dbcp容易与其发生冲突。
     * @return
     */
    @Bean(name = "druidDataSource")
    /**
     * 配置读取哪一部分的配置
     */
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource druidConfiguration(){
        return new DruidDataSource();
    }
}
