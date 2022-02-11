package com.zhang.datamanagementplatform.entity.POJO.VO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Data
public class ResourceVO {
    private String id;

    private MultipartFile file;

    private MultipartFile img;

    private String title;

    private String summary;

    private String introduction;

    private String type;

    public ResourceVO(){}

    public ResourceVO(String id,MultipartFile file, MultipartFile img, String title, String summary, String introduction, String type) {
        this.id = id;
        this.file = file;
        this.img = img;
        this.title = title;
        this.summary = summary;
        this.introduction = introduction;
        this.type = type;
    }
}
