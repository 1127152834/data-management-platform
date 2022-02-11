package com.zhang.datamanagementplatform.entity.POJO.PO;

import com.zhang.datamanagementplatform.entity.DTO.ResourceInfo;
import lombok.Data;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Data
public class ResourcePO extends ResourceInfo {
    private String createTime;

    private String updateTime;

    private String userId;

    private boolean hasPass;
}
