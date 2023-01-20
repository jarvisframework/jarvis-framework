package com.jarvis.platform.app.util;

import cn.hutool.core.io.FileUtil;
import com.alibaba.excel.EasyExcel;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 下载文件工具类
 *
 * @author ronghui
 * @date 2022/8/18
 */
public class DownloadUtil {

    private static final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    /**
     * 获取resources下文件
     *
     * @param path 路径
     * @return Resource
     */
    public static Resource getResourcesFile(String path) {
        return resolver.getResource(ResourceUtils.CLASSPATH_URL_PREFIX + path);
    }

    /**
     * 下载resources文件
     * @param path 路径
     * @return 文件对象
     * @throws IOException 抛出异常
     */
    public static ResponseEntity downloadResources(String path) throws IOException {
        return downloadResources(getResourcesFile(path));
    }

    /**
     * 下载resources文件
     * @param path
     * @param fileName
     * @return
     * @throws IOException
     */
    public static ResponseEntity downloadResources(String path, String fileName) throws IOException {
        return downloadResources(getResourcesFile(path), fileName);
    }

    /**
     * 下载文件
     * @param file 文件
     * @return 响应对象
     * @throws IOException 抛出异常
     */
    public static ResponseEntity downloadResources(Resource file) throws IOException {
        return downloadResources(file, file.getFilename());
    }

    /**
     * 下载文件
     * @param file
     * @param fileName
     * @return
     * @throws IOException
     */
    public static ResponseEntity downloadResources(Resource file, String fileName) throws IOException {
        return download(new InputStreamResource(file.getInputStream()), fileName);
    }

    /**
     * 下载文件
     * @param file
     * @return
     * @throws IOException
     */
    public static ResponseEntity download(File file) throws IOException {
        return download(file, file.getName());
    }

    /**
     * 下载文件
     * @param file
     * @param fileName
     * @return
     * @throws IOException
     */
    public static ResponseEntity download(File file, String fileName) throws IOException {
        return download(new FileSystemResource(file), fileName);
    }

    /**
     * 下载文件
     * @param path
     * @return
     * @throws IOException
     */
    public static ResponseEntity download(String path) throws IOException {
        return download(path, FileUtil.getName(path));
    }

    /**
     * 下载文件
     * @param path
     * @param fileName
     * @return
     * @throws IOException
     */
    public static ResponseEntity download(String path, String fileName) throws IOException {
        return download(new FileSystemResource(path), fileName);
    }

    /**
     * 下载文件
     * @param is
     * @param fileName
     * @return
     * @throws IOException
     */
    public static ResponseEntity download(InputStream is, String fileName) throws IOException {
        return download(new InputStreamResource(is), fileName);
    }

    /**
     * 下载文件
     * @param body
     * @param fileName
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> ResponseEntity download(T body, String fileName) throws IOException {
        return ResponseEntity.ok()
                .header("Content-disposition",
                        String.format("attachment;filename=%s",
                                URLEncoder.encode(fileName, "UTF-8")))
                .contentType(MediaTypeFactory.getMediaType(fileName)
                        .orElse(MediaType.APPLICATION_OCTET_STREAM))
                .cacheControl(CacheControl.noCache().mustRevalidate())
                .body(body);
    }

    /**
     * 配置response
     * @param fileName 文件名
     * @return response
     * @throws UnsupportedEncodingException
     */
    public static HttpServletResponse settingResponse(String fileName) throws UnsupportedEncodingException {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attr.getResponse();
        response.setContentType(MediaTypeFactory.getMediaType(fileName)
                .orElse(MediaType.APPLICATION_OCTET_STREAM).toString());
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition",
                String.format("attachment;filename=%s", URLEncoder.encode(fileName, "UTF-8")));
        return response;
    }
    /**
     * 导出EXCEL
     * @param fileName 保存时的文件名
     * @param data excel数据
     */
    public static void exportExcel(String fileName, List data) {
        try {
            HttpServletResponse response = settingResponse(fileName);

            EasyExcel.write(response.getOutputStream()).sheet().head(data.get(0).getClass()).doWrite(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 导出EXCEL
     * @param templateFilePath 模板路径
     * @param fileName 保存时的文件名
     * @param data excel数据
     */
    public static void exportTemplateExcel(String templateFilePath, String fileName, List data) {
        try {
            Resource templateFile = getResourcesFile(templateFilePath);
            HttpServletResponse response = settingResponse(fileName);

            EasyExcel.write(response.getOutputStream()).withTemplate(templateFile.getInputStream())
                    .sheet().head(data.get(0).getClass()).doFill(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 导出EXCEL
     * @param templateFilePath 模板路径
     * @param data excel数据
     */
    public static void exportTemplateExcel(String templateFilePath, List data) {
        exportTemplateExcel(templateFilePath, FileUtil.getName(templateFilePath), data);
    }

    /**
     * 导出XML
     * @param fileName 保存时的文件名
     * @param document XML数据
     */
    public static void exportXml(String fileName, Document document) {
        try {
            HttpServletResponse response = settingResponse(fileName);

            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(response.getOutputStream(), format);
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
