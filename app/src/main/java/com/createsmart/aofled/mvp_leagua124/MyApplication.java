package com.createsmart.aofled.mvp_leagua124;

import android.app.Application;

public class MyApplication extends Application {



    private int news_bnd;

    public int getNewsScr() {
        return news_bnd;
    }

    public void setNewsScr(int news_bnd) {
        this.news_bnd = news_bnd;
    }

}
