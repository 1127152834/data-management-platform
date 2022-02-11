package com.zhang.datamanagementplatform.dao;

import com.zhang.datamanagementplatform.entity.DTO.Notice;
import com.zhang.datamanagementplatform.entity.POJO.PO.ArticlePO;
import com.zhang.datamanagementplatform.entity.POJO.PO.ResourcePO;
import com.zhang.datamanagementplatform.entity.POJO.PO.WebSiteLinkPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * @Author ztc
 * @Date Created in 0:36 2018/12/25
 * @Description:
 */
@Mapper
public interface SearchDao {
    /**
     * 模糊搜索公告
     * @param keyword 关键字
     * @return ArrayList<Notice>
     */
    ArrayList<Notice> selectNotice(@Param("keyword") String keyword);

    /**
     * 模糊搜索资源
     * @param keyword 关键字
     * @return ArrayList<ResourcePO>
     */
    ArrayList<ResourcePO> selectResource(@Param("keyword") String keyword);

    /**
     * 模糊搜索文章
     * @param keyword 关键字
     * @return ArrayList<ArticlePO>
     */
    ArrayList<ArticlePO> selectArticles(@Param("keyword") String keyword);

    /**
     * 模糊搜索外链
     * @param keyword 关键字
     * @return ArrayList<ArticlePO>
     */
    ArrayList<WebSiteLinkPO> selectLinks(@Param("keyword") String keyword);
}
