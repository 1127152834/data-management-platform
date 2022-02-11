package com.zhang.datamanagementplatform.entity.POJO.PO;

import com.zhang.datamanagementplatform.entity.DTO.Role;
import lombok.Data;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Data
public class RolePO extends Role {

    public RolePO(){
        super();
    }

    public RolePO(String id, String roleName) {
        super(id, roleName);
    }
}
