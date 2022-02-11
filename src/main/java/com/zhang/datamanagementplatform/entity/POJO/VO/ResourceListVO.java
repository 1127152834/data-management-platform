package com.zhang.datamanagementplatform.entity.POJO.VO;

import com.zhang.datamanagementplatform.entity.DTO.ResourceInfo;
import com.zhang.datamanagementplatform.entity.DTO.User;
import lombok.Data;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Data
public class ResourceListVO {

    private User author;

    private ResourceInfo resource;
}
