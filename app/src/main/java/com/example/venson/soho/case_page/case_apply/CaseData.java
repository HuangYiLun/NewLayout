package com.example.venson.soho.case_page.case_apply;

import java.io.Serializable;

/**
 * Created by ricky on 2018/4/27.
 */

public class CaseData implements Serializable{
    private int id;
    private String casename;
    private String caseneed;

    public CaseData(int id,String casename, String caseneed) {
        super();
        this.id = id;
        this.casename = casename;
        this.caseneed = caseneed;
    }


    public String getCasename() {
        return casename;
    }

    public void setCasename(String casename) {
        this.casename = casename;
    }

    public String getCaseneed() {
        return caseneed;
    }

    public void setCaseneed(String caseneed) {
        this.caseneed = caseneed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

