package com.yoni.a2020_02_07mycarwashclient.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.yoni.a2020_02_07mycarwashclient.MenuActivity;
import com.yoni.a2020_02_07mycarwashclient.QueueList;
import com.yoni.a2020_02_07mycarwashclient.R;
import com.yoni.a2020_02_07mycarwashclient.exceptions.MinLengthExeption;
import com.yoni.a2020_02_07mycarwashclient.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFrag extends Fragment {

    public static final int PER_GPS_CODE = 100;
    Context context;
    EditText etUserMail ,etUserPass;
    Button btnLogin;
    ProgressBar pb;

    public LoginFrag() {
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkForPermissions();

        etUserMail = view.findViewById(R.id.etLogUserEmail);
        etUserPass = view.findViewById(R.id.etLogUserPass);
        pb = getActivity().findViewById(R.id.pbLoader);

        btnLogin = getActivity().findViewById(R.id.btnLogAndReg);
        btnLogin.setText("Login");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
            }
        });

        getActivity().findViewById(R.id.ivGetIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,QueueList.class));
            }
        });

    }

    private void checkForPermissions() {

        int fineGps = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);

        int coarselGps = ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION);

        int call = ContextCompat.checkSelfPermission(context,Manifest.permission.CALL_PHONE);
        List<String>neededPermission = new ArrayList<>();

        if (fineGps!= PackageManager.PERMISSION_GRANTED || coarselGps != PackageManager.PERMISSION_GRANTED){
            neededPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            neededPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);

        }

        if (call != PackageManager.PERMISSION_GRANTED){
            neededPermission.add(Manifest.permission.CALL_PHONE);
        }

        if (!neededPermission.isEmpty()){
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),neededPermission.toArray(new String[0]), PER_GPS_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void checkUser() {
        try {
            MyUtils.checkMinLength(etUserMail.getText().toString(),4);
            MyUtils.checkMinLength(etUserPass.getText().toString(),4);
            pb.setVisibility(View.VISIBLE);
            checkIfUserExists();
        } catch (MinLengthExeption e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkIfUserExists() {
        Backendless.initApp(context, MyUtils.APP_ID,MyUtils.APP_KEY);
        Backendless.UserService.login(etUserMail.getText().toString(), etUserPass.getText().toString(), new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                pb.setVisibility(View.INVISIBLE);
                Toast.makeText(context, "Welcome back", Toast.LENGTH_SHORT).show();

                //todo go to the new activity
                MyUtils.user = response;
                startActivity(new Intent(context, MenuActivity.class));

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(context, fault.getMessage(), Toast.LENGTH_SHORT).show();
                pb.setVisibility(View.INVISIBLE);
            }
        },true);
    }

    @Override
    public void onDetach() {
        btnLogin = null;
        super.onDetach();
    }
}
