package com.zhang.datamanagementplatform.dao;

import com.zhang.datamanagementplatform.entity.DTO.WebSiteLink;
import com.zhang.datamanagementplatform.entity.POJO.PO.WebSiteLinkPO;
import com.zhang.datamanagementplatform.entity.POJO.VO.WebSiteLinkVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ztc
 * @Date Created in 19:23 2018/11/7
 * @Description:
 */
@Mapper
public interface WebSiteLinkDao {
    //添加一个链接
    Boolean insertLink(@Param("link") WebSiteLinkPO link);
    //根据ID修改一条链接信息
    Boolean updateLinkById(@Param("link")WebSiteLinkPO link);
    //分页获取链接信息
    List<WebSiteLink> getLinkListByPaging(@Param("page") int page, @Param("pageSize") int pageSize);
    //获取指定的链接信息
    WebSiteLinkVO getLink(@Param("id")String id);
    //删除指定的链接
    boolean deleteLink(@Param("id")String id);
    //更改链接的可见性
    boolean updateViewState(@Param("id")String id,@Param("isView")int isView);
    //获取链接数量
    Integer getCount();
    //获取可见的链接数量
    Integer getViewCount();

    //前台    展示所有可见的链接集合
    List<WebSiteLinkPO> getViewLinkList();
}
