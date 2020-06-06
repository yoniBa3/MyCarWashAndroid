package com.yoni.a2020_02_07mycarwashclient.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.yoni.a2020_02_07mycarwashclient.DataClasses.Machon;
import com.yoni.a2020_02_07mycarwashclient.DataClasses.Services;
import com.yoni.a2020_02_07mycarwashclient.DataClasses.Slots;
import com.yoni.a2020_02_07mycarwashclient.DataClasses.Wash;
import com.yoni.a2020_02_07mycarwashclient.MenuActivity;
import com.yoni.a2020_02_07mycarwashclient.R;
import com.yoni.a2020_02_07mycarwashclient.adapter.AlertDialogAdapter;
import com.yoni.a2020_02_07mycarwashclient.utils.MyUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMachonFrag extends Fragment implements AlertDialogAdapter.OnAppointmentChosen {

    private Context context;
    private TextView tvMachonName;
    private TextView tvMachonAddress;
    private TextView tvMachonTell;
    private TextView tvMachonDate;
    private TextView tvMachonHours;
    private ProgressBar pbLoader;
    private Date currentDate;
    private DateFormat df;
    private Date chosenDate;
    private String formattedDate;
    private RecyclerView recyclerView;
    private String hour = "";


    public MyMachonFrag() {
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
        return inflater.inflate(R.layout.fragment_my_machon, container, false);
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Toast.makeText(context, MyUtils.myServices.size()+"", Toast.LENGTH_SHORT).show();
        tvMachonName = view.findViewById(R.id.myMachonName);
        tvMachonAddress = view.findViewById(R.id.myMachonAddress);
        tvMachonDate = view.findViewById(R.id.myMachonDateChosen);
        tvMachonTell = view.findViewById(R.id.myMachonTell);
        tvMachonHours = view.findViewById(R.id.myMachonHourChosen);
        pbLoader = view.findViewById(R.id.myMachonPbLoader);

        tvMachonName.setText(MyUtils.myMachon.getName());
        tvMachonTell.setText(MyUtils.myMachon.getTel());
        tvMachonAddress.setText(MyUtils.myMachon.getAddress()+" "+MyUtils.myMachon.getCity());

        Calendar c = Calendar.getInstance();
        currentDate = c.getTime();
        chosenDate = currentDate;
        df = new SimpleDateFormat("dd/MM/yyyy");
        formattedDate = df.format(currentDate);
        tvMachonDate.setText(formattedDate);

        //get service alert dialog
        view.findViewById(R.id.myMachonBtnServices).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Our services");
                StringBuilder sb = new StringBuilder();
                for (Services item:MyUtils.myServices){
                    Log.e("Error", "onClick: "+item.getName() );;
                    sb.append(item.getName()).append(" ");
                    sb.append(item.getCost()).append("₪\n");
                }
                builder.setMessage(sb.toString());
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        view.findViewById(R.id.myMachonBtnHourPicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                @SuppressLint("InflateParams") View alertView = inflater.inflate(R.layout.costume_alert_dialog, null);
                recyclerView = alertView.findViewById(R.id.alertRecyclerView);
                String start = MyUtils.myMachon.getOHour().substring(0,2);
                String stop = MyUtils.myMachon.getCHour().substring(0,2);
                List<Slots>timeList = new ArrayList<>();
                int startNumber = Integer.parseInt(start);
                int stopNumber = Integer.parseInt(stop);
                int counter = 0;

                while (startNumber < stopNumber){
                    String time = "";
                    if (counter % 2 == 0){
                        if (startNumber < 10)
                            time = "0";
                        time += startNumber+":00";

                    }else {
                        if (startNumber < 10)
                            time = "0";
                        time += startNumber+":30";
                        startNumber++;
                    }
                    counter++;
                    timeList.add(new Slots(time ,checkFree(MyUtils.myWashes ,time)));
                }
                AlertDialogAdapter adapter = new AlertDialogAdapter(timeList,MyUtils.getHourList(),MyMachonFrag.this ,chosenDate);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(context,4,RecyclerView.VERTICAL,false));
                builder.setView(alertView);
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hour = "";
                        tvMachonHours.setText("Appointment hour");
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        view.findViewById(R.id.myMachonBtnDatePicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.Builder builder = new AlertDialog.Builder(context);
                final DatePicker picker = new DatePicker(context);

                builder.setView(picker);
                builder.setPositiveButton("Set date", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR,picker.getYear());
                        calendar.set(Calendar.MONTH,picker.getMonth());
                        calendar.set(Calendar.DAY_OF_MONTH,picker.getDayOfMonth());
                        chosenDate = calendar.getTime();
                        if (currentDate.after(chosenDate)){
                            Toast.makeText(context, "Don't be silly", Toast.LENGTH_SHORT).show();
                        }else {
                            formattedDate = df.format(chosenDate);
                            tvMachonDate.setText(formattedDate);
                            hour = "";
                            tvMachonHours.setText("Appointment hour");
                            formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(chosenDate);
                        }

                    }
                });
                builder.show();
            }
        });

        view.findViewById(R.id.myMachonBtnSetAppointment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hour.length()==0){
                    Toast.makeText(context, "You have not chose an hour", Toast.LENGTH_SHORT).show();
                }else {
                    pbLoader.setVisibility(View.VISIBLE);
                    final Wash wash = new Wash();
                    wash.setCarNumber(String.valueOf(MyUtils.user.getProperty("lp")));
                    DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss");
                    String string = formattedDate+" "+ hour+":00";
                    try {
                        chosenDate = dateFormat.parse(string);
                        wash.setTime(chosenDate);
                    } catch (ParseException e) {
                        Log.e("Error", "onClick: "+e.getMessage() );;
                    }

                    Backendless.Data.of(Wash.class).save(wash, new AsyncCallback<Wash>() {
                        @Override
                        public void handleResponse(Wash response) {
                            List<Machon>machonList = new ArrayList<>();
                            machonList.add(MyUtils.myMachon);
                            //Add Machon relations to the wash object
                            Backendless.Data.of(Wash.class).addRelation(response, "machon:Machon:1", machonList, new AsyncCallback<Integer>() {
                                @Override
                                public void handleResponse(Integer response) {
                                    Log.e("Error", "handleResponse: machon:Machon:1 "+response);
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    Log.e("Error", "handleResponse: machon:Machon:1 "+fault);
                                }
                            });

                            //Add user relations to wash object
                            List<BackendlessUser>users = new ArrayList<>();
                            users.add(MyUtils.user);
                            Backendless.Data.of(Wash.class).addRelation(wash, "owner:Users:1", users, new AsyncCallback<Integer>() {
                                @Override
                                public void handleResponse(Integer response) {
                                    Log.e("Error", "handleResponse: owner:Users:1 "+response);
                                    pbLoader.setVisibility(View.INVISIBLE);
                                    Toast.makeText(context, "Appointment set can't wait...", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(context, MenuActivity.class));
                                    Objects.requireNonNull(getActivity()).finish();
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    Log.e("Error", "handleResponse: owner:Users:1"+fault);
                                }
                            });

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(context, "Something went wrong please try again later", Toast.LENGTH_SHORT).show();
                            Log.e("Error", "handleFault: "+fault.getMessage() );
                            pbLoader.setVisibility(View.INVISIBLE);
                        }
                    });
                }

            }
        });

        view.findViewById(R.id.myMachonBtnCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+MyUtils.myMachon.getTel()));
                startActivity(intent);
            }
        });

        view.findViewById(R.id.myMachonBtnOpeningHours).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Opening hours");
                String stringBuilder = MyUtils.myMachon.getOHour() + "-" +
                        MyUtils.myMachon.getCHour();
                builder.setMessage(stringBuilder);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        view.findViewById(R.id.myMachonBtnLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://maps.google.co.in/maps?q="+MyUtils.myMachon.getAddress()+" "+MyUtils.myMachon.getCity()));
                startActivity(intent);
            }
        });

        view.findViewById(R.id.myMachonBtnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).finish();
            }
        });

    }

    private boolean checkFree(List<Wash> myWash, String time){

        for (Wash item:myWash){
            String date = DateFormat.getDateInstance(DateFormat.FULL).format(item.getTime());
            if (date.equals(formattedDate)){
                @SuppressLint("SimpleDateFormat") SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
                String appointment = localDateFormat.format(item.getTime());
                if (appointment.equals(time)){
                    Toast.makeText(context, "true", Toast.LENGTH_SHORT).show();
                    return true;
                }

            }
        }
        return false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void appointmentChosen(String appointment) {
        this.hour = appointment;
        tvMachonHours.setText(hour);
    }
}
