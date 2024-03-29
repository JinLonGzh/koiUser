package com.koi.system.strategy.impl;

import com.koi.common.exception.ServiceException;
import com.koi.common.utils.file.FileUtils;
import com.koi.system.strategy.UploadStrategy;
import com.qiniu.common.QiniuException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.UPLOAD_ERROR;

/**
 * -
 *
 * @Author zjl
 * @Date 2024/1/21 18:01
 */
@Service
@Slf4j
public abstract class AbstractUploadStrategyImpl implements UploadStrategy {

    @Override
    public String uploadFile(MultipartFile file, String path) {
        try {
            // 获取文件md5值
            String md5 = FileUtils.getMd5(file.getInputStream());
            // 获取文件扩展名
            String extName = FileUtils.getExtName(file.getOriginalFilename());
            // 重新生成文件名
            String fileName = md5 + extName;
            // 返回的文件路径
            String responseFilePath = "";
            // 判断文件是否已存在
            if (!exists(path + fileName)) {
                // 不存在则继续上传
                responseFilePath = upload(path, fileName, file.getInputStream());
            }
            // 返回文件访问路径
            if (responseFilePath.isEmpty()) {
                return getFileAccessUrl(path + fileName);
            } else {
                return getFileAccessUrl(responseFilePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(UPLOAD_ERROR);
        }
    }

    @Override
    public String uploadFile(String fileName, InputStream inputStream, String path) {
        try {
            // 上传文件
            upload(path, fileName, inputStream);
            // 返回文件访问路径
            return getFileAccessUrl(path + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(UPLOAD_ERROR);
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return {@link Boolean}
     */
    public abstract Boolean exists(String filePath) throws QiniuException;

    /**
     * 上传
     *
     * @param path        路径
     * @param fileName    文件名
     * @param inputStream 输入流
     * @return {@link String} filePath
     * @throws IOException io异常
     */
    public abstract String upload(String path, String fileName, InputStream inputStream) throws IOException;

    /**
     * 获取文件访问url
     *
     * @param filePath 文件路径
     * @return {@link String}
     */
    public abstract String getFileAccessUrl(String filePath);

}
