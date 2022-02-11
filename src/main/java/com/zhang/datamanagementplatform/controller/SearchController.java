package com.zhang.datamanagementplatform.controller;

import com.zhang.datamanagementplatform.entity.POJO.VO.ResultVO;
import com.zhang.datamanagementplatform.service.SearchService;
import com.zhang.datamanagementplatform.constants.ApiConstants;
import com.zhang.datamanagementplatform.constants.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ztc
 * @Date Created in 0:35 2018/12/25
 * @Description:
 */
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 获取公共搜索结果
     * @return ResultVO
     */
    @GetMapping(CommonConstants.PUB_PREFIX + "/" + ApiConstants.SEARCH + "/public_search")
    public ResultVO publicSearch(@RequestParam("keyword") String keyword){
        return searchService.publicSearch(keyword);
    }
}
