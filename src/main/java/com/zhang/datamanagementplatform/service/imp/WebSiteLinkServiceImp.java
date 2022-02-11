package com.zhang.datamanagementplatform.service.imp;

import com.zhang.datamanagementplatform.entity.DTO.WebSiteLink;
import com.zhang.datamanagementplatform.entity.POJO.PO.WebSiteLinkPO;
import com.zhang.datamanagementplatform.entity.POJO.VO.ResultVO;
import com.zhang.datamanagementplatform.entity.POJO.VO.WebSiteLinkVO;
import com.zhang.datamanagementplatform.service.WebSiteLinkService;
import com.zhang.datamanagementplatform.dao.WebSiteLinkDao;
import com.zhang.datamanagementplatform.enums.ResultEnum;
import com.zhang.datamanagementplatform.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

/**
 * @Author ztc

 * @Date Created in 14:59 2018/11/20
 * @Description:
 */
@Service
public class WebSiteLinkServiceImp implements WebSiteLinkService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImp.class);

    @Value("${server.host}")
    private String host;

    @Autowired
    private ResultUtil resultUtil;

    @Autowired
    private WebSiteLinkDao webSiteLinkDao;
    @Autowired
    private FileUtil fileUtil;

    @Override
    public ResultVO insertLink(WebSiteLinkPO link, String userId) {
        try {
            MultipartFile img = link.getImage();
            // 检查文件和图片的类型
            if (!(fileUtil.checkImgType(img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf(".")+ 1)))){
                return resultUtil.error(ResultEnum.FILE_FORMAT_WRONG);
            }
            // 检查文件和图片的大小
            if(!(fileUtil.checkImgSize(img.getSize()))){
                return resultUtil.error(ResultEnum.RESOURCE_SIZE_TOO_BIG);
            }
            logger.info("上传资源图片：name={},type={},size={}",img.getOriginalFilename(),img.getContentType(),img.getSize()/(1024*1024)+"M");
            String imgName = fileUtil.saveFile(img,fileUtil.getArticleImgLocalPath(userId));
            String imgUrl = host + "/" + fileUtil.getArticleImgUrl(userId) + "/" + imgName;
            logger.info("保存资源图片：url={}",imgUrl);
            link.setLinkImage(imgUrl);
            webSiteLinkDao.insertLink(link);
            return resultUtil.success(link.getId());
        }catch (Exception e){
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
    }

    @Override
    public ResultVO updateLinkById(WebSiteLinkPO link,String userId) {
        try {
            MultipartFile img = link.getImage();
            // 检查文件和图片的类型
            if (!(fileUtil.checkImgType(img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf(".")+ 1)))){
                return resultUtil.error(ResultEnum.FILE_FORMAT_WRONG);
            }
            // 检查文件和图片的大小
            if(!(fileUtil.checkImgSize(img.getSize()))){
                return resultUtil.error(ResultEnum.RESOURCE_SIZE_TOO_BIG);
            }
            logger.info("上传资源图片：name={},type={},size={}",img.getOriginalFilename(),img.getContentType(),img.getSize()/(1024*1024)+"M");
            String imgName = fileUtil.saveFile(img,fileUtil.getArticleImgLocalPath(userId));
            String imgUrl = host + "/" + fileUtil.getArticleImgUrl(userId) + "/" + imgName;
            logger.info("保存资源图片：url={}",imgUrl);
            link.setLinkImage(imgUrl);
            webSiteLinkDao.updateLinkById(link);
            return resultUtil.success(link.getId());
        }catch (Exception e){
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
    }

    @Override
    public ResultVO getLinkListByPaging(int page, int pageSize) {
        try{
            List<WebSiteLink> webSiteList = webSiteLinkDao.getLinkListByPaging(page,pageSize);
            if (webSiteList!=null){
                int total = webSiteLinkDao.getCount();
                HashMap<String,Object> resMap = new HashMap<>();
                resMap.put("total",total);
                resMap.put("rows",webSiteList);
                return resultUtil.success(resMap);
            }
        }catch (Exception e){
            e.printStackTrace();
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
        return resultUtil.error(ResultEnum.UNKNOWN_ERROR);
    }

    @Override
    public ResultVO getLink(String id) {
        try {
            WebSiteLinkVO wv =  webSiteLinkDao.getLink(id);
            return resultUtil.success(wv);
        }catch (Exception e){
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
    }

    @Override
    public ResultVO deleteLink(String id) {
        try {
            webSiteLinkDao.deleteLink(id);
            return resultUtil.success(id);
        }catch (Exception e){
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
    }

    @Override
    public ResultVO updateViewState(String id, int isView) {
        try {
            webSiteLinkDao.updateViewState(id,isView);
            return resultUtil.success(id);
        }catch (Exception e){
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
    }

    @Override
    public ResultVO getViewLinkList() {
        try {
            List<WebSiteLinkPO> wps = webSiteLinkDao.getViewLinkList();
            return resultUtil.success(wps);
        }catch (Exception e){
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
    }

    @Override
    public ResultVO getCount() {
        try {
            Integer count = webSiteLinkDao.getCount();
            return resultUtil.success(count);
        }catch (Exception e){
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
    }

    @Override
    public ResultVO getViewCount() {
        try {
            Integer count = webSiteLinkDao.getViewCount();
            return resultUtil.success(count);
        }catch (Exception e){
            return resultUtil.error(ResultEnum.UNKNOWN_ERROR.getStatus(),e.getMessage());
        }
    }
}
