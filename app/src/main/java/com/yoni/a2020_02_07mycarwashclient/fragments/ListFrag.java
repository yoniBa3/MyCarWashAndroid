package com.yoni.a2020_02_07mycarwashclient.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.yoni.a2020_02_07mycarwashclient.DataClasses.Machon;
import com.yoni.a2020_02_07mycarwashclient.R;
import com.yoni.a2020_02_07mycarwashclient.adapter.QueueListAdapter;
import com.yoni.a2020_02_07mycarwashclient.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFrag extends Fragment {


    private static final int PER_GPS_REQUEST = 100;
    private Context context;
    private RecyclerView recyclerView;
    private boolean isGpsEnable = false;
    private LocationManager locationManager;
    private Location location;

    public ListFrag() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkPermissions();
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        if (isGpsEnable){
            getLocation();
        }

        /*Bundle bundle = getArguments();
        assert bundle != null;
        final String date = bundle.getString("date");*/

        final String date = "fddd" ;
        recyclerView = view.findViewById(R.id.rvQueueList);
        Backendless.initApp(context, MyUtils.APP_ID,MyUtils.APP_KEY);

        Backendless.Data.of(Machon.class).find(new AsyncCallback<List<Machon>>() {
            @Override
            public void handleResponse(List<Machon> response) {
                Log.e("Error",response.size()+"");
                Objects.requireNonNull(getActivity()).findViewById(R.id.layoytFragListUntillRecyeclerViewLoad).setVisibility(View.GONE);
                QueueListAdapter adapter = new QueueListAdapter(response,location,date);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(context, "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });







    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location1) {
                location = location1;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1_000,300,listener);

    }

    private void checkPermissions() {
        int localGps = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);

        int coarsGps = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);

        List<String> permissionNeeded = new ArrayList<>();

        if (localGps != PackageManager.PERMISSION_GRANTED || coarsGps != PackageManager.PERMISSION_GRANTED) {
            permissionNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            permissionNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        } else {
            isGpsEnable = true;

        }
        if (!permissionNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), permissionNeeded.toArray(new String[0]), PER_GPS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode== PER_GPS_REQUEST){
            isGpsEnable = grantResults[0]==PackageManager.PERMISSION_GRANTED;
        }
    }
}
