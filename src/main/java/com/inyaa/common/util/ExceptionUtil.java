package com.inyaa.common.util;

import com.inyaa.common.base.domain.Result;
import com.inyaa.common.constant.ResultConstants;
import com.inyaa.common.enums.ErrorEnum;
import com.inyaa.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author:inyaa
 * @Date:2018/09/27 12:52
 */
public class ExceptionUtil {

    /**
     * 业务回滚，抛出特定异常：包含错误消息
     */
    public static void rollback(String message) {
        throw new BusinessException(message);
    }

    /**
     * 业务回滚，抛出特定异常：包含错误消息，错误编码
     */
    public static void rollback(String message, String code) {
        throw new BusinessException(message, code);
    }

    /**
     * 业务回滚，抛出特定异常：包含错误消息，错误原因
     */
    public static void rollback(String message, Throwable cause) {
        throw new BusinessException(message, cause);
    }

    /**
     * 业务回滚，抛出特定异常：包含错误消息，错误编码，错误原因
     */
    public static void rollback(String message, String code, Throwable cause) {
        throw new BusinessException(message, code, cause);
    }

    /**
     * 业务不需回滚，设置result返回
     */
    public static Result setResult(String message) {
        return Result.createWithErrorMessage(StringUtils.isBlank(message) ? ResultConstants.ERROR_MESSAGE : message, ResultConstants.OPERATION_FAIL);
    }

    public static void rollback(ErrorEnum errorEnum) {
        throw new BusinessException(errorEnum);
    }

    public static void isRollback(boolean flag, ErrorEnum errorEnum){
        if (flag){
            rollback(errorEnum);
        }
    }
}
