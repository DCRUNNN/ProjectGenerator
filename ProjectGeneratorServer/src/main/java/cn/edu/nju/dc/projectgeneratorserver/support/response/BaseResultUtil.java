package cn.edu.nju.dc.projectgeneratorserver.support.response;

import org.springframework.http.HttpStatus;

/**
 * @author dc
 * @date 2019/12/17 23:10
 *
 * 响应结果生成工具
 */
public class BaseResultUtil {

    private static final String DEFAULT_OK_MESSAGE = "OK";

    private static final String DEFAULT_UNAUTHORIZED_MESSAGE = "Need authorized";

    private static final String DEFAULT_METHOD_NOT_ALLOWED_MESSAGE = "Request method incorrect";

    public static BaseResult createOkResult() {
        return new BaseResult.Builder(HttpStatus.OK.value()).message(DEFAULT_OK_MESSAGE).build();
    }

    public static BaseResult createOkResult(final Object data) {
        return new BaseResult.Builder(HttpStatus.OK.value()).message(DEFAULT_OK_MESSAGE).data(data).build();
    }

    public static BaseResult createFailedResult(final String message) {
        return new BaseResult.Builder(HttpStatus.BAD_REQUEST.value()).message(message).build();
    }

    public static BaseResult createMethodErrorResult() {
        return new BaseResult.Builder(HttpStatus.METHOD_NOT_ALLOWED.value()).message(DEFAULT_METHOD_NOT_ALLOWED_MESSAGE)
            .build();
    }

    public static BaseResult createUnauthorizedResult() {
        return new BaseResult.Builder(HttpStatus.UNAUTHORIZED.value()).message(DEFAULT_UNAUTHORIZED_MESSAGE).build();
    }

    public static BaseResult createUnauthorizedResult(final String message) {
        return new BaseResult.Builder(HttpStatus.UNAUTHORIZED.value()).message(message).build();
    }

    public static BaseResult createInternalServerErrorResult(final String url) {
        return new BaseResult.Builder(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(
            "API [" + url + "] internal server error. Please call engineer to debug.").build();
    }
}
