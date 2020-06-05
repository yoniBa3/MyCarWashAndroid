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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.yoni.a2020_02_07mycarwashclient.R;
import com.yoni.a2020_02_07mycarwashclient.exceptions.MinLengthExeption;
import com.yoni.a2020_02_07mycarwashclient.exceptions.PasswordsNotTheSame;
import com.yoni.a2020_02_07mycarwashclient.utils.MyUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFrag extends Fragment {


    Context context;
    EditText etUserEmail ,etUserPass ,etUserRePass;
    Button btnRegister;
    ProgressBar pb;
    FragmentManager fm;
    ImageView imageView;






    public RegisterFrag() {
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etUserEmail = view.findViewById(R.id.etRegUserEmail);
        etUserPass = view.findViewById(R.id.etRegUserPass);
        etUserRePass = view.findViewById(R.id.etRegUserRePass);
        pb = getActivity().findViewById(R.id.pbLoader);
        fm = getActivity().getSupportFragmentManager();

        /*imageView = getActivity().findViewById(R.id.ivGetIn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().findViewById(R.id.logAndRegLayout).setVisibility(View.GONE);
                getActivity().findViewById(R.id.bigContainer).setVisibility(View.VISIBLE);
                FragmentTransaction ft = fm.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("email",etUserEmail.getText().toString());
                RegisterFragFace2 fragFace2 = new RegisterFragFace2();
                fragFace2.setArguments(bundle);
                ft.replace(R.id.bigContainer,fragFace2)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });
*/
        btnRegister = getActivity().findViewById(R.id.btnLogAndReg);
        btnRegister.setText("Register");
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();

            }
        });
    }

    private void registerUser() {
        String email =etUserEmail.getText().toString();
        String pass = etUserPass.getText().toString();
        String rePass = etUserRePass.getText().toString();

        try {
            MyUtils.checkMinLength(email,4);
            MyUtils.checkMinLength(pass,4);
            MyUtils.checkIfPasswordsAreTheSame(pass,rePass);

            Bundle bundle = new Bundle();
            bundle.putString("email",email);
            bundle.putString("pass",pass);

            getActivity().findViewById(R.id.logAndRegLayout).setVisibility(View.INVISIBLE);
            getActivity().findViewById(R.id.bigContainer).setVisibility(View.VISIBLE);

            RegisterFragFace2 face2 = new RegisterFragFace2();
            face2.setArguments(bundle);
            FragmentTransaction ft = fm.beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.bigContainer,face2)
                    .commit();

            //registerUserBackEndless();
        } catch (MinLengthExeption | PasswordsNotTheSame e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUserBackEndless() {
        pb.setVisibility(View.VISIBLE);
        Backendless.initApp(context,MyUtils.APP_ID,MyUtils.APP_KEY);
        BackendlessUser user = new BackendlessUser();
        user.setProperty("email",etUserEmail.getText().toString());
        user.setPassword(etUserPass.getText().toString());


        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                pb.setVisibility(View.INVISIBLE);
                Toast.makeText(context, "success ", Toast.LENGTH_SHORT).show();

                getActivity().findViewById(R.id.logAndRegLayout).setVisibility(View.INVISIBLE);
                getActivity().findViewById(R.id.bigContainer).setVisibility(View.VISIBLE);

                Bundle bundle = new Bundle();
                bundle.putString("email" ,etUserEmail.getText().toString());
                RegisterFragFace2 face2 = new RegisterFragFace2();
                face2.setArguments(bundle);
                FragmentTransaction ft = fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.bigContainer,face2)
                        .commit();

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                pb.setVisibility(View.INVISIBLE);
                Toast.makeText(context, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
