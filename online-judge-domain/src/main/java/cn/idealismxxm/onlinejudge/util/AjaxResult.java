package cn.idealismxxm.onlinejudge.util;

import cn.idealismxxm.onlinejudge.enums.ResultCodeEnum;

import java.io.Serializable;

/**
 * ajax返回结果封装类
 *
 * @author idealism
 * @date 2018/3/23
 */
public class AjaxResult<T> implements Serializable {
    /**
     * 相关代码
     */
    private String code;

    /**
     * 相关信息
     */
    private String msg;

    /**
     * 封装的数据
     */
    private T data;

    public AjaxResult(ResultCodeEnum resultCodeEnum, T data) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg();
        this.data = data;
    }

    public AjaxResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AjaxResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
