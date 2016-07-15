package com.example.lorin.helloworld_coretest.object.litepal;

import org.litepal.crud.DataSupport;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lorin on 16/1/29.
 */
public class DBTable2 extends DataSupport {

    private String name2;
    private String myType;

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getMyType() {
        return myType;
    }

    public void setMyType(String myType) {
        this.myType = myType;
    }
}
