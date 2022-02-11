package com.zhang.datamanagementplatform.config.druid;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * 配置本过滤器放行的请求后缀
 * @Author ztc
 * @Date Created in 19:04 2018/11/7
 * @Description:
 */
@WebFilter(
        filterName="druidWebStatFilter",
        urlPatterns= {"/*"},
        initParams= {
                @WebInitParam(name="exclusions",value="*.js,*.jpg,*.png,*.gif,*.ico,*.css,/druid/*")
        }
)
public class DruidStatFilter extends WebStatFilter {
}
