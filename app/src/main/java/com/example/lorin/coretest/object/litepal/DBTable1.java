package com.example.lorin.coretest.object.litepal;

import org.litepal.crud.DataSupport;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lorin on 16/1/29.
 */
public class DBTable1 extends DataSupport {

  private String name;
  private int myIndex;
  private List<DBTable2> dbTable2s = new LinkedList<DBTable2>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getMyIndex() {
    return myIndex;
  }

  public void setMyIndex(int myIndex) {
    this.myIndex = myIndex;
  }

  public List<DBTable2> getDbTable2s() {
    return dbTable2s;
  }

  public void setDbTable2s(List<DBTable2> dbTable2s) {
    this.dbTable2s = dbTable2s;
  }
}
