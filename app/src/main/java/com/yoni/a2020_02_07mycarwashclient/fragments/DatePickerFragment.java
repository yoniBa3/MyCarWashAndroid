package com.yoni.a2020_02_07mycarwashclient.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.yoni.a2020_02_07mycarwashclient.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends Fragment {


    private Context context;
    private DatePicker datePicker;
    private FragmentManager fm;

    public DatePickerFragment() {
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
        return inflater.inflate(R.layout.fragment_date_picker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        datePicker = view.findViewById(R.id.datePicker);
        fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();

        getActivity().findViewById(R.id.btnGetDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar currentDay = Calendar.getInstance();
                Date currentDayTime = currentDay.getTime();


                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR,datePicker.getYear());
                c.set(Calendar.MONTH,datePicker.getMonth());
                c.set(Calendar.DAY_OF_MONTH,datePicker.getDayOfMonth());
                if (currentDayTime.after(c.getTime())){
                    Toast.makeText(context, "Don't be silly...", Toast.LENGTH_SHORT).show();
                }else {

                    String date = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());


                    FragmentTransaction ft = fm.beginTransaction();

                    ListFrag listFrag = new ListFrag();
                    Bundle bundle = new Bundle();
                    bundle.putString("date", date);
                    listFrag.setArguments(bundle);
                    ft.replace(R.id.queueListContainer, listFrag)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
            }
        });



    }
}
