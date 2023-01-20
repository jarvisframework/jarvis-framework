package com.jarvis.platform.excel.verify;

import com.jarvis.framework.core.code.CodeEnum;
import com.jarvis.framework.util.CodeEnumUtil;
import com.jarvis.platform.excel.verify.annotation.ExcelVerify;
import com.jarvis.platform.excel.verify.dto.ExcelData;
import com.jarvis.platform.excel.verify.dto.VerifyResult;
import com.jarvis.platform.filestorage.sdk.FileStorageSdkService;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;

/**
 * @author hewm
 * @date 2022/9/27
 */
public final class VerifyUtil {

    /**
     * 创建常用构建类
     *
     * @return builder
     */
    public static VerifyBuilder createBuilder() {
        return new VerifyBuilder();
    }

    @Accessors(chain = true)
    @Setter
    public static class VerifyBuilder {

        private Long startLineNumber;

        private FileStorageSdkService fileStorageSdkService;

        private String importType;

        private String templatePath;

        private String excelClass;

        /**
         * 构建校验类
         *
         * @param verifyData 校验数据
         * @param <T>        继承ExcelData接口的实体类
         * @return CommonVerify
         */
        public <T extends ExcelData> CommonVerify<T> buildVerify(List<T> verifyData) {
            return new CommonVerify<>(
                startLineNumber,
                fileStorageSdkService,
                importType,
                verifyData,
                templatePath,
                excelClass
            );
        }
    }


    public static class CommonVerify<T extends ExcelData> extends AbstractFailDataStoreImportVerify<T> {

        private Function<List<T>, List<T>> verify;

        /**
         * 错误数据储存
         *
         * @param startLineNumber       Excel开始下标
         * @param fileStorageSdkService 存储服务
         * @param importType            导入类型
         * @param importData            导入数据
         * @param templatePath          失败模板文件路径
         * @param excelClass            失败模板文件对应的Excel类
         */
        public CommonVerify(
            Long startLineNumber,
            FileStorageSdkService fileStorageSdkService,
            String importType,
            List<T> importData,
            String templatePath,
            String excelClass
        ) {
            super(startLineNumber, fileStorageSdkService, importType, importData, templatePath, excelClass);
        }

        @Override
        protected List<T> verifyImportData(List<T> importData) {
            return verify.apply(importData);
        }

        /**
         * 开始校验
         *
         * @param verify 校验方式
         * @return 校验结果
         */
        public VerifyResult<T> startVerify(Function<List<T>, List<T>> verify) {
            this.verify = verify;
            return super.verify();
        }
    }

    public static <T extends ExcelData> void verifyAnnotation(List<T> excelData) {

        if (excelData == null) return;

        excelData.forEach(excel -> {
            Field[] fields = excel.getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    if (field.isAnnotationPresent(ExcelVerify.class)) {
                        ExcelVerify annotation = field.getAnnotation(ExcelVerify.class);
                        field.setAccessible(true);
                        Object v = field.get(excel);
                        if (annotation.notEmpty() && StringUtils.isEmpty(v)) {
                            excel.setMsg(annotation.msg() + "为空");
                            excel.setSuccess(false);
                        }
                        if (annotation.length() > 0 && !StringUtils.isEmpty(v)
                                && v.toString().length() > annotation.length()) {
                            excel.setMsg(annotation.msg() + "长度过长");
                            excel.setSuccess(false);
                        }
                        if (!StringUtils.isEmpty(annotation.illegalityChars()) && !StringUtils.isEmpty(v)) {
                            String[] split = annotation.illegalityChars().split("");
                            for (String illegalityChar : split) {
                                if (v.toString().indexOf(illegalityChar) > -1) {
                                    excel.setMsg(annotation.msg() + "存在非法字符");
                                    excel.setSuccess(false);
                                }
                            }
                        }
                        if (annotation.matches().length() > 0) {
                            if (!v.toString().matches(annotation.matches())) {
                                excel.setMsg(annotation.msg() + "表达式错误");
                                excel.setSuccess(false);
                            }
                        }

                        if (!StringUtils.isEmpty(v) && !CodeEnum.class.getSimpleName().equals(annotation.codeEnum().getSimpleName())
                                && CodeEnumUtil.codeEnumMap(annotation.codeEnum()).get(v.toString()) == null) {
                            excel.setMsg(annotation.msg() + "错误");
                            excel.setSuccess(false);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
