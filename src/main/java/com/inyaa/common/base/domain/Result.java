package com.inyaa.common.base.domain;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.inyaa.auth.bean.UserInfo;
import com.inyaa.common.constant.ResultConstants;
import com.inyaa.common.enums.ErrorEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于向前端返回统一的结果对象
 *
 * @param <T>
 */
@Data
@Accessors(chain = true)
public class Result<T> {

    private int success = 0; // 操作标识，标记
    private String resultCode; // 结果编码
    private String message; // 提示信息
    private T model; // 结果对象
    private List<T> models; // 结果集对象
    private PageInfo pageInfo; // 分页信息对象
    private IPage<T> page; // 分页信息对象
    private Object extra; // 扩展字段

    // 禁止空参构造
    private Result() {
    }

    public static <T> Result<T> createWithPaging(IPage<T> page) {
        Result<T> result = createWithSuccessMessage();
        result.setPage(page);
        return result;
    }

    // 通过操作标识及提示信息构建结果对象
    private static <T> Result<T> createWithSuccessFlag(int success) {
        Result result = new Result();
        result.setSuccess(success);
        return result;
    }

    public static <T> Result<T> createWithSuccessMessage() {
        Result result = createWithSuccessFlag(ResultConstants.YES);
        result.setResultCode(ResultConstants.OPERATION_SUCCESS);
        result.setMessage(ResultConstants.SUCCESS_MESSAGE);
        return result;
    }

    public static <T> Result<T> createWithSuccessMessage(String message) {
        Result result = createWithSuccessFlag(ResultConstants.YES);
        result.setResultCode(ResultConstants.OPERATION_SUCCESS);
        result.setMessage(StringUtils.isBlank(message) ? ResultConstants.SUCCESS_MESSAGE : message);
        return result;
    }

    public static <T> Result<T> createWithModel(T model) {
        Result result = createWithSuccessMessage();
        result.setModel(model);
        return result;
    }

    public static <T> Result<T> createWithModel(String message, T model) {
        Result result = createWithSuccessMessage(message);
        result.setModel(model);
        return result;
    }

    public static <T> Result<T> createWithModels(String message, List<T> models) {
        Result result = createWithSuccessMessage(message);
        result.setModels(models == null ? new ArrayList<>(0) : models);
        return result;
    }

    public static <T> Result<T> createWithModels(List<T> models) {
        Result result = createWithSuccessMessage();
        result.setModels(models == null ? new ArrayList<>(0) : models);
        return result;
    }

    public static <T> Result<T> createWithPaging(String message, List<T> models, PageInfo pagingInfo) {
        Result result = createWithModels(message, models == null ? new ArrayList<>(0) : models);
        result.setPageInfo(pagingInfo);
        return result;
    }

    public static <T> Result<T> createWithPaging(List<T> models, PageInfo pagingInfo) {
        Result result = createWithModels(models == null ? new ArrayList<>(0) : models);
        result.setPageInfo(pagingInfo);
        return result;
    }

    public static <T> Result<T> createWithErrorMessage(String message, String errorCode) {
        Result result = createWithSuccessFlag(ResultConstants.NO);
        result.setMessage(message);
        result.setResultCode(errorCode);
        return result;
    }

    public static <T> Result<T> createWithErrorMessage(ErrorEnum errorEnum) {
        return createWithErrorMessage(errorEnum.getMsg(errorEnum.getCode()), errorEnum.getCode());
    }

    public static <T> Result<T> createWithError() {
        return createWithErrorMessage(ErrorEnum.ERROR);
    }
}