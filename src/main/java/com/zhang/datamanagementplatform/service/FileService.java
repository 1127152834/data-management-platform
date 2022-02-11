package com.zhang.datamanagementplatform.service;

import com.zhang.datamanagementplatform.entity.POJO.VO.ResultFileVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author ztc

 * @Date Created in 21:01 2018/11/16
 * @Description:
 */
public interface FileService {

    /**
     * 上传文章图片
     * @param token 消息摘要
     * @param file 文件
     * @return ResultVO
     */
    ResultFileVO uploadArticleImg(String userId, MultipartFile file);

}
