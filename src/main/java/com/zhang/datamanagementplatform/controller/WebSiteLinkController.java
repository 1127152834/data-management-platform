package com.zhang.datamanagementplatform.controller;

import com.zhang.datamanagementplatform.entity.POJO.PO.WebSiteLinkPO;
import com.zhang.datamanagementplatform.entity.POJO.VO.ResultVO;
import com.zhang.datamanagementplatform.service.WebSiteLinkService;
import com.zhang.datamanagementplatform.constants.*;
import com.zhang.datamanagementplatform.util.TokenUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


/**
 * @Author ztc
 * @Date Created in 14:58 2018/11/20
 * @Description:
 */
@RestController
public class WebSiteLinkController {

    @Autowired
    private WebSiteLinkService webSiteLinkService;

    /**
     * 更新链接
     * @param webSiteLink 链接对象
     * @return ResultVO
     */
    @PostMapping(CommonConstants.PUB_PREFIX + "/" + ApiConstants.LINK + "/update_link")
    public ResultVO updateLink(@RequestParam("id") String id,
                               @RequestParam("isView") Integer isView,
                               @RequestParam("linkType") Integer linkType,
                               @RequestParam("linkName") String linkName,
                               @RequestParam("linkAddress") String linkAddress,
                               @RequestParam(value = "Image",required = false) MultipartFile Image,
                               @RequestHeader(HttpParamKeyConstants.CLIENT_DIGEST) String token
                               ){
        WebSiteLinkPO webSiteLink = new WebSiteLinkPO();
        webSiteLink.setId(id);
        webSiteLink.setImage(Image);
        webSiteLink.setIsView(isView);
        webSiteLink.setLinkType(linkType);
        webSiteLink.setLinkAddress(linkAddress);
        webSiteLink.setLinkName(linkName);
        return webSiteLinkService.updateLinkById(webSiteLink,new TokenUtil().getUserKeyByToken(token));
    }

    /**
     * 上传链接
     * @param webSiteLink 链接对象
     * @return ResultVO
     */
    @PostMapping(CommonConstants.PUB_PREFIX + "/" + ApiConstants.LINK + "/add_link")
    public ResultVO addLink(@RequestParam("isView") Integer isView,
                            @RequestParam("linkType") Integer linkType,
                            @RequestParam("linkName") String linkName,
                            @RequestParam("linkAddress") String linkAddress,
                            @RequestParam(value = "Image",required = false) MultipartFile Image,
                            @RequestHeader(HttpParamKeyConstants.CLIENT_DIGEST) String token
    ){
        WebSiteLinkPO webSiteLink = new WebSiteLinkPO();
        webSiteLink.setId(UUID.randomUUID().toString().replace("-",""));
        webSiteLink.setImage(Image);
        webSiteLink.setIsView(isView);
        webSiteLink.setLinkType(linkType);
        webSiteLink.setLinkAddress(linkAddress);
        webSiteLink.setLinkName(linkName);
        return webSiteLinkService.insertLink(webSiteLink,new TokenUtil().getUserKeyByToken(token));
    }

    /**
     * 查询某个链接的具体内容
     * @param id 链接id
     * @return ResultVO
     */
    @GetMapping(CommonConstants.PUB_PREFIX + "/" + ApiConstants.LINK + "/get_link")
    public ResultVO getLink(@RequestParam("id") String id){
        return webSiteLinkService.getLink(id);
    }

    /**
     * 分页查询文章简介信息列表
     * @param page 当前页
     * @param pageSize 一页显示数量
     * @return ResultVO
     */
    @GetMapping(CommonConstants.PUB_PREFIX + "/" +ApiConstants.LINK + "/get_linklist")
    public ResultVO getLinkList(@RequestParam("page") int page,
                                   @RequestParam("page_size") int pageSize){
        System.out.println(webSiteLinkService.getLinkListByPaging(page,pageSize).getData());
        return webSiteLinkService.getLinkListByPaging(page,pageSize);
    }

    /**
     * 获取可见的链接
     * @return ResultVO
     */
    @GetMapping(CommonConstants.PUB_PREFIX + "/" + ApiConstants.LINK + "/view_link")
    public ResultVO get(){
        return webSiteLinkService.getViewLinkList();
    }

    /**
     * 修改链接的可见状态
     * @return ResultVO
     */
    @PutMapping(CommonConstants.PUB_PREFIX + "/" + ApiConstants.LINK + "/update_view")
    public ResultVO get(@RequestParam("id") String id,@RequestParam("isView") int isView){
        return webSiteLinkService.updateViewState(id,isView);
    }

    /**
     * 删除链接
     * @param id 链接id
     * @return ResultVO
     */
    @DeleteMapping(CommonConstants.PUB_PREFIX + "/" + ApiConstants.LINK + "/del_link")
    public ResultVO deleteLink(@RequestParam("id") String id){
        return webSiteLinkService.deleteLink(id);
    }
}
