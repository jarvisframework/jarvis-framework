package com.jarvis.platform.excel.verify;

import com.jarvis.platform.excel.verify.dto.ExcelData;
import com.jarvis.platform.excel.verify.dto.VerifyResult;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通用的导入数据处理类
 *
 * @author hewm
 * @date 2022/9/26
 */
@Slf4j
public abstract class AbstractExcelImportVerify<T extends ExcelData> {

    private final Long startLineNumber;

    private final List<T> importData;

    public AbstractExcelImportVerify(Long startLineNumber, List<T> importData) {
        this.startLineNumber = startLineNumber;
        this.importData = importData;
    }

    public VerifyResult<T> verify() {
        //1. 设置导入数据的行号
        List<T> importData = this.importData;
        for (int i = 0; i < importData.size(); i++) {
            importData.get(i).setLineNumber((long) i + startLineNumber + 1);
        }

        //2. 导入数据的前置处理
        List<T> newImportData = beforeHandle(importData);

        //3. 校验导入数据
        List<T> verifyCompleteData = verifyImportData(newImportData);

        //4. 区分成功与失败数据
        List<T> successList = verifyCompleteData
            .stream()
            .filter(ExcelData::getSuccess)
            .collect(Collectors.toList());
        List<T> failList = verifyCompleteData.stream()
            .filter(filter -> !filter.getSuccess())
            .collect(Collectors.toList());

        //5. 排序数据,按行号排序
        successList.sort(Comparator.comparingLong(ExcelData::getLineNumber));
        failList.sort(Comparator.comparingLong(ExcelData::getLineNumber));

        //6. 构建校验结果数据
        return buildImportResult(successList, failList);
    }


    /**
     * 导入前置处理
     *
     * @param importData 导入数据
     * @return List
     */
    protected List<T> beforeHandle(List<T> importData){
        return importData;
    }

    /**
     * 校验导入数据
     *
     * @param importData 导入数据
     * @return List
     */
    protected abstract List<T> verifyImportData(List<T> importData);

    /**
     * 生成导入结果
     *
     * @param successList 成功的数据
     * @param failList    失败的数据
     * @return ImportResult
     */
    protected VerifyResult<T> buildImportResult(List<T> successList, List<T> failList) {
        return new VerifyResult<T>().setSuccess(successList).setFail(failList);
    }

}
