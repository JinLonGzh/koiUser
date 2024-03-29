package com.koi.system.strategy.context;

import com.koi.system.constants.UploadModeEnum;
import com.koi.system.strategy.UploadStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Map;

/**
 * 上传策略上下文
 *
 * @Author zjl
 * @Date 2024/1/21 17:56
 */
@Service
public class UploadStrategyContext {

    /**
     * 上传模式
     */
    @Value("${upload.mode}")
    private String uploadMode;

    @Resource
    private Map<String, UploadStrategy> uploadStrategyMap;

    /**
     * 执行上传策略
     *
     * @param file 文件
     * @param path 路径
     * @return {@link String} 文件地址
     */
    public String executeUploadStrategy(MultipartFile file, String path) {
        return uploadStrategyMap.get(UploadModeEnum.getStrategy(uploadMode)).uploadFile(file, path);
    }


    /**
     * 执行上传策略
     *
     * @param fileName    文件名称
     * @param inputStream 输入流
     * @param path        路径
     * @return {@link String} 文件地址
     */
    public String executeUploadStrategy(String fileName, InputStream inputStream, String path) {
        return uploadStrategyMap.get(UploadModeEnum.getStrategy(uploadMode)).uploadFile(fileName, inputStream, path);
    }

}
