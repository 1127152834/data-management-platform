package com.zhang.datamanagementplatform.entity.DTO;


import lombok.Data;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Data
public class Permission {
    private String id;

    /**
     * 资源
     */
    private String resource;

    /**
     * 操作
     */
    private String action;
}
