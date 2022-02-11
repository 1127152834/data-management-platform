package com.zhang.datamanagementplatform.service;

import com.zhang.datamanagementplatform.entity.POJO.VO.ResultVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 人脸比对
 */
@Service
public interface FaceCompareService {
    /**
     * 一对一比对
     */
    ResultVO oneToOne(String resource,String target) throws IOException;

    /**
     * 一对多比对
     */
    ResultVO oneToMore(String resource,String filesDir) throws IOException;
}
