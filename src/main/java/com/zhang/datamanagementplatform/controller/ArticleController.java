package com.zhang.datamanagementplatform.controller;

import com.zhang.datamanagementplatform.entity.DTO.Article;
import com.zhang.datamanagementplatform.entity.POJO.VO.ResultVO;
import com.zhang.datamanagementplatform.service.ArticleService;
import com.zhang.datamanagementplatform.constants.*;
import com.zhang.datamanagementplatform.util.TokenUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


/**
 * @Author ztc

 * @Date Created in 14:58 2018/11/20
 * @Description:
 */
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 上传文章
     * @param article 文章对象
     * @return ResultVO
     */
    @RequiresPermissions(ResourceConstants.ARTICLE + PermissionActionConstant.ADD)
    @PostMapping(CommonConstants.NONPUBLIC_PREFIX + "/" + ApiConstants.ARTICLE + "/upload")
    public ResultVO upload(@RequestBody Article article, @RequestHeader(HttpParamKeyConstants.CLIENT_DIGEST) String token){
        article.setId(UUID.randomUUID().toString().replace("-",""));
        return articleService.upload(article, new TokenUtil().getUserKeyByToken(token));
    }

    /**
     * 查询某篇文章具体内容
     * @param id 文章id
     * @return ResultVO
     */
    @GetMapping(CommonConstants.PUB_PREFIX + "/" + ApiConstants.ARTICLE + "/get")
    public ResultVO getArticle(@RequestParam("id") String id){
        return articleService.getById(id);
    }

    /**
     * 分页查询文章简介信息列表
     * @param page 当前页
     * @param pageSize 一页显示数量
     * @return ResultVO
     */
    @GetMapping(CommonConstants.PUB_PREFIX + "/" +ApiConstants.ARTICLE + "/get_list")
    public ResultVO getArticleList(@RequestParam("page") int page,
                                   @RequestParam("page_size") int pageSize){
        return articleService.getList(page,pageSize);
    }

    /**
     * 查询个人的文章列表
     * @param page 当前页
     * @param pageSize 一页显示的数量
     * @return ResultVO
     */
    @GetMapping(CommonConstants.NONPUBLIC_PREFIX + "/" + ApiConstants.ARTICLE + "/list_personal")
    public ResultVO getPersonalArticleList(@RequestParam("page") int page,
                                           @RequestParam("page_size") int pageSize,
                                           @RequestHeader(HttpParamKeyConstants.CLIENT_DIGEST) String token){
        return articleService.getPersonalArticleList(page,pageSize,new TokenUtil().getUserKeyByToken(token));
    }

    /**
     * 删除个人文章
     * @param id 文章id
     * @return ResultVO
     */
    @DeleteMapping(CommonConstants.NONPUBLIC_PREFIX + "/" + ApiConstants.ARTICLE + "/del_personal")
    public ResultVO deletePersonalArticle(@RequestParam("id") String id,
                                          @RequestHeader(HttpParamKeyConstants.CLIENT_DIGEST) String token){
        return articleService.delPersonalArticleById(id,new TokenUtil().getUserKeyByToken(token));
    }
}
