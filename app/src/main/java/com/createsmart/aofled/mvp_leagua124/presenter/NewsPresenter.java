package com.createsmart.aofled.mvp_leagua124.presenter;



import com.createsmart.aofled.mvp_leagua124.contractInterface.NewsContract;
import com.createsmart.aofled.mvp_leagua124.model.News;


import java.util.ArrayList;

public class NewsPresenter
        implements NewsContract.NewsPresenter,
        NewsContract.NewsRepository.OnFinishedListener {


    private NewsContract.NewsView newsView; //экзампляр вьюхи
    private NewsContract.NewsRepository mainlogics; //экземпляр модели

    //конструктор
    public NewsPresenter(NewsContract.NewsView newsView) {
        this.newsView = newsView;
        this.mainlogics =new com.createsmart.aofled.mvp_leagua124.repository.NewsRepository(newsView.getActivityContext()); //передаем контекст
    }




    @Override //отправляем данные для проверки на конец списка
    public void onScrolledRecyler(int visibleItemCount, int totalItemCount, int firstVisibleItems){
        mainlogics.onScrollRecyler(visibleItemCount, totalItemCount, firstVisibleItems, this);
    }


    @Override //удаляем прогрессбар
    public void showPgBottom(ArrayList<News> mNewsArray) {
        if(newsView != null){
            newsView.showProgressBottom(mNewsArray);
            mainlogics.downloadNewsEver(this);
        }
    }


    @Override //запрос на получение данных
    public void requestData() {
        mainlogics.downloadNews(this);
    }


    @Override //данные получены
    public void onFinishedLoadingNews(ArrayList<News> mNewsArray) {
        if(newsView != null){
            newsView.setDataToRecyclerView(mNewsArray);
            newsView.hideProgressCen();
            newsView.hideSwipe();
        }
    }


    @Override //данные получены
    public void onFinishedLoadingNewsEver(ArrayList<News> mNewsArray) {
        if(newsView != null){
            newsView.addDataToRecyclerView(mNewsArray);
        }
    }


    @Override //получение сообщения
    public void onMessage(String message) {
        if(newsView != null){
            newsView.showMessage(message);
        }
    }


    @Override //получение ошибки
    public void onFailure(Throwable t) {
        if(newsView != null){
           // newsView.onResponseFailure(t);
        }
    }






    @Override //уничтожаем презентер
    public void onDestroy() {
        newsView = null;
    }


}
