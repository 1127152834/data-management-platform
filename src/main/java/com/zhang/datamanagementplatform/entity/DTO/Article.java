package com.zhang.datamanagementplatform.entity.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Data
public class Article {

    private String id;

    private String title;

    private String summary;

    private String content;

    private List<String> tags;

    private String strTags;
}
