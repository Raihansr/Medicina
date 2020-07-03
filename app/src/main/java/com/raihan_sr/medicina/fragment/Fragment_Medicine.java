package com.raihan_sr.medicina.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import com.raihan_sr.medicina.database.MySQLiteDB;
import com.raihan_sr.medicina.R;
import com.raihan_sr.medicina.reminder.Medicine_Reminder;
import com.raihan_sr.medicina.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static android.graphics.Color.TRANSPARENT;

public class Fragment_Medicine extends Fragment {

    private SimpleAdapter adapter;
    private ListView CustomList;
    private boolean flag = false;
    private RelativeLayout add_medicine, update_medicine;
    private AppCompatImageButton cancel_update_btn, clear_add_btn;
    private AppCompatImageButton medicine_Name_Btn, medicine_Power_Btn, medicine_Type_Btn, medicine_Shift_Btn, medicine_Time_Btn, medicine_Notes_Btn;
    private AppCompatImageButton medicine_Alarm_On_Btn, medicine_Alarm_Off_Btn;
    private NestedScrollView medicine_scroll;
    private AlertDialog alertDialog;
    private Animation Bounce_Animation, Dialog_Bounce_Animation;

    public Fragment_Medicine() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_medicine_, container, false);

        final AppCompatEditText medicine_Id_etxt = v.findViewById(R.id.medicine_id_etxt);
        final AppCompatEditText medicine_Name_etxt = v.findViewById(R.id.medicine_name_etxt);
        final AppCompatEditText medicine_Power_etxt = v.findViewById(R.id.medicine_power_etxt);
        final AppCompatEditText medicine_Power_unit_etxt = v.findViewById(R.id.medicine_power_unit_tv);
        final AppCompatEditText medicine_Type_etxt = v.findViewById(R.id.medicine_type_etxt);
        final AppCompatEditText medicine_Shift_etxt = v.findViewById(R.id.medicine_shift_etxt);
        final AppCompatEditText medicine_Time_etxt = v.findViewById(R.id.medicine_time_etxt);
        final AppCompatEditText medicine_Notes_etxt = v.findViewById(R.id.medicine_notes_etxt);
        final AppCompatImageButton medicine_add_button = v.findViewById(R.id.medicine_add_button);
        final SearchView medicine_search_button = v.findViewById(R.id.medicine_search_button);

        medicine_scroll = v.findViewById(R.id.medicine_scroll);
        add_medicine = v.findViewById(R.id.add_medicine);
        cancel_update_btn = v.findViewById(R.id.cancel_update_id);
        clear_add_btn = v.findViewById(R.id.clear_add_id);
        update_medicine = v.findViewById(R.id.update_medicine);
        final AppCompatTextView add_medicine_text = v.findViewById(R.id.add_medicine_text);
        final AppCompatTextView update_medicine_text = v.findViewById(R.id.update_medicine_text);


        /* Set Font */
        final Typeface tf =Typeface.createFromAsset(getActivity().getAssets(), "pf_regular.ttf");
        add_medicine_text.setTypeface(tf);
        update_medicine_text.setTypeface(tf);
        medicine_Name_etxt.setTypeface(tf);
        medicine_Power_etxt.setTypeface(tf);
        medicine_Power_unit_etxt.setTypeface(tf);
        medicine_Type_etxt.setTypeface(tf);
        medicine_Shift_etxt.setTypeface(tf);
        medicine_Time_etxt.setTypeface(tf);
        medicine_Notes_etxt.setTypeface(tf);


        /* Medicine Name */
        medicine_Name_Btn = v.findViewById(R.id.medicine_name_btn);
        final LinearLayoutCompat medicine_Name_Btn_Layout = v.findViewById(R.id.medicine_name_btn_layout);
        final AppCompatImageButton medicine_Name_Clear_Btn = v.findViewById(R.id.medicine_name_clear_btn);

        medicine_Name_etxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (medicine_Name_Btn_Layout.getVisibility() == View.VISIBLE) {

                    medicine_Name_Btn_Layout.setVisibility(View.GONE);

                    medicine_Name_Clear_Btn.animate().rotationBy(-360).setDuration(625);

                    Animation rightToLeft = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_left);
                    medicine_Name_Btn_Layout.setAnimation(rightToLeft);
                }
            }
        });

        medicine_Name_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                medicine_Name_Btn.startAnimation(Bounce_Animation);
                vibrator();

                Animation(medicine_Name_Btn_Layout, medicine_Name_Clear_Btn);
            }
        });

        medicine_Name_Clear_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                medicine_Name_Clear_Btn.startAnimation(Bounce_Animation);
                vibrator();

                medicine_Name_etxt.setText(null);

                Animation(medicine_Name_Btn_Layout, medicine_Name_Clear_Btn);
            }
        });



        /* Medicine Power */
        medicine_Power_Btn = v.findViewById(R.id.medicine_power_btn);
        final LinearLayoutCompat medicine_Power_Btn_Layout = v.findViewById(R.id.medicine_power_btn_layout);
        final AppCompatImageButton medicine_Power_Clear_Btn = v.findViewById(R.id.medicine_power_clear_btn);

        medicine_Power_unit_etxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (medicine_Power_Btn_Layout.getVisibility() == View.VISIBLE) {

                    medicine_Power_Btn_Layout.setVisibility(View.GONE);

                    medicine_Power_Clear_Btn.animate().rotationBy(-360).setDuration(625);

                    Animation rightToLeft = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_left);
                    medicine_Power_Btn_Layout.setAnimation(rightToLeft);
                }

                PopupMenu popup = new PopupMenu(getContext(), medicine_Power_unit_etxt);

                popup.getMenu().add("MG");
                popup.getMenu().add("MCG");
                popup.getMenu().add("IU");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        medicine_Power_unit_etxt.setText(item.getTitle());
                        return true;
                    }
                });

                popup.show();
            }
        });

        medicine_Power_etxt.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,2)});

        medicine_Power_etxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (medicine_Power_Btn_Layout.getVisibility() == View.VISIBLE) {

                    medicine_Power_Btn_Layout.setVisibility(View.GONE);

                    medicine_Power_Clear_Btn.animate().rotationBy(-360).setDuration(625);

                    Animation rightToLeft = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_left);
                    medicine_Power_Btn_Layout.setAnimation(rightToLeft);
                }
            }
        });

        medicine_Power_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                medicine_Power_Btn.startAnimation(Bounce_Animation);
                vibrator();

                Animation(medicine_Power_Btn_Layout, medicine_Power_Clear_Btn);
            }
        });

        medicine_Power_Clear_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                medicine_Power_Clear_Btn.startAnimation(Bounce_Animation);
                vibrator();

                medicine_Power_etxt.setText(null);

                Animation(medicine_Power_Btn_Layout, medicine_Power_Clear_Btn);
            }
        });



        /* Medicine Type */
        medicine_Type_Btn = v.findViewById(R.id.medicine_type_btn);
        final LinearLayoutCompat medicine_Type_Btn_Layout = v.findViewById(R.id.medicine_type_btn_layout);
        final AppCompatImageButton medicine_Type_Clear_Btn = v.findViewById(R.id.medicine_type_clear_btn);

        medicine_Type_etxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (medicine_Type_Btn_Layout.getVisibility() == View.VISIBLE) {

                    medicine_Type_Btn_Layout.setVisibility(View.GONE);

                    medicine_Type_Clear_Btn.animate().rotationBy(-360).setDuration(625);

                    Animation rightToLeft = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_left);
                    medicine_Type_Btn_Layout.setAnimation(rightToLeft);
                }

                PopupMenu popup = new PopupMenu(getContext(), medicine_Type_etxt);
                popup.getMenu().add("Liquid");
                popup.getMenu().add("Tablet");
                popup.getMenu().add("Capsule");
                popup.getMenu().add("Drop");
                popup.getMenu().add("Cream");
                popup.getMenu().add("suppository");
                popup.getMenu().add("Inhaler");
                popup.getMenu().add("Injection");
                popup.getMenu().add("Patch");
                popup.getMenu().add("Sublingual");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        medicine_Type_etxt.setText(item.getTitle());
                        return true;
                    }
                });

                popup.show();
            }
        });

        medicine_Type_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                medicine_Type_Btn.startAnimation(Bounce_Animation);
                vibrator();

                Animation(medicine_Type_Btn_Layout, medicine_Type_Clear_Btn);
            }
        });

        medicine_Type_Clear_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                medicine_Type_Clear_Btn.startAnimation(Bounce_Animation);
                vibrator();

                medicine_Type_etxt.setText(null);

                Animation(medicine_Type_Btn_Layout, medicine_Type_Clear_Btn);
            }
        });



        /* Medicine Shift */
        medicine_Shift_Btn = v.findViewById(R.id.medicine_shift_btn);
        final LinearLayoutCompat medicine_Shift_Btn_Layout = v.findViewById(R.id.medicine_shift_btn_layout);
        final AppCompatImageButton medicine_Shift_Clear_Btn = v.findViewById(R.id.medicine_shift_clear_btn);

        medicine_Shift_etxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (medicine_Shift_Btn_Layout.getVisibility() == View.VISIBLE) {

                    medicine_Shift_Btn_Layout.setVisibility(View.GONE);

                    medicine_Shift_Clear_Btn.animate().rotationBy(-360).setDuration(625);

                    Animation rightToLeft = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_left);
                    medicine_Shift_Btn_Layout.setAnimation(rightToLeft);
                }

                PopupMenu popup = new PopupMenu(getContext(), medicine_Shift_etxt);
                popup.getMenu().add("Morning");
                popup.getMenu().add("Noon");
                popup.getMenu().add("Evening");
                popup.getMenu().add("Night");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        medicine_Shift_etxt.setText(item.getTitle());
                        return true;
                    }
                });

                popup.show();
            }
        });

        medicine_Shift_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                medicine_Shift_Btn.startAnimation(Bounce_Animation);
                vibrator();

                Animation(medicine_Shift_Btn_Layout, medicine_Shift_Clear_Btn);
            }
        });


        medicine_Shift_Clear_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                medicine_Shift_Clear_Btn.startAnimation(Bounce_Animation);
                vibrator();

                medicine_Shift_etxt.setText(null);

                Animation(medicine_Shift_Btn_Layout, medicine_Shift_Clear_Btn);
            }
        });



        /* Medicine Time */
        medicine_Time_Btn = v.findViewById(R.id.medicine_time_btn);
        final LinearLayoutCompat medicine_Time_Btn_Layout = v.findViewById(R.id.medicine_time_btn_layout);
        medicine_Alarm_On_Btn = v.findViewById(R.id.medicine_alarm_on_btn);
        medicine_Alarm_Off_Btn = v.findViewById(R.id.medicine_alarm_off_btn);
        final AppCompatImageButton medicine_Time_Clear_Btn = v.findViewById(R.id.medicine_time_clear_btn);

        medicine_Time_etxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (medicine_Time_Btn_Layout.getVisibility() == View.VISIBLE) {

                    medicine_Time_Btn_Layout.setVisibility(View.GONE);

                    medicine_Alarm_On_Btn.animate().rotationBy(-360).setDuration(625);
                    medicine_Alarm_Off_Btn.animate().rotationBy(-360).setDuration(625);
                    medicine_Time_Clear_Btn.animate().rotationBy(-360).setDuration(625);

                    Animation rightToLeft = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_left);
                    medicine_Time_Btn_Layout.setAnimation(rightToLeft);
                }

                TimePicker timePicker = new TimePicker(getContext());
                int currentHour = timePicker.getCurrentHour();
                int currentMinute = timePicker.getCurrentMinute();

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), DatePickerDialog.THEME_HOLO_LIGHT,new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String time = hourOfDay+":"+minute;

                        try {
                            medicine_Time_etxt.setText(new SimpleDateFormat("hh:mm a").format(new SimpleDateFormat("HH:mm").parse(time)));
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                },currentHour, currentMinute, false);

                /* Set Old Time */
                if(!medicine_Time_etxt.getText().toString().isEmpty()){

                    try {
                        Date getTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).parse(medicine_Time_etxt.getText().toString().trim());

                        @SuppressLint("SimpleDateFormat") String[] oldTime = new SimpleDateFormat("HH:mm").format(getTime).split(":");
                        int h = Integer.parseInt(oldTime[0].trim());
                        int m = Integer.parseInt(oldTime[1].trim());

                        timePickerDialog.updateTime(h,m);
                    }
                    catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                timePickerDialog.show();
            }
        });

        medicine_Time_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                medicine_Time_Btn.startAnimation(Bounce_Animation);
                vibrator();

                AnimationForTime(medicine_Time_Btn_Layout, medicine_Alarm_On_Btn, medicine_Alarm_Off_Btn, medicine_Time_Clear_Btn);
            }
        });


        medicine_Alarm_On_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                medicine_Alarm_On_Btn.startAnimation(Bounce_Animation);
                vibrator();

                medicine_Alarm_On_Btn.setVisibility(View.GONE);
                medicine_Alarm_Off_Btn.setVisibility(View.VISIBLE);

                AnimationForTime(medicine_Time_Btn_Layout, medicine_Alarm_On_Btn, medicine_Alarm_Off_Btn, medicine_Time_Clear_Btn);
            }
        });


        medicine_Alarm_Off_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                medicine_Alarm_Off_Btn.startAnimation(Bounce_Animation);
                vibrator();

                medicine_Alarm_On_Btn.setVisibility(View.VISIBLE);
                medicine_Alarm_Off_Btn.setVisibility(View.GONE);

                AnimationForTime(medicine_Time_Btn_Layout, medicine_Alarm_On_Btn, medicine_Alarm_Off_Btn, medicine_Time_Clear_Btn);
            }
        });

        medicine_Time_Clear_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                medicine_Time_Clear_Btn.startAnimation(Bounce_Animation);
                vibrator();

                medicine_Time_etxt.setText(null);

                AnimationForTime(medicine_Time_Btn_Layout, medicine_Alarm_On_Btn, medicine_Alarm_Off_Btn, medicine_Time_Clear_Btn);
            }
        });



        /* Medicine Notes */
        medicine_Notes_Btn = v.findViewById(R.id.medicine_notes_btn);
        final LinearLayoutCompat medicine_Notes_Btn_Layout = v.findViewById(R.id.medicine_notes_btn_layout);
        final AppCompatImageButton medicine_Notes_Clear_Btn = v.findViewById(R.id.medicine_notes_clear_btn);

        medicine_Notes_etxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (medicine_Notes_Btn_Layout.getVisibility() == View.VISIBLE) {

                    medicine_Notes_Btn_Layout.setVisibility(View.GONE);

                    medicine_Notes_Clear_Btn.animate().rotationBy(-360).setDuration(625);

                    Animation rightToLeft = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_left);
                    medicine_Notes_Btn_Layout.setAnimation(rightToLeft);
                }
            }
        });

        medicine_Notes_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                medicine_Notes_Btn.startAnimation(Bounce_Animation);
                vibrator();

                Animation(medicine_Notes_Btn_Layout, medicine_Notes_Clear_Btn);
            }
        });

        medicine_Notes_Clear_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                medicine_Notes_Clear_Btn.startAnimation(Bounce_Animation);
                vibrator();

                medicine_Notes_etxt.setText(null);

                Animation(medicine_Notes_Btn_Layout, medicine_Notes_Clear_Btn);
            }
        });


        /* List Item Refresh */
        new Timer().scheduleAtFixedRate(new Refresh(), 10, 10);

        final LinearLayoutCompat bottomSheetLayout = v.findViewById(R.id.bottomSheet_id);
        BottomSheetBehavior.from(bottomSheetLayout).setState(BottomSheetBehavior.STATE_COLLAPSED);


        /* Display Data */
        CustomList = v.findViewById(R.id.info_list);
        flag= true;

        CustomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                final AppCompatTextView _id = view.findViewById(R.id.medicine_id);
                final AppCompatTextView name = view.findViewById(R.id.medicine_name);
                final AppCompatTextView power = view.findViewById(R.id.medicine_power);
                final AppCompatTextView type = view.findViewById(R.id.medicine_type);
                final AppCompatTextView shift = view.findViewById(R.id.medicine_shift);
                final AppCompatTextView time = view.findViewById(R.id.medicine_time);
                final AppCompatTextView alarm = view.findViewById(R.id.medicine_alarm);
                final AppCompatTextView notes = view.findViewById(R.id.medicine_notes);

                vibrator();

                Custom_DialogBox(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Medicine_View_DialogBox(name, power, type, shift, time, alarm, notes);

                        vibrator();
                        alertDialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        vibrator();

                        BottomSheetBehavior.from(bottomSheetLayout).setState(BottomSheetBehavior.STATE_EXPANDED);

                        add_medicine.setVisibility(View.GONE);
                        update_medicine.setVisibility(View.VISIBLE);

                        medicine_Id_etxt.setText(_id.getText().toString());
                        medicine_Name_etxt.setText(name.getText().toString());
                        medicine_Power_etxt.setText(power.getText().toString().replaceAll("[A-Z]", "").trim());
                        medicine_Power_unit_etxt.setText(power.getText().toString().replaceAll("[0-9.]", "").trim());
                        medicine_Type_etxt.setText(type.getText().toString());
                        medicine_Shift_etxt.setText(shift.getText().toString());
                        medicine_Time_etxt.setText(time.getText().toString());
                        medicine_Notes_etxt.setText(notes.getText().toString());


                        if (alarm.getText().toString().equals("Alarm_On")) {
                            medicine_Alarm_On_Btn.setVisibility(View.VISIBLE);
                            medicine_Alarm_Off_Btn.setVisibility(View.GONE);

                        } else if (alarm.getText().toString().equals("Alarm_Off")) {
                            medicine_Alarm_On_Btn.setVisibility(View.GONE);
                            medicine_Alarm_Off_Btn.setVisibility(View.VISIBLE);
                        }

                        alertDialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        vibrator();

                        AppCompatTextView id_tv = view.findViewById(R.id.medicine_id);
                        final String _id = id_tv.getText().toString().trim();
                        int value = new MySQLiteDB(getContext()).deleteMedicineData(_id);

                        flag = true;

                        if (value > 0) {
                            Log.i(Utils.TAG, "Data is deleted");
                            medicine_Id_etxt.setText(null);
                            medicine_Name_etxt.setText(null);
                            medicine_Power_etxt.setText(null);
                            medicine_Type_etxt.setText(null);
                            medicine_Shift_etxt.setText(null);
                            medicine_Time_etxt.setText(null);
                            medicine_Alarm_On_Btn.setVisibility(View.GONE);
                            medicine_Alarm_Off_Btn.setVisibility(View.VISIBLE);
                            medicine_Notes_etxt.setText(null);

                        } else {
                            Log.i(Utils.TAG, "Data isn't deleted");
                        }

                        alertDialog.dismiss();
                    }
                });

            }
        });


        /* BottomSheet Item */
        Medicine_Item(bottomSheetLayout, medicine_add_button, medicine_search_button, medicine_Id_etxt, medicine_Name_etxt, medicine_Power_etxt, medicine_Power_unit_etxt, medicine_Type_etxt, medicine_Shift_etxt, medicine_Time_etxt, medicine_Notes_etxt);


        /* Set Medicine_Reminder */
        new Medicine_Reminder().setAlarm(getActivity());

        return v;
    }


    /* Display data in ListView */
    public void Display_Data(){
        final Cursor cursor = new MySQLiteDB(getContext()).displayMedicineData();
        final ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        if(cursor.getCount()==0){

            Log.i(Utils.TAG, "Data not found");

            flag=true;

            adapter = new SimpleAdapter(
                    getContext(), list, R.layout.medicine_list_item,
                    new String[]{MySQLiteDB.MEDICINE_ID, MySQLiteDB.MEDICINE_NAME, MySQLiteDB.MEDICINE_POWER, MySQLiteDB.MEDICINE_TYPE, MySQLiteDB.MEDICINE_SHIFT, MySQLiteDB.MEDICINE_TIME, MySQLiteDB.MEDICINE_NOTES},
                    new int[]{R.id.medicine_id, R.id.medicine_name, R.id.medicine_power, R.id.medicine_type, R.id.medicine_shift, R.id.medicine_time, R.id.medicine_notes});
            CustomList.setAdapter(adapter);
        }

        while(cursor.moveToNext()){

            final HashMap<String, String> map = new HashMap<String, String>();
            map.put(MySQLiteDB.MEDICINE_ID, cursor.getString(0));
            map.put(MySQLiteDB.MEDICINE_NAME, cursor.getString(1));
            map.put(MySQLiteDB.MEDICINE_POWER, cursor.getString(2));
            map.put(MySQLiteDB.MEDICINE_TYPE, cursor.getString(3));
            map.put(MySQLiteDB.MEDICINE_SHIFT, cursor.getString(4));
            map.put(MySQLiteDB.MEDICINE_TIME, cursor.getString(5));
            map.put(MySQLiteDB.MEDICINE_ALARM, cursor.getString(6));
            map.put(MySQLiteDB.MEDICINE_NOTES, cursor.getString(7));
            list.add(map);

            adapter = new SimpleAdapter(
                    getContext(), list, R.layout.medicine_list_item,
                    new String[]{MySQLiteDB.MEDICINE_ID, MySQLiteDB.MEDICINE_NAME, MySQLiteDB.MEDICINE_POWER, MySQLiteDB.MEDICINE_TYPE, MySQLiteDB.MEDICINE_SHIFT, MySQLiteDB.MEDICINE_TIME, MySQLiteDB.MEDICINE_ALARM, MySQLiteDB.MEDICINE_NOTES},
                    new int[]{R.id.medicine_id, R.id.medicine_name, R.id.medicine_power, R.id.medicine_type, R.id.medicine_shift, R.id.medicine_time, R.id.medicine_alarm, R.id.medicine_notes});

            CustomList.setAdapter(adapter);

        }
    }


    /* BottomSheet Medicine item */
    private String medicine_Power, medicine_Time, medicine_Alarm;
    private void Medicine_Item(final LinearLayoutCompat bottomSheetLayout, final ImageButton Show_BS_Btn, final SearchView Search, final EditText Id, final EditText Name, final EditText Power, final EditText Power_Unit, final EditText Type, final EditText Shift, final EditText Time, final EditText Notes){

        bottomSheetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator();
            }
        });


        Show_BS_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(BottomSheetBehavior.from(bottomSheetLayout).getState() == BottomSheetBehavior.STATE_COLLAPSED){
                    BottomSheetBehavior.from(bottomSheetLayout).setState(BottomSheetBehavior.STATE_EXPANDED);

                    Id.setText(null);
                    Name.setText(null);
                    Power.setText(null);
                    Power_Unit.setText(null);
                    Type.setText(null);
                    Shift.setText(null);
                    Time.setText(null);
                    medicine_Alarm_On_Btn.setVisibility(View.GONE);
                    medicine_Alarm_Off_Btn.setVisibility(View.VISIBLE);
                    Name.setText(null);
                    Notes.setText(null);

                    add_medicine.setVisibility(View.VISIBLE);
                    update_medicine.setVisibility(View.GONE);
                }
                else {
                    BottomSheetBehavior.from(bottomSheetLayout).setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

                vibrator();
            }
        });


        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search.setIconified(false);
                vibrator();
            }
        });

        Search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                try {
                    adapter.getFilter().filter(newText);
                }
                catch (Exception ex){
                    Log.i(Utils.TAG, "Search Medicine Exception: " + ex.getMessage());
                    ex.printStackTrace();
                }

                return true;
            }
        });

        add_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vibrator();

                final String medicine_Name = Name.getText().toString().trim();

                medicine_Power = Power.getText().toString().trim();

                if(!medicine_Power.isEmpty()){
                    medicine_Power = Power.getText().toString().trim()+Power_Unit.getText().toString().trim();
                }

                final String medicine_Type = Type.getText().toString().trim();
                final String medicine_Shift = Shift.getText().toString().trim();
                medicine_Time = Time.getText().toString().trim();
                final String medicine_Notes = Notes.getText().toString().trim();


                if(medicine_Alarm_On_Btn.getVisibility() == View.VISIBLE && !medicine_Time.isEmpty()){
                    medicine_Alarm = "Alarm_On";
                    medicine_Time = Time.getText().toString().trim()+" "+getResources().getString(R.string.clock);
                }
                else if(medicine_Alarm_Off_Btn.getVisibility() == View.VISIBLE){
                    medicine_Alarm = "Alarm_Off";
                }

                long rowId= new MySQLiteDB(getContext()).insertMedicineData(medicine_Name, medicine_Power, medicine_Type, medicine_Shift, medicine_Time, medicine_Alarm, medicine_Notes);

                if(rowId==-1){
                    Log.i(Utils.TAG, "Data Insert Unsuccessful");
                }
                else {
                    BottomSheetBehavior.from(bottomSheetLayout).setState(BottomSheetBehavior.STATE_COLLAPSED);
                    Log.i(Utils.TAG, "Row "+rowId+" is successfully inserted");

                    medicine_scroll.post(new Runnable(){ @Override public void run() {
                        medicine_scroll.scrollTo(medicine_scroll.getBottom(), Id.getTop());
                    }});

                    Id.setText(null);
                    Name.setText(null);
                    Power.setText(null);
                    Power_Unit.setText(null);
                    Type.setText(null);
                    Shift.setText(null);
                    Time.setText(null);
                    medicine_Alarm_On_Btn.setVisibility(View.GONE);
                    medicine_Alarm_Off_Btn.setVisibility(View.VISIBLE);
                    Notes.setText(null);
                    flag = true;
                }
            }
        });

        clear_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                clear_add_btn.startAnimation(Bounce_Animation);
                vibrator();

                medicine_scroll.post(new Runnable(){ @Override public void run() {
                    medicine_scroll.scrollTo(medicine_scroll.getBottom(), Id.getTop());
                }});

                Id.setText(null);
                Name.setText(null);
                Power.setText(null);
                Power_Unit.setText(null);
                Type.setText(null);
                Shift.setText(null);
                Time.setText(null);
                medicine_Alarm_On_Btn.setVisibility(View.GONE);
                medicine_Alarm_Off_Btn.setVisibility(View.VISIBLE);
                Notes.setText(null);
                flag = true;
            }
        });

        update_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vibrator();

                final String medicine_Id = Id.getText().toString().trim();
                final String medicine_Name = Name.getText().toString().trim();

                medicine_Power = Power.getText().toString().trim();

                if(!medicine_Power.isEmpty() && !Power_Unit.getText().toString().isEmpty()){
                    medicine_Power = Power.getText().toString().trim()+Power_Unit.getText().toString().trim();
                }

                final String medicine_Type = Type.getText().toString().trim();
                final String medicine_Shift = Shift.getText().toString().trim();
                medicine_Time = Time.getText().toString().trim();
                final String medicine_Notes = Notes.getText().toString().trim();

                if(medicine_Alarm_On_Btn.getVisibility() == View.VISIBLE && !medicine_Time.isEmpty()){
                    medicine_Alarm = "Alarm_On";
                    medicine_Time = Time.getText().toString().trim()+" "+getResources().getString(R.string.clock);
                }
                else if(medicine_Alarm_Off_Btn.getVisibility() == View.VISIBLE){
                    medicine_Alarm = "Alarm_Off";
                }

                boolean isUpdated = new MySQLiteDB(getContext()).updateMedicineData(medicine_Id, medicine_Name, medicine_Power, medicine_Type, medicine_Shift, medicine_Time, medicine_Alarm, medicine_Notes);

                if(isUpdated){

                    Log.i(Utils.TAG, "Data is updated");

                    medicine_scroll.post(new Runnable(){ @Override public void run() {
                        medicine_scroll.scrollTo(medicine_scroll.getBottom(), Id.getTop());
                    }});

                    Id.setText(null);
                    Name.setText(null);
                    Power.setText(null);
                    Power_Unit.setText(null);
                    Type.setText(null);
                    Shift.setText(null);
                    Time.setText(null);
                    medicine_Alarm_On_Btn.setVisibility(View.GONE);
                    medicine_Alarm_Off_Btn.setVisibility(View.VISIBLE);
                    Notes.setText(null);
                    flag= true;

                    add_medicine.setVisibility(View.VISIBLE);
                    update_medicine.setVisibility(View.GONE);

                    BottomSheetBehavior.from(bottomSheetLayout).setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                else {
                    Log.i(Utils.TAG, "Data isn't updated");
                }
            }
        });

        cancel_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                cancel_update_btn.startAnimation(Bounce_Animation);
                vibrator();

                medicine_scroll.post(new Runnable(){ @Override public void run() {
                    medicine_scroll.scrollTo(medicine_scroll.getBottom(), Id.getTop());
                }});

                Id.setText(null);
                Name.setText(null);
                Power.setText(null);
                Power_Unit.setText(null);
                Type.setText(null);
                Shift.setText(null);
                Time.setText(null);
                medicine_Alarm_On_Btn.setVisibility(View.GONE);
                medicine_Alarm_Off_Btn.setVisibility(View.VISIBLE);
                Notes.setText(null);
                flag= true;

                add_medicine.setVisibility(View.VISIBLE);
                update_medicine.setVisibility(View.GONE);
            }
        });

    }


    /* Dialog Box */
    public void Custom_DialogBox( View.OnClickListener view_listener, View.OnClickListener update_listener, View.OnClickListener delete_listener){

        View Custom_Dialog_View = LayoutInflater.from(getContext()).inflate(R.layout.dialog_medicine, null);

        final ImageButton View_Button = Custom_Dialog_View.findViewById(R.id.view_btn_id);
        final ImageButton Update_Button = Custom_Dialog_View.findViewById(R.id.update_btn_id);
        final ImageButton Delete_Button = Custom_Dialog_View.findViewById(R.id.delete_btn_id);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        alertDialogBuilder.setView(Custom_Dialog_View);
        alertDialogBuilder.setCancelable(true);


        alertDialog = alertDialogBuilder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getDecorView().setBackgroundColor(TRANSPARENT);
        alertDialog.show();


        /* View */
        View_Button.setOnClickListener(view_listener);
        Dialog_Bounce_Animation();
        View_Button.startAnimation(Dialog_Bounce_Animation);


        /* Update */
        Update_Button.setOnClickListener(update_listener);
        Dialog_Bounce_Animation();
        Update_Button.startAnimation(Dialog_Bounce_Animation);


        /* Delete */
        Delete_Button.setOnClickListener(delete_listener);
        Dialog_Bounce_Animation();
        Delete_Button.startAnimation(Dialog_Bounce_Animation);
    }


    /* View Medicine Details */
    public void Medicine_View_DialogBox(TextView name, TextView power, TextView type, TextView shift, TextView time, TextView alarm, TextView notes){

        View Medicine_Dialog_View = LayoutInflater.from(getContext()).inflate(R.layout.dialog_medicine_details, null);

        Dialog_Bounce_Animation();
        Medicine_Dialog_View.startAnimation(Dialog_Bounce_Animation);

        final ImageButton cancel_dialog = Medicine_Dialog_View.findViewById(R.id.cancel_dialog_id);
        final TextView Medicine_Name_Content_Tv = Medicine_Dialog_View.findViewById(R.id.medicine_name_content_tv);
        final TextView Medicine_Power_Content_Tv = Medicine_Dialog_View.findViewById(R.id.medicine_power_content_tv);
        final TextView Medicine_Type_Content_Tv = Medicine_Dialog_View.findViewById(R.id.medicine_type_content_tv);
        final TextView Medicine_Shift_Content_Tv = Medicine_Dialog_View.findViewById(R.id.medicine_shift_content_tv);
        final TextView Medicine_Time_Title_Tv = Medicine_Dialog_View.findViewById(R.id.medicine_timing_title_tv);
        final TextView Medicine_Time_Content_Tv = Medicine_Dialog_View.findViewById(R.id.medicine_time_content_tv);
        final ImageView Medicine_Reminder = Medicine_Dialog_View.findViewById(R.id.medicine_reminder_Icon);
        final TextView Medicine_Notes_Title_Tv = Medicine_Dialog_View.findViewById(R.id.medicine_notes_title_tv);
        final TextView Medicine_Notes_Content_Tv = Medicine_Dialog_View.findViewById(R.id.medicine_notes_content_tv);


        /* Set Font */
        if (getActivity() == null) return;
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "pf_regular.ttf");
        Medicine_Name_Content_Tv.setTypeface(tf);
        Medicine_Power_Content_Tv.setTypeface(tf);
        Medicine_Type_Content_Tv.setTypeface(tf);
        Medicine_Shift_Content_Tv.setTypeface(tf);
        Medicine_Time_Title_Tv.setTypeface(tf);
        Medicine_Time_Content_Tv.setTypeface(tf);
        Medicine_Notes_Title_Tv.setTypeface(tf);
        Medicine_Notes_Content_Tv.setTypeface(tf);


        /* Set Text */
        Medicine_Name_Content_Tv.setText(name.getText().toString().trim());
        Medicine_Power_Content_Tv.setText(power.getText().toString().trim());
        Medicine_Type_Content_Tv.setHint(type.getText().toString().trim());


        if(time.getText().toString().trim().length()>8){
            Medicine_Time_Content_Tv.setText(time.getText().toString().substring(0,8).trim());
        }
        else {
            Medicine_Time_Content_Tv.setText(time.getText().toString().trim());
        }

        if(alarm.getText().toString().equals("Alarm_On")){
            Medicine_Reminder.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_alarm_on));
        }
        else {
            Medicine_Reminder.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_alarm_off));
        }

        Medicine_Shift_Content_Tv.setText(shift.getText().toString().trim());
        Medicine_Notes_Content_Tv.setText(notes.getText().toString().trim());


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(Medicine_Dialog_View);
        alertDialogBuilder.setCancelable(true);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getDecorView().setBackgroundColor(TRANSPARENT);
        alertDialog.show();

        /* Cancel Dialog Box */
        cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator();
                alertDialog.dismiss();
            }
        });

    }


    /* Refresh display data */
    private class Refresh extends TimerTask {
        @Override
        public void run() {

            if(getActivity() == null) return;

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(flag){
                        Display_Data();
                        flag=false;
                    }
                }
            });
        }
    }


    /* Input Filter */
    public class DecimalDigitsInputFilter implements InputFilter {

        Pattern mPattern;

        private DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }

    }


    /* Button Animation */
    private void Animation(View Layout, ImageButton clear) {

        if (Layout.getVisibility() == View.GONE) {

            clear.setRotation(0);

            clear.animate().rotationBy(360).setDuration(625);

            Animation leftToRight = AnimationUtils.loadAnimation(getContext(), R.anim.left_to_right);
            Layout.setAnimation(leftToRight);

            Layout.setVisibility(View.VISIBLE);
        }
        else {
            clear.animate().rotationBy(-360).setDuration(625);

            Animation rightToLeft = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_left);
            Layout.setAnimation(rightToLeft);

            Layout.setVisibility(View.GONE);
        }
    }

    private void AnimationForTime(View Layout, ImageButton view_on, ImageButton view_off, ImageButton clear) {

        if (Layout.getVisibility() == View.GONE) {

            view_on.setRotation(0);
            view_off.setRotation(0);
            clear.setRotation(0);

            view_on.animate().rotationBy(360).setDuration(625);
            view_off.animate().rotationBy(360).setDuration(625);
            clear.animate().rotationBy(360).setDuration(625);

            Animation leftToRight = AnimationUtils.loadAnimation(getContext(), R.anim.left_to_right);
            Layout.setAnimation(leftToRight);

            Layout.setVisibility(View.VISIBLE);
        }
        else {
            view_on.animate().rotationBy(-360).setDuration(625);
            view_off.animate().rotationBy(-360).setDuration(625);
            clear.animate().rotationBy(-360).setDuration(625);

            Animation rightToLeft = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_left);
            Layout.setAnimation(rightToLeft);

            Layout.setVisibility(View.GONE);
        }
    }


    /* Bounce when button is clicked */
    public void Bounce_Animation(){
        if (getActivity() == null) return;
        Bounce_Animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.025, 0.5);
        Bounce_Animation.setInterpolator(interpolator);
    }

    public void Dialog_Bounce_Animation(){
        if (getActivity() == null) return;
        Dialog_Bounce_Animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(.1, 5);
        Dialog_Bounce_Animation.setInterpolator(interpolator);
    }


    /* Vibrate when button is clicked */
    public void vibrator() {

        if (getActivity() == null) return;
        Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(35);
    }
}
