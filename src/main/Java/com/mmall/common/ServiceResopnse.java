package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

@JsonSerialize(include =  JsonSerialize.Inclusion.NON_NULL)
//保证在序列化的时候，若对象为null，key消失
public class ServiceResopnse<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    private ServiceResopnse(int status){
        this.status = status;
    }


    private ServiceResopnse(int status,T data){
        this.status = status;
        this.data = data;
    }

    private ServiceResopnse(int status,String msg,T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ServiceResopnse(int status,String msg){
        this.status = status;
        this.msg = msg;
    }

    @JsonIgnore
    //使该方法不出现在json序列化结果当中
    public boolean isSuccess(){
        return this.status==ResponseCode.SUCCESS.getCode();
    }

    public int getStatus(){
        return this.status;
    }
    public String getMsg(){
        return this.msg;
    }
    public T getData(){
        return this.data;
    }

    public  static <T> ServiceResopnse<T> createBySuccess(){
        return new ServiceResopnse<>(ResponseCode.SUCCESS.getCode());
    }
    public  static <T> ServiceResopnse<T> createBySuccessMessage(String msg){
        return new ServiceResopnse<>(ResponseCode.SUCCESS.getCode(),msg);
    }
    public  static <T> ServiceResopnse<T> createBySuccess(T data){
        return new ServiceResopnse<>(ResponseCode.SUCCESS.getCode(),data);
    }
    public  static <T> ServiceResopnse<T> createBySuccess(String msg,T data){
        return new ServiceResopnse<>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public  static <T> ServiceResopnse<T> createByError(){
        return new ServiceResopnse<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }
    public  static <T> ServiceResopnse<T> createByErrorMessage(String msg){
        return new ServiceResopnse<>(ResponseCode.ERROR.getCode(),msg);
    }
    public  static <T> ServiceResopnse<T> createByError(int errorcode,String msg){
        return new ServiceResopnse<>(errorcode,msg);
    }

    public  static <T> ServiceResopnse<T> createByError(T data){
        return new ServiceResopnse<>(ResponseCode.ERROR.getCode(),data);
    }

}
