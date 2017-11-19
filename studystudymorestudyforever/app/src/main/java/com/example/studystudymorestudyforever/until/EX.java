package com.example.studystudymorestudyforever.until;

/**
 * Created by VANKHANHPR on 9/28/2017.
 */

public class EX {


    private int SCHE_ID;
    private int USER_ID;
    private long START_TIME;
    private long END_TIME;
    private String FEE;
    private  String LOCATION;
    private long SUB_ID;

    public int getSCHE_ID() {
        return SCHE_ID;
    }

    public void setSCHE_ID(int SCHE_ID) {
        this.SCHE_ID = SCHE_ID;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(int USER_ID) {
        this.USER_ID = USER_ID;
    }

    public long getSTART_TIME() {
        return START_TIME;
    }

    public void setSTART_TIME(long START_TIME) {
        this.START_TIME = START_TIME;
    }

    public long getEND_TIME() {
        return END_TIME;
    }

    public void setEND_TIME(long END_TIME) {
        this.END_TIME = END_TIME;
    }

    public String getFEE() {
        return FEE;
    }

    public void setFEE(String FEE) {
        this.FEE = FEE;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }
    private  String SUB_NAME;

    public String getSUB_NAME() {
        return SUB_NAME;
    }

    public void setSUB_NAME(String SUB_NAME) {
        this.SUB_NAME = SUB_NAME;
    }


    public long getSUB_ID() {
        return SUB_ID;
    }

    public void setSUB_ID(long SUB_ID) {
        this.SUB_ID = SUB_ID;
    }

    public EX(int SCHE_ID, int USER_ID, long START_TIME, long END_TIME, String FEE, String LOCATION, long SUB_ID, String SUB_NAME) {
        this.SCHE_ID = SCHE_ID;
        this.USER_ID = USER_ID;
        this.START_TIME = START_TIME;
        this.END_TIME = END_TIME;
        this.FEE = FEE;
        this.LOCATION = LOCATION;
        this.SUB_ID = SUB_ID;
        this.SUB_NAME = SUB_NAME;
    }
    private  String THU;

    public String getTHU() {
        return THU;
    }

    public void setTHU(String THU) {
        this.THU = THU;
    }

    private String TIME;

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    private String TEACHER_ID;

    public String getTEACHER_ID() {
        return TEACHER_ID;
    }

    public void setTEACHER_ID(String TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
    }
}
