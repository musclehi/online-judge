package cn.idealismxxm.onlinejudge.util;

import java.io.Serializable;

/**
 * ajax返回结果封装类
 *
 * @author idealism
 * @date 2018/3/23
 */
public class AjaxResult<T> implements Serializable {
    /**
     * 相关信息
     */
    private String msg;

    /**
     * 封装的数据
     */
    private T data;

    public AjaxResult(String msg) {
        this.msg = msg;
    }

    public AjaxResult(String msg, T data) {
        this.msg = msg;
        this.data = data;
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
                "msg='" + msg + '\'' +
                ", data=" + JsonUtil.ObjectToJson(data) +
                '}';
    }
}
