package com.zhang.datamanagementplatform.entity.DTO;


/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 网站链接
 * @author 张天成PC
 */
@Data
public class WebSiteLink {
    /**
     * 主键
     */
    private String id;
    /**
     * 链接名称
     */
    private String linkName;
    /**
     * 链接地址
     */
    private String linkAddress;
    /**
     * 展示图片
     */
    private String linkImage;
    /**
     * 展示图片
     */
    private MultipartFile Image;
    /**
     * 链接类型
     */
    private Integer linkType;
}
