package com.zhang.datamanagementplatform.entity.POJO.PO;

import com.zhang.datamanagementplatform.entity.DTO.Article;
import lombok.Data;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Data
public class ArticlePO extends Article {

    private String createTime;

    private String updateTime;

    private String userId;

    private Integer seeNum;

    private Boolean hasPass;

}
