package com.zhang.datamanagementplatform.entity.POJO.VO;

import com.zhang.datamanagementplatform.entity.DTO.Permission;
import com.zhang.datamanagementplatform.entity.DTO.Role;
import lombok.Data;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Data
public class RolePermissionVO {

    private String id;

    private Role role;

    private Permission permission;
}
