package top.yuany3721.ir.util;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * 统一异常处理
 * 
 * @anthor lcl
 * @since 2022-5-10 10:50:06
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {
    /**
     * 处理业务异常
     * 
     * @param baseException
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Response<?> exceptionHandler(RuntimeException runtimeException) {
        log.error(runtimeException.getMessage());
        return Response.failure(runtimeException.getMessage());
    }

    /**
     * 未知异常处理
     * 
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response<?> exceptionHandler(Exception exception) {
        log.error(exception.getMessage());
        return Response.failure(exception.getMessage());
    }
}