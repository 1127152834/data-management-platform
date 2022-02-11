package com.zhang.datamanagementplatform.entity.POJO.PO;

import com.zhang.datamanagementplatform.entity.DTO.WebSiteLink;
import lombok.Data;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Data
public class WebSiteLinkPO extends WebSiteLink {

    /**
     * 是否网页可见
     */
    private Integer isView;
}
