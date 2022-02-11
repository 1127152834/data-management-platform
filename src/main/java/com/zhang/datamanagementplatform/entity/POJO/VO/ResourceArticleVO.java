package com.zhang.datamanagementplatform.entity.POJO.VO;

import com.zhang.datamanagementplatform.entity.DTO.User;
import com.zhang.datamanagementplatform.entity.POJO.PO.ResourcePO;
import lombok.Data;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Data
public class ResourceArticleVO {
    private ResourcePO resource;

    private User author;
}
