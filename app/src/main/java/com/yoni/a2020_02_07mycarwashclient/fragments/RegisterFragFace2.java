package com.yoni.a2020_02_07mycarwashclient.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.yoni.a2020_02_07mycarwashclient.R;
import com.yoni.a2020_02_07mycarwashclient.exceptions.MinLengthExeption;
import com.yoni.a2020_02_07mycarwashclient.exceptions.NotCarNumber;
import com.yoni.a2020_02_07mycarwashclient.exceptions.NotPhoneNumber;
import com.yoni.a2020_02_07mycarwashclient.users.UserInfo;
import com.yoni.a2020_02_07mycarwashclient.utils.MyUtils;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragFace2 extends Fragment {

    Context context;
    EditText etCarNum ,etUserTell ,etUserName;
    String email = "" ,pass = "";
    ProgressBar pb;
    FragmentManager fm;

    public RegisterFragFace2() {
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
        return inflater.inflate(R.layout.fragment_register_frag_fase2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etCarNum = view.findViewById(R.id.etRegUserCarNum);
        etUserName = view.findViewById(R.id.etRegUserName);
        etUserTell = view.findViewById(R.id.etRegUserTell);
        pb = view.findViewById(R.id.pbLoaderRegFace2);
        fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        assert getArguments() != null;
        email = getArguments().getString("email");
        pass = getArguments().getString("pass");

        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().findViewById(R.id.logAndRegLayout).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.bigContainer).setVisibility(View.GONE);
            }
        });

        view.findViewById(R.id.btnRegisterUserInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MyUtils.checkCarNumber(etCarNum.getText().toString());
                    MyUtils.checkTellNumber(etUserTell.getText().toString());
                    MyUtils.checkMinLength(etUserName.getText().toString(),2);
                    pb.setVisibility(View.VISIBLE);

                    registerUserToBackendless();

                    //addNewClient();
                } catch (NotCarNumber | NotPhoneNumber | MinLengthExeption e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();;
                }

            }
        });


        getActivity().findViewById(R.id.ivGetIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFrag loginFrag = new LoginFrag();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container,loginFrag)
                        .commit();
                getActivity().findViewById(R.id.bigContainer).setVisibility(View.GONE);
                getActivity().findViewById(R.id.logAndRegLayout).setVisibility(View.VISIBLE);

            }
        });
    }

    private void registerUserToBackendless() {
        Backendless.initApp(context,MyUtils.APP_ID,MyUtils.APP_KEY);
        BackendlessUser user = new BackendlessUser();
        user.setEmail(email);
        user.setPassword(pass);
        user.setProperty("lp",etCarNum.getText().toString().trim());
        user.setProperty("tel",etUserTell.getText().toString().trim());
        user.setProperty("name",etUserName.getText().toString());

        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                Toast.makeText(context, "Success registering", Toast.LENGTH_SHORT).show();

                pb.setVisibility(View.INVISIBLE);

                // TODO: move to login frag
                LoginFrag loginFrag = new LoginFrag();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container,loginFrag)
                        .commit();
                Objects.requireNonNull(getActivity()).findViewById(R.id.bigContainer).setVisibility(View.GONE);
                getActivity().findViewById(R.id.logAndRegLayout).setVisibility(View.VISIBLE);


            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(context, "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addNewClient() {
        Backendless.initApp(context , MyUtils.APP_ID,MyUtils.APP_KEY);

        UserInfo userInfo = new UserInfo();
        userInfo.setCarNumber(etCarNum.getText().toString());
        userInfo.setName(etUserName.getText().toString());
        userInfo.setPhoneNumber(etUserTell.getText().toString());
        userInfo.setEmail(email);
        Backendless.Data.of(UserInfo.class).save(userInfo, new AsyncCallback<UserInfo>() {
            @Override
            public void handleResponse(UserInfo response) {

                pb.setVisibility(View.INVISIBLE);

                // TODO: move to login frag
                LoginFrag loginFrag = new LoginFrag();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container,loginFrag)
                        .commit();
                Objects.requireNonNull(getActivity()).findViewById(R.id.bigContainer).setVisibility(View.GONE);
                getActivity().findViewById(R.id.logAndRegLayout).setVisibility(View.VISIBLE);



            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(context, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
