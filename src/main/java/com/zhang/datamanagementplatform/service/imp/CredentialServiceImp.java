package com.zhang.datamanagementplatform.service.imp;

import com.zhang.datamanagementplatform.entity.POJO.VO.ResultVO;
import com.zhang.datamanagementplatform.service.CredentialService;
import com.zhang.datamanagementplatform.enums.ResultEnum;
import com.zhang.datamanagementplatform.util.RedisUtil;
import com.zhang.datamanagementplatform.util.ResultUtil;
import com.zhang.datamanagementplatform.util.VerifyCodeUtil;
import com.zhang.datamanagementplatform.util.redis.RedisKeyManagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author ztc
 * @Date Created in 15:02 2018/11/18
 * @Description:
 */
@Service
public class CredentialServiceImp implements CredentialService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ResultUtil resultUtil;

    @Override
    public void createCredentialImg(HttpServletRequest request, HttpServletResponse response) {
        VerifyCodeUtil verifyCodeUtil = new VerifyCodeUtil();
        try {
            String verifyCodeKey = UUID.randomUUID().toString().replace("-","");
            // 将四位数字的验证码保存到Redis中。
            redisUtil.set(RedisKeyManagerUtil.getVerifyCodeKey(verifyCodeKey),verifyCodeUtil.createCredentialImg(response,verifyCodeKey),60);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultVO checkVerifyCode(String key, String value) {
        String redisValue = (String) redisUtil.get(RedisKeyManagerUtil.getVerifyCodeKey(key));
        if (redisValue == null){
            return resultUtil.error(ResultEnum.VERIFY_CODE_INVALID);
        }else if(value.toLowerCase().equals(redisValue.toLowerCase())){
            redisUtil.del(RedisKeyManagerUtil.getVerifyCodeKey(key));
            return resultUtil.success();
        }
        return resultUtil.error(ResultEnum.VERIFY_CODE_ERROR);
    }
}
