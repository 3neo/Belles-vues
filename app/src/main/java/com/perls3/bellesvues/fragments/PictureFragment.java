package com.perls3.bellesvues.fragments;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.perls3.bellesvues.R;
import com.perls3.bellesvues.services.MyService;
import com.perls3.bellesvues.utilies.CameraXmanipulation;
import com.perls3.bellesvues.view_models.AlertViewModel;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class PictureFragment extends Fragment implements View.OnClickListener {
    Location thefuckingfucklocation;




    private static final int LOCATION_REQUEST_CODE = 17;

    private AlertViewModel mViewModel;
    private FusedLocationProviderClient fusedLocationClient;
    private Location lastLocation;
    private SharedPreferences sharedPreferences;
    // The BroadcastReceiver used to listen from broadcasts from the service.
    private MyReceiver myReceiver;


    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(myReceiver,
                new IntentFilter(MyService.ACTION_BROADCAST));
        WifiManager wifiManager = (WifiManager)this.requireContext().getSystemService(Context.WIFI_SERVICE);
        if  (!wifiManager.isWifiEnabled()) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
            alertDialogBuilder.setMessage("vous devez obligatoirement activer le wifi sur votre appareil ! ");
            alertDialogBuilder.setPositiveButton("accéder aux reglages", (dialog, which) -> {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_WIFI_SETTINGS);
                requireContext().startActivity(intent);

            });
            alertDialogBuilder.create().show();


        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        sharedPreferences = requireContext().getSharedPreferences("MI", MODE_PRIVATE);
        myReceiver = new MyReceiver();

    }

    @Keep
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mViewModel = new ViewModelProvider(this).get(AlertViewModel.class);


        return inflater.inflate(R.layout.fragment_picture, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton camera_capture_button = view.findViewById(R.id.camera_capture_button);
        camera_capture_button.setOnClickListener(this);
        PreviewView previewView = requireView().findViewById(R.id.viewFinder);
        CameraXmanipulation.startCamera(previewView, ContextCompat.getMainExecutor(requireContext()), requireContext());



    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View view) {

        getLastLocation();
        LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);

            if(hasLocationPermission())
                if (locationManager.isLocationEnabled())
                CameraXmanipulation.takePicture(CameraXmanipulation.imageCapture, ContextCompat.getMainExecutor(requireContext()), requireView(), requireContext(), mViewModel,requireActivity());
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
                    alertDialogBuilder.setMessage("Votre demande n'a pas été envoyé ! vous devez obligatoirement  activer la localisation sur votre appareil et réessayer ");
                    alertDialogBuilder.setPositiveButton("accéder aux reglages", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            //  Uri uri = Uri.fromParts("package", reqContext.getPackageName(), "PictureFragment");
                            //  intent.setData(uri);
                            requireContext().startActivity(intent);

                        }
                    });
                    alertDialogBuilder.setNegativeButton("annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("LOCALISATION", "onClick: Cancelling");

                        }
                    });
                    alertDialogBuilder.create().show();
                }
            else takingLastLocation();









    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                requireActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_REQUEST_CODE
        );


    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void takingLastLocation() {
        if (!hasLocationPermission()) {
            //   shouldShowRequestPermissionRationale(); return true if user denied permission but never select never ask again
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
                requestPermission();


            else {
                boolean userAskedPermissionBefore = sharedPreferences.getBoolean("USER_ASKED_STORAGE_PERMISSION_BEFORE",
                        false);
                if (userAskedPermissionBefore) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
                    alertDialogBuilder.setMessage("Votre localisation est obligatoire ");
                    alertDialogBuilder.setPositiveButton("accéder aux reglages", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getActivity().getPackageName(),
                                    "PictureFragment");
                            intent.setData(uri);
                            getContext().startActivity(intent);

                        }
                    });
                    alertDialogBuilder.setNegativeButton("annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("LOCALISATION", "onClick: Cancelling");

                        }
                    });
                    alertDialogBuilder.create().show();
                } else {
                    requestPermission();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("USER_ASKED_STORAGE_PERMISSION_BEFORE", true);
                    editor.apply();
                }

            }

        } else
            getLastLocation();

    }
    private void getLastLocation() {
        try {
            fusedLocationClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                lastLocation = task.getResult();
                                Log.w(TAG, "we  get the  location.");

                            } else {
                                Log.w(TAG, "Failed to get location.");
                            }
                        }
                    });
        } catch (SecurityException unlikely) {
            Log.e(TAG, "Lost location permission." + unlikely);
        }
    }
    /**
     * Receiver for broadcasts sent by {@link MyService}.
     */
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(MyService.EXTRA_LOCATION);
            if (location != null) {
                Log.e("broadcastreceiver","location!=null");
                 thefuckingfucklocation = location;
                //
                //

            }
        }
    }


}