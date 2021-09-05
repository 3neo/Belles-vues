package com.perls3.bellesvues.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.perls3.bellesvues.R;
import com.perls3.bellesvues.services.NotificationsService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlertResolvedPicture#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlertResolvedPicture extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    public AlertResolvedPicture() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static AlertResolvedPicture newInstance() {

        return new AlertResolvedPicture();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imageView = requireView().findViewById(R.id.alertResopicture);
        Glide.with(this).load(NotificationsService.imagesolvedURL).into(imageView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alert_resolved_picture2, container, false);
    }


}