package com.yoni.a2020_02_07mycarwashclient.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.LoadRelationsQueryBuilder;
import com.yoni.a2020_02_07mycarwashclient.DataClasses.Machon;
import com.yoni.a2020_02_07mycarwashclient.DataClasses.Services;
import com.yoni.a2020_02_07mycarwashclient.DataClasses.Slots;
import com.yoni.a2020_02_07mycarwashclient.DataClasses.Wash;
import com.yoni.a2020_02_07mycarwashclient.R;
import com.yoni.a2020_02_07mycarwashclient.fragments.MyMachonFrag;
import com.yoni.a2020_02_07mycarwashclient.utils.MyUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class QueueListAdapter extends RecyclerView.Adapter<QueueListAdapter.ViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback ,LocationListener , AlertDialogAdapter.OnAppointmentChoosen {


    public static final int PER_GPS_REQUEST = 100;

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMachonName;
        TextView tvMachonTel;
        TextView tvMachonAddress;
        TextView tvMachonOpenHour;
        TextView tvMachonServices;
        TextView tvMachonDistanceFromMe;
        Button btnMachonAvailability;


        public ViewHolder(View view) {
            super(view);
            tvMachonName = view.findViewById(R.id.tvMachonName);
            tvMachonTel = view.findViewById(R.id.tvMachonTel);
            tvMachonAddress = view.findViewById(R.id.tvMachonAddress);
            tvMachonOpenHour = view.findViewById(R.id.tvMachonOpenHour);
            tvMachonServices = view.findViewById(R.id.tvMachonServices);
            tvMachonDistanceFromMe = view.findViewById(R.id.tvMachonDistanceFromMe);
            btnMachonAvailability = view.findViewById(R.id.btnMachonAvailability);

        }
    }

    private Context context;
    private boolean isGpsEnable = false;
    private LocationManager locationManager;
    private Location location;
    private String receivedDate;
    RecyclerView recyclerView;
    private List<Machon> freeQueues;
    private String hour = "";
    private Map<Machon,List<Services>>servicesMap = new HashMap<>();
    private Map<Machon,List<Wash>>washMap = new HashMap<>();


    public QueueListAdapter(List<Machon> freeQueues ,Location location ,String receivedDate) {
        this.freeQueues = freeQueues;
        this.location = location;
        this.receivedDate = receivedDate;
    }

    @SuppressLint("MissingPermission")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();

        //checkForPermissions(parent.getContext());
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

