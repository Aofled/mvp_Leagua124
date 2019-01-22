package com.createsmart.aofled.mvp_leagua124.repository;

import android.content.Context;
import android.util.Log;

import com.createsmart.aofled.mvp_leagua124.Constants;
import com.createsmart.aofled.mvp_leagua124.api.RestClient;
import com.createsmart.aofled.mvp_leagua124.api.RetrofitAPI;
import com.createsmart.aofled.mvp_leagua124.contractInterface.NewsContract;
import com.createsmart.aofled.mvp_leagua124.dataBaseRealm.RealmWorker;
import com.createsmart.aofled.mvp_leagua124.model.EntryModeles;
import com.createsmart.aofled.mvp_leagua124.model.News;
import com.createsmart.aofled.mvp_leagua124.R;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsRepository implements NewsContract.NewsRepository {


    private static final String TAG = "MVS";
    private static final String FRG = "news";

    private ArrayList<News> mNewsArray = new ArrayList<>();


    private boolean is_download = true; // отвечает за загрузку с сервера
    private boolean is_loading = true;   //отвечает за загрузку RecyclerView
    private int bundle_dwn =0;
    private boolean dvn_upd;

    private Context mContext;

    private RealmWorker realm;
    private RetrofitAPI api;


    public NewsRepository(Context mContext) {
        this.mContext=mContext;
    }


    @Override
    public void onScrollRecyler(int visibleItemCount, int totalItemCount, int firstVisibleItems, final OnFinishedListener onFinishedListener) {
        if (is_loading) {
            if ((visibleItemCount + firstVisibleItems) >= totalItemCount) {
                is_loading = false;//ставим флаг что мы попросили еще элементы . После загрузки данных его надо будет сбросить снова в true;
                if (is_download) {
                    mNewsArray.add(null);//добавляем эелемент для PG
                    onFinishedListener.showPgBottom(mNewsArray);//активируем нижний прогрессбар (передаем массив с удаленным элементом)
                    Log.v(TAG, mContext.getString(R.string.more_data));
                } else {
                    onFinishedListener.onMessage(mContext.getString(R.string.end_of_list)); //отправляем сообщение пользователю
                    Log.v(TAG, mContext.getString(R.string.end_of_list_next) + bundle_dwn);
                }
            }
        }


    }


    /**
     * Т.к. retrofit грузит все синхронно, то приходится дергать коллбеки
     */

    @Override
    public void downloadNews(final OnFinishedListener onFinishedListener) {
        realm = new RealmWorker(mContext, TAG);
        api = RestClient.getApi();
        Call<EntryModeles> call = api.loadContent(Constants.URL_NEWS, 0);
        call.enqueue(new Callback<EntryModeles>() {
            @Override //грузим из интренета
            public void onResponse(Call<EntryModeles> call, Response<EntryModeles> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSurplus() < 1) { //если данных осталось 0 и меньше, то больше не грузим
                        is_download = false;
                    }
                    realm.deleteData(); //если загрузка идет, то чистим БД
                    mNewsArray=response.body().getNews();
                    realm.insertData(mNewsArray);
                    onFinishedListener.onFinishedLoadingNews(mNewsArray); //данные успешно получены
                    Log.v(TAG, mContext.getString(R.string.data_loading_0));
                } else {
                    onFinishedListener.onFinishedLoadingNews(loadDataBase()); //грузим c БД, если ошибка при загрузке
                    onFinishedListener.onMessage(mContext.getString(R.string.connect_fail)); //отправляем сообщение пjльзователю
                    Log.v(TAG,mContext.getString(R.string.data_fail_0));
                }
            }
            @Override  //грузим из базы если нету интрнета
            public void onFailure(Call<EntryModeles> call, Throwable t) {
                onFinishedListener.onFinishedLoadingNews(loadDataBase()); //грузим c БД, если ошибка при загрузке
                onFinishedListener.onMessage(mContext.getString(R.string.connect_fail)); //отправляем сообщение пользователю
                onFinishedListener.onFailure(t); //отправляем сообщение о системной ошибке
                Log.v(TAG, mContext.getString(R.string.connection_fail_0));
            }
        });
    }


    @Override//для ДОгрузки данных из интренета
    public void downloadNewsEver(final OnFinishedListener onFinishedListener) {
            bundle_dwn++; //номер партии для загрузки
            api = RestClient.getApi();
            Call<EntryModeles> call = api.loadContent(Constants.URL_NEWS, bundle_dwn);
            call.enqueue(new Callback<EntryModeles>() {
                @Override //грузим из интренета
                public void onResponse(Call<EntryModeles> call, Response<EntryModeles> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSurplus() < 2) { //если данных осталось 0 и меньше, то больше не грузим
                            is_download = false;
                        }
                        is_loading = true;
                        mNewsArray.remove(mNewsArray.size() - 1);//"удаляем" PG
                        realm.insertData(response.body().getNews());
                        mNewsArray.addAll(response.body().getNews());
                        onFinishedListener.onFinishedLoadingNewsEver(mNewsArray);
                        Log.v(TAG, mContext.getString(R.string.data_loading_next) + bundle_dwn);
                    } else {  //грузим, если ошибка при загрузке
                        mNewsArray.remove(mNewsArray.size() - 1);//"удаляем" PG
                        onFinishedListener.onFinishedLoadingNewsEver(mNewsArray);
                        Log.v(TAG, mContext.getString(R.string.data_fail_next) + bundle_dwn);
                    }
                }

                @Override  //грузим из базы если нету интрнета
                public void onFailure(Call<EntryModeles> call, Throwable t) {
                    mNewsArray.remove(mNewsArray.size() - 1);//"удаляем" PG
                    onFinishedListener.onFinishedLoadingNewsEver(mNewsArray); //передаем данные активити о PG
                    onFinishedListener.onMessage(mContext.getString(R.string.connect_fail)); //отправляем сообщение пльзователю
                    onFinishedListener.onFailure(t); //отправляем сообщение о системной ошибке
                    Log.v(TAG, mContext.getString(R.string.connection_fail_next) + bundle_dwn);
                }
            });
    }


    //для загрузки данных из БД
    private ArrayList<News> loadDataBase(){  //грузим при возвращении на фрагмент
        mNewsArray.clear();
        mNewsArray.addAll(realm.getData(new News(), "id_news", "DESCENDING"));
        mNewsArray.size();
        return mNewsArray;
    }
























}












