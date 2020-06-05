package com.yoni.a2020_02_07mycarwashclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;
import com.yoni.a2020_02_07mycarwashclient.utils.MyUtils;

import java.util.Calendar;

public class EnterActivity extends AppCompatActivity {

    Context context;
    TextView tvLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        setPointer();
    }

    private void setPointer() {
        this.context = this;
        tvLoad = findViewById(R.id.textView2);
        tvLoad.setText("Checking logging credentials...pleas wait ");

        Backendless.initApp(context, MyUtils.APP_ID,MyUtils.APP_KEY);
        Backendless.UserService.isValidLogin(new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response) {
                if (response) {
                    String userObjectId = UserIdStorageFactory.instance().getStorage().get();
                    tvLoad.setText("Logging you in...please wait...");
                    Backendless.Data.of(BackendlessUser.class).findById(userObjectId, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            MyUtils.user = response;
                            startActivity(new Intent(context, MenuActivity.class));
                            finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                        }
                    });
                }else{
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(context,MainActivity.class));
                            finish();
                        }
                    },3_000);

                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

    }
}