//        if (isGpsEnable){
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1_000, 300, this);
//        }

        LayoutInflater inflater = LayoutInflater.from(context);
        View singleCard = inflater.inflate(R.layout.queue_item, parent, false);
        return new ViewHolder(singleCard);
    }

    private void checkForPermissions(Context context) {
        int localGps = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);

        int coarsGps = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);

        List<String> permissionNeeded = new ArrayList<>();

        if (localGps != PackageManager.PERMISSION_GRANTED || coarsGps != PackageManager.PERMISSION_GRANTED) {
            permissionNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            permissionNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        } else {
            isGpsEnable = true;

        }
        if (!permissionNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) context, permissionNeeded.toArray(new String[0]), PER_GPS_REQUEST);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PER_GPS_REQUEST) {
            isGpsEnable = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final Machon singleMachon = freeQueues.get(position);
        final String fullAddress = singleMachon.getCity() + " " + singleMachon.getAddress();


        LoadRelationsQueryBuilder<Services>servicesBuilder = LoadRelationsQueryBuilder.of(Services.class);
        servicesBuilder.setRelationName("services");
        Backendless.Data.of(Machon.class).loadRelations(singleMachon.getObjectId(), servicesBuilder, new AsyncCallback<List<Services>>() {
            @Override
            public void handleResponse(List<Services> response) {
                servicesMap.put(singleMachon,response);
                StringBuilder stringBuilder = new StringBuilder();
                for (Services services : response) {
                    stringBuilder.append(services.getName()).append(" ");
                }
                holder.tvMachonServices.setText(stringBuilder.substring(0,stringBuilder.length()-1));
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(context, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        /*LoadRelationsQueryBuilder<Region> load1 = LoadRelationsQueryBuilder.of(Region.class);
        load1.setRelationName("Region");
        Backendless.Data.of(Machon.class).loadRelations(singleMachon.getObjectId(), load1, new AsyncCallback<List<Region>>() {
            @Override
            public void handleResponse(List<Region> response) {
                //Toast.makeText(context, response.size()+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(context, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        LoadRelationsQueryBuilder<Services> load2 = LoadRelationsQueryBuilder.of(Services.class);
        load2.setRelationName("services");

        Backendless.Data.of(Machon.class).loadRelations(singleMachon.getObjectId(), load2, new AsyncCallback<List<Services>>() {
            @Override
            public void handleResponse(List<Services> response) {
                StringBuilder allServices = new StringBuilder();
                for (Services service : response) {
                    allServices.append(service.getName()).append(" ");
                }
                holder.tvMachonServices.setText(allServices.toString().substring(0, allServices.length() - 1));

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(context, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/




        holder.tvMachonName.setText(singleMachon.getName());
        holder.tvMachonTel.setText(singleMachon.getTel());


        holder.tvMachonAddress.setText(fullAddress);

        String openHour = singleMachon.getOHour() + "-" + singleMachon.getCHour();
        holder.tvMachonOpenHour.setText(openHour);

        Geocoder geocoder = new Geocoder(context,new Locale("he"));
        if (location!=null) {
            try {
                List<Address> addresses = geocoder.getFromLocationName(fullAddress, 1);
                Location location1 = new Location("pointA");
                location1.setLongitude(addresses.get(0).getLongitude());
                location1.setLatitude(addresses.get(0).getLatitude());
                float distance = location.distanceTo(location1)/1000;
                distance = Math.round(distance * 10) / 10.0f;
                String distanceFromClient = distance + " km";
                holder.tvMachonDistanceFromMe.setText(distanceFromClient);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            holder.tvMachonDistanceFromMe.setText("?km");
        }


        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setPageSize(100);
        final String machonTest = singleMachon.getName();
        queryBuilder.setWhereClause("machon.name='"+machonTest+"'");

        Backendless.Data.of(Wash.class).find(queryBuilder, new AsyncCallback<List<Wash>>() {
            @Override
            public void handleResponse(List<Wash> response) {
                washMap.put(singleMachon,response);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("Error",fault.getMessage());
            }
        });



        holder.btnMachonAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyUtils.myServices = servicesMap.get(singleMachon);
                MyUtils.myWashes = washMap.get(singleMachon);
                MyUtils.myMachon = singleMachon;
                FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.queueListContainer,new MyMachonFrag());
                ft.commit();

               /* final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                final View view = inflater.inflate(R.layout.costume_alert_dialog,null);
                final ProgressBar pbLoading = view.findViewById(R.id.pbLoading);
                final String start = singleMachon.getOHour().substring(0,2);
                Toast.makeText(context, start, Toast.LENGTH_SHORT).show();
                final String stop = singleMachon.getCHour().substring(0,2);
                Toast.makeText(context, stop, Toast.LENGTH_SHORT).show();
                final List<Slots>timeList = new ArrayList<>();



                DataQueryBuilder queryBuilder = DataQueryBuilder.create();
                queryBuilder.setPageSize(100);
                final String machonTest = singleMachon.getName();
                queryBuilder.setWhereClause("machon.name='"+machonTest+"'");

                Backendless.Data.of(Wash.class).find(queryBuilder, new AsyncCallback<List<Wash>>() {
                    @Override
                    public void handleResponse(List<Wash> response) {
                        for (Wash item:response){
                            Log.e("Error", "handleResponse: "+item.getTime() );
                        }
                        Log.e("Error","Inside");
                        int startNumber = Integer.valueOf(start);
                        int stopNumber = Integer.valueOf(stop);
                        int counter = 0;

                        while ( startNumber < stopNumber) {
                            String time = "";
                            if (counter%2==0){
                                if (startNumber<10)
                                    time = "0";
                                time += startNumber+":00";

                            }else {
                                if (startNumber<10)
                                    time = "0";
                                time += startNumber+":30";
                                startNumber++;

                            }
                            counter++;
                            timeList.add(new Slots(time,checkFree(response,time)));


                        }
                        pbLoading.setVisibility(View.INVISIBLE);
                        AlertDialogAdapter adapter = new AlertDialogAdapter(timeList,getHourList() ,QueueListAdapter.this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(context,4,RecyclerView.VERTICAL,false));


                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.e("Error",fault.getMessage());
                    }
                });



                recyclerView = view.findViewById(R.id.alertRecyclerView);
                builder.setView(view);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (hour.length()==0){
                            Toast.makeText(context, "Appointment did not set...", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Wash wash = new Wash();
                            wash.setCarNumber((String)MyUtils.user.getProperty("lp"));

                            String string = receivedDate +" "+ hour+":00";
                            @SuppressLint("SimpleDateFormat") DateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss");


                            try {
                                Date date = format.parse(string);
                                assert date != null;
                                Log.e("Error", "onClick: "+date.toString() );
                                wash.setTime(date);
                            } catch (ParseException e) {
                                Log.e("Error", "onClick: "+e.getMessage() );
                            }

                            //Add Machon relations to the wash object
                            Backendless.Data.of(Wash.class).save(wash, new AsyncCallback<Wash>() {
                                @Override
                                public void handleResponse(Wash response) {
                                    List<Machon>list = new ArrayList<>();
                                    list.add(singleMachon);
                                    Backendless.Data.of(Wash.class).addRelation(response, "machon:Machon:1", list, new AsyncCallback<Integer>() {
                                        @Override
                                        public void handleResponse(Integer response) {
                                            Log.e("Error", "handleFault: machon:Machon:1 "+response );
                                        }

                                        @Override
                                        public void handleFault(BackendlessFault fault) {
                                            Log.e("Error", "handleFault: machon:Machon:1 "+fault.getMessage() );
                                        }
                                    });
                                    List<BackendlessUser>userList = new ArrayList<>();
                                    userList.add(MyUtils.user);

                                    Backendless.Data.of(Wash.class).addRelation(response, "owner:Users:1", userList, new AsyncCallback<Integer>() {
                                        @Override
                                        public void handleResponse(Integer response) {
                                            Log.e("Error", "handleFault: owner:Users:1 "+response );
                                        }

                                        @Override
                                        public void handleFault(BackendlessFault fault) {
                                            Log.e("Error", "handleFault: owner:Users:1 "+fault.getMessage() );
                                        }
                                    });
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {

                                }
                            });

                            *//*Backendless.Data.of(Wash.class).save(wash, new AsyncCallback<Wash>() {
                                @Override
                                public void handleResponse(Wash response) {
                                    Toast.makeText(context, "Appointment were set", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {

                                }
                            });*//*
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Appointment did not set", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();*/

            }
        });


    }

    private boolean checkFree(List<Wash> myWash, String time){

        for (Wash item:myWash){
            String date = DateFormat.getDateInstance(DateFormat.FULL).format(item.getTime());
            if (date.equals(receivedDate)){
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

    private List<String> getHourList(){
        List<String>list =new ArrayList<>();

        int startNumber = 6;
        int stopNumber = 21;
        int counter = 0;

        while ( startNumber <= stopNumber) {
            String time = "";
            if (counter % 2 == 0) {
                if (startNumber < 10)
                    time = "0";
                time += startNumber + ":00";

            } else {
                if (startNumber < 10)
                    time = "0";
                time += startNumber + ":30";
                startNumber++;

            }
            counter++;
            list.add(time);
        }
        Log.e("Error", "hourList: "+list.toString() );

            return list;
    }


    @Override
    public int getItemCount() {
        return freeQueues.size();
    }


    @Override
    public void appointmentChosen(String appointment) {
        hour = appointment;
    }
}
