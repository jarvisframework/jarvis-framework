package com.jarvis.platform.excel.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 下载文件工具类
 *
 * @author hewm
 * @date 2022/8/12
 */
@Slf4j
public class DownloadUtil {


    /**
     * 设置头部
     *
     * @param fileName 文件名称
     * @param response 响应对象
     * @param request  请求对象
     * @param length   文件长度
     */
    public static void setHeader(
        String fileName,
        HttpServletResponse response,
        HttpServletRequest request,
        Long length
    ) {
        String agent = request.getHeader("USER-AGENT").toLowerCase();
        String codedFileName;
        try {
            codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            if (agent.contains("firefox")) {
                response.setCharacterEncoding("utf-8");
                response.setHeader("content-disposition", "attachment;filename="
                    + new String(fileName.getBytes(), "ISO8859-1"));
            } else {
                response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
            }
            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/octet-stream; charset=utf-8");
            response.addHeader(HttpHeaders.CONTENT_LENGTH, "" + length);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @param response 响应对象
     */
    public static void downloadFile(String filePath, HttpServletResponse response) {
        BufferedInputStream inputStream = FileUtil.getInputStream(filePath);
        downloadFile(inputStream, response);
    }

    /**
     * 下载文件
     *
     * @param inputStream 文件流
     * @param response    响应对象
     */
    public static void downloadFile(InputStream inputStream, HttpServletResponse response) {
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            IoUtil.copy(inputStream, outputStream);
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @param response 响应对象
     * @param request  请求对象
     */
    public static void downloadFile(String filePath, HttpServletResponse response, HttpServletRequest request) {
        File file = FileUtil.file(filePath);
        DownloadUtil.setHeader(file.getName(), response, request, file.length());
        DownloadUtil.downloadFile(filePath, response);
    }

    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @param response 响应对象
     * @param request  请求对象
     * @param fileName 文件名称
     */
    public static void downloadFile(
        String filePath,
        HttpServletResponse response,
        HttpServletRequest request,
        String fileName
    ) {
        File file = FileUtil.file(filePath);
        DownloadUtil.setHeader(fileName, response, request, file.length());
        DownloadUtil.downloadFile(filePath, response);
    }

    /**
     * 下载资源文件
     *
     * @param filePath 文件路径
     * @param response 响应对象
     * @param request  请求对象
     * @param fileName 文件名称
     */
    public static void downloadResource(
        String filePath,
        HttpServletResponse response,
        HttpServletRequest request,
        String fileName
    ) {
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        InputStream inputStream;
        try {
            inputStream = classPathResource.getInputStream();
            DownloadUtil.setHeader(fileName, response, request, classPathResource.contentLength());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DownloadUtil.downloadFile(inputStream, response);
    }

    @Slf4j
    public static class ExcelDownload {

        /**
         * 写入模板
         *
         * @param templateFilePath Excel模板文件路径
         * @param data             写入数据
         * @return 文件路径
         */
        public static String writeExcelTemplate(String templateFilePath, List<?> data) {
            log.debug("templateFilePath:{}", templateFilePath);
            ClassPathResource classPathResource = new ClassPathResource(templateFilePath);
            InputStream resourceAsStream;
            try {
                resourceAsStream = classPathResource.getInputStream();
            }catch (IOException e){
                throw new RuntimeException(e);
            }
            String fileName = System.getProperty("java.io.tmpdir")
                + File.separator + "temp_excel_" + System.currentTimeMillis() + ".xlsx";
            EasyExcel.write(fileName).withTemplate(resourceAsStream).sheet().doFill(data);
            return fileName;
        }

        /**
         * 写入模板
         *
         * @param templateFilePath Excel模板文件路径
         * @param data             写入数据
         * @param clazz            模板类
         * @return 文件路径
         */
        public static String writeExcelTemplate(String templateFilePath,
                                                List<?> data,
                                                Class<?> clazz
        ) {
            log.debug("templateFilePath:{}", templateFilePath);
            ClassPathResource classPathResource = new ClassPathResource(templateFilePath);
            InputStream resourceAsStream;
            try {
                resourceAsStream = classPathResource.getInputStream();
            }catch (IOException e){
                throw new RuntimeException(e);
            }
            log.debug("resourceAsStream:{}",resourceAsStream);
            String fileName = System.getProperty("java.io.tmpdir")
                + File.separator + "temp_excel_" + System.currentTimeMillis() + ".xlsx";
            EasyExcel.write(fileName).head(clazz).withTemplate(resourceAsStream).sheet().doFill(data);
            return fileName;
        }
    }
}
