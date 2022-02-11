package com.zhang.datamanagementplatform.service;

import com.zhang.datamanagementplatform.entity.POJO.PO.WebSiteLinkPO;
import com.zhang.datamanagementplatform.entity.POJO.VO.ResultVO;

/**
 * @Author ztc

 * @Date Created in 19:22 2018/11/7
 * @Description: 用户业务逻辑层接口类
 */
public interface WebSiteLinkService {

    /***
     * 插入一个网址链接
     * @param link
     * @return
     */
    ResultVO insertLink(WebSiteLinkPO link, String userId);

    /***
     * 根据ID修改一条链接信息
     * @param link
     * @return
     */
    ResultVO updateLinkById(WebSiteLinkPO link,String userId);

    /***
     * 分页获取链接信息
     * @param page
     * @param pageSize
     * @return
     */
    ResultVO getLinkListByPaging(int page,int pageSize);

    /***
     * 获取指定的链接信息
     * @param id
     * @return
     */
    ResultVO getLink(String id);

    /***
     * 删除指定的链接
     * @param id
     * @return
     */
    ResultVO deleteLink(String id);

    /***
     * 更改链接的可见性
     * @param id
     * @param isView
     * @return
     */
    ResultVO updateViewState(String id,int isView);

    /***
     * 前台    展示所有可见的链接集合
     * @return
     */
    ResultVO getViewLinkList();

    /***
     * 获取链接数量
     * @return
     */
    ResultVO getCount();

    /***
     * 获取可见的链接数量
     * @return
     */
    ResultVO getViewCount();
}
