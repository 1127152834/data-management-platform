package com.zhang.datamanagementplatform.entity.POJO.PO;

import com.zhang.datamanagementplatform.entity.DTO.User;
import lombok.Data;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Data
public class UserPO extends User {

    private String password;

    private String createTime;

    private String updateTime;
}
