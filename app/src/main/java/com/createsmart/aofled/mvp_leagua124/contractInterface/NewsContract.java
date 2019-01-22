package com.createsmart.aofled.mvp_leagua124.contractInterface;

import android.content.Context;

import com.createsmart.aofled.mvp_leagua124.model.News;


import java.util.ArrayList;

public interface NewsContract {

    interface NewsView {
        Context getActivityContext();
        void hideSwipe();
        void showProgressCen();
        void hideProgressCen();
        void showProgressBottom(ArrayList<News> mNewsArray);
        void addDataToRecyclerView(ArrayList<News> mNewsArray);
        void showMessage(String message);
        void setDataToRecyclerView(ArrayList<News> mNewsArray);
        void onResponseFailure(Throwable throwable);

    }


    interface NewsPresenter {
        void onScrolledRecyler(int visibleItemCount, int totalItemCount, int firstVisibleItems);
        void requestData();
        void onDestroy();


    }


    interface NewsRepository {
        interface OnFinishedListener {
            void onFinishedLoadingNews(ArrayList<News> mNewsArray);
            void onFinishedLoadingNewsEver(ArrayList<News> mNewsArray);
            void onFailure(Throwable t);
            void onMessage(String message);
            void showPgBottom(ArrayList<News> mNewsArray);
        }
        void downloadNews(OnFinishedListener onFinishedListener);
        void downloadNewsEver(OnFinishedListener onFinishedListener);
        void onScrollRecyler(int visibleItemCount, int totalItemCount, int firstVisibleItems, OnFinishedListener onFinishedListener);
    }


}
