package top.yuany3721.ir.util;

public class Response<T> {
    public enum ResponseCode {
        SUCCESS(200, "SUCCESS"),
        FAILURE(400, "FAILURE"),
        NOT_FOUND(404, "NOT FOUND"),
        INTERNAL_ERROR(500, "INTERNAL ERROR");

        private final Integer code;
        private final String message;

        ResponseCode(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        Integer code() {
            return this.code;
        }

        String message() {
            return this.message;
        }
    }

    private Integer code;
    private String message;
    private T data;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Response(ResponseCode responseCode, T data) {
        this.code = responseCode.code();
        this.message = responseCode.message();
        this.data = data;
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(ResponseCode.SUCCESS, data);
    }

    public static <T> Response<T> failure(T errorMessage) {
        return new Response<>(ResponseCode.FAILURE, errorMessage);
    }

}
