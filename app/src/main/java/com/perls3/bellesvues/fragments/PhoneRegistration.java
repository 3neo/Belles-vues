package com.perls3.bellesvues.fragments;

import android.content.Context;
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
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.perls3.bellesvues.R;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static androidx.navigation.Navigation.findNavController;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhoneRegistration#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhoneRegistration extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button btn;
    private EditText editText;
    private EditText editText2;
    private ProgressBar progressBar;
    private boolean clickmemory;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RC_SIGN_IN = 707;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText phoneNumber;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthCredential credential;
    public static String userPhoneNumber;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    public static String mVerificationId;

    public PhoneRegistration() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhoneRegistration.
     */
    // TODO: Rename and change types and number of parameters
    public static PhoneRegistration newInstance(String param1, String param2) {
        PhoneRegistration fragment = new PhoneRegistration();
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
            mAuth = FirebaseAuth.getInstance();
            mAuth.setLanguageCode("fr");
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String phone = requireActivity().getPreferences(Context.MODE_PRIVATE).getString("phoneNumber", null);
        if (phone!=null){
            NavDirections action = PhoneRegistrationDirections.actionPhoneRegistrationToAlertFragment2();
            findNavController(requireView()).navigate(action);

        }
        btn = (Button) view.findViewById(R.id.btx);
        btn.setOnClickListener(this);
        editText = (EditText) view.findViewById(R.id.edi);
        editText.setText(R.string.phoneprefix);
        progressBar = (ProgressBar) view.findViewById(R.id.prog);

    }

    // 2 - Launch Sign-In Activity


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");

                            userPhoneNumber = task.getResult().getUser().getPhoneNumber();
                            NavDirections action = PhoneRegistrationDirections.actionPhoneRegistrationToAlertFragment2();
                            findNavController(requireView()).navigate(action);

                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(requireContext(), "signInWithCredential:failure", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        String str = editText.getText().toString();
        Pattern pattern = Pattern.compile("((\\+|00)216)?([2579][0-9]{7}|(3[012]|4[01]|8[0128])[0-9]{6}|42[16][0-9]{5})", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        boolean matchFound = matcher.find();
        if (matchFound){
            progressBar.setVisibility(View.VISIBLE);
            mAuth = FirebaseAuth.getInstance();
            mAuth.setLanguageCode("fr");
            try {
                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber(str)       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(requireActivity())                 // Activity (for callback binding)
                                .setCallbacks(mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                        signInWithPhoneAuthCredential(phoneAuthCredential);
                                        NavDirections action = PhoneRegistrationDirections.actionPhoneRegistrationToAlertFragment2();
                                        try {
                                            findNavController(requireView()).navigate(action);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String verificationId,
                                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                        // The SMS verification code has been sent to the provided phone number, we
                                        // now need to ask the user to enter the code and then construct a credential
                                        // by combining the code with a verification ID.

                                        // Save verification ID and resending token so we can use them later
                                        mVerificationId = verificationId;
                                        mResendToken = token;
                                        NavDirections action = PhoneRegistrationDirections.actionPhoneRegistrationToCodeFb();
                                        findNavController(requireView()).navigate(action);


                                        // [START_EXCLUDE]
                                        // Update UI
                                        // [END_EXCLUDE]
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        // This callback is invoked in an invalid request for verification is made,
                                        // for instance if the the phone number format is not valid.
                                        Log.w("tAG", "onVerificationFailed", e);
                                        Toast.makeText(requireActivity(), "votre connexion a échoué ", Toast.LENGTH_SHORT).show();

                                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                            // Invalid request
                                            // ...

                                        } else if (e instanceof FirebaseTooManyRequestsException) {


                                        }


                                    }
                                })          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);


            }catch (IllegalArgumentException b){
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(requireActivity(), "numéro de téléphone invalide", Toast.LENGTH_SHORT).show();
                b.printStackTrace();
            }

        }else {
            Toast.makeText(requireActivity(), "numéro de téléphone invalide", Toast.LENGTH_SHORT).show();
        }






    }
}
