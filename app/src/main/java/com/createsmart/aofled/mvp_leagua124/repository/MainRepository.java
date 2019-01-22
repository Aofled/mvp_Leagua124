package com.createsmart.aofled.mvp_leagua124.repository;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

import com.createsmart.aofled.mvp_leagua124.R;

import com.createsmart.aofled.mvp_leagua124.contractInterface.MainContract;
import com.createsmart.aofled.mvp_leagua124.fragment.InfoFragment;
import com.createsmart.aofled.mvp_leagua124.fragment.NewsFragment;
import com.createsmart.aofled.mvp_leagua124.fragment.PlayersFragment;
import com.createsmart.aofled.mvp_leagua124.fragment.TeamFragment;

import androidx.fragment.app.Fragment;


public class MainRepository implements MainContract.MainRepository {

    private Context mContext;


    private long last_time; //время для выхода (нажмите два раза)


    public MainRepository(Context mContext) {
        this.mContext=mContext;
    }



    public Fragment onSetFragment(int position) {
        switch (position) {
            case 0://переход на новости/*
                InfoFragment infoFragment = new InfoFragment();
                Bundle bundle_i = new Bundle();
             //   bundle_i.putBoolean("upd_news", upd_news); //обновляем/грузим?
        //        bundle_i.putBoolean("upd_mtch", upd_mtch); //обновляем/грузим?
                infoFragment.setArguments(bundle_i);
       //         fragmentTransaction.replace(R.id.fragment, infoFragment);
          //      upd_news=false;
          //      upd_mtch=false;
          //      fr =1;
                return infoFragment;
            case 1: //переход на команды
                NewsFragment newsFragment = new NewsFragment();
                Bundle bundle_N = new Bundle();
                //    bundle_L.putBoolean("dwn_upd", upd_team); //обновляем/грузим?
                newsFragment.setArguments(bundle_N);
                //  fragmentTransaction.replace(R.id.fragment, teamFragment);
                //  upd_team=false;
                //   fr =2;
                return newsFragment;
            case 2: //переход на команды
                TeamFragment teamFragment = new TeamFragment();
                Bundle bundle_L = new Bundle();
            //    bundle_L.putBoolean("dwn_upd", upd_team); //обновляем/грузим?
                teamFragment.setArguments(bundle_L);
              //  fragmentTransaction.replace(R.id.fragment, teamFragment);
              //  upd_team=false;
             //   fr =2;
                return teamFragment;
            case 3: //переход на игроков/*
                PlayersFragment playersFragment = new PlayersFragment();
                Bundle bundle_p = new Bundle();
           //     bundle_p.putBoolean("dwn_upd", upd_pl); //обновляем/грузим?
                playersFragment.setArguments(bundle_p);
              //  fragmentTransaction.replace(R.id.fragment, playersFragment);
             //   upd_pl=false;
             //   fr =3;
                return playersFragment;
            default:
                return null;
        }
    }







    @Override
    public void exitApp(final OnListener onListener) {
        //смотрим, сколько времени прошло с последнего нажатия "назад"
        if ((SystemClock.elapsedRealtime() - last_time) < 1500) {
            onListener.onFinishApp();
            return;
        }
        if ((SystemClock.elapsedRealtime() - last_time) > 1500) {
            last_time = SystemClock.elapsedRealtime();
            onListener.onMessage(mContext.getString(R.string.exit));
        }
    }





    /*
    *
    *
    *             case 0://переход на новости/*
                /*
                InfoFragment infoFragment = new InfoFragment();
                Bundle bundle_i = new Bundle();
                bundle_i.putBoolean("upd_news", upd_news); //обновляем/грузим?
                bundle_i.putBoolean("upd_mtch", upd_mtch); //обновляем/грузим?
                infoFragment.setArguments(bundle_i);
                fragmentTransaction.replace(R.id.fragment, infoFragment);
                upd_news=false;
                upd_mtch=false;
                fr =1;
                return null;
                break;
            case 1: //переход на страницe турнирной таблицы
                /*
                StandingsFragment standingsFragment = new StandingsFragment();
                Bundle bundleS = new Bundle();
                bundleS.putBoolean("dwn_upd", upd_stnd); //обновляем/грузим?
                standingsFragment.setArguments(bundleS);
                fragmentTransaction.replace(R.id.fragment, standingsFragment);
                upd_stnd=false;
                fr =0;

                    return null;
                break;

    * */





}
