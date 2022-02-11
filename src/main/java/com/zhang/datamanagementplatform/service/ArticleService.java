package com.zhang.datamanagementplatform.service;

import com.zhang.datamanagementplatform.entity.DTO.Article;
import com.zhang.datamanagementplatform.entity.POJO.VO.ResultVO;

/**
 * @Author ztc

 * @Date Created in 14:59 2018/11/20
 * @Description:
 */
public interface ArticleService {

    /**
     * 上传文章
     * @param article 文章对象
     * @return ResultVO
     */
    ResultVO upload(Article article, String userId);

    /**
     * 查询某篇文章具体内容
     * @param id 文章id
     * @return ResultVO
     */
    ResultVO getById(String id);

    /**
     * 分页查询文章简介信息列表
     * @param page 当前页
     * @param pageSize 一页显示数量
     * @return ResultVO
     */
    ResultVO getList(int page, int pageSize);

    /**
     * 查询个人的文章列表
     * @param page 当前页
     * @param pageSize 一页显示的数量
     * @return ResultVO
     */
    ResultVO getPersonalArticleList(int page, int pageSize,String userId);

    /**
     * 删除个人文章
     * @param id 文章id
     * @param userId 用户id
     * @return  ResultVO
     */
    ResultVO delPersonalArticleById(String id, String userId);

}
