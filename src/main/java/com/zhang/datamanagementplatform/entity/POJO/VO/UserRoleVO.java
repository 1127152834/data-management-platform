package com.zhang.datamanagementplatform.entity.POJO.VO;

import com.zhang.datamanagementplatform.entity.DTO.Role;
import com.zhang.datamanagementplatform.entity.DTO.User;
import lombok.Data;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Data
public class UserRoleVO {
    private String id;

    private User user;

    private Role role;
}
