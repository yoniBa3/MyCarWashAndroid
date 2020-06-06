package com.yoni.a2020_02_07mycarwashclient.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.LoadRelationsQueryBuilder;
import com.yoni.a2020_02_07mycarwashclient.DataClasses.Machon;
import com.yoni.a2020_02_07mycarwashclient.DataClasses.Wash;
import com.yoni.a2020_02_07mycarwashclient.MenuActivity;
import com.yoni.a2020_02_07mycarwashclient.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CancelAppointmentAdapter extends RecyclerView.Adapter<CancelAppointmentAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvMachonName;
        TextView tvMachonAddress;
        TextView tvMachonTell;
        TextView tvDateAppointment;
        ProgressBar pbLoader;
        LinearLayout layout;
        CardView cardView;

        public ViewHolder(View view){
            super(view);

            tvMachonName = view.findViewById(R.id.tvCancelName);
            tvMachonAddress = view.findViewById(R.id.tvCancelAddress);
            tvMachonTell = view.findViewById(R.id.tvCancelTel);
            tvDateAppointment = view.findViewById(R.id.tvCancelDate);
            pbLoader = view.findViewById(R.id.pbCancelItem);
            layout = view.findViewById(R.id.layoutCancelItem);
            cardView = view.findViewById(R.id.cardView);
        }
    }

    Context context;
    List<Wash>appointmentsList;

    public CancelAppointmentAdapter(List<Wash>appointmentsList){
        this.appointmentsList = appointmentsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cancel_appointment_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Wash wash = appointmentsList.get(position);
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String pattern = "dd/MM/yyyy HH:mm";
        DateFormat df = new SimpleDateFormat(pattern);
        final String string = df.format(wash.getTime());
        DataQueryBuilder builder = DataQueryBuilder.create();
        builder.setPageSize(100);
        final String objectId = wash.getObjectId();
        LoadRelationsQueryBuilder<Machon> machonBuilder = LoadRelationsQueryBuilder.of(Machon.class);
        machonBuilder.setRelationName("machon");
        Backendless.Data.of(Wash.class).loadRelations(objectId, machonBuilder, new AsyncCallback<List<Machon>>() {
            @Override
            public void handleResponse(List<Machon> response) {
                Machon machon = response.get(0);
                holder.tvMachonName.setText(machon.getName());
                holder.tvMachonTell.setText(machon.getTel());
                holder.tvMachonAddress.setText(machon.getAddress());
                holder.tvDateAppointment.setText(string);
                holder.pbLoader.setVisibility(View.GONE);
                holder.layout.setVisibility(View.VISIBLE);

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("Error", "handleFault: "+fault.getMessage());
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("ביטול תור");
                builder.setMessage("אישור ביטול התור");
                builder.setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Backendless.Data.of(Wash.class).remove(wash, new AsyncCallback<Long>() {
                            @Override
                            public void handleResponse(Long response) {
                                Toast.makeText(context, "התור בוטל", Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context, MenuActivity.class));
                                ((Activity)context).finish();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                            }
                        });
                    }
                });
                builder.show();
            }
        });
        //Backendless.Data.of(Wash.class).loadRelations(objectId,)

    }

    @Override
    public int getItemCount() {
        return appointmentsList.size();
    }


}
