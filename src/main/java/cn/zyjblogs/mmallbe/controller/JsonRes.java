package cn.zyjblogs.mmallbe.controller;

/**
 * 自定义的Json返回结果
 */
public class JsonRes {
    //状态
    private int status;
    //字符串提示信息
    private String msg;
    //返回真实数据
    private Object data;

    public JsonRes(int status, String msg,Object data) {
        this.status = status;
        this.msg = msg;
        this.data=data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
