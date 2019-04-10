package com.example.lorin.coretest.object.litepal;


/**
 * Created by lorin on 16/1/29.
 */
public class CrashMarkManual {

    private int _id;
    private String currence_time;
    private String crash_message;
    private int has_sent;//0未发送，1已发送

    public CrashMarkManual()
    {

    }

    public CrashMarkManual(String currence_time, String crash_message, int has_sent)
    {
        this.currence_time = currence_time;
        this.crash_message = crash_message;
        this.has_sent = has_sent;

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCurrence_time() {
        return currence_time;
    }

    public void setCurrence_time(String currence_time) {
        this.currence_time = currence_time;
    }

    public String getCrash_message() {
        return crash_message;
    }

    public void setCrash_message(String crash_message) {
        this.crash_message = crash_message;
    }

    public int getHas_sent() {
        return has_sent;
    }

    public void setHas_sent(int has_sent) {
        this.has_sent = has_sent;
    }
}
