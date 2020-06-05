package com.yoni.a2020_02_07mycarwashclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.yoni.a2020_02_07mycarwashclient.DataClasses.Wash;
import com.yoni.a2020_02_07mycarwashclient.adapter.CancelAppointmentAdapter;

import java.util.List;

public class CancelAppointmentActivity extends AppCompatActivity {

    Context context;
    LinearLayout untilLoading;
    LinearLayout comleteLoading;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_appointment);

        setPointer();
    }

    private void setPointer() {
        this.context = this;
        untilLoading = findViewById(R.id.untilLoading);
        comleteLoading = findViewById(R.id.completeLoading);
        untilLoading.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.cancelAppointmentRecycleView);

        Backendless.Data.of(Wash.class).find(new AsyncCallback<List<Wash>>() {
            @Override
            public void handleResponse(List<Wash> response) {
                untilLoading.setVisibility(View.GONE);
                CancelAppointmentAdapter appointmentAdapter = new CancelAppointmentAdapter(response);
                recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
                recyclerView.setAdapter(appointmentAdapter);
                Toast.makeText(context, response.size()+"", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
}
