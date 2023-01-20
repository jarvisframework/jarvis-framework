package com.jarvis.platform.excel.verify;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.jarvis.framework.core.exception.BusinessException;
import com.jarvis.platform.excel.verify.dto.ExcelData;
import com.jarvis.platform.excel.verify.dto.FailDataStoreResult;
import com.jarvis.platform.excel.verify.dto.VerifyResult;
import com.jarvis.platform.filestorage.sdk.FileStorageSdkService;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author hewm
 * @date 2022/9/26
 */
@Slf4j
public abstract class AbstractFailDataStoreImportVerify<T extends ExcelData> extends AbstractExcelImportVerify<T> {

    private final FileStorageSdkService fileStorageSdkService;

    private final String importType;

    private final String tempPath;

    private final String templatePath;

    private final String excelClass;

    /**
     * 错误数据储存
     * @param startLineNumber Excel开始下标
     * @param fileStorageSdkService 存储服务
     * @param importType 导入类型
     * @param importData 导入数据
     * @param templatePath 失败模板文件路径
     * @param excelClass 失败模板文件对应的Excel类
     */
    public AbstractFailDataStoreImportVerify(Long startLineNumber,
                                           FileStorageSdkService fileStorageSdkService,
                                           String importType,
                                           List<T> importData,
                                           String templatePath,
                                           String excelClass
    ) {
        super(startLineNumber, importData);
        this.fileStorageSdkService = fileStorageSdkService;
        this.importType = importType;
        this.tempPath = System.getProperty("java.io.tmpdir") + File.separator + "import-error" + File.separator + importType;
        log.info("导入时，临时写入目录:{}", tempPath);
        FileUtil.mkdir(this.tempPath);
        this.templatePath = templatePath;
        this.excelClass = excelClass;
    }


    @Override
    protected VerifyResult<T> buildImportResult(List<T> successList, List<T> failList) {
        FailDataStoreResult<T> result = new FailDataStoreResult<>();
        result.setSuccess(successList);
        result.setFail(failList);
        //1.生成文件名
        String fileName = IdUtil.getSnowflakeNextIdStr() + ".txt";

        //2. 生成临时文件路径
        String tempFilePath = tempPath + File.separator + fileName;

        //3. 数据写入临时文件
        if (failList.size() > 0) {
            log.info("本地临时文件:{}", tempFilePath);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFilePath))) {
                //写入excelClass和templatePath
                writer.write(excelClass);
                writer.newLine();
                writer.write(templatePath);
                writer.newLine();
                failList.stream().map(JSONUtil::toJsonStr).forEach(fail -> {
                    try {
                        writer.append(fail);
                        writer.newLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (IOException e) {
                throw new BusinessException("导入的失败数据写入临时文件发生异常", e);
            }
            //4. 上传到公共文件储存服务
            String subPath = importType + File.separator + fileName;
            String location = "import-error";
            try {
                fileStorageSdkService.makedir(location, importType);
                fileStorageSdkService.upload(location, subPath, tempFilePath);
                log.info("上传到公共存储路径:{}/{}", location, subPath);
            }finally {
                //6. 删除临时文件
                new File(tempFilePath).delete();
            }
            result.setLocation(location);
            result.setPath(subPath);
        }

        return result;
    }
}
