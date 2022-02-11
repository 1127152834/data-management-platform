package com.zhang.datamanagementplatform.config.web;

import com.zhang.datamanagementplatform.constants.CommonConstants;
import com.zhang.datamanagementplatform.filter.DosFilter;
import com.zhang.datamanagementplatform.filter.DownloadFileFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ztc
 * @Date Created in 17:40 2018/11/9
 * @Description:
 */
@Configuration
public class FilterRegistrationConfiguration {
    @Bean
    public FilterRegistrationBean dosFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(dosFilter());
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/"+CommonConstants.API_VERSION+"/*");
        registrationBean.setName("dosFilter");
        registrationBean.setOrder(-1000000000);
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }
    @Bean
    public DosFilter dosFilter(){
        return new DosFilter();
    }

    @Bean
    public FilterRegistrationBean downloadFileFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(downloadFileFilter());
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/"+CommonConstants.DOWNLOAD_PREFIX + "/*");
        registrationBean.setName("downloadFileFilter");
        registrationBean.setOrder(1);
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }
    @Bean
    public DownloadFileFilter downloadFileFilter(){
        return new DownloadFileFilter();
    }
}
