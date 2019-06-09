package cc.lastone.hiblog.utils;

public class ResultData<T> {
    public static final int ERROR_PARAM = 1; // 参数错误
    public static final int ERROR_SAVE = 2; // 保存失败
    public static final int ERROR_DELETE = 3; // 删除失败
    public static final int ERROR_NODATA = 4; // 没有数据
    public static final int ERROR_NOAUTH = 5; // 没有权限
    public static final int ERROR_EXIST = 6; // 数据已存在据
    public static final int ERROR_ONUSE = 7; // 数据已使用
    private int state = 0;
    private String msg = "";
    private T data;

    public ResultData() {

    }

    public ResultData(T data) {
        this.data = data;
    }

    public ResultData(String msg) {
        this.msg = msg;
    }

    public ResultData(T data, String msg) {
        this.data = data;
        this.msg = msg;
    }

    public ResultData(int state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public ResultData(int state, String msg, T data) {
        this(state, msg);
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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
}
