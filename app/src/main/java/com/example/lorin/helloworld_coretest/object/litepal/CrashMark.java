package com.example.lorin.helloworld_coretest.object.litepal;

import org.litepal.crud.DataSupport;

/**
 * Created by lorin on 16/1/29.
 */
public class CrashMark extends DataSupport{

    private String ccurrenceTime;
    private String crashMessage;
    private boolean hasSent;

    public String getCcurrenceTime() {
        return ccurrenceTime;
    }

    public void setCcurrenceTime(String ccurrenceTime) {
        this.ccurrenceTime = ccurrenceTime;
    }

    public String getCrashMessage() {
        return crashMessage;
    }

    public void setCrashMessage(String crashMessage) {
        this.crashMessage = crashMessage;
    }

    public boolean isHasSent() {
        return hasSent;
    }

    public void setHasSent(boolean hasSent) {
        this.hasSent = hasSent;
    }
}
