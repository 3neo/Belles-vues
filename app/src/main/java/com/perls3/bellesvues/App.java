package com.perls3.bellesvues;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.perls3.bellesvues.di.component.ApplicationComponent;
import com.perls3.bellesvues.di.component.DaggerApplicationComponent;
import com.perls3.bellesvues.model.db.AppDataBase;

public class App extends Application {
    public static Context context;

    FirebaseApp firebaseApp;
    ApplicationComponent appComponent;
   public static AppDataBase db;


    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        //Room singleton instance
         db= AppDataBase.getDatabase( getApplicationContext());

        // Reference to the application graph that is used across the whole app
         appComponent =com.perls3.bellesvues.di.component.DaggerApplicationComponent.create();

        firebaseApp= FirebaseApp.getInstance();





    }
}
