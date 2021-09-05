package com.perls3.bellesvues.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.perls3.bellesvues.R;
import com.perls3.bellesvues.view_models.SplashScreenViewModel;

import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class SplashScreen extends Fragment implements View.OnClickListener {

    private SplashScreenViewModel mViewModel;
    private VideoView mVideoView;
    private TextView textView;

    FirebaseAuth firebaseAuth;
    //FOR DATA
    // 1 - Identifier for Sign-In Activity
    private static final int RC_SIGN_IN = 123;

    public SplashScreen() {
    }


    public static SplashScreen newInstance() {
        return new SplashScreen();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.splash_screen_fragment, container, false);

        Button button = view.findViewById(R.id.loginBtn1);
        textView = (TextView) view.findViewById(R.id.propos);
        textView.setOnClickListener(this);

        button.setOnClickListener(this);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {

            NavDirections action = SplashScreenDirections.actionSplashScreenToPhoneRegistration();
            Navigation.findNavController(requireView()).navigate(action);
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.propos:
                NavDirections action = SplashScreenDirections.actionSplashScreenToApropos();
                Navigation.findNavController(requireView()).navigate(action);


                break;
            case R.id.loginBtn1:
                ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected())
                    this.startSignInActivity();
                else
                    Toast.makeText(requireContext(), "vous n'êtes pas connecté(e) !", Toast.LENGTH_SHORT).show();
                break;


        }


    }

    // 2 - Launch Sign-In Activity
    private void startSignInActivity() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());


// Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    // [START auth_fui_result]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("UserEmail", "" + user.getEmail());
                editor.putString("UserNoun", "" + user.getDisplayName());
                editor.apply();
                NavDirections action = SplashScreenDirections.actionSplashScreenToPhoneRegistration();
                Navigation.findNavController(requireView()).navigate(action);

                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...

            }
        }
    }

    // [END auth_fui_result]


}