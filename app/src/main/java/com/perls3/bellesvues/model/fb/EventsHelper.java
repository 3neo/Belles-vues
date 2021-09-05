package com.perls3.bellesvues.model.fb;

import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EventsHelper  {
  private  FirebaseFirestore db ;
  private ArrayList<String> listFinalImage;
  private ArrayList<String> listFinalText;

    public EventsHelper() {
        db =FirebaseFirestore.getInstance();
        listFinalImage=new ArrayList<>();
        listFinalText=new ArrayList<>();
    }
    @Keep
    public void getEvents(){

        db.collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<EventsPOJO> list = task.getResult().toObjects(EventsPOJO.class);
                            for (EventsPOJO event : list) {
                                String str = event.getEventImageUrl();
                                listFinalImage.add(str);
                                String str2 = event.getEvent();
                                listFinalText.add(str2);
                            }



                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });


    }


    public FirebaseFirestore getDb() {
        return db;
    }

    public ArrayList<String> getListFinalImage() {
        return listFinalImage;
    }

    public ArrayList<String> getListFinalText() {
        return listFinalText;
    }
}
