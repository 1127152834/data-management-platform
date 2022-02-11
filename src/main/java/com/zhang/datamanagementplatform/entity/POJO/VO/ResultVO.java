package com.zhang.datamanagementplatform.entity.POJO.VO;

import lombok.Data;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/20
 * @Description:
 */
@Data
public class ResultVO {
    private int status;

    private String msg;

    private Object data;

    public ResultVO() {

    }

    public ResultVO(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ResultVO(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
