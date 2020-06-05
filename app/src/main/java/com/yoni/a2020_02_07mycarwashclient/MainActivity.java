package com.yoni.a2020_02_07mycarwashclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yoni.a2020_02_07mycarwashclient.fragments.LoginFrag;
import com.yoni.a2020_02_07mycarwashclient.fragments.RegisterFrag;

public class MainActivity extends AppCompatActivity {

    Context context;
    FragmentManager fm;
    TextView btnLogin, btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPointer();
    }

    private void setPointer() {
        this.context = this;
        fm = getSupportFragmentManager();


        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);


        changeFrag(new LoginFrag());



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    changeFrag(new LoginFrag());
                    btnLogin.setTextColor(getResources().getColor(R.color.YankeesBlue));
                    btnRegister.setTextColor(Color.BLACK);

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    changeFrag(new RegisterFrag());
                    btnRegister.setTextColor(getResources().getColor(R.color.YankeesBlue));
                    btnLogin.setTextColor(Color.BLACK);
                }

        });
    }


    private void changeFrag(Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
