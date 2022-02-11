package com.zhang.datamanagementplatform.constants;

/**
 * API前缀的前缀
 * @Author ztc
 * @Date Created in 10:08 2018/9/22
 * @Description:
 */
public interface CommonConstants {
    /**
     * api版本号
     */
    String API_VERSION = "v1";

    /**
     * 非公共api的前缀
     */
    String NONPUBLIC_PREFIX = API_VERSION + "/nonpub";

    /**
     * 公共api前缀
     */
    String PUB_PREFIX = API_VERSION + "/pub";

    /**
     * 文件下载api前缀
     */
    String DOWNLOAD_PREFIX = NONPUBLIC_PREFIX + "/download";
}
