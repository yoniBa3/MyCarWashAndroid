package com.yoni.a2020_02_07mycarwashclient.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yoni.a2020_02_07mycarwashclient.DataClasses.Slots;
import com.yoni.a2020_02_07mycarwashclient.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AlertDialogAdapter extends RecyclerView.Adapter<AlertDialogAdapter.ViewHolder> {


    public static final int WASH_OPEN = 324342;
    public static final int TAKEN = 32435;
    public static final int WASH_CLOSE = 345345;

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button btnTime;

        public ViewHolder(View view) {
            super(view);
            btnTime = view.findViewById(R.id.btnTime);
        }
    }

    Context context;
    List<Slots> timeList;
    List<String> hours;
    int[] status;
    Pressed pressed;
    boolean firstPress;
    String hour3 = "";
    OnAppointmentChosen appointmentListener;
    Date chosenDate;


    public AlertDialogAdapter(List<Slots> timeList, List<String> hours, OnAppointmentChosen appointmentListener, Date chosenDate) {
        this.timeList = timeList;
        Log.e("Error", "onBindViewHolder: " + timeList.toString());
        this.hours = hours;
        this.appointmentListener = appointmentListener;
        this.chosenDate = chosenDate;
        inIt();

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.alert_recyclerview_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        final String hour = hours.get(position);
        holder.btnTime.setText(hour);

        if (status[position] == WASH_OPEN) {
            holder.btnTime.setBackgroundColor(Color.GREEN);
            holder.btnTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!firstPress) {
                        firstPress = true;
                        pressed = new Pressed(false, position);
                    }
                    if (pressed.getPosition() == position && !pressed.isPressed()) {
                        holder.btnTime.setBackgroundColor(Color.RED);
                        pressed.setPressed(true);
                        hour3 = holder.btnTime.getText().toString();
                        appointmentListener.appointmentChosen(hour3);
                    } else if (pressed.getPosition() == position && pressed.isPressed()) {
                        holder.btnTime.setBackgroundColor(Color.GREEN);
                        firstPress = false;
                        hour3 = "";
                        appointmentListener.appointmentChosen(hour3);

                    }
                }
            });
        } else if (status[position] == AlertDialogAdapter.TAKEN) {
            holder.btnTime.setBackgroundColor(Color.RED);
        } else {
            holder.btnTime.setBackgroundColor(Color.LTGRAY);
        }
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    public void inIt() {
        status = new int[hours.size()];
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        boolean today = false;
        if (date.equals(chosenDate)){
            today = true;
        }
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String rightNow;



        for (int i = 0; i < status.length; i++) {

            for (Slots slots : timeList) {

                if (hours.get(i).equals(slots.getTime()) && slots.isTaken()) {
                    status[i] = TAKEN;
                    break;
                }
                if (hours.get(i).equals(slots.getTime())) {
                    status[i] = WASH_OPEN;
                    break;
                } else {
                    status[i] = WASH_CLOSE;
                }
            }
        }
        this.pressed = new Pressed(false, null);
    }

    class Pressed {
        boolean isPressed;
        Integer position;

        public Pressed(boolean isPressed, Integer position) {
            this.isPressed = isPressed;
            this.position = position;
        }

        public boolean isPressed() {
            return isPressed;
        }

        public void setPressed(boolean pressed) {
            isPressed = pressed;
        }

        public Integer getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }


    public interface OnAppointmentChosen {
        void appointmentChosen(String appointment);
    }
}
