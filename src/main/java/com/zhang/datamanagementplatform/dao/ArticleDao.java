package com.zhang.datamanagementplatform.dao;

import com.zhang.datamanagementplatform.entity.DTO.Article;
import com.zhang.datamanagementplatform.entity.POJO.PO.ArticlePO;
import com.zhang.datamanagementplatform.entity.POJO.VO.ArticleListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ztc
 * @Date Created in 15:47 2018/11/20
 * @Description:
 */
@Mapper
public interface ArticleDao {
    /**
     * 插入文章
     * @param article 文章
     * @param userId 作者id
     * @return boolean
     */
    boolean insertArticle(@Param("article") Article article,
                          @Param("userId") String userId);

    /**
     * 更新浏览量（+1）
     * @param id 文章id
     * @return boolean
     */
    boolean updateSeeNum(@Param("id") String id);

    /**
     * 通过id查询文章
     * @param id 文章id
     * @return ArticlePO
     */
    ArticlePO selectById(@Param("id") String id);

    /**
     * 分页查询文章列表
     * @param page 开始索引
     * @param pageSize 查询量
     * @return ArticleListVO
     */
    List<ArticleListVO> selectArticleByPaging(@Param("page") int page, @Param("pageSize") int pageSize);

    /**
     * 统计文章总数(只包含通过的)
     * @return int
     */
    int countArticles();

    /**
     * 通过用户id查询文章
     * @param userId 用户id
     * @return ArrayList<Article>
     */
    ArrayList<ArticlePO> selectArticleByUserId(@Param("userId") String userId);

    /**
     * 通过userId统计文章总数
     * @param userId
     * @return
     */
    int countArticlesByUserId(@Param("userId") String userId);

    /**
     * 通过文章id删除
     * @param id 文章id
     */
    void deleteArticleById(@Param("id") String id);
}
