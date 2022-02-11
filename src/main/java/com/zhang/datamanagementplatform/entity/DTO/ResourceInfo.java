package com.zhang.datamanagementplatform.entity.DTO;

import lombok.Data;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Data
public class ResourceInfo extends Resource {

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件大小
     * 单位KB
     */
    private Long fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件类型（全称）
     */
    private String fileFullType;

    /**
     * 下载次数
     */
    private Integer downloadNum;

    /**
     * 查看次数
     */
    private Integer seeNum;
}
