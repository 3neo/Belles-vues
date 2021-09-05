package com.perls3.bellesvues.utilies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.perls3.bellesvues.App;
import com.perls3.bellesvues.R;
import com.perls3.bellesvues.model.fb.FbRequest;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MyWindowInfoAdapter implements GoogleMap.InfoWindowAdapter {


    private final Context context;
    private final FbRequest fbreq;

    public MyWindowInfoAdapter(Context context) {
        this.context = context;
        fbreq = new FbRequest();

    }
    @Keep

    @Override
    public View getInfoWindow(Marker marker) {
        View view2 = LayoutInflater.from(context).inflate(R.layout.window_info_map, null);
        ImageView imageView=(ImageView)view2.findViewById(R.id.logo);
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        Task<QuerySnapshot> collectionReference = db.collection("users")
                .whereEqualTo("latitude", marker.getPosition().latitude)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //    List<FbRequest> reqList=   task.getResult().toObjects(FbRequest.class);
                            //     String string = reqList.get(0).getPictureURL();
                            //  Glide.with(context).load(reqList.get(0).getPictureURL()).into((ImageView) view2.findViewById(R.id.logo));

                            //     textView.setText(string);

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (collectionReference.getResult()!=null) {
            List<FbRequest> reqList = collectionReference.getResult().toObjects(FbRequest.class);
            String string = null;
            try {
                string = reqList.get(0).getPictureURL();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Glide.with(context).load(string).into(imageView);
        } else Toast.makeText(App.context,"votre connection est faible ! essayer encore une foi",Toast.LENGTH_SHORT).show();

        return view2;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;


    }
}
