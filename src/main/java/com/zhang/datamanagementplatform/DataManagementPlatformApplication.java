package com.zhang.datamanagementplatform;

import com.zhang.datamanagementplatform.zhang.TianchengShow;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ServletComponentScan
@EnableCaching
@MapperScan("com.zhang.datamanagementplatform.dao") //扫描mybatis的mapper
@EnableTransactionManagement
public class DataManagementPlatformApplication {
// extends SpringBootServletInitializer

//    /**
//     * 修改构建方法
//     * @param builder
//     * @return
//     */
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(DataManagementPlatformApplication.class);
//    }

    public static void main(String[] args) {
        SpringApplication.run(DataManagementPlatformApplication.class, args);
        TianchengShow.printLogo();
    }
}
