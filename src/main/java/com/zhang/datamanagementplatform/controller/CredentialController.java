package com.zhang.datamanagementplatform.controller;

import com.zhang.datamanagementplatform.constants.ApiConstants;
import com.zhang.datamanagementplatform.constants.CommonConstants;
import com.zhang.datamanagementplatform.entity.POJO.VO.ResultVO;
import com.zhang.datamanagementplatform.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author ztc
 * @Date Created in 12:26 2018/11/8
 * @Description:
 */
@RestController
public class CredentialController {
    @Autowired
    private CredentialService credentialService;

    /**
     * 获取验证码
     * @param request 请求
     * @param response 响应
     */
    @GetMapping(CommonConstants.PUB_PREFIX+"/"+ ApiConstants.CREDENTIAL+"/get_verify_code")
    public void getVerifyCode( HttpServletRequest request, HttpServletResponse response){
        credentialService.createCredentialImg(request,response);
    }

    /**
     * 验证验证码
     * @param key 键
     * @param value 值
     * @return ResultVO
     */
    @GetMapping(CommonConstants.PUB_PREFIX+"/"+ApiConstants.CREDENTIAL+"/check_verify_code")
    public ResultVO checkVerifyCode(@RequestParam("key") String key,
                                    @RequestParam("value") String value){
        return credentialService.checkVerifyCode(key,value);
    }
}
