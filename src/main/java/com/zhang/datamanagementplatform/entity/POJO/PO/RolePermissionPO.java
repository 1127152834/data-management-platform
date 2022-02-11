package com.zhang.datamanagementplatform.entity.POJO.PO;

import lombok.Data;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Data
public class RolePermissionPO {
    private String id;

    private String roleId;

    private String permissionId;

    public RolePermissionPO(){

    }

    public RolePermissionPO(String id, String roleId, String permissionId) {
        this.id = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
    }
}
