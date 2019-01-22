package com.createsmart.aofled.mvp_leagua124.fragment;


import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.createsmart.aofled.mvp_leagua124.MyApplication;
import com.createsmart.aofled.mvp_leagua124.R;
import com.createsmart.aofled.mvp_leagua124.activity.MainActivity;
import com.createsmart.aofled.mvp_leagua124.adapter.NewsAdapter;
import com.createsmart.aofled.mvp_leagua124.contractInterface.NewsContract;
import com.createsmart.aofled.mvp_leagua124.model.News;
import com.createsmart.aofled.mvp_leagua124.presenter.NewsPresenter;


import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class NewsFragment extends Fragment
        implements NewsContract.NewsView, SwipeRefreshLayout.OnRefreshListener{


    private SwipeRefreshLayout mSwipeRefreshLayout; //свайп для обновления
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;


    private NewsAdapter newsAdapter;
    private LinearLayoutManager mLayoutManager;

    private static final String TAG = "MVS";
    private static final String FRG = "news";

    private Context context;

    private NewsContract.NewsPresenter presenter;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity){
            this.context = getActivity();
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.f_news_fragment, container, false);

      /////  int bundle_dwn = ((MyApplication)getActivity().getApplication()).getNewsScr();

        initComponents(view);
        presenter = new NewsPresenter(this);//создаем экземпляр презентера
        presenter.requestData(); //запрос на данные


        return view;
    }


    //инициализируем компоненты
    private void initComponents(View view) {
        //инициализация Pull to Refresh (выбираем цвета)
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_blue_light);

        //прогрессбар
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mLayoutManager = new LinearLayoutManager(context);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                presenter.onScrolledRecyler(mLayoutManager.getChildCount(), mLayoutManager.getItemCount(), mLayoutManager.findFirstVisibleItemPosition());
            }
        });

    }

    @Override
    public Context getActivityContext() {
        return context;
    }


    //грузим данные в ресаклвью
    @Override
    public void setDataToRecyclerView(ArrayList<News> mNewsArray) {
        newsAdapter = new NewsAdapter(context, mNewsArray);
        mRecyclerView.setAdapter(newsAdapter);

        //Обработка нажатия
        newsAdapter.setOnItemClickListener(new NewsAdapter.ClickListener() {
            @Override
            public void onClick(View v, int position,  boolean click) {
                if (click) {
                    Log.v(TAG,getString(R.string.fast_click)+ position);
                  /*  Intent intent = new Intent(context, NewsDetalsActivity.class);
                    intent.putExtra("position", position);
                    startActivity(intent);*/
                } else {
                    Log.v(TAG,getString(R.string.long_click)+ position);
                }
            }
        });
    }


    @Override //SwipeRefresh действие при обновлении
    public void onRefresh() {
        presenter.requestData();
    }


    @Override //показываем НИЖНИЙ прогрессбар
    public void showProgressBottom(ArrayList<News> mNewsArray) {
        newsAdapter.notifyItemInserted(mNewsArray.size() - 1);
    }


    @Override //загружаем данные + прячем НИЖНИЙ прогрессбар
    public void addDataToRecyclerView(ArrayList<News> mNewsArray) {
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideSwipe() { //прячем свайп
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override //тост для оповещения пользователя
    public void showMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show(); //
    }

    @Override //сообщение о внутренней ошибке
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(context,
                "Something went wrong...Error message: " + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }

    @Override //показываем ЦЕНТРАЛЬНЫЙ прогрессбар
    public void showProgressCen() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override //прячем ЦЕНТРАЛЬНЫЙ прогрессбар
    public void hideProgressCen() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override //уничтожение активности
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy(); //вызываем метод уничтожение презентера
    }




}








