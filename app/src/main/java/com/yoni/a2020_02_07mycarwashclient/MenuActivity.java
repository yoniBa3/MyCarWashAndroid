package com.yoni.a2020_02_07mycarwashclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.yoni.a2020_02_07mycarwashclient.utils.MyUtils;

public class MenuActivity extends AppCompatActivity {

    Context context;
    TextView tvUserName;
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setPointer();
    }

    private void setPointer() {
        this.context = this;
        tvUserName = findViewById(R.id.tvMenuUserName);
        name = (String) MyUtils.user.getProperty("name");

        tvUserName.setText("שלום " + name);

        findViewById(R.id.btnMakeApoitment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, QueueList.class));
            }
        });

        findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Backendless.UserService.logout(new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {
                        Toast.makeText(context, "להתראות " + name, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });
            }
        });

        findViewById(R.id.btnCancelApointment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CancelAppointmentActivity.class));
                finish();
            }
        });
    }
}
