package com.kmks.jianguan.response;

import com.kmks.jianguan.enums.HttpEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/9 9:46
 */
@Data
public class Response<T> implements Serializable {
    private static final long SerialVersionUID = 1;
    private int code;
    private String message;
    private T data;

    public Response(){
        this.data = null;
        this.code = HttpEnum.SUCCESS.getCode();
        this.message = HttpEnum.SUCCESS.getMessage();
    }

    public Response(T data){
        this.data = data;
        this.code = HttpEnum.SUCCESS.getCode();
        this.message = HttpEnum.SUCCESS.getMessage();
    }

    public Response(int code,String message){
        this(code,message,null);
    }

    public Response(int code,String message,T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
