package com.createsmart.aofled.mvp_leagua124.adapter;

import android.os.Bundle;


import com.createsmart.aofled.mvp_leagua124.fragment.NewsFragment;
import com.createsmart.aofled.mvp_leagua124.fragment.PlayersFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class InfoAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private boolean upd_news = true;
    private boolean upd_mtch = true;

    public InfoAdapter(FragmentManager fm, int NumOfTabs, boolean upd_news, boolean upd_mtch) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.upd_news=upd_news;
        this.upd_mtch=upd_mtch;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Bundle bundle1 = new Bundle();
         //       bundle1.putBoolean("dwn_upd", upd_news); //обновляем/грузим?
               NewsFragment newsFragment = new NewsFragment();
                newsFragment.setArguments(bundle1);
                return newsFragment;



            case 1:
                Bundle bundle2 = new Bundle();
             //   bundle2.putBoolean("dwn_upd", upd_mtch); //обновляем/грузим?
                /*
                MatchesFragment matchesFragment = new MatchesFragment();
                matchesFragment.setArguments(bundle2);
                return matchesFragment;*/

                Bundle bundle3 = new Bundle();
                //       bundle1.putBoolean("dwn_upd", upd_news); //обновляем/грузим?
          /*      NewsFragment newsFragment = new NewsFragment();
                newsFragment.setArguments(bundle1);
                return newsFragment;
                */

                PlayersFragment playersFragment2 = new PlayersFragment();
                playersFragment2.setArguments(bundle3);
                return playersFragment2;





            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}