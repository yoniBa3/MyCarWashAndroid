package com.yoni.a2020_02_07mycarwashclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.yoni.a2020_02_07mycarwashclient.fragments.DatePickerFragment;
import com.yoni.a2020_02_07mycarwashclient.DataClasses.*;
import com.yoni.a2020_02_07mycarwashclient.fragments.ListFrag;

import java.util.List;

public class QueueList extends AppCompatActivity {

    Context context;
    FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_list);

        setPointer();
    }

    private void setPointer() {
        this.context = this;
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.queueListContainer,new ListFrag())
                .commit();

    }

    private void zeevExamples(){
        Backendless.Data.of(Machon.class).find(new AsyncCallback<List<Machon>>() {
            @Override
            public void handleResponse(List<Machon> response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
}
