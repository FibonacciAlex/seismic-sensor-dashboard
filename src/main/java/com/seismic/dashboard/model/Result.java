package com.seismic.dashboard.model;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Objects;


/**
 * Result Wrapper
 **/
public class Result<T> implements Serializable {


    private int code = HttpStatus.OK.value();
    private String message = "";
    private T data;

    private Result() {
    }


    public static <T> Result<T> success(T data) {
        Result<T> success = new Result<>();
        success.setMessage("successful operation");
        success.setData(data);
        return success;
    }

    public static <T> Result<T> success(String message, T data) {
        Result<T> success = new Result<>();
        success.setMessage(message);
        success.setData(data);
        return success;
    }

    public static <T> Result<T> error(String message, T data) {
        Result<T> success = new Result<>();
        success.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        success.setMessage(message);
        success.setData(data);
        return success;
    }

    public static Result wrap(boolean flag) {
        Result result = new Result<>();
        if (flag) {
            result.setMessage("successful operation");
        } else {
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setMessage("operation failure");
        }
        return result;
    }

    public static Result wrap(boolean flag, String message) {
        Result result = new Result<>();
        if (flag) {
            result.setMessage(message);
        } else {
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

            result.setMessage(message);
        }
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result<?> result = (Result<?>) o;
        return code == result.code && Objects.equals(message, result.message) && Objects.equals(data, result.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, data);
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}