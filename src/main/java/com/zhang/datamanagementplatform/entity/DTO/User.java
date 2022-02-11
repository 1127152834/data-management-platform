package com.zhang.datamanagementplatform.entity.DTO;


import lombok.Data;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Data
public class User {
    private String id;

    private String account;

    private String realName;

    private String email;

    private String telephone;

    private String website;

    private Integer sex;

    private String role;

}
