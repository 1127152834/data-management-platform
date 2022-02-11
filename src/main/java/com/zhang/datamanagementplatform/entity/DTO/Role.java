package com.zhang.datamanagementplatform.entity.DTO;

import lombok.Data;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Data
public class Role {

    private String id;

    private String roleName;

    private String createTime;

    public Role() {
    }

    public Role(String id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }
}
