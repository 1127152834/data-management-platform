package com.zhang.datamanagementplatform.service.imp;

import com.zhang.datamanagementplatform.entity.DTO.Notice;
import com.zhang.datamanagementplatform.entity.POJO.PO.ArticlePO;
import com.zhang.datamanagementplatform.entity.POJO.PO.ResourcePO;
import com.zhang.datamanagementplatform.entity.POJO.PO.WebSiteLinkPO;
import com.zhang.datamanagementplatform.entity.POJO.VO.ResultVO;
import com.zhang.datamanagementplatform.service.SearchService;
import com.zhang.datamanagementplatform.dao.SearchDao;
import com.zhang.datamanagementplatform.enums.ResultEnum;
import com.zhang.datamanagementplatform.util.RedisUtil;
import com.zhang.datamanagementplatform.util.ResultUtil;
import com.zhang.datamanagementplatform.util.redis.RedisKeyManagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Author ztc

 * @Date Created in 0:36 2018/12/25
 * @Description:
 */
@Service
public class SearchServiceImp implements SearchService {

    private static final long SEARCH_RESULT_CACHE_TIME = 2 * 60;    //搜索结果缓存保存2分钟

    private static final Logger logger = LoggerFactory.getLogger(SearchServiceImp.class);

    @Autowired
    private SearchDao searchDao;
    @Autowired
    private ResultUtil resultUtil;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResultVO publicSearch(String keyword) {
        try{
            if ("".equals(keyword.trim())){
                return resultUtil.error(ResultEnum.PARAM_ERROR.getStatus(),"搜索字符不能为空");
            }else{
                HashMap<Object,Object> cacheResult = (HashMap<Object, Object>) redisUtil.hmget(RedisKeyManagerUtil.getSearchKey(keyword));
                if (!"success".equals(cacheResult.get("status"))){  // 如果map中的status不等于success，则表示缓存数据中没有搜索结果
                    ArrayList<Notice> notices = searchDao.selectNotice("%"+keyword.trim()+"%");
                    ArrayList<ResourcePO> resources = searchDao.selectResource("%"+keyword.trim()+"%");
                    ArrayList<ArticlePO> articles = searchDao.selectArticles("%"+keyword.trim()+"%");
                    ArrayList<WebSiteLinkPO> links = searchDao.selectLinks("%"+keyword.trim()+"%");
                    HashMap<String,Object> resMap = new HashMap<>();
                    resMap.put("resources",resources);
                    resMap.put("articles",articles);
                    resMap.put("notices",notices);
                    resMap.put("links",links);
                    resMap.put("status","success"); // 用于鉴别查询状态
                    redisUtil.hmset(RedisKeyManagerUtil.getSearchKey(keyword),resMap,SEARCH_RESULT_CACHE_TIME);
                    logger.info("全局搜索，从数据库读取，keyword={}",keyword);
                    return resultUtil.success(resMap);
                }else{
                    logger.info("全局搜索，从缓存读取，keyword={}",keyword);
                    return resultUtil.success(cacheResult);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
    }
}
