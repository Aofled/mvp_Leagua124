package com.createsmart.aofled.mvp_leagua124.contractInterface;

import android.content.Context;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

public interface MainContract {


    interface MainView {
        Context getActivityContext();



        void setFragment(Fragment fragment);
        void showMessage(String message);
        void finishActivity();
    }


    interface MainPresenter {
        void setFragment(int position);
        void buttonExitApp();
        void onDestroy();
    }


    interface MainRepository {
        interface OnListener {
            void onMessage(String message);
            void onFinishApp();
        }

        Fragment onSetFragment(int position);
        void exitApp(OnListener onListener);
    }




}
