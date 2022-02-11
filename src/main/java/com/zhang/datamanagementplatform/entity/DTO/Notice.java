package com.zhang.datamanagementplatform.entity.DTO;

import lombok.Data;

/**
 * @Author ztc
 * @Date Created in 21:22 2018/12/22
 * @Description:
 */
@Data
public class Notice {
    private String id;

    private String userId;

    private String title;

    private String content;

    private Boolean longTerm;

    private String createTime;

    private String seeNum;

    private String summary;

    public Notice() {
    }

    public Notice(String id, String userId, String title, String content,String summary, boolean longTerm) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.longTerm = longTerm;
    }
}
