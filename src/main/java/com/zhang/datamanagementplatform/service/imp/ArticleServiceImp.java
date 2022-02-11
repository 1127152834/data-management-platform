package com.zhang.datamanagementplatform.service.imp;

import com.zhang.datamanagementplatform.entity.DTO.Article;
import com.zhang.datamanagementplatform.entity.POJO.PO.ArticlePO;
import com.zhang.datamanagementplatform.entity.POJO.VO.ArticleListVO;
import com.zhang.datamanagementplatform.entity.POJO.VO.ResultVO;
import com.zhang.datamanagementplatform.service.ArticleService;
import com.zhang.datamanagementplatform.dao.ArticleDao;
import com.zhang.datamanagementplatform.enums.ResultEnum;
import com.zhang.datamanagementplatform.util.ObjectTransformUtil;
import com.zhang.datamanagementplatform.util.RedisUtil;
import com.zhang.datamanagementplatform.util.ResultUtil;
import com.zhang.datamanagementplatform.util.StrUtil;
import com.zhang.datamanagementplatform.util.redis.RedisKeyManagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Service
public class ArticleServiceImp implements ArticleService {

    @Autowired
    private ResultUtil resultUtil;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private RedisUtil redisUtil;

    @Transactional
    @Override
    public ResultVO upload(Article article, String userId) {
        // 字符转义
        article.setTitle(StrUtil.strEncode(article.getTitle()));
        article.setSummary(StrUtil.strEncode(article.getSummary()));
        article.setContent(StrUtil.strEncode(article.getContent()));
        article.setStrTags(article.getTags().toString().replace("[","").replace("]",""));
        try{
            if (articleDao.insertArticle(article,userId)){
                return resultUtil.success(article.getId());
            }
        }catch (Exception e){
            e.printStackTrace();
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
        return resultUtil.error(ResultEnum.UNKNOWN_ERROR);
    }

    @Transactional
    @Override
    public ResultVO getById(String id) {
        try{
            // 从redis查询是否有该文章，如果有的话直接返回
            Map<Object,Object> articleMap = redisUtil.hmget(RedisKeyManagerUtil.getArticleKey(id));
            if(articleMap.isEmpty()){
                ArticlePO articlePO = articleDao.selectById(id);
                if (articlePO != null){
                    // 成功从数据库取得文章数据，将数据保存到redis，有效时间两分钟
                    redisUtil.hmset(RedisKeyManagerUtil.getArticleKey(id), (Map<String, Object>) ObjectTransformUtil.objectToMap(articlePO),120);
                    if(articleDao.updateSeeNum(id)){
                        return resultUtil.success(articlePO);
                    }
                }else {
                    return resultUtil.error(ResultEnum.RESOURCE_NOT_FOUND);
                }
            }else{
                return resultUtil.success(articleMap);
            }
        }catch (Exception e){
            e.printStackTrace();
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
        return resultUtil.error(ResultEnum.UNKNOWN_ERROR);
    }

    @Override
    public ResultVO getList(int page, int pageSize) {
        try{
            List<ArticleListVO> articleList = articleDao.selectArticleByPaging((page-1)*pageSize,pageSize);
            if (articleList!=null){
                int total = articleDao.countArticles();
                HashMap<String,Object> resMap = new HashMap<>();
                resMap.put("total",total);
                resMap.put("rows",articleList);
                return resultUtil.success(resMap);
            }
        }catch (Exception e){
            e.printStackTrace();
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
        return resultUtil.error(ResultEnum.UNKNOWN_ERROR);
    }

    @Override
    public ResultVO getPersonalArticleList(int page, int pageSize,String userId) {
        try{
            ArrayList<ArticlePO> articles = articleDao.selectArticleByUserId(userId);
            int total = articleDao.countArticlesByUserId(userId);
            HashMap<String,Object> resMap = new HashMap<>();
            resMap.put("total",total);
            resMap.put("rows",articles);
            return resultUtil.success(resMap);
        }catch (Exception e){
            e.printStackTrace();
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
    }

    @Override
    public ResultVO delPersonalArticleById(String id, String userId) {
        try{
            ArticlePO articlePO = articleDao.selectById(id);
            if (articlePO == null){
                return resultUtil.error(ResultEnum.RESOURCE_NOT_FOUND);
            }else if (!articlePO.getUserId().equals(userId)){
                return resultUtil.error(ResultEnum.ACTION_UNAUTHORIZED);
            }else{
                articleDao.deleteArticleById(id);
                return resultUtil.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
    }
}
