package com.zhang.datamanagementplatform.util;

import com.zhang.datamanagementplatform.entity.POJO.VO.ResultVO;
import com.zhang.datamanagementplatform.enums.ResultEnum;
import org.springframework.stereotype.Component;

/**
 * @Author ztc

 * @Date Created in 18:37 2018/10/30
 * @Description:
 */
@Component
public class ResultUtil {

    public ResultVO success(Object object){
        return new ResultVO(ResultEnum.SUCCESS.getStatus(),ResultEnum.SUCCESS.getMsg(),object);
    }

    public ResultVO success(){
        return success(null);
    }

    public ResultVO error(Integer status,String msg){
        return new ResultVO(status,msg);
    }

    public ResultVO error(ResultEnum resultEnum){
        return error(resultEnum.getStatus(),resultEnum.getMsg());
    }
}
