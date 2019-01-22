package com.createsmart.aofled.mvp_leagua124.activity;

import android.content.Context;
import android.os.Bundle;

import com.createsmart.aofled.mvp_leagua124.MyApplication;
import com.createsmart.aofled.mvp_leagua124.R;
import com.createsmart.aofled.mvp_leagua124.contractInterface.MainContract;



import com.createsmart.aofled.mvp_leagua124.presenter.MainPresenter;

import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MainContract.MainView{


    private long last_time; //время для выхода (нажмите два раза)

    private Context context;

    private MainContract.MainPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        context = MainActivity.this;
        initComponents();
        presenter = new MainPresenter(this);//создаем экземпляр презентера



        presenter.setFragment(1);
    }

    //инициализируем компоненты
    private void initComponents() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public Context getActivityContext() {
        return context;
    }



    @Override //уничтожение активности
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy(); //вызываем метод уничтожение презентера
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_news) {
            presenter.setFragment(0);
        } else if (id == R.id.nav_standings) {
            presenter.setFragment(1);
        } else if (id == R.id.nav_teams) {
            presenter.setFragment(2);
        }  else if (id == R.id.nav_players) {
            presenter.setFragment(3);
        } else if (id == R.id.nav_exit) { //выход
            super.finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }






    @Override
    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();



        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }








    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        presenter.buttonExitApp();
    }


    @Override //тост для оповещения пользователя
    public void finishActivity(){
        finish();
    }




    @Override //тост для оповещения пользователя
    public void showMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show(); //
    }




}
