package com.perls3.bellesvues.fragments;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.perls3.bellesvues.R;
import com.perls3.bellesvues.model.fb.EventsHelper;
import com.perls3.bellesvues.model.fb.FbRequest;
import com.perls3.bellesvues.services.MyService;
import com.perls3.bellesvues.utilies.MyWindowInfoAdapter;
import com.perls3.bellesvues.view_models.AlertViewModel;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AlertFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {
    @Keep
    private static final String TAG = AlertFragment.class.getSimpleName();
    View view2;
    public static EventsHelper eventsHelper;

    // Used in checking for runtime permissions.
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    // The BroadcastReceiver used to listen from broadcasts from the service.

    // A reference to the service used to get location updates.
    private MyService mService = null;

    // Tracks the bound state of the service.
    private boolean mBound = false;


    private static final int CAMERA_REQUEST_CODE = 1888;

    private AlertViewModel mViewModel;
    FloatingActionButton button;

    //  Fragment fragmentManager = getParentFragmentManager().findFragmentByTag("theMapFragmentObject");


    public static AlertFragment newInstance() {
        return new AlertFragment();
    }

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Keep
    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(getContext(), MyService.class);
        //  requireActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
       /* ExecutorService executorService = new ThreadPoolExecutor(4, 4, 1000,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                mService.startLocationUpdates();

            }
        });*/
        requireActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);


    }

    @Keep
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AlertViewModel.class);
        View view = inflater.inflate(R.layout.alert_fragment, container, false);
        DrawerLayout drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer);
        androidx.appcompat.widget.Toolbar mToolbar = (androidx.appcompat.widget.Toolbar) view.findViewById(R.id.toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, mToolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = view.findViewById(R.id.floatingActionButton2);
        button.setOnClickListener(this);
        sharedPreferences = requireContext().getSharedPreferences("MI1", MODE_PRIVATE);
        try {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.gogo);
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        eventsHelper = new EventsHelper();
        eventsHelper.getEvents();
        Toast.makeText(requireContext(), "cliquez sur les marqueurs de la carte pour visualiser les anomalies correspondantes ", Toast.LENGTH_LONG).show();

    }


    @Override
    public void onClick(View view) {
        takingLastLocation();


    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                requireActivity(),
                new String[]{Manifest.permission.CAMERA},
                CAMERA_REQUEST_CODE
        );
    }

    private void takingLastLocation() {
        if (!hasCameraPermission()) {
            //   shouldShowRequestPermissionRationale(); return true if user denied permission but never select never ask again
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CAMERA))
                requestPermission();


            else {
                boolean userAskedPermissionBefore = sharedPreferences.getBoolean("USER_ASKED_CAMERA_PERMISSION_BEFORE",
                        false);
                if (userAskedPermissionBefore) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
                    alertDialogBuilder.setMessage("Votre permission pour utilise le camera  est obligatoire ");
                    alertDialogBuilder.setPositiveButton("accéder aux reglages", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getActivity().getPackageName(),
                                    "AlertFragment");
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
                    editor.putBoolean("USER_ASKED_CAMERA_PERMISSION_BEFORE", true);
                    editor.apply();
                }

            }


        } else {
            NavDirections action = AlertFragmentDirections.actionAlertFragment2ToPictureFragment();


            Navigation.findNavController(requireView()).navigate(action);
        }

    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            mService.requestLocationUpdates();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            mService = null;

            Log.e("lavieestbelle", "connection failed");

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        // requireActivity().unbindService(connection);
        //mBound=false;
    }

    @Keep
    @Override
    public void onMapReady(GoogleMap googleMap) {


        // [START_EXCLUDE silent]
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        // [END_EXCLUDE]
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<FbRequest> fullAlerts = task.getResult().toObjects(FbRequest.class);
                            try {


                                for (FbRequest fbRequest : fullAlerts) {
                                    double latitude = fbRequest.getLatitude();
                                    double longitude = fbRequest.getLongitude();
                                    LatLng variableMarker = new LatLng(latitude, longitude);
                                    googleMap.addMarker(new MarkerOptions()
                                            .position(variableMarker));

                                }
                            } finally {
                                LatLng municipaliteDeTunis = new LatLng(36.73369623827995, 10.20946522049592);
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(municipaliteDeTunis));
                                googleMap.addMarker(new MarkerOptions()
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.download))
                                        .position(municipaliteDeTunis));

                                googleMap.setMinZoomPreference(17.0f);

                            }


                            //  LatLng municipaliteMourouj = new LatLng(36.73373552059186, 10.209468562203034);

                            //  googleMap.moveCamera(CameraUpdateFactory.newLatLng(municipaliteMourouj));
                            // [START_EXCLUDE silent]


                        }
                    }
                    //     googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


                });
        googleMap.setInfoWindowAdapter(new MyWindowInfoAdapter(requireContext()));
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected())
                    try {
                        marker.showInfoWindow();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                else
                    Toast.makeText(requireContext(), "vous n'êtes pas connecté !", Toast.LENGTH_SHORT).show();


                return true;
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.benevole:
                NavDirections action = AlertFragmentDirections.actionAlertFragment2ToBenevoleReg();
                Navigation.findNavController(requireView()).navigate(action);
                break;

            //add here the ather destination
            case R.id.propos:
                NavDirections action2 = AlertFragmentDirections.actionAlertFragment2ToApropos();
                Navigation.findNavController(requireView()).navigate(action2);
                break;

            case R.id.evenements:

                NavDirections action3 = AlertFragmentDirections.actionAlertFragment2ToEvents();
                Navigation.findNavController(requireView()).navigate(action3);

                break;
            case R.id.admin:

               NavDirections action5 = AlertFragmentDirections.actionAlertFragment2ToBlankFragment();
               Navigation.findNavController(requireView()).navigate(action5);

                break;
            case R.id.disconnect:
                FirebaseAuth.getInstance().signOut();
                NavDirections action4 = AlertFragmentDirections.actionAlertFragment2ToSplashScreen();
                Navigation.findNavController(requireView()).navigate(action4);


                break;


            case R.id.contact:
                Intent email = new Intent(Intent.ACTION_SENDTO);
                email.setData(Uri.parse("mailto:belles.vues.app@gmail.com"));
                email.putExtra(Intent.EXTRA_SUBJECT, "Utilsateur Belles Vues");
                email.putExtra(Intent.EXTRA_TEXT, "Bonjour Belles Vues ;");
                if (email.resolveActivity(requireContext().getPackageManager()) != null) {
                    startActivity(email);
                }


                break;
        }


        return false;
    }


}

