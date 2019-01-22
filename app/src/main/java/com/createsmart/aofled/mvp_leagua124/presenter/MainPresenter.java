package com.createsmart.aofled.mvp_leagua124.presenter;

import com.createsmart.aofled.mvp_leagua124.contractInterface.MainContract;
import com.createsmart.aofled.mvp_leagua124.repository.MainRepository;


public class MainPresenter
        implements MainContract.MainPresenter,
        MainContract.MainRepository.OnListener{


    private MainContract.MainView mainView; //экзампляр вьюхи
    private MainContract.MainRepository mainRepository; //экземпляр модели


    //конструктор
    public MainPresenter (MainContract.MainView mainView) {
        this.mainView=mainView;
        this.mainRepository =new MainRepository(mainView.getActivityContext()); //передаем контекст
    }


    @Override //выход из приложения
    public void buttonExitApp () {
        mainRepository.exitApp(this);
    }



    @Override //выбор загружаемого фрагмента
    public void setFragment(int position) {
        if(mainView != null) {
            mainView.setFragment(mainRepository.onSetFragment(position));
        }
    }



    @Override //получение сообщения
    public void onMessage(String message) {
        if(mainView != null){
            mainView.showMessage(message);
        }
    }


    @Override //надо закрыть активити
    public void onFinishApp() {
        if(mainView != null){
            mainView.finishActivity();
        }
    }








    @Override
    public void onDestroy() {
        mainView = null;
    }
}
