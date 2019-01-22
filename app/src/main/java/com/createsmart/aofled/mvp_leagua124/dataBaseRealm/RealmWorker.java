package com.createsmart.aofled.mvp_leagua124.dataBaseRealm;

import android.content.Context;


import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by n.kozhuro on 16.06.2017.
 */

public class RealmWorker {

    private Realm realm;

    public RealmWorker(Context context, String name) {
        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(name)
                .deleteRealmIfMigrationNeeded()
                .build();
        // Создать новый пустой экземпляр Realm
        realm = Realm.getInstance(realmConfiguration);
    }


    public void insertData (ArrayList data) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(data); //закидываем все данные
        realm.commitTransaction();
    }


    public void deleteData() {
        realm.beginTransaction();
        realm.deleteAll(); //удаляем все данные
        realm.commitTransaction();
    }


    public ArrayList getData (RealmObject object, String fildname, String sort) {
        ArrayList result = new ArrayList<>();
        RealmResults results = realm.where(object.getClass()).sort(fildname, Sort.valueOf(sort)).findAll();
        result.addAll(results);
        return result;
    }









}
