package com.perls3.bellesvues.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.perls3.bellesvues.R;
import com.perls3.bellesvues.model.fb.BenevoleHelper;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BenevoleReg#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BenevoleReg extends Fragment implements View.OnClickListener {
    private ProgressBar progressBar;
    private Button button;
    private EditText editTextNom;
    private EditText editTextPrenom;
    private EditText editTextprofeession;
    private EditText editTextAge;
    private BenevoleHelper benevoleHelper;
    private String userEmail;
    private String phoneNumbe;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BenevoleReg() {
        // Required empty public constructor
    }

    public static BenevoleReg newInstance(String param1, String param2) {
        BenevoleReg fragment = new BenevoleReg();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_benevole_reg, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = (Button) view.findViewById(R.id.btx333);
        editTextNom = (EditText) view.findViewById(R.id.ed1);
        editTextPrenom = (EditText) view.findViewById(R.id.ed2);
        editTextprofeession = (EditText) view.findViewById(R.id.ed3);
        editTextAge = (EditText) view.findViewById(R.id.ed4);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarBenevole);
        button.setOnClickListener(this);
        benevoleHelper = new BenevoleHelper(requireContext());
        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        userEmail = sharedPref.getString("UserEmail", "");
        phoneNumbe = sharedPref.getString("phoneNumber", "");
    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        Map<String, Object> collection = benevoleHelper.prepareData(String.valueOf(editTextNom.getText()),
                String.valueOf(editTextPrenom.getText()),
                String.valueOf(editTextprofeession.getText()),
                String.valueOf(editTextAge.getText()),
                phoneNumbe,
                userEmail
        );
        benevoleHelper.uploadDocument(collection, progressBar);


    }
}