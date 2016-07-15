package com.example.lorin.helloworld_coretest.object;

/**
 * Created by lorin on 16/7/4.
 */
public class SettingDescription {

    private String setting1;
    private String setting2;
    private String setting3;
    private String setting4;
    private String setting5;
    private String title;

    public SettingDescription() {

    }

    public SettingDescription(String setting1, String setting2,String setting3,String setting4,String setting5,String title) {
        this.setting1 = setting1;
        this.setting2 = setting2;
        this.setting3 = setting3;
        this.setting4 = setting4;
        this.setting5 = setting5;
        this.title = title;
    }

    public String getSetting1() {
        return setting1;
    }

    public void setSetting1(String setting1) {
        this.setting1 = setting1;
    }

    public String getSetting2() {
        return setting2;
    }

    public void setSetting2(String setting2) {
        this.setting2 = setting2;
    }

    public String getSetting3() {
        return setting3;
    }

    public void setSetting3(String setting3) {
        this.setting3 = setting3;
    }

    public String getSetting4() {
        return setting4;
    }

    public void setSetting4(String setting4) {
        this.setting4 = setting4;
    }

    public String getSetting5() {
        return setting5;
    }

    public void setSetting5(String setting5) {
        this.setting5 = setting5;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
