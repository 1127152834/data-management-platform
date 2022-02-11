package com.zhang.datamanagementplatform.filter;

import com.zhang.datamanagementplatform.dao.ResourceDao;
import com.zhang.datamanagementplatform.enums.ResultEnum;
import com.zhang.datamanagementplatform.util.ResultUtil;
import com.zhang.datamanagementplatform.util.web.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/26
 * @Description:
 */
public class DownloadFileFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(DownloadFileFilter.class);

    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private ResultUtil resultUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String resourceId = request.getHeader("Download-File-Digest");
        if (resourceId == null || "".equals(resourceId.trim())){
            ResponseUtil.toJson((HttpServletResponse) servletResponse,resultUtil.error(ResultEnum.DOWNLOAD_DIGEST_NOT_FOUND));
        }else{
            if(resourceDao.updateDownloadNum(resourceId)){
                logger.info("下载资源，id={}",resourceId);
                filterChain.doFilter(servletRequest,servletResponse);
            }else{
                ResponseUtil.toJson((HttpServletResponse) servletResponse,resultUtil.error(ResultEnum.UNKNOWN_ERROR));
            }
        }
    }

    @Override
    public void destroy() {

    }
}
