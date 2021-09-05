package com.perls3.bellesvues.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.perls3.bellesvues.R;

import java.util.Objects;

import static androidx.navigation.Navigation.findNavController;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CodeFb#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CodeFb extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText editText;
    Button btx13;
    ProgressBar proggg;
  //  AuthCredential credential;

    public CodeFb() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CodeFb.
     */
    // TODO: Rename and change types and number of parameters
    public static CodeFb newInstance(String param1, String param2) {
        CodeFb fragment = new CodeFb();
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


        // credential = GoogleAuthProvider.getCredential(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getIdToken(true)), null);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_code_fb, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        proggg=(ProgressBar)view.findViewById(R.id.progressBar);
        editText = (EditText) view.findViewById(R.id.ediii);
        btx13=(Button)view.findViewById(R.id.Batooun);
        btx13.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        proggg.setVisibility(View.VISIBLE);
        String txt = String.valueOf(editText.getText());
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(PhoneRegistration.mVerificationId, txt);
        signInWithPhoneAuthCredential(credential);


    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
    FirebaseAuth    mAuth = FirebaseAuth.getInstance();
        try {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "signInWithCredential:success");
    
                                FirebaseUser user = task.getResult().getUser();


                                String PhoneNumber = Objects.requireNonNull(user).getPhoneNumber();
                                Log.e("tag","phone number is "+PhoneNumber);
                                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("phoneNumber",""+PhoneNumber);
                                editor.apply();
                                NavDirections action = CodeFbDirections.actionCodeFbToAlertFragment2();
                                findNavController(requireView()).navigate(action);
                                // ...
                            } else {
                                // Sign in failed, display a message and update the UI
                                Log.w("TAG", "signInWithCredential:failure", task.getException());
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    // The verification code entered was invalid
                                    Toast.makeText(requireContext(), "la verification a échoué", Toast.LENGTH_SHORT).show();
                                    proggg.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}