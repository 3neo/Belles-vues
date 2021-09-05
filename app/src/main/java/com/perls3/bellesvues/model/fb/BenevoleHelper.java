package com.perls3.bellesvues.model.fb;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * BenevoleHelper is a class used to upload Benevole collections to Firebase web service
 */

public class BenevoleHelper {
   private FirebaseFirestore db ;
   private Context context;

    public BenevoleHelper(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();

    }


    public Map<String, Object> prepareData(String nom, String prenom, String profession, String age,String telephoneBenevole, String emailBenevole){
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("nom",nom);
        user.put("prenom", prenom);
        user.put("profession", profession);
        user.put("age", age);
        user.put("telephoneBenevole",telephoneBenevole );
        user.put("emailBenevole",emailBenevole );
        return user;
    }

    public void uploadDocument(Map<String, Object> benevole, ProgressBar progressBar){

        db.collection("benevoles")
                .add(benevole)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(context, "Bénévole ajouté ! on vous contactera si besoin .", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                        Toast.makeText(context, "Vous avez un probleme de connexion !", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);


                    }
                });


    }
}
