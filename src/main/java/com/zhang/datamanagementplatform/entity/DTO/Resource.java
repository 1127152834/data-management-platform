package com.zhang.datamanagementplatform.entity.DTO;


import lombok.Data;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Data
public class Resource {
    private String id;
    /**
     * 文件资源路径
     */
    private String  fileUrl;
    /**
     * 资源图片路径
     */
    private String imgUrl;

    private String title;

    private String summary;

    private String introduction;

    private String type;

    private Integer seeNum;

    private Integer downloadNum;
}
