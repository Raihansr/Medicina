package com.raihan_sr.medicina.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.raihan_sr.medicina.util.CustomSnackbar;
import com.raihan_sr.medicina.util.Utils;
import com.raihan_sr.medicina.database.MySQLiteDB;
import com.raihan_sr.medicina.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static android.graphics.Color.TRANSPARENT;


public class Fragment_Profile extends Fragment implements TabLayout.OnTabSelectedListener {

    private View rootView;
    private Context context;
    private Activity activity;
    private TabLayout tabLayout;
    private AppCompatEditText profile_Name_etxt;
    private AppCompatEditText profile_Sex_etxt;
    private AppCompatEditText profile_BirthDate_etxt;
    private AppCompatEditText profile_Age_etxt;
    private AppCompatEditText profile_BloodGroup_etxt;

    private LinearLayoutCompat Height_Metric_Mode_Layout;
    private LinearLayoutCompat Height_Imperial_Mode_Layout;
    private AppCompatEditText profile_Height_cm_etxt;
    private AppCompatEditText profile_Height_ft_etxt;
    private AppCompatEditText profile_Height_in_etxt;
    private AppCompatImageButton profile_Height_Mode;

    private LinearLayoutCompat Weight_Metric_Mode_Layout;
    private LinearLayoutCompat Weight_Imperial_Mode_Layout;
    private AppCompatEditText profile_Weight_kg_etxt;
    private AppCompatEditText profile_Weight_st_etxt;
    private AppCompatEditText profile_Weight_lb_etxt;
    private AppCompatImageButton profile_Weight_Mode;

    private AppCompatEditText profile_Address_etxt;
    private AppCompatEditText profile_Notes_etxt;

    private AppCompatImageButton name_btn;
    private AppCompatImageButton sex_btn;
    private AppCompatImageButton birth_btn;
    private AppCompatImageButton age_btn;
    private AppCompatImageButton blood_btn;
    private AppCompatImageButton height_btn;
    private AppCompatImageButton weight_btn;
    private AppCompatImageButton address_btn;
    private AppCompatImageButton notes_btn;

    private AppCompatTextView profile_height_cm_tv;
    private AppCompatTextView profile_height_ft_tv;
    private AppCompatTextView profile_height_in_tv;
    private AppCompatTextView profile_weight_kg_tv;
    private AppCompatTextView profile_weight_st_tv;
    private AppCompatTextView profile_weight_lb_tv;
    private long rowId;
    private boolean isNamePressed = true, isSexPressed = true, isBirthPressed = true, isAgePressed = true, isBloodPressed = true,
            isHeightPressed = true, isWeightPressed = true, isAddressPressed = true, isNotesPressed = true;
    private String profile_Height, profile_Weight;
    private AlertDialog alertDialog;
    private Animation Bounce_Animation;
    private Handler handler = new Handler();
    private Handler handlers = new Handler();
    private int progress;
    private DatePickerDialog datePickerDialog;
    private String _id = null;
    private boolean isNewData = true;

    public Fragment_Profile() {}

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_, container, false);

        profile_Name_etxt = rootView.findViewById(R.id.profile_name);
        profile_Sex_etxt = rootView.findViewById(R.id.profile_sex);
        profile_BirthDate_etxt = rootView.findViewById(R.id.profile_birthDate);
        profile_Age_etxt = rootView.findViewById(R.id.profile_age);
        profile_BloodGroup_etxt = rootView.findViewById(R.id.profile_bloodGroup);

        Height_Metric_Mode_Layout = rootView.findViewById(R.id.profile_Height_Metric_Layout);
        Height_Imperial_Mode_Layout = rootView.findViewById(R.id.profile_Height_Imperial_Layout);
        profile_Height_cm_etxt = rootView.findViewById(R.id.profile_height_cm);
        profile_Height_ft_etxt = rootView.findViewById(R.id.profile_height_ft);
        profile_Height_in_etxt = rootView.findViewById(R.id.profile_height_inch);
        profile_Height_Mode = rootView.findViewById(R.id.profile_height_Mode);

        Weight_Metric_Mode_Layout = rootView.findViewById(R.id.profile_Weight_Metric_Layout);
        Weight_Imperial_Mode_Layout = rootView.findViewById(R.id.profile_Weight_Imperial_Layout);
        profile_Weight_kg_etxt = rootView.findViewById(R.id.profile_weight_kg);
        profile_Weight_st_etxt = rootView.findViewById(R.id.profile_weight_st);
        profile_Weight_lb_etxt = rootView.findViewById(R.id.profile_weight_lb);
        profile_Weight_Mode = rootView.findViewById(R.id.profile_weight_Mode);

        profile_Address_etxt = rootView.findViewById(R.id.profile_address);
        profile_Notes_etxt = rootView.findViewById(R.id.profile_notes);

        name_btn = rootView.findViewById(R.id.name_btn);
        sex_btn = rootView.findViewById(R.id.sex_btn);
        birth_btn = rootView.findViewById(R.id.birth_btn);
        age_btn = rootView.findViewById(R.id.age_btn);
        blood_btn = rootView.findViewById(R.id.blood_btn);
        height_btn = rootView.findViewById(R.id.height_btn);
        weight_btn = rootView.findViewById(R.id.weight_btn);
        address_btn = rootView.findViewById(R.id.address_btn);
        notes_btn = rootView.findViewById(R.id.notes_btn);

        profile_height_cm_tv = rootView.findViewById(R.id.profile_height_cm_tv);
        profile_height_ft_tv = rootView.findViewById(R.id.profile_height_ft_tv);
        profile_height_in_tv = rootView.findViewById(R.id.profile_height_in_tv);
        profile_weight_kg_tv = rootView.findViewById(R.id.profile_weight_kg_tv);
        profile_weight_st_tv = rootView.findViewById(R.id.profile_weight_st_tv);
        profile_weight_lb_tv = rootView.findViewById(R.id.profile_weight_lb_tv);


        /* Tab Button */
        tabLayout = rootView.findViewById(R.id.tabLayout_id);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.ic_add_tab);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.ic_bsa_tab);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.ic_bmi_tab);
        Objects.requireNonNull(tabLayout.getTabAt(3)).setIcon(R.drawable.ic_bmr_tab);
        Objects.requireNonNull(tabLayout.getTabAt(4)).setIcon(R.drawable.ic_hr_tab);
        Objects.requireNonNull(tabLayout.getTabAt(5)).setIcon(R.drawable.ic_bp_tab);
        Objects.requireNonNull(tabLayout.getTabAt(6)).setIcon(R.drawable.ic_share_tab);

        tabLayout.addOnTabSelectedListener(this);


        /* Set Font */
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "pf_regular.ttf");

        profile_Name_etxt.setTypeface(tf);
        profile_Sex_etxt.setTypeface(tf);
        profile_BirthDate_etxt.setTypeface(tf);
        profile_Age_etxt.setTypeface(tf);
        profile_BloodGroup_etxt.setTypeface(tf);

        profile_Height_cm_etxt.setTypeface(tf);
        profile_Height_ft_etxt.setTypeface(tf);
        profile_Height_in_etxt.setTypeface(tf);

        profile_Weight_kg_etxt.setTypeface(tf);
        profile_Weight_st_etxt.setTypeface(tf);
        profile_Weight_lb_etxt.setTypeface(tf);

        profile_Address_etxt.setTypeface(tf);
        profile_Notes_etxt.setTypeface(tf);

        profile_height_cm_tv.setTypeface(tf);
        profile_height_ft_tv.setTypeface(tf);
        profile_height_in_tv.setTypeface(tf);

        profile_weight_kg_tv.setTypeface(tf);
        profile_weight_st_tv.setTypeface(tf);
        profile_weight_lb_tv.setTypeface(tf);


        /* Add Profile Data */
        isNewData = Objects.requireNonNull(getContext()).getSharedPreferences(MySQLiteDB.SHARED_PREF_NAME, Context.MODE_PRIVATE).getBoolean(MySQLiteDB.BOOLEAN_SHARED_PREF, true);
        if (isNewData) {
            Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.ic_add_tab);
            Objects.requireNonNull(tabLayout.getTabAt(0)).setText("Add");

            for (AppCompatImageButton button : new AppCompatImageButton[]{name_btn, sex_btn, birth_btn, age_btn, blood_btn, height_btn, weight_btn, address_btn, notes_btn}){
                button.setEnabled(false);
            }

            for (View view : new View[]{profile_Name_etxt, profile_Sex_etxt, profile_BirthDate_etxt, profile_Age_etxt, profile_BloodGroup_etxt, profile_Height_cm_etxt, profile_Height_ft_etxt,
                    profile_Height_in_etxt, profile_Height_Mode, profile_Weight_kg_etxt, profile_Weight_st_etxt, profile_Weight_lb_etxt, profile_Weight_Mode, profile_Address_etxt, profile_Notes_etxt}){
                view.setEnabled(true);
            }

        } else {
            Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.ic_delete_tab);
            Objects.requireNonNull(tabLayout.getTabAt(0)).setText("Delete");

            for (AppCompatImageButton button : new AppCompatImageButton[]{name_btn, sex_btn, birth_btn, age_btn, blood_btn, height_btn, weight_btn, address_btn, notes_btn}){
                button.setEnabled(true);
            }

            for (View view : new View[]{profile_Name_etxt, profile_Sex_etxt, profile_BirthDate_etxt, profile_Age_etxt, profile_BloodGroup_etxt, profile_Height_cm_etxt, profile_Height_ft_etxt,
                    profile_Height_in_etxt, profile_Height_Mode, profile_Weight_kg_etxt, profile_Weight_st_etxt, profile_Weight_lb_etxt, profile_Weight_Mode, profile_Address_etxt, profile_Notes_etxt}){
                view.setEnabled(false);
            }
        }


        /* Display Profile Data */
        Cursor cursor = new MySQLiteDB(getContext()).displayProfileData();

        if (cursor.getCount() == 0) {
            Log.i(Utils.TAG, "Data not found");
        }
        while (cursor.moveToNext()) {

            _id = cursor.getString(0);
            profile_Name_etxt.setText(cursor.getString(1));
            profile_Sex_etxt.setText(cursor.getString(2));
            profile_BirthDate_etxt.setText(cursor.getString(3));
            profile_Age_etxt.setText(cursor.getString(4));
            profile_BloodGroup_etxt.setText(cursor.getString(5));

            if (cursor.getString(6).contains("cm")) {
                Height_Metric_Mode_Layout.setVisibility(View.VISIBLE);
                Height_Imperial_Mode_Layout.setVisibility(View.GONE);

                if (cursor.getString(6).replace("cm", "").equals("")) {
                    profile_Height_cm_etxt.setText(null);

                } else {
                    String height = cursor.getString(6).replace("cm", "").trim();
                    profile_Height_cm_etxt.setText(height);
                }
            }
            if (cursor.getString(6).contains("in")) {
                Height_Metric_Mode_Layout.setVisibility(View.GONE);
                Height_Imperial_Mode_Layout.setVisibility(View.VISIBLE);

                if (cursor.getString(6).replace("ft", "").replace("in", "").equals("")) {
                    profile_Height_ft_etxt.setText(null);
                    profile_Height_in_etxt.setText(null);

                } else {
                    String[] height = cursor.getString(6).replace("in", "").trim().split("ft");
                    profile_Height_ft_etxt.setText(height[0].trim());
                    profile_Height_in_etxt.setText(height[1].trim());
                }
            }


            if (cursor.getString(7).contains("kg")) {
                Weight_Metric_Mode_Layout.setVisibility(View.VISIBLE);
                Weight_Imperial_Mode_Layout.setVisibility(View.GONE);

                if (cursor.getString(7).replace("kg", "").equals("")) {
                    profile_Weight_kg_etxt.setText(null);
                }
                else {
                    String weight = cursor.getString(7).replace("kg", "").trim();
                    profile_Weight_kg_etxt.setText(weight);
                }

            }
            if (cursor.getString(7).contains("st")) {
                Weight_Metric_Mode_Layout.setVisibility(View.GONE);
                Weight_Imperial_Mode_Layout.setVisibility(View.VISIBLE);

                if (cursor.getString(7).replace("st", "").replace("lb", "").equals("")) {
                    profile_Weight_st_etxt.setText(null);
                    profile_Weight_lb_etxt.setText(null);
                }
                else {
                    String[] weight = cursor.getString(7).replace("lb", "").trim().split("st");
                    profile_Weight_st_etxt.setText(weight[0].trim());
                    profile_Weight_lb_etxt.setText(weight[1].trim());
                }
            }

            profile_Address_etxt.setText(cursor.getString(8));
            profile_Notes_etxt.setText(cursor.getString(9));
        }


        /* Profile Name */
        final LinearLayoutCompat name_btn_layout = rootView.findViewById(R.id.name_btn_layout);
        final AppCompatImageButton name_update_btn = rootView.findViewById(R.id.name_update_btn);
        final AppCompatImageButton name_delete_btn = rootView.findViewById(R.id.name_delete_btn);

        profile_Name_etxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() >= 20) {
                    profile_Name_etxt.setTextSize(16);

                } else if (s.length() >= 15 && s.length() < 20) {
                    profile_Name_etxt.setTextSize(18);

                } else if (s.length() >= 10 && s.length() < 15) {
                    profile_Name_etxt.setTextSize(19);

                } else if (s.length() < 10) {
                    profile_Name_etxt.setTextSize(20);
                }
            }
        });

        profile_Name_etxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Open_Close_Animation(name_btn_layout, new ImageButton[]{name_update_btn, name_delete_btn});
                return false;
            }
        });

        name_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                name_btn.startAnimation(Bounce_Animation);
                vibrator();

                Open_Close_Animation(name_btn_layout, new ImageButton[]{name_update_btn, name_delete_btn});
            }
        });

        name_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                name_update_btn.startAnimation(Bounce_Animation);
                vibrator();

                if (isNamePressed) {

                    isNamePressed = false;

                    name_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_update));

                    profile_Name_etxt.setTextColor(Color.RED);
                    profile_Name_etxt.setEnabled(true);

                } else {
                    isNamePressed = true;

                    Open_Close_Animation(name_btn_layout, new ImageButton[]{name_update_btn, name_delete_btn});

                    new MySQLiteDB(getContext()).updateProfile(_id, MySQLiteDB.NAME, Objects.requireNonNull(profile_Name_etxt.getText()).toString());

                    name_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));

                    profile_Name_etxt.setTextColor(Color.BLACK);
                    profile_Name_etxt.setEnabled(false);
                }
            }
        });

        name_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                name_delete_btn.startAnimation(Bounce_Animation);
                vibrator();

                Delete_Data(MySQLiteDB.NAME, profile_Name_etxt);
                Open_Close_Animation(name_btn_layout, new ImageButton[]{name_update_btn, name_delete_btn});
            }
        });



        /* Profile Sex */
        final LinearLayoutCompat sex_btn_layout = rootView.findViewById(R.id.sex_btn_layout);
        final AppCompatImageButton sex_update_btn = rootView.findViewById(R.id.sex_update_btn);
        final AppCompatImageButton sex_delete_btn = rootView.findViewById(R.id.sex_delete_btn);

        profile_Sex_etxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Open_Close_Animation(sex_btn_layout, new ImageButton[]{sex_update_btn, sex_delete_btn});

                return false;
            }
        });

        profile_Sex_etxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(getContext(), profile_Sex_etxt);

                popup.getMenu().add("Male");
                popup.getMenu().add("Female");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        profile_Sex_etxt.setText(item.getTitle());
                        return true;
                    }
                });

                popup.show();
            }
        });

        sex_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                sex_btn.startAnimation(Bounce_Animation);
                vibrator();

                Open_Close_Animation(sex_btn_layout, new ImageButton[]{sex_update_btn, sex_delete_btn});
            }
        });

        sex_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                sex_update_btn.startAnimation(Bounce_Animation);
                vibrator();

                if (isSexPressed) {

                    isSexPressed = false;

                    sex_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_update));

                    profile_Sex_etxt.setTextColor(Color.RED);
                    profile_Sex_etxt.setEnabled(true);

                }
                else {
                    isSexPressed = true;

                    Open_Close_Animation(sex_btn_layout, new ImageButton[]{sex_update_btn, sex_delete_btn});

                    new MySQLiteDB(getContext()).updateProfile(_id, MySQLiteDB.SEX, Objects.requireNonNull(profile_Sex_etxt.getText()).toString());

                    sex_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));

                    profile_Sex_etxt.setTextColor(Color.BLACK);
                    profile_Sex_etxt.setEnabled(false);
                }
            }
        });

        sex_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                sex_delete_btn.startAnimation(Bounce_Animation);
                vibrator();

                Delete_Data(MySQLiteDB.SEX, profile_Sex_etxt);
                Open_Close_Animation(sex_btn_layout, new ImageButton[]{sex_update_btn, sex_delete_btn});
            }
        });



        /* Profile BirthDate */
        final LinearLayoutCompat birth_btn_layout = rootView.findViewById(R.id.birth_btn_layout);
        final AppCompatImageButton birth_update_btn = rootView.findViewById(R.id.birth_update_btn);
        final AppCompatImageButton birth_delete_btn = rootView.findViewById(R.id.birth_delete_btn);

        profile_BirthDate_etxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Open_Close_Animation(birth_btn_layout, new ImageButton[]{birth_update_btn, birth_delete_btn});

                return false;
            }
        });

        profile_BirthDate_etxt.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View v) {

                final DatePicker datePicker = new DatePicker(context);
                final int currentDay = datePicker.getDayOfMonth();
                final int currentMonth = datePicker.getMonth() + 1;
                final int currentYear = datePicker.getYear();


                datePickerDialog = new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        /* For Birth Date */
                        Calendar dob = Calendar.getInstance();
                        dob.set(year, month, dayOfMonth);

                        profile_BirthDate_etxt.setText(new SimpleDateFormat("dd MMM yyyy (EEE)").format(dob.getTime()));

                        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000);


                        /* For age */
                        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");

                        Date BirthDate = new Date();
                        Date CurrentDate = new Date();

                        try {
                            BirthDate = new SimpleDateFormat("dd/MM/yyyy").parse(dayOfMonth + "/" + (month + 1) + "/" + year);
                            CurrentDate = new SimpleDateFormat("dd/MM/yyyy").parse(currentDay + "/" + currentMonth + "/" + currentYear);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        int d1 = Integer.parseInt(formatter.format(BirthDate));
                        int d2 = Integer.parseInt(formatter.format(CurrentDate));
                        int age = (d2 - d1) / 10000;

                        profile_Age_etxt.setText(age + "");

                    }
                }, currentYear, currentMonth, currentDay);

                /* Set Old Date */
                if (!Objects.requireNonNull(profile_BirthDate_etxt.getText()).toString().isEmpty()) {

                    try {
                        Date getDate = new SimpleDateFormat("dd MMM yyyy").parse(profile_BirthDate_etxt.getText().toString().trim());

                        @SuppressLint("SimpleDateFormat") String[] birthDate = new SimpleDateFormat("yyyy/MM/dd").format(getDate).split("/");
                        int y = Integer.parseInt(birthDate[0].trim());
                        int m = Integer.parseInt(birthDate[1].trim()) - 1;
                        int d = Integer.parseInt(birthDate[2].trim());

                        datePickerDialog.updateDate(y, m, d);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                Calendar minDate = new GregorianCalendar(currentYear - 100, currentMonth - 1, currentDay);
                datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());

                Calendar maxDate = new GregorianCalendar(currentYear - 1, currentMonth - 1, currentDay);
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());

                datePickerDialog.show();
            }
        });

        birth_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                birth_btn.startAnimation(Bounce_Animation);
                vibrator();

                Open_Close_Animation(birth_btn_layout, new ImageButton[]{birth_update_btn, birth_delete_btn});
            }
        });

        birth_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                birth_update_btn.startAnimation(Bounce_Animation);
                vibrator();

                if (isBirthPressed) {

                    isBirthPressed = false;

                    birth_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_update));

                    profile_BirthDate_etxt.setTextColor(Color.RED);
                    profile_BirthDate_etxt.setEnabled(true);

                } else {
                    isBirthPressed = true;

                    Open_Close_Animation(birth_btn_layout, new ImageButton[]{birth_update_btn, birth_delete_btn});

                    new MySQLiteDB(getContext()).updateProfile(_id, MySQLiteDB.BIRTH_DATE, Objects.requireNonNull(profile_BirthDate_etxt.getText()).toString());
                    new MySQLiteDB(getContext()).updateProfile(_id, MySQLiteDB.AGE, Objects.requireNonNull(profile_Age_etxt.getText()).toString());

                    birth_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));

                    profile_BirthDate_etxt.setTextColor(Color.BLACK);
                    profile_BirthDate_etxt.setEnabled(false);
                }
            }
        });

        birth_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                birth_delete_btn.startAnimation(Bounce_Animation);
                vibrator();

                Delete_Data(MySQLiteDB.BIRTH_DATE, profile_BirthDate_etxt);

                Open_Close_Animation(birth_btn_layout, new ImageButton[]{birth_update_btn, birth_delete_btn});
            }
        });



        /* Profile Age */
        final LinearLayoutCompat age_btn_layout = rootView.findViewById(R.id.age_btn_layout);
        final AppCompatImageButton age_update_btn = rootView.findViewById(R.id.age_update_btn);
        final AppCompatImageButton age_delete_btn = rootView.findViewById(R.id.age_delete_btn);

        profile_Age_etxt.setFilters(new InputFilter[]{new InputFilter_Min_Max_Integer("1", "100")});
        profile_Age_etxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                Open_Close_Animation(age_btn_layout, new ImageButton[]{age_update_btn, age_delete_btn});
            }
        });

        age_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                age_btn.startAnimation(Bounce_Animation);
                vibrator();

                Open_Close_Animation(age_btn_layout, new ImageButton[]{age_update_btn, age_delete_btn});
            }
        });

        age_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                age_update_btn.startAnimation(Bounce_Animation);
                vibrator();

                if (isAgePressed) {

                    isAgePressed = false;

                    age_update_btn.setImageDrawable(Objects.requireNonNull(getContext()).getResources().getDrawable(R.drawable.ic_update));

                    profile_Age_etxt.setTextColor(Color.RED);
                    profile_Age_etxt.setEnabled(true);

                } else {
                    isAgePressed = true;

                    Open_Close_Animation(age_btn_layout, new ImageButton[]{age_update_btn, age_delete_btn});

                    new MySQLiteDB(getContext()).updateProfile(_id, MySQLiteDB.AGE, Objects.requireNonNull(profile_Age_etxt.getText()).toString());

                    age_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));

                    profile_Age_etxt.setTextColor(Color.BLACK);
                    profile_Age_etxt.setEnabled(false);
                }
            }
        });

        age_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                age_delete_btn.startAnimation(Bounce_Animation);
                vibrator();

                Delete_Data(MySQLiteDB.AGE, profile_Age_etxt);

                Open_Close_Animation(age_btn_layout, new ImageButton[]{age_update_btn, age_delete_btn});
            }
        });



        /* Profile Blood Group */
        final LinearLayoutCompat blood_btn_layout = rootView.findViewById(R.id.blood_btn_layout);
        final AppCompatImageButton blood_view_btn = rootView.findViewById(R.id.blood_view_btn);
        final AppCompatImageButton blood_update_btn = rootView.findViewById(R.id.blood_update_btn);
        final AppCompatImageButton blood_delete_btn = rootView.findViewById(R.id.blood_delete_btn);

        profile_BloodGroup_etxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Open_Close_Animation(blood_btn_layout, new ImageButton[]{blood_view_btn, blood_update_btn, blood_delete_btn});

                return false;
            }
        });

        profile_BloodGroup_etxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(getContext(), profile_BloodGroup_etxt);

                popup.getMenu().add("O+");
                popup.getMenu().add("O-");
                popup.getMenu().add("A+");
                popup.getMenu().add("A-");
                popup.getMenu().add("B+");
                popup.getMenu().add("B-");
                popup.getMenu().add("AB+");
                popup.getMenu().add("AB-");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        profile_BloodGroup_etxt.setText(item.getTitle());
                        return true;
                    }
                });

                popup.show();
            }
        });

        blood_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                blood_btn.startAnimation(Bounce_Animation);
                vibrator();

                Open_Close_Animation(blood_btn_layout, new ImageButton[]{blood_view_btn, blood_update_btn, blood_delete_btn});
            }
        });

        blood_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                blood_view_btn.startAnimation(Bounce_Animation);
                vibrator();

                if (!Objects.requireNonNull(profile_BloodGroup_etxt.getText()).toString().isEmpty()) {
                    Blood_Dialog_Box(profile_BloodGroup_etxt);
                }
                else {
                    new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill your Blood Group", CustomSnackbar.Duration.SHORT);
                }

                Open_Close_Animation(blood_btn_layout, new ImageButton[]{blood_view_btn, blood_update_btn, blood_delete_btn});
            }
        });

        blood_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                blood_update_btn.startAnimation(Bounce_Animation);
                vibrator();

                if (isBloodPressed) {

                    isBloodPressed = false;

                    blood_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_update));

                    profile_BloodGroup_etxt.setTextColor(Color.RED);
                    profile_BloodGroup_etxt.setEnabled(true);

                } else {
                    isBloodPressed = true;

                    Open_Close_Animation(blood_btn_layout, new ImageButton[]{blood_view_btn, blood_update_btn, blood_delete_btn});

                    new MySQLiteDB(getContext()).updateProfile(_id, MySQLiteDB.BLOOD_GROUP, Objects.requireNonNull(profile_BloodGroup_etxt.getText()).toString());

                    blood_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));

                    profile_BloodGroup_etxt.setTextColor(Color.BLACK);
                    profile_BloodGroup_etxt.setEnabled(false);
                }
            }
        });

        blood_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                blood_delete_btn.startAnimation(Bounce_Animation);
                vibrator();

                Delete_Data(MySQLiteDB.BLOOD_GROUP, profile_BloodGroup_etxt);

                Open_Close_Animation(blood_btn_layout, new ImageButton[]{blood_view_btn, blood_update_btn, blood_delete_btn});
            }
        });



        /* Profile Height */
        final RelativeLayout height_parent_layout = rootView.findViewById(R.id.height_parent_layout);
        final LinearLayoutCompat height_btn_layout = rootView.findViewById(R.id.height_btn_layout);
        final AppCompatImageButton height_update_btn = rootView.findViewById(R.id.height_update_btn);
        final AppCompatImageButton height_delete_btn = rootView.findViewById(R.id.height_delete_btn);

        profile_Height_cm_etxt.setFilters(new InputFilter[]{new InputFilter_Min_Max_Decimal("4", "1", "1", "213.4")});

        profile_Height_in_etxt.setFilters(new InputFilter[]{new InputFilter_Min_Max_Decimal("3", "1", "0", "12")});

        profile_Height_ft_etxt.setFilters(new InputFilter[]{new InputFilter_Min_Max_Integer("0", "6")});

        profile_Height_ft_etxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    if (s.toString().length() == 1) {
                        Selection.setSelection(profile_Height_ft_etxt.getText(), profile_Height_in_etxt.getSelectionStart());
                        profile_Height_in_etxt.requestFocus();
                    }
                } catch (Exception ex) {
                    Log.i(Utils.TAG, "Height Ft Exception: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        profile_Height_Mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), profile_Height_Mode);

                popup.getMenu().add("Metric");
                popup.getMenu().add("Imperial");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("SetTextI18n")
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getTitle().equals("Metric")) {

                            if (Height_Imperial_Mode_Layout.getVisibility() == View.VISIBLE) {

                                Height_Metric_Mode_Layout.setVisibility(View.VISIBLE);
                                Height_Imperial_Mode_Layout.setVisibility(View.GONE);

                                try {
                                    if (!Objects.requireNonNull(profile_Height_ft_etxt.getText()).toString().isEmpty() && !Objects.requireNonNull(profile_Height_in_etxt.getText()).toString().isEmpty()) {
                                        double feet = Double.parseDouble(profile_Height_ft_etxt.getText().toString().trim());
                                        double inch = Double.parseDouble(profile_Height_in_etxt.getText().toString().trim());
                                        double cm = ((feet * 12) + inch) / 0.39370;

                                        DecimalFormat df = new DecimalFormat("###.#");

                                        profile_Height_cm_etxt.setText(df.format(cm));
                                    }
                                } catch (Exception ex) {
                                    Log.i(Utils.TAG, "Height Metric Mode Exception: " + ex.getMessage());
                                    ex.printStackTrace();
                                }
                            }
                            else {
                                new CustomSnackbar(context, Utils.rootLayout, "You are already on Metric Mode", CustomSnackbar.Duration.SHORT);
                            }
                        }

                        if (item.getTitle().equals("Imperial")) {

                            if (Height_Metric_Mode_Layout.getVisibility() == View.VISIBLE) {

                                Height_Metric_Mode_Layout.setVisibility(View.GONE);
                                Height_Imperial_Mode_Layout.setVisibility(View.VISIBLE);

                                try {
                                    if (!Objects.requireNonNull(profile_Height_cm_etxt.getText()).toString().isEmpty()) {
                                        double cm = Double.parseDouble(profile_Height_cm_etxt.getText().toString().trim());
                                        double feet = ((cm / 2.54) / 12);
                                        double inch = ((cm / 2.54) % 12);

                                        if (feet == 7) {
                                            profile_Height_ft_etxt.setText(6 + "");
                                            profile_Height_in_etxt.setText(12 + "");
                                        } else if (feet < 1) {
                                            profile_Height_ft_etxt.setText(0 + "");
                                            DecimalFormat df = new DecimalFormat("##.#");
                                            profile_Height_in_etxt.setText(df.format(inch));
                                        } else {
                                            profile_Height_ft_etxt.setText(Math.round(Math.floor(feet)) + "");
                                            DecimalFormat df = new DecimalFormat("##.#");
                                            profile_Height_in_etxt.setText(df.format(inch));
                                        }
                                    }
                                } catch (Exception ex) {
                                    Log.i(Utils.TAG, "Height Imperial Mode Exception: " + ex.getMessage());
                                    ex.printStackTrace();
                                }
                            }
                            else {
                                new CustomSnackbar(context, Utils.rootLayout, "You are already on Imperial Mode", CustomSnackbar.Duration.SHORT);
                            }
                        }

                        return true;
                    }
                });

                popup.show();
            }
        });

        for(View view : new View[]{height_parent_layout, profile_Height_Mode, profile_Height_cm_etxt, profile_Height_ft_etxt, profile_Height_in_etxt}){
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    Open_Close_Animation(height_btn_layout, new ImageButton[]{height_update_btn, height_delete_btn});

                    return false;
                }
            });
        }

        height_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                height_btn.startAnimation(Bounce_Animation);
                vibrator();

                Open_Close_Animation(height_btn_layout, new ImageButton[]{height_update_btn, height_delete_btn});
            }
        });

        height_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                height_update_btn.startAnimation(Bounce_Animation);
                vibrator();

                if (isHeightPressed) {

                    isHeightPressed = false;

                    height_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_update));

                    for (EditText etxt : new EditText[]{profile_Height_cm_etxt, profile_Height_ft_etxt, profile_Height_in_etxt}){
                        etxt.setTextColor(Color.RED);
                        etxt.setEnabled(true);
                    }

                    profile_Height_Mode.setEnabled(true);

                } else {

                    if (Height_Metric_Mode_Layout.getVisibility() == View.VISIBLE) {

                        isHeightPressed = true;

                        String height_Value = Objects.requireNonNull(profile_Height_cm_etxt.getText()).toString().trim() + "cm";
                        new MySQLiteDB(getContext()).updateProfile(_id, MySQLiteDB.HEIGHT, height_Value);

                        Open_Close_Animation(height_btn_layout, new ImageButton[]{height_update_btn, height_delete_btn});

                        height_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));

                        for (EditText etxt : new EditText[]{profile_Height_cm_etxt, profile_Height_ft_etxt, profile_Height_in_etxt}){
                            etxt.setTextColor(Color.RED);
                            etxt.setEnabled(false);
                        }

                        profile_Height_Mode.setEnabled(false);

                    } else {

                        if (!Objects.requireNonNull(profile_Height_ft_etxt.getText()).toString().isEmpty() && Objects.requireNonNull(profile_Height_in_etxt.getText()).toString().isEmpty()) {
                            new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both feet & inch", CustomSnackbar.Duration.SHORT);
                        }
                        else if (profile_Height_ft_etxt.getText().toString().isEmpty() && !Objects.requireNonNull(profile_Height_in_etxt.getText()).toString().isEmpty()) {
                            new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both feet & inch", CustomSnackbar.Duration.SHORT);
                        }
                        else {
                            isHeightPressed = true;

                            String height_Value = profile_Height_ft_etxt.getText().toString().trim() + "ft" + Objects.requireNonNull(profile_Height_in_etxt.getText()).toString().trim() + "in";
                            new MySQLiteDB(getContext()).updateProfile(_id, MySQLiteDB.HEIGHT, height_Value);

                            Open_Close_Animation(height_btn_layout, new ImageButton[]{height_update_btn, height_delete_btn});

                            height_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));

                            profile_Height_cm_etxt.setTextColor(Color.BLACK);
                            profile_Height_ft_etxt.setTextColor(Color.BLACK);
                            profile_Height_in_etxt.setTextColor(Color.BLACK);

                            profile_Height_cm_etxt.setEnabled(false);
                            profile_Height_ft_etxt.setEnabled(false);
                            profile_Height_in_etxt.setEnabled(false);
                            profile_Height_Mode.setEnabled(false);
                        }

                    }

                }
            }
        });

        height_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                height_delete_btn.startAnimation(Bounce_Animation);
                vibrator();

                profile_Height_cm_etxt.setText(null);
                profile_Height_ft_etxt.setText(null);
                profile_Height_in_etxt.setText(null);

                new MySQLiteDB(getContext()).deleteProfile(_id, MySQLiteDB.HEIGHT);

                Open_Close_Animation(height_btn_layout, new ImageButton[]{height_update_btn, height_delete_btn});
            }
        });



        /* Profile Weight */
        final RelativeLayout weight_parent_layout = rootView.findViewById(R.id.weight_parent_layout);
        final LinearLayoutCompat weight_btn_layout = rootView.findViewById(R.id.weight_btn_layout);
        final AppCompatImageButton weight_update_btn = rootView.findViewById(R.id.weight_update_btn);
        final AppCompatImageButton weight_delete_btn = rootView.findViewById(R.id.weight_delete_btn);

        profile_Weight_kg_etxt.setFilters(new InputFilter[]{new InputFilter_Min_Max_Decimal("4", "1", "1", "152.4")});

        profile_Weight_lb_etxt.setFilters(new InputFilter[]{new InputFilter_Min_Max_Decimal("3", "1", "0", "14")});

        profile_Weight_st_etxt.setFilters(new InputFilter[]{new InputFilter_Min_Max_Integer("0", "23")});

        profile_Weight_st_etxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    if (s.toString().length() == 2) {
                        Selection.setSelection(profile_Weight_st_etxt.getText(), profile_Weight_lb_etxt.getSelectionStart());
                        profile_Weight_lb_etxt.requestFocus();
                    }
                } catch (Exception ex) {
                    Log.i(Utils.TAG, "Weight St Exception: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        profile_Weight_Mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup = new PopupMenu(getContext(), profile_Weight_Mode);

                popup.getMenu().add("Metric");
                popup.getMenu().add("Imperial");
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("SetTextI18n")
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getTitle().equals("Metric")) {

                            if (Weight_Imperial_Mode_Layout.getVisibility() == View.VISIBLE) {

                                Weight_Metric_Mode_Layout.setVisibility(View.VISIBLE);
                                Weight_Imperial_Mode_Layout.setVisibility(View.GONE);

                                try {
                                    if (!Objects.requireNonNull(profile_Weight_st_etxt.getText()).toString().isEmpty() && !Objects.requireNonNull(profile_Weight_lb_etxt.getText()).toString().isEmpty()) {

                                        double stone = Double.parseDouble(profile_Weight_st_etxt.getText().toString().trim());
                                        double pound = Double.parseDouble(profile_Weight_lb_etxt.getText().toString().trim());

                                        double kg = ((stone * 14) + pound) / 2.205;

                                        DecimalFormat df = new DecimalFormat("###.#");
                                        profile_Weight_kg_etxt.setText(df.format(kg));
                                    }
                                } catch (Exception ex) {
                                    Log.i(Utils.TAG, "Weight Metric Mode Exception: " + ex.getMessage());
                                    ex.printStackTrace();
                                }
                            }
                            else {
                                new CustomSnackbar(context, Utils.rootLayout, "You are already on Metric Mode", CustomSnackbar.Duration.SHORT);
                            }

                        }
                        if (item.getTitle().equals("Imperial")) {

                            if (Weight_Metric_Mode_Layout.getVisibility() == View.VISIBLE) {

                                Weight_Metric_Mode_Layout.setVisibility(View.GONE);
                                Weight_Imperial_Mode_Layout.setVisibility(View.VISIBLE);

                                try {
                                    if (!Objects.requireNonNull(profile_Weight_kg_etxt.getText()).toString().isEmpty()) {

                                        double kg = Double.parseDouble(profile_Weight_kg_etxt.getText().toString().trim());
                                        double stone = ((kg * 2.2046) / 14);
                                        double pound = ((kg * 2.2046) % 14);

                                        if (stone == 24) {
                                            profile_Weight_st_etxt.setText(23 + "");
                                            profile_Weight_lb_etxt.setText(14 + "");

                                        } else if (stone < 1) {
                                            profile_Weight_st_etxt.setText(0 + "");
                                            DecimalFormat df = new DecimalFormat("##.#");
                                            profile_Weight_lb_etxt.setText(df.format(pound));

                                        } else {
                                            profile_Weight_st_etxt.setText(Math.round(Math.floor(stone)) + "");
                                            DecimalFormat df = new DecimalFormat("##.#");
                                            profile_Weight_lb_etxt.setText(df.format(pound));
                                        }
                                    }
                                } catch (Exception ex) {
                                    Log.i(Utils.TAG, "Weight Imperial Mode Exception: " + ex.getMessage());
                                    ex.printStackTrace();
                                }
                            }
                            else {
                                new CustomSnackbar(context, Utils.rootLayout, "You are already on Imperial Mode", CustomSnackbar.Duration.SHORT);
                            }
                        }

                        return true;
                    }
                });

                popup.show();
            }
        });

        for(View view : new View[]{weight_parent_layout, profile_Weight_Mode, profile_Weight_kg_etxt, profile_Weight_st_etxt, profile_Weight_lb_etxt}){
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    Open_Close_Animation(weight_btn_layout, new ImageButton[]{weight_update_btn, weight_delete_btn});

                    return false;
                }
            });
        }

        weight_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                weight_btn.startAnimation(Bounce_Animation);
                vibrator();

                Open_Close_Animation(weight_btn_layout, new ImageButton[]{weight_update_btn, weight_delete_btn});
            }
        });

        weight_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                weight_update_btn.startAnimation(Bounce_Animation);
                vibrator();

                if (isWeightPressed) {

                    isWeightPressed = false;

                    weight_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_update));

                    profile_Weight_kg_etxt.setTextColor(Color.RED);
                    profile_Weight_st_etxt.setTextColor(Color.RED);
                    profile_Weight_lb_etxt.setTextColor(Color.RED);

                    profile_Weight_kg_etxt.setEnabled(true);
                    profile_Weight_st_etxt.setEnabled(true);
                    profile_Weight_lb_etxt.setEnabled(true);
                    profile_Weight_Mode.setEnabled(true);
                } else {

                    if (Weight_Metric_Mode_Layout.getVisibility() == View.VISIBLE) {
                        isWeightPressed = true;
                        String weight_Value = Objects.requireNonNull(profile_Weight_kg_etxt.getText()).toString().trim() + "kg";
                        new MySQLiteDB(getContext()).updateProfile(_id, MySQLiteDB.WEIGHT, weight_Value);

                        Open_Close_Animation(weight_btn_layout, new ImageButton[]{weight_update_btn, weight_delete_btn});

                        weight_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));

                        profile_Weight_kg_etxt.setTextColor(Color.BLACK);
                        profile_Weight_st_etxt.setTextColor(Color.BLACK);
                        profile_Weight_lb_etxt.setTextColor(Color.BLACK);

                        profile_Weight_kg_etxt.setEnabled(false);
                        profile_Weight_st_etxt.setEnabled(false);
                        profile_Weight_lb_etxt.setEnabled(false);
                        profile_Weight_Mode.setEnabled(false);
                    } else {

                        if (!Objects.requireNonNull(profile_Weight_st_etxt.getText()).toString().isEmpty() && Objects.requireNonNull(profile_Weight_lb_etxt.getText()).toString().isEmpty()) {
                            new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both Stone & Pound", CustomSnackbar.Duration.SHORT);
                        }
                        else if (profile_Weight_st_etxt.getText().toString().isEmpty() && !Objects.requireNonNull(profile_Weight_lb_etxt.getText()).toString().isEmpty()) {
                            new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both Stone & Pound", CustomSnackbar.Duration.SHORT);
                        }
                        else {
                            isWeightPressed = true;

                            String weight_Value = profile_Weight_st_etxt.getText().toString().trim() + "st" + Objects.requireNonNull(profile_Weight_lb_etxt.getText()).toString().trim() + "lb";
                            new MySQLiteDB(getContext()).updateProfile(_id, MySQLiteDB.WEIGHT, weight_Value);

                            Open_Close_Animation(weight_btn_layout, new ImageButton[]{weight_update_btn, weight_delete_btn});

                            weight_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));

                            profile_Weight_kg_etxt.setTextColor(Color.BLACK);
                            profile_Weight_st_etxt.setTextColor(Color.BLACK);
                            profile_Weight_lb_etxt.setTextColor(Color.BLACK);

                            profile_Weight_kg_etxt.setEnabled(false);
                            profile_Weight_st_etxt.setEnabled(false);
                            profile_Weight_lb_etxt.setEnabled(false);
                            profile_Weight_Mode.setEnabled(false);
                        }
                    }
                }
            }
        });

        weight_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                weight_delete_btn.startAnimation(Bounce_Animation);
                vibrator();

                profile_Weight_kg_etxt.setText(null);
                profile_Weight_st_etxt.setText(null);
                profile_Weight_lb_etxt.setText(null);

                new MySQLiteDB(getContext()).deleteProfile(_id, MySQLiteDB.WEIGHT);
                Open_Close_Animation(weight_btn_layout, new ImageButton[]{weight_update_btn, weight_delete_btn});
            }
        });


        /* Profile Address */
        final LinearLayoutCompat address_btn_layout = rootView.findViewById(R.id.address_btn_layout);
        final AppCompatImageButton address_update_btn = rootView.findViewById(R.id.address_update_btn);
        final AppCompatImageButton address_delete_btn = rootView.findViewById(R.id.address_delete_btn);

        profile_Address_etxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() >= 20) {
                    profile_Address_etxt.setTextSize(16);

                } else if (s.length() >= 15 && s.length() < 20) {
                    profile_Address_etxt.setTextSize(18);

                } else if (s.length() >= 10 && s.length() < 15) {
                    profile_Address_etxt.setTextSize(19);

                } else if (s.length() < 10) {
                    profile_Address_etxt.setTextSize(20);
                }
            }
        });

        profile_Address_etxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                Open_Close_Animation(address_btn_layout, new ImageButton[]{address_update_btn, address_delete_btn});
            }
        });

        address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                address_btn.startAnimation(Bounce_Animation);
                vibrator();

                Open_Close_Animation(address_btn_layout, new ImageButton[]{address_update_btn, address_delete_btn});
            }
        });

        address_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                address_update_btn.startAnimation(Bounce_Animation);
                vibrator();

                if (isAddressPressed) {

                    isAddressPressed = false;

                    address_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_update));

                    profile_Address_etxt.setTextColor(Color.RED);
                    profile_Address_etxt.setEnabled(true);

                } else {
                    isAddressPressed = true;

                    Open_Close_Animation(address_btn_layout, new ImageButton[]{address_update_btn, address_delete_btn});

                    new MySQLiteDB(context).updateProfile(_id, MySQLiteDB.ADDRESS, profile_Address_etxt.getText().toString());

                    address_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));

                    profile_Address_etxt.setTextColor(Color.BLACK);
                    profile_Address_etxt.setEnabled(false);
                }
            }
        });

        address_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                address_delete_btn.startAnimation(Bounce_Animation);
                vibrator();

                Delete_Data(MySQLiteDB.ADDRESS, profile_Address_etxt);

                Open_Close_Animation(address_btn_layout, new ImageButton[]{address_update_btn, address_delete_btn});
            }
        });



        /* Profile Notes */
        final LinearLayoutCompat notes_btn_layout = rootView.findViewById(R.id.notes_btn_layout);
        final AppCompatImageButton notes_update_btn = rootView.findViewById(R.id.notes_update_btn);
        final AppCompatImageButton notes_delete_btn = rootView.findViewById(R.id.notes_delete_btn);

        profile_Notes_etxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() >= 20) {
                    profile_Notes_etxt.setTextSize(16);

                } else if (s.length() >= 15 && s.length() < 20) {
                    profile_Notes_etxt.setTextSize(18);

                } else if (s.length() >= 10 && s.length() < 15) {
                    profile_Notes_etxt.setTextSize(19);

                } else if (s.length() < 10) {
                    profile_Notes_etxt.setTextSize(20);
                }
            }
        });

        profile_Notes_etxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Open_Close_Animation(notes_btn_layout, new ImageButton[]{notes_update_btn, notes_delete_btn});
            }
        });

        notes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                notes_btn.startAnimation(Bounce_Animation);
                vibrator();

                Open_Close_Animation(notes_btn_layout, new ImageButton[]{notes_update_btn, notes_delete_btn});
            }
        });

        notes_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                notes_update_btn.startAnimation(Bounce_Animation);
                vibrator();

                if (isNotesPressed) {

                    isNotesPressed = false;

                    notes_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_update));

                    profile_Notes_etxt.setTextColor(Color.RED);
                    profile_Notes_etxt.setEnabled(true);

                } else {
                    isNotesPressed = true;

                    Open_Close_Animation(notes_btn_layout, new ImageButton[]{notes_update_btn, notes_delete_btn});

                    new MySQLiteDB(getContext()).updateProfile(_id, MySQLiteDB.NOTES, Objects.requireNonNull(profile_Notes_etxt.getText()).toString());

                    notes_update_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));

                    profile_Notes_etxt.setTextColor(Color.BLACK);
                    profile_Notes_etxt.setEnabled(false);
                }
            }
        });

        notes_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bounce_Animation();
                notes_delete_btn.startAnimation(Bounce_Animation);
                vibrator();

                Delete_Data(MySQLiteDB.NOTES, profile_Notes_etxt);
                Open_Close_Animation(notes_btn_layout, new ImageButton[]{notes_update_btn, notes_delete_btn});
            }
        });

        return rootView;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        setTabActions(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        setTabActions(tab.getPosition());
    }

    private void setTabActions(int position){
        switch (position){

            case 0:
                Bounce_Animation();
                vibrator();

                final String profile_Name = Objects.requireNonNull(profile_Name_etxt.getText()).toString().trim();
                final String profile_Sex = Objects.requireNonNull(profile_Sex_etxt.getText()).toString().trim();
                final String profile_BirthDate = Objects.requireNonNull(profile_BirthDate_etxt.getText()).toString().trim();
                final String profile_Age = Objects.requireNonNull(profile_Age_etxt.getText()).toString().trim();
                final String profile_BloodGroup = Objects.requireNonNull(profile_BloodGroup_etxt.getText()).toString().trim();

                final String profile_Height_cm = Objects.requireNonNull(profile_Height_cm_etxt.getText()).toString().trim();
                final String profile_Height_ft = Objects.requireNonNull(profile_Height_ft_etxt.getText()).toString().trim();
                final String profile_Height_in = Objects.requireNonNull(profile_Height_in_etxt.getText()).toString().trim();

                final String profile_Weight_kg = Objects.requireNonNull(profile_Weight_kg_etxt.getText()).toString().trim();
                final String profile_Weight_st = Objects.requireNonNull(profile_Weight_st_etxt.getText()).toString().trim();
                final String profile_Weight_lb = Objects.requireNonNull(profile_Weight_lb_etxt.getText()).toString().trim();

                if (Height_Metric_Mode_Layout.getVisibility() == View.VISIBLE) {
                    profile_Height = profile_Height_cm + "cm";

                } else {
                    profile_Height = profile_Height_ft + "ft" + profile_Height_in + "in";
                }

                if (Weight_Metric_Mode_Layout.getVisibility() == View.VISIBLE) {
                    profile_Weight = profile_Weight_kg + "kg";

                } else {
                    profile_Weight = profile_Weight_st + "st" + profile_Weight_lb + "lb";
                }

                final String profile_Address = Objects.requireNonNull(profile_Address_etxt.getText()).toString().trim();
                final String profile_Description = Objects.requireNonNull(profile_Notes_etxt.getText()).toString().trim();

                if (isNewData) {
                    Custom_Dialog_Box("Are You Sure You Want To Create Your Profile", "Confirm", "Cancel", new View.OnClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(View v) {

                            vibrator();

                            if (!profile_Height_ft_etxt.getText().toString().isEmpty() && profile_Height_in_etxt.getText().toString().isEmpty()) {
                                new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both feet & inch", CustomSnackbar.Duration.SHORT);
                            }
                            else if (profile_Height_ft_etxt.getText().toString().isEmpty() && !profile_Height_in_etxt.getText().toString().isEmpty()) {
                                new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both feet & inch", CustomSnackbar.Duration.SHORT);
                            }
                            else if (!profile_Weight_st_etxt.getText().toString().isEmpty() && profile_Weight_lb_etxt.getText().toString().isEmpty()) {
                                new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both Stone & Pound", CustomSnackbar.Duration.SHORT);
                            }
                            else if (profile_Weight_st_etxt.getText().toString().isEmpty() && !profile_Weight_lb_etxt.getText().toString().isEmpty()) {
                                new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both Stone & Pound", CustomSnackbar.Duration.SHORT);
                            }
                            else {
                                rowId = new MySQLiteDB(getContext()).insertProfileData(profile_Name, profile_Sex, profile_BirthDate, profile_Age, profile_BloodGroup, profile_Height, profile_Weight, profile_Address, profile_Description);

                                if (rowId == -1) {
                                    Log.i(Utils.TAG, "Sorry! Your Profile isn't Created");
                                } else {
                                    Log.i(Utils.TAG, "Your Profile successfully Created");
                                    Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.ic_delete_tab);
                                    Objects.requireNonNull(tabLayout.getTabAt(0)).setText("Delete");
                                }

                                Objects.requireNonNull(getContext()).getSharedPreferences(MySQLiteDB.SHARED_PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(MySQLiteDB.BOOLEAN_SHARED_PREF, false).apply();
                                isNewData = false;

                                for (AppCompatImageButton button : new AppCompatImageButton[]{name_btn, sex_btn, birth_btn, age_btn, blood_btn, height_btn, weight_btn, address_btn, notes_btn}){
                                    button.setEnabled(true);
                                }

                                for (View view : new View[]{profile_Name_etxt, profile_Sex_etxt, profile_BirthDate_etxt, profile_Age_etxt, profile_BloodGroup_etxt, profile_Height_cm_etxt, profile_Height_ft_etxt,
                                        profile_Height_in_etxt, profile_Height_Mode, profile_Weight_kg_etxt, profile_Weight_st_etxt, profile_Weight_lb_etxt, profile_Weight_Mode, profile_Address_etxt, profile_Notes_etxt}){
                                    view.setEnabled(false);
                                }
                            }

                            alertDialog.dismiss();
                        }
                    });

                } else {
                    Custom_Dialog_Box("Are You Sure You Want To Delete_Data Your Profile", "Confirm Delete_Data?", "", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            vibrator();

                            new MySQLiteDB(getContext()).deleteProfileData(_id);
                            _id = null;

                            Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.ic_add_tab);
                            Objects.requireNonNull(tabLayout.getTabAt(0)).setText("Add");

                            Objects.requireNonNull(getContext()).getSharedPreferences(MySQLiteDB.SHARED_PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(MySQLiteDB.BOOLEAN_SHARED_PREF, true).apply();
                            isNewData = true;

                            for (AppCompatImageButton button : new AppCompatImageButton[]{name_btn, sex_btn, birth_btn, age_btn, blood_btn, height_btn, weight_btn, address_btn, notes_btn}){
                                button.setEnabled(false);
                            }

                            for (View view : new View[]{profile_Name_etxt, profile_Sex_etxt, profile_BirthDate_etxt, profile_Age_etxt, profile_BloodGroup_etxt, profile_Height_cm_etxt, profile_Height_ft_etxt,
                                    profile_Height_in_etxt, profile_Height_Mode, profile_Weight_kg_etxt, profile_Weight_st_etxt, profile_Weight_lb_etxt, profile_Weight_Mode, profile_Address_etxt, profile_Notes_etxt}){
                                view.setEnabled(true);
                                if(view instanceof AppCompatEditText) ((AppCompatEditText) view).setText(null);
                            }

                            for (int id : new int[]{R.id.name_btn_layout, R.id.sex_btn_layout, R.id.birth_btn_layout, R.id.age_btn_layout, R.id.blood_btn_layout,
                                    R.id.height_btn_layout, R.id.weight_btn_layout, R.id.address_btn_layout, R.id.notes_btn_layout}){
                                rootView.findViewById(id).setVisibility(View.GONE);
                            }

                            alertDialog.dismiss();
                        }
                    });
                }
                break;

            case 1:
                if ((Height_Imperial_Mode_Layout.getVisibility() == View.VISIBLE) && (Weight_Imperial_Mode_Layout.getVisibility() == View.VISIBLE)) {

                    final String height_ft_value = Objects.requireNonNull(profile_Height_ft_etxt.getText()).toString().trim();
                    final String height_in_value = Objects.requireNonNull(profile_Height_in_etxt.getText()).toString().trim();
                    final String weight_st_value = Objects.requireNonNull(profile_Weight_st_etxt.getText()).toString().trim();
                    final String weight_lb_value = Objects.requireNonNull(profile_Weight_lb_etxt.getText()).toString().trim();

                    if (!height_ft_value.isEmpty() && !height_in_value.isEmpty() && !weight_st_value.isEmpty() && !weight_lb_value.isEmpty()) {

                        try {
                            double feet = Double.parseDouble(height_ft_value);
                            double inch = Double.parseDouble(height_in_value);
                            double cm = ((feet * 12) + inch) / 0.39370;

                            double stone = Double.parseDouble(weight_st_value);
                            double pound = Double.parseDouble(weight_lb_value);
                            double kg = ((stone * 14) + pound) / 2.205;

                            BSA_Dialog_Box(cm, kg);

                        } catch (Exception ex) {
                            Log.i(Utils.TAG, "BSA Imperial Mode Exception: " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                    else {
                        new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both Height & Weight", CustomSnackbar.Duration.SHORT);
                    }
                }
                else if ((Height_Metric_Mode_Layout.getVisibility() == View.VISIBLE) && (Weight_Imperial_Mode_Layout.getVisibility() == View.VISIBLE)) {

                    final String height_cm_value = Objects.requireNonNull(profile_Height_cm_etxt.getText()).toString().trim();
                    final String weight_st_value = Objects.requireNonNull(profile_Weight_st_etxt.getText()).toString().trim();
                    final String weight_lb_value = Objects.requireNonNull(profile_Weight_lb_etxt.getText()).toString().trim();

                    if (!height_cm_value.isEmpty() && !weight_st_value.isEmpty() && !weight_lb_value.isEmpty()) {

                        try {
                            double cm = Double.parseDouble(height_cm_value);

                            double stone = Double.parseDouble(weight_st_value);
                            double pound = Double.parseDouble(weight_lb_value);
                            double kg = ((stone * 14) + pound) / 2.205;

                            BSA_Dialog_Box(cm, kg);

                        } catch (Exception ex) {
                            Log.i(Utils.TAG, "BSA Metric/Imperial Mode Exception: " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                    else {
                        new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both Height & Weight", CustomSnackbar.Duration.SHORT);
                    }
                }
                else if ((Height_Imperial_Mode_Layout.getVisibility() == View.VISIBLE) && (Weight_Metric_Mode_Layout.getVisibility() == View.VISIBLE)) {

                    final String weight_kg_value = Objects.requireNonNull(profile_Weight_kg_etxt.getText()).toString().trim();
                    final String height_ft_value = Objects.requireNonNull(profile_Height_ft_etxt.getText()).toString().trim();
                    final String height_in_value = Objects.requireNonNull(profile_Height_in_etxt.getText()).toString().trim();

                    if (!height_ft_value.isEmpty() && !height_in_value.isEmpty() && !weight_kg_value.isEmpty()) {

                        try {
                            double feet = Double.parseDouble(height_ft_value);
                            double inch = Double.parseDouble(height_in_value);
                            double cm = ((feet * 12) + inch) / 0.39370;

                            double kg = Double.parseDouble(weight_kg_value);

                            BSA_Dialog_Box(cm, kg);

                        } catch (Exception ex) {
                            Log.i(Utils.TAG, "BSA Imperial/Metric Mode Exception: " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                    else {
                        new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both Height & Weight", CustomSnackbar.Duration.SHORT);
                    }
                }
                else if ((Height_Metric_Mode_Layout.getVisibility() == View.VISIBLE) && (Weight_Metric_Mode_Layout.getVisibility() == View.VISIBLE)) {

                    final String height_cm_value = Objects.requireNonNull(profile_Height_cm_etxt.getText()).toString().trim();
                    final String weight_kg_value = Objects.requireNonNull(profile_Weight_kg_etxt.getText()).toString().trim();

                    if (!height_cm_value.isEmpty() && !weight_kg_value.isEmpty()) {

                        try {
                            double cm = Double.parseDouble(height_cm_value);
                            double kg = Double.parseDouble(weight_kg_value);

                            BSA_Dialog_Box(cm, kg);

                        } catch (Exception ex) {
                            Log.i(Utils.TAG, "BSA Metric Mode Exception: " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                    else {
                        new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both Height & Weight", CustomSnackbar.Duration.SHORT);
                    }
                }
                else {
                    new CustomSnackbar(context, Utils.rootLayout, "Please! make sure your Height & Weight both are on Metric/Imperial Mode", CustomSnackbar.Duration.LONG);
                }
                break;

            case 2:
                if ((Height_Imperial_Mode_Layout.getVisibility() == View.VISIBLE) && (Weight_Imperial_Mode_Layout.getVisibility() == View.VISIBLE)) {

                    final String height_ft_value = Objects.requireNonNull(profile_Height_ft_etxt.getText()).toString().trim();
                    final String height_in_value = Objects.requireNonNull(profile_Height_in_etxt.getText()).toString().trim();
                    final String weight_st_value = Objects.requireNonNull(profile_Weight_st_etxt.getText()).toString().trim();
                    final String weight_lb_value = Objects.requireNonNull(profile_Weight_lb_etxt.getText()).toString().trim();

                    if (!height_ft_value.isEmpty() && !height_in_value.isEmpty() && !weight_st_value.isEmpty() && !weight_lb_value.isEmpty()) {

                        if (!Objects.requireNonNull(profile_Sex_etxt.getText()).toString().isEmpty()) {

                            if (!Objects.requireNonNull(profile_Age_etxt.getText()).toString().isEmpty()) {

                                try {
                                    double feet = Double.parseDouble(height_ft_value);
                                    double inch = Double.parseDouble(height_in_value);
                                    double cm = ((feet * 12) + inch) / 0.39370;

                                    double stone = Double.parseDouble(weight_st_value);
                                    double pound = Double.parseDouble(weight_lb_value);
                                    double kg = ((stone * 14) + pound) / 2.205;

                                    double bmi = kg / Math.pow((cm / 100), 2);
                                    String sex = profile_Sex_etxt.getText().toString().trim();
                                    int age = Integer.parseInt(profile_Age_etxt.getText().toString().trim());

                                    BMI_Dialog_Box(bmi, sex, age, cm, kg);

                                } catch (Exception ex) {
                                    Log.i(Utils.TAG, "BMI Imperial Mode Exception: " + ex.getMessage());
                                    ex.printStackTrace();
                                }
                            }
                            else {
                                new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill your Age", CustomSnackbar.Duration.SHORT);
                            }
                        }
                        else {
                            new CustomSnackbar(context, Utils.rootLayout, "Please! you have to choose your Gender", CustomSnackbar.Duration.SHORT);
                        }
                    }
                    else {
                        new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both Height & Weight", CustomSnackbar.Duration.SHORT);
                    }
                }
                else if ((Height_Metric_Mode_Layout.getVisibility() == View.VISIBLE) && (Weight_Imperial_Mode_Layout.getVisibility() == View.VISIBLE)) {

                    final String height_cm_value = Objects.requireNonNull(profile_Height_cm_etxt.getText()).toString().trim();
                    final String weight_st_value = Objects.requireNonNull(profile_Weight_st_etxt.getText()).toString().trim();
                    final String weight_lb_value = Objects.requireNonNull(profile_Weight_lb_etxt.getText()).toString().trim();

                    if (!height_cm_value.isEmpty() && !weight_st_value.isEmpty() && !weight_lb_value.isEmpty()) {

                        if (!Objects.requireNonNull(profile_Sex_etxt.getText()).toString().isEmpty()) {

                            if (!Objects.requireNonNull(profile_Age_etxt.getText()).toString().isEmpty()) {

                                try {
                                    double cm = Double.parseDouble(height_cm_value);

                                    double stone = Double.parseDouble(weight_st_value);
                                    double pound = Double.parseDouble(weight_lb_value);
                                    double kg = ((stone * 14) + pound) / 2.205;

                                    double bmi = kg / Math.pow((cm / 100), 2);
                                    String sex = profile_Sex_etxt.getText().toString().trim();
                                    int age = Integer.parseInt(profile_Age_etxt.getText().toString().trim());

                                    BMI_Dialog_Box(bmi, sex, age, cm, kg);

                                }
                                catch (Exception ex) {
                                    Log.i(Utils.TAG, "BMI Metric/Imperial Mode Exception: " + ex.getMessage());
                                    ex.printStackTrace();
                                }
                            }
                            else {
                                new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill your Age", CustomSnackbar.Duration.SHORT);
                            }
                        }
                        else {
                            new CustomSnackbar(context, Utils.rootLayout, "Please! you have to choose your Gender", CustomSnackbar.Duration.SHORT);
                        }
                    }
                    else {
                        new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both Height & Weight", CustomSnackbar.Duration.SHORT);
                    }
                }
                else if ((Height_Imperial_Mode_Layout.getVisibility() == View.VISIBLE) && (Weight_Metric_Mode_Layout.getVisibility() == View.VISIBLE)) {

                    final String weight_kg_value = Objects.requireNonNull(profile_Weight_kg_etxt.getText()).toString().trim();
                    final String height_ft_value = Objects.requireNonNull(profile_Height_ft_etxt.getText()).toString().trim();
                    final String height_in_value = Objects.requireNonNull(profile_Height_in_etxt.getText()).toString().trim();

                    if (!height_ft_value.isEmpty() && !height_in_value.isEmpty() && !weight_kg_value.isEmpty()) {

                        if (!Objects.requireNonNull(profile_Sex_etxt.getText()).toString().isEmpty()) {

                            if (!Objects.requireNonNull(profile_Age_etxt.getText()).toString().isEmpty()) {

                                try {
                                    double feet = Double.parseDouble(height_ft_value);
                                    double inch = Double.parseDouble(height_in_value);
                                    double cm = ((feet * 12) + inch) / 0.39370;

                                    double kg = Double.parseDouble(weight_kg_value);

                                    double bmi = kg / Math.pow((cm / 100), 2);
                                    String sex = profile_Sex_etxt.getText().toString().trim();
                                    int age = Integer.parseInt(profile_Age_etxt.getText().toString().trim());

                                    BMI_Dialog_Box(bmi, sex, age, cm, kg);

                                }
                                catch (Exception ex) {
                                    Log.i(Utils.TAG, "BMI Imperial/Metric Mode Exception: " + ex.getMessage());
                                    ex.printStackTrace();
                                }
                            }
                            else {
                                new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill your Age", CustomSnackbar.Duration.SHORT);
                            }
                        }
                        else {
                            new CustomSnackbar(context, Utils.rootLayout, "Please! you have to choose your Gender", CustomSnackbar.Duration.SHORT);
                        }
                    }
                    else {
                        new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both Height & Weight", CustomSnackbar.Duration.SHORT);
                    }
                } else if ((Height_Metric_Mode_Layout.getVisibility() == View.VISIBLE) && (Weight_Metric_Mode_Layout.getVisibility() == View.VISIBLE)) {

                    final String height_cm_value = Objects.requireNonNull(profile_Height_cm_etxt.getText()).toString().trim();
                    final String weight_kg_value = Objects.requireNonNull(profile_Weight_kg_etxt.getText()).toString().trim();

                    if (!height_cm_value.isEmpty() && !weight_kg_value.isEmpty()) {

                        if (!Objects.requireNonNull(profile_Sex_etxt.getText()).toString().isEmpty()) {

                            if (!Objects.requireNonNull(profile_Age_etxt.getText()).toString().isEmpty()) {

                                try {
                                    double cm = Double.parseDouble(height_cm_value);
                                    double kg = Double.parseDouble(weight_kg_value);

                                    double bmi = kg / Math.pow((cm / 100), 2);
                                    String sex = profile_Sex_etxt.getText().toString().trim();
                                    int age = Integer.parseInt(profile_Age_etxt.getText().toString().trim());

                                    BMI_Dialog_Box(bmi, sex, age, cm, kg);

                                } catch (Exception ex) {
                                    Log.i(Utils.TAG, "BMI Metric Mode Exception: " + ex.getMessage());
                                    ex.printStackTrace();
                                }
                            }
                            else {
                                new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill your Age", CustomSnackbar.Duration.SHORT);
                            }
                        }
                        else {
                            new CustomSnackbar(context, Utils.rootLayout, "Please! you have to choose your Gender", CustomSnackbar.Duration.SHORT);
                        }
                    }
                    else {
                        new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both Height & Weight", CustomSnackbar.Duration.SHORT);
                    }
                }
                else {
                    new CustomSnackbar(context, Utils.rootLayout, "Please! make sure your Height & Weight both are on Metric/Imperial Mode", CustomSnackbar.Duration.LONG);
                }
                break;

            case 3:
                if ((Height_Imperial_Mode_Layout.getVisibility() == View.VISIBLE) && (Weight_Imperial_Mode_Layout.getVisibility() == View.VISIBLE)) {

                    final String height_ft_value = Objects.requireNonNull(profile_Height_ft_etxt.getText()).toString().trim();
                    final String height_in_value = Objects.requireNonNull(profile_Height_in_etxt.getText()).toString().trim();
                    final String weight_st_value = Objects.requireNonNull(profile_Weight_st_etxt.getText()).toString().trim();
                    final String weight_lb_value = Objects.requireNonNull(profile_Weight_lb_etxt.getText()).toString().trim();

                    if (!height_ft_value.isEmpty() && !height_in_value.isEmpty() && !weight_st_value.isEmpty() && !weight_lb_value.isEmpty()) {

                        if (!Objects.requireNonNull(profile_Sex_etxt.getText()).toString().isEmpty()) {

                            if (!Objects.requireNonNull(profile_Age_etxt.getText()).toString().isEmpty()) {

                                try {
                                    double feet = Double.parseDouble(height_ft_value);
                                    double inch = Double.parseDouble(height_in_value);
                                    double cm = ((feet * 12) + inch) / 0.39370;

                                    double stone = Double.parseDouble(weight_st_value);
                                    double pound = Double.parseDouble(weight_lb_value);
                                    double kg = ((stone * 14) + pound) / 2.205;

                                    String sex = profile_Sex_etxt.getText().toString().trim();
                                    int age = Integer.parseInt(profile_Age_etxt.getText().toString().trim());

                                    BMR_Dialog_Box(sex, age, cm, kg);

                                }
                                catch (Exception ex) {
                                    Log.i(Utils.TAG, "BMR Imperial Mode Exception: " + ex.getMessage());
                                    ex.printStackTrace();
                                }
                            }
                            else {
                                new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill your Age", CustomSnackbar.Duration.SHORT);
                            }
                        }
                        else {
                            new CustomSnackbar(context, Utils.rootLayout, "Please! you have to choose your Gender", CustomSnackbar.Duration.SHORT);
                        }
                    }
                    else {
                        new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both Height & Weight", CustomSnackbar.Duration.SHORT);
                    }
                }
                else if ((Height_Metric_Mode_Layout.getVisibility() == View.VISIBLE) && (Weight_Imperial_Mode_Layout.getVisibility() == View.VISIBLE)) {

                    final String height_cm_value = Objects.requireNonNull(profile_Height_cm_etxt.getText()).toString().trim();
                    final String weight_st_value = Objects.requireNonNull(profile_Weight_st_etxt.getText()).toString().trim();
                    final String weight_lb_value = Objects.requireNonNull(profile_Weight_lb_etxt.getText()).toString().trim();

                    if (!height_cm_value.isEmpty() && !weight_st_value.isEmpty() && !weight_lb_value.isEmpty()) {

                        if (!Objects.requireNonNull(profile_Sex_etxt.getText()).toString().isEmpty()) {

                            if (!Objects.requireNonNull(profile_Age_etxt.getText()).toString().isEmpty()) {

                                try {
                                    double cm = Double.parseDouble(height_cm_value);

                                    double stone = Double.parseDouble(weight_st_value);
                                    double pound = Double.parseDouble(weight_lb_value);
                                    double kg = ((stone * 14) + pound) / 2.205;

                                    String sex = profile_Sex_etxt.getText().toString().trim();
                                    int age = Integer.parseInt(profile_Age_etxt.getText().toString().trim());

                                    BMR_Dialog_Box(sex, age, cm, kg);

                                } catch (Exception ex) {
                                    Log.i(Utils.TAG, "BMR Metric/Imperial Mode Exception: " + ex.getMessage());
                                    ex.printStackTrace();
                                }
                            }
                            else {
                                new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill your Age", CustomSnackbar.Duration.SHORT);
                            }
                        }
                        else {
                            new CustomSnackbar(context, Utils.rootLayout, "Please! you have to choose your Gender", CustomSnackbar.Duration.SHORT);
                        }
                    }
                    else {
                        new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both Height & Weight", CustomSnackbar.Duration.SHORT);
                    }
                }
                else if ((Height_Imperial_Mode_Layout.getVisibility() == View.VISIBLE) && (Weight_Metric_Mode_Layout.getVisibility() == View.VISIBLE)) {

                    final String weight_kg_value = Objects.requireNonNull(profile_Weight_kg_etxt.getText()).toString().trim();
                    final String height_ft_value = Objects.requireNonNull(profile_Height_ft_etxt.getText()).toString().trim();
                    final String height_in_value = Objects.requireNonNull(profile_Height_in_etxt.getText()).toString().trim();

                    if (!height_ft_value.isEmpty() && !height_in_value.isEmpty() && !weight_kg_value.isEmpty()) {

                        if (!Objects.requireNonNull(profile_Sex_etxt.getText()).toString().isEmpty()) {

                            if (!Objects.requireNonNull(profile_Age_etxt.getText()).toString().isEmpty()) {

                                try {
                                    double feet = Double.parseDouble(height_ft_value);
                                    double inch = Double.parseDouble(height_in_value);
                                    double cm = ((feet * 12) + inch) / 0.39370;

                                    double kg = Double.parseDouble(weight_kg_value);

                                    String sex = profile_Sex_etxt.getText().toString().trim();
                                    int age = Integer.parseInt(profile_Age_etxt.getText().toString().trim());

                                    BMR_Dialog_Box(sex, age, cm, kg);

                                } catch (Exception ex) {
                                    Log.i(Utils.TAG, "BMR Imperial/Metric Mode Exception: " + ex.getMessage());
                                    ex.printStackTrace();
                                }
                            }
                            else {
                                new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill your Age", CustomSnackbar.Duration.SHORT);
                            }
                        }
                        else {
                            new CustomSnackbar(context, Utils.rootLayout, "Please! you have to choose your Gender", CustomSnackbar.Duration.SHORT);
                        }
                    }
                    else {
                        new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both Height & Weight", CustomSnackbar.Duration.SHORT);
                    }
                }
                else if ((Height_Metric_Mode_Layout.getVisibility() == View.VISIBLE) && (Weight_Metric_Mode_Layout.getVisibility() == View.VISIBLE)) {

                    final String height_cm_value = Objects.requireNonNull(profile_Height_cm_etxt.getText()).toString().trim();
                    final String weight_kg_value = Objects.requireNonNull(profile_Weight_kg_etxt.getText()).toString().trim();

                    if (!height_cm_value.isEmpty() && !weight_kg_value.isEmpty()) {

                        if (!Objects.requireNonNull(profile_Sex_etxt.getText()).toString().isEmpty()) {

                            if (!Objects.requireNonNull(profile_Age_etxt.getText()).toString().isEmpty()) {

                                try {
                                    double cm = Double.parseDouble(height_cm_value);
                                    double kg = Double.parseDouble(weight_kg_value);

                                    String sex = profile_Sex_etxt.getText().toString().trim();
                                    int age = Integer.parseInt(profile_Age_etxt.getText().toString().trim());

                                    BMR_Dialog_Box(sex, age, cm, kg);

                                } catch (Exception ex) {
                                    Log.i(Utils.TAG, "BMR Metric Mode Exception: " + ex.getMessage());
                                    ex.printStackTrace();
                                }
                            }
                            else {
                                new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill your Age", CustomSnackbar.Duration.SHORT);
                            }
                        }
                        else {
                            new CustomSnackbar(context, Utils.rootLayout, "Please! you have to choose your Gender", CustomSnackbar.Duration.SHORT);
                        }
                    }
                    else {
                        new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill both Height & Weight", CustomSnackbar.Duration.SHORT);
                    }
                }
                else {
                    new CustomSnackbar(context, Utils.rootLayout, "Please! make sure your Height & Weight both are on Metric/Imperial Mode", CustomSnackbar.Duration.LONG);
                }
                break;

            case 4:
                if (!Objects.requireNonNull(profile_Age_etxt.getText()).toString().isEmpty()) {

                    int Age = Integer.parseInt(profile_Age_etxt.getText().toString().trim());
                    HR_Dialog_Box(Age);

                }
                else {
                    new CustomSnackbar(context, Utils.rootLayout, "Please! you have to fill your Age", CustomSnackbar.Duration.SHORT);
                }
                break;

            case 5:
                BP_Dialog_Box();
                break;

            case 6:
                if(isPermissionGranted(111)) Share_Apk();
                break;
        }
    }


    /* Dialog Box */
    private void Custom_Dialog_Box(String dialogText, String confirmBtnText, String cancelBtnText, final View.OnClickListener listener) {

        View Custom_Dialog_View = LayoutInflater.from(getContext()).inflate(R.layout.dialog_profile, null);

        final AppCompatImageButton cancel_dialog = Custom_Dialog_View.findViewById(R.id.cancel_dialog_id);
        final AppCompatTextView DialogText = Custom_Dialog_View.findViewById(R.id.dialog_text_id);
        final AppCompatButton ConfirmButton = Custom_Dialog_View.findViewById(R.id.confirm_btn_id);
        final AppCompatButton CancelButton = Custom_Dialog_View.findViewById(R.id.cancel_btn_id);

        CancelButton.setVisibility(View.GONE);


        /* Font Set */
        if (getActivity() == null) return;
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "pf_regular.ttf");
        DialogText.setTypeface(tf);
        ConfirmButton.setTypeface(tf);
        CancelButton.setTypeface(tf);


        /* Set Custom Text */
        DialogText.setText(dialogText);
        ConfirmButton.setText(confirmBtnText);
        CancelButton.setText(cancelBtnText);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(Custom_Dialog_View);
        alertDialogBuilder.setCancelable(true);

        alertDialog = alertDialogBuilder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(alertDialog.getWindow()).getDecorView().setBackgroundColor(TRANSPARENT);
        alertDialog.show();


        /* Cancel Dialog Box */
        cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator();
                alertDialog.dismiss();
            }
        });


        /* Confirm */
        ConfirmButton.setOnClickListener(listener);
    }

    @SuppressLint("SetTextI18n") private void Blood_Dialog_Box(final EditText blood_etxt) {

        View Blood_Dialog_View = LayoutInflater.from(getContext()).inflate(R.layout.dialog_blood_details, null);

        final AppCompatImageButton cancel_dialog = Blood_Dialog_View.findViewById(R.id.cancel_dialog_id);
        final AppCompatTextView Blood_Title = Blood_Dialog_View.findViewById(R.id.blood_title_id);
        final AppCompatTextView Blood_Ratio_Tv = Blood_Dialog_View.findViewById(R.id.progress_ratio_tv);
        final ProgressBar Blood_Ratio_Progress = Blood_Dialog_View.findViewById(R.id.blood_ratio_progress);

        /* Font Set */
        if (getActivity() == null) return;
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "pf_regular.ttf");
        Blood_Title.setTypeface(tf);
        Blood_Ratio_Tv.setTypeface(tf);

        Blood_Title.setText("Blood Group\n(" + blood_etxt.getText().toString() + ")");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(Blood_Dialog_View);
        alertDialogBuilder.setCancelable(true);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(alertDialog.getWindow()).getDecorView().setBackgroundColor(TRANSPARENT);
        alertDialog.show();


        switch (blood_etxt.getText().toString()) {

            case "O+":
                progress_Ratio(Blood_Ratio_Tv, Blood_Ratio_Progress, 39);
                break;

            case "O-":
            case "B+":
                progress_Ratio(Blood_Ratio_Tv, Blood_Ratio_Progress, 9);
                break;

            case "A+":
                progress_Ratio(Blood_Ratio_Tv, Blood_Ratio_Progress, 30);
                break;

            case "A-":
                progress_Ratio(Blood_Ratio_Tv, Blood_Ratio_Progress, 6);
                break;

            case "B-":
                progress_Ratio(Blood_Ratio_Tv, Blood_Ratio_Progress, 2);
                break;

            case "AB+":
                progress_Ratio(Blood_Ratio_Tv, Blood_Ratio_Progress, 4);
                break;

            case "AB-":
                progress_Ratio(Blood_Ratio_Tv, Blood_Ratio_Progress, 1);
                break;
        }



        /* Cancel Dialog Box */
        cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator();
                alertDialog.dismiss();
            }
        });
    }

    private void progress_Ratio(final TextView Blood_Ratio, final ProgressBar progressBar, final int value) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                for (progress = 0; progress <= value; progress += 1) {

                    handlers.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            progressBar.setSecondaryProgress(progress);
                            Blood_Ratio.setText(progress + " %");

                        }
                    });

                    try {
                        Thread.sleep(50);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }

    @SuppressLint("SetTextI18n") private void BMI_Dialog_Box(final double BMI, final String Sex, final int Age, final double Height, final double Weight) {

        View BMI_Dialog_View = LayoutInflater.from(getContext()).inflate(R.layout.dialog_bmi_details, null);

        final AppCompatImageButton cancel_dialog = BMI_Dialog_View.findViewById(R.id.cancel_dialog_id);
        final AppCompatTextView BMI_Main_Title = BMI_Dialog_View.findViewById(R.id.bmi_main_title_id);
        final AppCompatTextView BMI_Description = BMI_Dialog_View.findViewById(R.id.bmi_desc_id);
        final AppCompatTextView BMI_Title = BMI_Dialog_View.findViewById(R.id.bmi_title);
        final AppCompatTextView BMI_Output = BMI_Dialog_View.findViewById(R.id.bmi_output);
        final AppCompatTextView BMI_Result_Title = BMI_Dialog_View.findViewById(R.id.bmi_result_title_id);
        final AppCompatTextView BMI_Result = BMI_Dialog_View.findViewById(R.id.bmi_result_id);
        final AppCompatTextView LBM_Title = BMI_Dialog_View.findViewById(R.id.bmi_LBM_title);
        final AppCompatTextView LBM_Result = BMI_Dialog_View.findViewById(R.id.bmi_LBM_result);
        final AppCompatTextView LFM_Title = BMI_Dialog_View.findViewById(R.id.bmi_LFM_title);
        final AppCompatTextView LFM_Result = BMI_Dialog_View.findViewById(R.id.bmi_LFM_result);
        final AppCompatSeekBar progressBar = BMI_Dialog_View.findViewById(R.id.progressBar);

        /* Font Set */
        if (getActivity() == null) return;
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "pf_regular.ttf");
        BMI_Main_Title.setTypeface(tf);
        BMI_Description.setTypeface(tf);
        BMI_Title.setTypeface(tf);
        BMI_Output.setTypeface(tf);
        BMI_Result_Title.setTypeface(tf);
        BMI_Result.setTypeface(tf);
        LBM_Title.setTypeface(tf);
        LBM_Result.setTypeface(tf);
        LFM_Title.setTypeface(tf);
        LFM_Result.setTypeface(tf);

        if (android.os.Build.VERSION.SDK_INT > 21) {
            GradientDrawable rainbow = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[]{getResources().getColor(R.color.SeekBar_Color_One), getResources().getColor(R.color.SeekBar_Color_Two), getResources().getColor(R.color.SeekBar_Color_Three),
                            getResources().getColor(R.color.SeekBar_Color_Four), getResources().getColor(R.color.SeekBar_Color_Five), getResources().getColor(R.color.SeekBar_Color_Six),
                            getResources().getColor(R.color.SeekBar_Color_Seven), getResources().getColor(R.color.SeekBar_Color_Eight), getResources().getColor(R.color.SeekBar_Color_Nine),
                            getResources().getColor(R.color.SeekBar_Color_Ten), getResources().getColor(R.color.SeekBar_Color_Eleven), getResources().getColor(R.color.SeekBar_Color_Twelve),
                            getResources().getColor(R.color.SeekBar_Color_Thirteen), getResources().getColor(R.color.SeekBar_Color_Fourteen), getResources().getColor(R.color.SeekBar_Color_Fifteen),
                            getResources().getColor(R.color.SeekBar_Color_Sixteen), getResources().getColor(R.color.SeekBar_Color_Seventeen), getResources().getColor(R.color.SeekBar_Color_Eighteen),
                            getResources().getColor(R.color.SeekBar_Color_Nineteen), getResources().getColor(R.color.SeekBar_Color_Twenty)});

            progressBar.setProgressDrawable(rainbow);
            progressBar.setPadding(2, 2, 2, 2);
            progressBar.setEnabled(false);

        } else {
            progressBar.setVisibility(View.GONE);
        }


        /* Bmi Result */
        BMI_Output.setText(new DecimalFormat("###.##").format(BMI) + "  kg/m²");

        if (BMI >= 0 && BMI < 15) {
            BMI_Result.setText("Very Severely Underweight");
            Progress_Work(progressBar, 210);

        } else if (BMI >= 15 && BMI < 16) {
            BMI_Result.setText("Severely Underweight");
            Progress_Work(progressBar, 175);

        } else if (BMI >= 16 && BMI < 18.5) {
            BMI_Result.setText("Underweight");
            Progress_Work(progressBar, 140);

        } else if (BMI >= 18.5 && BMI < 25) {
            BMI_Result.setText("Healthy Weight\n(Normal)");
            Progress_Work(progressBar, 105);

        } else if (BMI >= 25 && BMI < 30) {
            BMI_Result.setText("Overweight");
            Progress_Work(progressBar, 90);

        } else if (BMI >= 30 && BMI < 35) {
            BMI_Result.setText("Obese Class III\n(Moderately Obese)");
            Progress_Work(progressBar, 75);

        } else if (BMI >= 35 && BMI < 40) {
            BMI_Result.setText("Obese Class II\n(Severely Obese)");
            Progress_Work(progressBar, 60);

        } else if (BMI >= 40 && BMI < 45) {
            BMI_Result.setText("Obese Class III\n(Very Severely Obese)");
            Progress_Work(progressBar, 45);

        } else if (BMI >= 45 && BMI < 50) {
            BMI_Result.setText("Obese Class IV\n(Morbidly Obese)");
            Progress_Work(progressBar, 30);

        } else if (BMI >= 50 && BMI < 60) {
            BMI_Result.setText("Obese Class V\n(Super Obese)");
            Progress_Work(progressBar, 15);

        } else {
            BMI_Result.setText("Obese Class VI\n(Hyper Obese)");
            Progress_Work(progressBar, 0);
        }


        /* LBM Result */
        DecimalFormat df = new DecimalFormat("###.#");
        if (Age <= 14) {
            double ECV = 0.0215 * (Math.pow(Weight, 0.6469)) * (Math.pow(Height, 0.7236));
            double Calculation = (3.8 * ECV);
            String LBM = df.format(Calculation);
            String LFM = df.format(Weight - Calculation);
            String LBM_Per = df.format((Calculation * 100) / Weight);
            String LFM_Per = df.format(((Weight - Calculation) * 100) / Weight);
            LBM_Result.setText(LBM + " kg" + " (" + LBM_Per + "%)");
            LFM_Result.setText(LFM + " kg" + " (" + LFM_Per + "%)");

        } else {
            if (Sex.equals("Male")) {
                double Calculation = (0.407 * Weight) + (0.267 * Height) - 19.2;
                String LBM = df.format(Calculation);
                String LFM = df.format(Weight - Calculation);
                String LBM_Per = df.format((Calculation * 100) / Weight);
                String LFM_Per = df.format(((Weight - Calculation) * 100) / Weight);
                LBM_Result.setText(LBM + " kg" + "(" + LBM_Per + "%)");
                LFM_Result.setText(LFM + " kg" + "(" + LFM_Per + "%)");

            } else {
                double Calculation = (0.252 * Weight) + (0.473 * Height) - 48.3;
                String LBM = df.format(Calculation);
                String LFM = df.format(Weight - Calculation);
                String LBM_Per = df.format((Calculation * 100) / Weight);
                String LFM_Per = df.format(((Weight - Calculation) * 100) / Weight);
                LBM_Result.setText(LBM + " kg" + "(" + LBM_Per + "%)");
                LFM_Result.setText(LFM + " kg" + "(" + LFM_Per + "%)");
            }
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(BMI_Dialog_View);
        alertDialogBuilder.setCancelable(true);


        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(alertDialog.getWindow()).getDecorView().setBackgroundColor(TRANSPARENT);
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

    @SuppressLint("SetTextI18n") private void BSA_Dialog_Box(final double Height, final double Weight) {

        View BSA_Dialog_View = LayoutInflater.from(getContext()).inflate(R.layout.dialog_bsa_details, null);

        final AppCompatImageButton cancel_dialog = BSA_Dialog_View.findViewById(R.id.cancel_dialog_id);
        final AppCompatTextView BSA_Main_Title = BSA_Dialog_View.findViewById(R.id.bsa_main_title_id);
        final AppCompatTextView BSA_Description = BSA_Dialog_View.findViewById(R.id.bsa_desc_id);
        final AppCompatTextView BSA_Title = BSA_Dialog_View.findViewById(R.id.bsa_title);
        final AppCompatTextView BSA_Output = BSA_Dialog_View.findViewById(R.id.bsa_output);

        /* Font Set */
        if (getActivity() == null) return;
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "pf_regular.ttf");
        BSA_Main_Title.setTypeface(tf);
        BSA_Description.setTypeface(tf);
        BSA_Title.setTypeface(tf);
        BSA_Output.setTypeface(tf);

        double BSA = Math.sqrt((Height * Weight) / 3600);
        String metre_Result = new DecimalFormat("#.##").format(BSA) + " m²";
        String feet_Result = new DecimalFormat("##.##").format(BSA * Math.pow(3.2808399, 2)) + " ft²";
        String inch_Result = new DecimalFormat().format(Math.round(BSA * Math.pow(39.3700787, 2))) + " in²";

        BSA_Output.setText(metre_Result + " | " + feet_Result + " | " + inch_Result);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(BSA_Dialog_View);
        alertDialogBuilder.setCancelable(true);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(alertDialog.getWindow()).getDecorView().setBackgroundColor(TRANSPARENT);
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

    @SuppressLint("SetTextI18n") private void BMR_Dialog_Box(final String Sex, final int Age, final double Height, final double Weight) {

        View BMR_Dialog_View = LayoutInflater.from(getContext()).inflate(R.layout.dialog_bmr_details, null);

        final AppCompatImageButton cancel_dialog = BMR_Dialog_View.findViewById(R.id.cancel_dialog_id);
        final LinearLayoutCompat BMR_Activities_Layout = BMR_Dialog_View.findViewById(R.id.bmr_activity_layout);
        final LinearLayoutCompat BMR_Result_Layout = BMR_Dialog_View.findViewById(R.id.bmr_result_layout);
        final AppCompatTextView BMR_Result_Title = BMR_Dialog_View.findViewById(R.id.bmr_title_id);
        final AppCompatTextView BMR_Description = BMR_Dialog_View.findViewById(R.id.bmr_desc_id);
        final AppCompatTextView BMR_Title = BMR_Dialog_View.findViewById(R.id.bmr_title);
        final AppCompatTextView BMR_Output = BMR_Dialog_View.findViewById(R.id.bmr_output);
        final AppCompatTextView BMR_Nutritional_Title = BMR_Dialog_View.findViewById(R.id.bmr_Nutritional_title_id);
        final AppCompatTextView BMR_Carbohydrates_Title = BMR_Dialog_View.findViewById(R.id.bmr_Carbohydrates_title_id);
        final AppCompatTextView BMR_Carbohydrates_Content = BMR_Dialog_View.findViewById(R.id.bmr_Carbohydrates_content_id);
        final AppCompatTextView BMR_Proteins_Title = BMR_Dialog_View.findViewById(R.id.bmr_Proteins_title_id);
        final AppCompatTextView BMR_Proteins_Content = BMR_Dialog_View.findViewById(R.id.bmr_Proteins_content_id);
        final AppCompatTextView BMR_Fats_Title = BMR_Dialog_View.findViewById(R.id.bmr_Fats_title_id);
        final AppCompatTextView BMR_Fats_Content = BMR_Dialog_View.findViewById(R.id.bmr_Fats_content_id);

        final AppCompatTextView BMR_Activities_Title = BMR_Dialog_View.findViewById(R.id.bmr_activities_title_id);
        final RadioGroup radioGroup = BMR_Dialog_View.findViewById(R.id.radioGroup_activity);
        final RadioButton activity_One = BMR_Dialog_View.findViewById(R.id.activity_one_id);
        final RadioButton activity_Two = BMR_Dialog_View.findViewById(R.id.activity_two_id);
        final RadioButton activity_Three = BMR_Dialog_View.findViewById(R.id.activity_three_id);
        final RadioButton activity_Four = BMR_Dialog_View.findViewById(R.id.activity_four_id);
        final RadioButton activity_Five = BMR_Dialog_View.findViewById(R.id.activity_five_id);

        /* Font Set */
        if (getActivity() == null) return;
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "pf_regular.ttf");
        BMR_Activities_Title.setTypeface(tf);
        activity_One.setTypeface(tf);
        activity_Two.setTypeface(tf);
        activity_Three.setTypeface(tf);
        activity_Four.setTypeface(tf);
        activity_Five.setTypeface(tf);

        BMR_Result_Title.setTypeface(tf);
        BMR_Description.setTypeface(tf);
        BMR_Title.setTypeface(tf);
        BMR_Output.setTypeface(tf);
        BMR_Nutritional_Title.setTypeface(tf);
        BMR_Carbohydrates_Title.setTypeface(tf);
        BMR_Carbohydrates_Content.setTypeface(tf);
        BMR_Proteins_Title.setTypeface(tf);
        BMR_Proteins_Content.setTypeface(tf);
        BMR_Fats_Title.setTypeface(tf);
        BMR_Fats_Content.setTypeface(tf);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.activity_one_id) {

                    if (Sex.equals("Male")) {
                        double v = (10 * Weight) + (6.25 * Height) - (5 * Age);
                        double BMR = (v + 5) * 1.2;
                        BMR_Output.setText(Math.round(Math.ceil(BMR)) + "  cal/day");
                        BMR_Result(BMR, Age, BMR_Carbohydrates_Content, BMR_Proteins_Content, BMR_Fats_Content);

                    } else {
                        double BMR = ((10 * Weight) + (6.25 * Height) - (5 * Age) - 161) * 1.2;
                        BMR_Output.setText(Math.round(Math.ceil(BMR)) + "  cal/day");
                        BMR_Result(BMR, Age, BMR_Carbohydrates_Content, BMR_Proteins_Content, BMR_Fats_Content);
                    }

                    BMR_Activities_Layout.setVisibility(View.GONE);
                    BMR_Result_Layout.setVisibility(View.VISIBLE);
                }

                if (checkedId == R.id.activity_two_id) {

                    if (Sex.equals("Male")) {
                        double BMR = ((10 * Weight) + (6.25 * Height) - (5 * Age) + 5) * 1.375;
                        BMR_Output.setText(Math.round(Math.ceil(BMR)) + "  cal/day");
                        BMR_Result(BMR, Age, BMR_Carbohydrates_Content, BMR_Proteins_Content, BMR_Fats_Content);

                    } else {
                        double BMR = ((10 * Weight) + (6.25 * Height) - (5 * Age) - 161) * 1.375;
                        BMR_Output.setText(Math.round(Math.ceil(BMR)) + "  cal/day");
                        BMR_Result(BMR, Age, BMR_Carbohydrates_Content, BMR_Proteins_Content, BMR_Fats_Content);
                    }

                    BMR_Activities_Layout.setVisibility(View.GONE);
                    BMR_Result_Layout.setVisibility(View.VISIBLE);
                }

                if (checkedId == R.id.activity_three_id) {

                    if (Sex.equals("Male")) {
                        double BMR = ((10 * Weight) + (6.25 * Height) - (5 * Age) + 5) * 1.55;
                        BMR_Output.setText(Math.round(Math.ceil(BMR)) + "  cal/day");
                        BMR_Result(BMR, Age, BMR_Carbohydrates_Content, BMR_Proteins_Content, BMR_Fats_Content);

                    } else {
                        double BMR = ((10 * Weight) + (6.25 * Height) - (5 * Age) - 161) * 1.55;
                        BMR_Output.setText(Math.round(Math.ceil(BMR)) + "  cal/day");
                        BMR_Result(BMR, Age, BMR_Carbohydrates_Content, BMR_Proteins_Content, BMR_Fats_Content);
                    }

                    BMR_Activities_Layout.setVisibility(View.GONE);
                    BMR_Result_Layout.setVisibility(View.VISIBLE);
                }

                if (checkedId == R.id.activity_four_id) {

                    if (Sex.equals("Male")) {
                        double BMR = ((10 * Weight) + (6.25 * Height) - (5 * Age) + 5) * 1.725;
                        BMR_Output.setText(Math.round(Math.ceil(BMR)) + "  cal/day");
                        BMR_Result(BMR, Age, BMR_Carbohydrates_Content, BMR_Proteins_Content, BMR_Fats_Content);

                    } else {
                        double BMR = ((10 * Weight) + (6.25 * Height) - (5 * Age) - 161) * 1.725;
                        BMR_Output.setText(Math.round(Math.ceil(BMR)) + "  cal/day");
                        BMR_Result(BMR, Age, BMR_Carbohydrates_Content, BMR_Proteins_Content, BMR_Fats_Content);
                    }

                    BMR_Activities_Layout.setVisibility(View.GONE);
                    BMR_Result_Layout.setVisibility(View.VISIBLE);
                }

                if (checkedId == R.id.activity_five_id) {

                    if (Sex.equals("Male")) {
                        double BMR = ((10 * Weight) + (6.25 * Height) - (5 * Age) + 5) * 1.9;
                        BMR_Output.setText(Math.round(Math.ceil(BMR)) + "  cal/day");
                        BMR_Result(BMR, Age, BMR_Carbohydrates_Content, BMR_Proteins_Content, BMR_Fats_Content);

                    } else {
                        double BMR = ((10 * Weight) + (6.25 * Height) - (5 * Age) - 161) * 1.9;
                        BMR_Output.setText(Math.round(Math.ceil(BMR)) + "  cal/day");
                        BMR_Result(BMR, Age, BMR_Carbohydrates_Content, BMR_Proteins_Content, BMR_Fats_Content);
                    }

                    BMR_Activities_Layout.setVisibility(View.GONE);
                    BMR_Result_Layout.setVisibility(View.VISIBLE);
                }
            }
        });


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(BMR_Dialog_View);
        alertDialogBuilder.setCancelable(true);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(alertDialog.getWindow()).getDecorView().setBackgroundColor(TRANSPARENT);
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

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"}) private void HR_Dialog_Box(final int Age) {

        View HR_Dialog_View = LayoutInflater.from(getContext()).inflate(R.layout.dialog_hr_details, null);

        final AppCompatImageButton cancel_dialog = HR_Dialog_View.findViewById(R.id.cancel_dialog_id);
        final RelativeLayout Dialog_Root_Layout = HR_Dialog_View.findViewById(R.id.dialog_root_layout);
        final NestedScrollView HR_scrollView = HR_Dialog_View.findViewById(R.id.scrollView_hr_id);
        final AppCompatTextView HR_main_title = HR_Dialog_View.findViewById(R.id.hr_main_title_id);
        final AppCompatTextView HR_Description = HR_Dialog_View.findViewById(R.id.hr_desc_id);
        final AppCompatTextView ARHR_Title = HR_Dialog_View.findViewById(R.id.arhr_title_id);
        final AppCompatTextView ARHR_Description = HR_Dialog_View.findViewById(R.id.arhr_desc_id);
        final AppCompatEditText RHR_etxt = HR_Dialog_View.findViewById(R.id.RHR_etxt);
        final AppCompatEditText DPE_etxt = HR_Dialog_View.findViewById(R.id.DPE_etxt);
        final CardView MHR_Layout = HR_Dialog_View.findViewById(R.id.mhr_layout);
        final AppCompatTextView MHR_title = HR_Dialog_View.findViewById(R.id.mhr_title_id);
        final AppCompatTextView MHR_Result = HR_Dialog_View.findViewById(R.id.mhr_result_id);
        final CardView THR_Layout = HR_Dialog_View.findViewById(R.id.thr_layout);
        final AppCompatTextView THR_title = HR_Dialog_View.findViewById(R.id.thr_title_id);
        final AppCompatTextView THR_Result = HR_Dialog_View.findViewById(R.id.thr_result_id);
        final CardView RTR_Layout = HR_Dialog_View.findViewById(R.id.rtr_layout);
        final AppCompatTextView RTR_title = HR_Dialog_View.findViewById(R.id.rtr_title_id);
        final AppCompatTextView RTR_Result = HR_Dialog_View.findViewById(R.id.rtr_result_id);

        /* Font Set */
        if (getActivity() == null) return;
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "pf_regular.ttf");
        HR_main_title.setTypeface(tf);
        HR_Description.setTypeface(tf);
        ARHR_Title.setTypeface(tf);
        ARHR_Description.setTypeface(tf);
        RHR_etxt.setTypeface(tf);
        DPE_etxt.setTypeface(tf);
        MHR_title.setTypeface(tf);
        MHR_Result.setTypeface(tf);
        THR_title.setTypeface(tf);
        THR_Result.setTypeface(tf);
        RTR_title.setTypeface(tf);
        RTR_Result.setTypeface(tf);

        RHR_etxt.setFilters(new InputFilter[]{new InputFilter_Min_Max_Integer("1", "100")});

        RHR_etxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RHR_etxt.setFocusableInTouchMode(true);
                return false;
            }
        });

        RHR_etxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    if (s.toString().length() == 2) {

                        hideKeyboardFrom(context, RHR_etxt);
                        RHR_etxt.setFocusable(false);

                        HR_scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                HR_scrollView.scrollTo(HR_scrollView.getBottom(), DPE_etxt.getTop());
                            }
                        });
                    }
                } catch (Exception ex) {
                    Log.i(Utils.TAG, "HR RHR Exception: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        DPE_etxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Objects.requireNonNull(RHR_etxt.getText()).toString().isEmpty()) {

                    PopupMenu popup = new PopupMenu(getContext(), DPE_etxt);

                    popup.getMenu().add("35% (Beginner Exerciser)");
                    popup.getMenu().add("40% (Beginner Exerciser)");
                    popup.getMenu().add("45% (Beginner Exerciser)");
                    popup.getMenu().add("50% (Intermediate Exerciser)");
                    popup.getMenu().add("55% (Intermediate Exerciser)");
                    popup.getMenu().add("60% (Intermediate Exerciser)");
                    popup.getMenu().add("65% (Advanced Int. Exerciser)");
                    popup.getMenu().add("70% (Advanced Int. Exerciser)");
                    popup.getMenu().add("75% (Advanced Exerciser)");
                    popup.getMenu().add("80% (Advanced Exerciser)");
                    popup.getMenu().add("85% (Advanced Exerciser)");

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {

                            DPE_etxt.setText(item.getTitle());

                            HR_scrollView.post(new Runnable() {
                                @Override
                                public void run() {
                                    HR_scrollView.scrollTo(HR_scrollView.getBottom(), RTR_Layout.getTop());
                                }
                            });

                            int RHR = Integer.parseInt(RHR_etxt.getText().toString().trim());

                            MHR_Result.setText((220 - Age) + "  beats per minute");

                            if (item.getTitle().equals("35% (Beginner Exerciser)")) {
                                THR_Result.setText(Math.round(Math.floor((((220 - Age) - RHR) * .35) + RHR)) + "  beats per minute");

                            } else if (item.getTitle().equals("40% (Beginner Exerciser)")) {
                                THR_Result.setText(Math.round(Math.floor((((220 - Age) - RHR) * .40) + RHR)) + "  beats per minute");

                            } else if (item.getTitle().equals("45% (Beginner Exerciser)")) {
                                THR_Result.setText(Math.round(Math.floor((((220 - Age) - RHR) * .45) + RHR)) + "  beats per minute");

                            } else if (item.getTitle().equals("50% (Intermediate Exerciser)")) {
                                THR_Result.setText(Math.round(Math.floor((((220 - Age) - RHR) * .50) + RHR)) + "  beats per minute");

                            } else if (item.getTitle().equals("55% (Intermediate Exerciser)")) {
                                THR_Result.setText(Math.round(Math.floor((((220 - Age) - RHR) * .55) + RHR)) + "  beats per minute");

                            } else if (item.getTitle().equals("60% (Intermediate Exerciser)")) {
                                THR_Result.setText(Math.round(Math.floor((((220 - Age) - RHR) * .60) + RHR)) + "  beats per minute");

                            } else if (item.getTitle().equals("65% (Advanced Int. Exerciser)")) {
                                THR_Result.setText(Math.round(Math.floor((((220 - Age) - RHR) * .65) + RHR)) + "  beats per minute");

                            } else if (item.getTitle().equals("70% (Advanced Int. Exerciser)")) {
                                THR_Result.setText(Math.round(Math.floor((((220 - Age) - RHR) * .70) + RHR)) + "  beats per minute");

                            } else if (item.getTitle().equals("75% (Advanced Exerciser)")) {
                                THR_Result.setText(Math.round(Math.floor((((220 - Age) - RHR) * .75) + RHR)) + "  beats per minute");

                            } else if (item.getTitle().equals("80% (Advanced Exerciser)")) {
                                THR_Result.setText(Math.round(Math.floor((((220 - Age) - RHR) * .80) + RHR)) + "  beats per minute");

                            } else if (item.getTitle().equals("85% (Advanced Exerciser)")) {
                                THR_Result.setText(Math.round(Math.floor((((220 - Age) - RHR) * .85) + RHR)) + "  beats per minute");
                            }

                            RTR_Result.setText(Math.round(Math.floor((220 - Age) * .55)) + " - " + Math.round(Math.floor((220 - Age) * .85)) + "  beats per minute");

                            MHR_Layout.setVisibility(View.VISIBLE);
                            THR_Layout.setVisibility(View.VISIBLE);
                            RTR_Layout.setVisibility(View.VISIBLE);

                            return true;
                        }
                    });

                    popup.show();

                }
                else {
                    vibrator();
                    new CustomSnackbar(context, Utils.rootLayout, "Please! fill your Average Resting Heart Rate", CustomSnackbar.Duration.SHORT);
                }
            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(HR_Dialog_View);
        alertDialogBuilder.setCancelable(false);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(alertDialog.getWindow()).getDecorView().setBackgroundColor(TRANSPARENT);
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

    @SuppressLint("SetTextI18n")
    private void BMR_Result(final double BMR, final double Age, final TextView Carbohydrates, final TextView Proteins, final TextView Fats) {

        /* BMR Result */
        double carbo_cal_lower = (BMR * 45) / 100;
        double carbo_cal_upper = (BMR * 65) / 100;
        double carbo_gram_lower = ((BMR * 45) / 100) * 0.12959782;
        double carbo_gram_upper = ((BMR * 65) / 100) * 0.12959782;

        Carbohydrates.setText(Math.round(carbo_cal_lower) + "-" + Math.round(carbo_cal_upper) + "  Calories/Day\n" + Math.round(carbo_gram_lower) + "-" + Math.round(carbo_gram_upper) + "  Grams/Day");

        if (Age <= 3) {
            double protein_cal_lower = (BMR * 5) / 100;
            double protein_cal_upper = (BMR * 20) / 100;
            double protein_gram_lower = ((BMR * 5) / 100) * 0.12959782;
            double protein_gram_upper = ((BMR * 20) / 100) * 0.12959782;

            double fat_cal_lower = (BMR * 30) / 100;
            double fat_cal_upper = (BMR * 40) / 100;
            double fat_gram_lower = ((BMR * 30) / 100) * 0.12959782;
            double fat_gram_upper = ((BMR * 40) / 100) * 0.12959782;

            Proteins.setText(Math.round(protein_cal_lower) + "-" + Math.round(protein_cal_upper) + "  Calories/Day\n" + Math.round(protein_gram_lower) + "-" + Math.round(protein_gram_upper) + "  Grams/Day");
            Fats.setText(Math.round(fat_cal_lower) + "-" + Math.round(fat_cal_upper) + "  Calories/Day\n" + Math.round(fat_gram_lower) + "-" + Math.round(fat_gram_upper) + "  Grams/Day");

        } else if (Age <= 18) {
            double protein_cal_lower = (BMR * 10) / 100;
            double protein_cal_upper = (BMR * 30) / 100;
            double protein_gram_lower = ((BMR * 10) / 100) * 0.12959782;
            double protein_gram_upper = ((BMR * 30) / 100) * 0.12959782;

            double fat_cal_lower = (BMR * 25) / 100;
            double fat_cal_upper = (BMR * 35) / 100;
            double fat_gram_lower = ((BMR * 25) / 100) * 0.12959782;
            double fat_gram_upper = ((BMR * 35) / 100) * 0.12959782;

            Proteins.setText(Math.round(protein_cal_lower) + "-" + Math.round(protein_cal_upper) + "  Calories/Day\n" + Math.round(protein_gram_lower) + "-" + Math.round(protein_gram_upper) + "  Grams/Day");
            Fats.setText(Math.round(fat_cal_lower) + "-" + Math.round(fat_cal_upper) + "  Calories/Day\n" + Math.round(fat_gram_lower) + "-" + Math.round(fat_gram_upper) + "  Grams/Day");

        } else {
            double protein_cal_lower = (BMR * 10) / 100;
            double protein_cal_upper = (BMR * 35) / 100;
            double protein_gram_lower = ((BMR * 10) / 100) * 0.12959782;
            double protein_gram_upper = ((BMR * 35) / 100) * 0.12959782;

            double fat_cal_lower = (BMR * 20) / 100;
            double fat_cal_upper = (BMR * 35) / 100;
            double fat_gram_lower = ((BMR * 20) / 100) * 0.12959782;
            double fat_gram_upper = ((BMR * 35) / 100) * 0.12959782;

            Proteins.setText(Math.round(protein_cal_lower) + "-" + Math.round(protein_cal_upper) + "  Calories/Day\n" + Math.round(protein_gram_lower) + "-" + Math.round(protein_gram_upper) + "  Grams/Day");
            Fats.setText(Math.round(fat_cal_lower) + "-" + Math.round(fat_cal_upper) + "  Calories/Day\n" + Math.round(fat_gram_lower) + "-" + Math.round(fat_gram_upper) + "  Grams/Day");
        }
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"}) private void BP_Dialog_Box() {

        View BP_Dialog_View = LayoutInflater.from(getContext()).inflate(R.layout.dialog_bp_details, null);

        final AppCompatImageButton cancel_dialog = BP_Dialog_View.findViewById(R.id.cancel_dialog_id);
        final AppCompatTextView BP_Title = BP_Dialog_View.findViewById(R.id.bp_title_id);
        final AppCompatTextView BP_Description = BP_Dialog_View.findViewById(R.id.bp_desc_id);
        final NestedScrollView BP_Scroll = BP_Dialog_View.findViewById(R.id.bp_scroll);
        final AppCompatEditText BP_Syst_Etxt = BP_Dialog_View.findViewById(R.id.bp_Systolic);
        final AppCompatEditText BP_Diast_Etxt = BP_Dialog_View.findViewById(R.id.bp_Diastolic);
        final CardView BP_Result_Layout = BP_Dialog_View.findViewById(R.id.bp_result_layout);
        final AppCompatTextView BP_Result_Title = BP_Dialog_View.findViewById(R.id.bp_result_title_id);
        final AppCompatTextView BP_Result = BP_Dialog_View.findViewById(R.id.bp_result_id);
        final AppCompatSeekBar progressBar = BP_Dialog_View.findViewById(R.id.progressBar);


        /* Font Set */
        if (getActivity() == null) return;
        final Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "pf_regular.ttf");
        BP_Title.setTypeface(tf);
        BP_Description.setTypeface(tf);
        BP_Syst_Etxt.setTypeface(tf);
        BP_Diast_Etxt.setTypeface(tf);
        BP_Result_Title.setTypeface(tf);
        BP_Result.setTypeface(tf);


        if (android.os.Build.VERSION.SDK_INT > 21) {
            GradientDrawable rainbow = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[]{getResources().getColor(R.color.SeekBar_Color_One), getResources().getColor(R.color.SeekBar_Color_Two), getResources().getColor(R.color.SeekBar_Color_Three),
                            getResources().getColor(R.color.SeekBar_Color_Four), getResources().getColor(R.color.SeekBar_Color_Five), getResources().getColor(R.color.SeekBar_Color_Six),
                            getResources().getColor(R.color.SeekBar_Color_Seven), getResources().getColor(R.color.SeekBar_Color_Eight), getResources().getColor(R.color.SeekBar_Color_Nine),
                            getResources().getColor(R.color.SeekBar_Color_Ten), getResources().getColor(R.color.SeekBar_Color_Eleven), getResources().getColor(R.color.SeekBar_Color_Twelve),
                            getResources().getColor(R.color.SeekBar_Color_Thirteen), getResources().getColor(R.color.SeekBar_Color_Fourteen), getResources().getColor(R.color.SeekBar_Color_Fifteen),
                            getResources().getColor(R.color.SeekBar_Color_Sixteen), getResources().getColor(R.color.SeekBar_Color_Seventeen), getResources().getColor(R.color.SeekBar_Color_Eighteen),
                            getResources().getColor(R.color.SeekBar_Color_Nineteen), getResources().getColor(R.color.SeekBar_Color_Twenty)});

            progressBar.setProgressDrawable(rainbow);
            progressBar.setPadding(2, 2, 2, 2);
            progressBar.setEnabled(false);

        } else {
            progressBar.setVisibility(View.GONE);
        }


        /* Systolic Pressure */
        BP_Syst_Etxt.requestFocus();

        BP_Syst_Etxt.setFilters(new InputFilter[]{new InputFilter_Min_Max_Integer("0", "300")});

        BP_Syst_Etxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BP_Syst_Etxt.setFocusableInTouchMode(true);
                return false;
            }
        });

        BP_Syst_Etxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                try {

                    if (s.toString().length() == 3) {

                        if (BP_Diast_Etxt.getText().toString().isEmpty()) {
                            Selection.setSelection(BP_Syst_Etxt.getText(), BP_Diast_Etxt.getSelectionStart());
                            BP_Diast_Etxt.requestFocus();

                        } else {
                            BP_Syst_Etxt.setFocusable(false);
                            BP_Diast_Etxt.setFocusable(false);
                            hideKeyboardFrom(context, BP_Diast_Etxt);
                            hideKeyboardFrom(context, BP_Syst_Etxt);
                        }
                    }
                } catch (Exception ex) {
                    Log.i(Utils.TAG, "BP Syst Exception: " + ex.getMessage());
                    ex.printStackTrace();
                }


                if (!BP_Syst_Etxt.getText().toString().isEmpty() && !BP_Diast_Etxt.getText().toString().isEmpty()) {

                    BP_Scroll.post(new Runnable() {
                        @Override
                        public void run() {
                            BP_Scroll.scrollTo(BP_Scroll.getBottom(), BP_Result_Layout.getBottom());
                        }
                    });

                    final double bp_syst_value = Double.parseDouble(BP_Syst_Etxt.getText().toString().trim());
                    final double bp_diast_value = Double.parseDouble(BP_Diast_Etxt.getText().toString().trim());

                    if (bp_syst_value <= 50 && bp_diast_value <= 35) {
                        BP_Result.setText("Extremely Low Blood Pressure\n(Extremely Severe Hypotension)");
                        Progress_Work(progressBar, 210);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if ((bp_syst_value > 50 && bp_syst_value <= 70) || (bp_diast_value > 35 && bp_diast_value <= 40)) {
                        BP_Result.setText("Too Low Blood Pressure\n(Severe Hypotension)");
                        Progress_Work(progressBar, 160);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if ((bp_syst_value > 70 && bp_syst_value <= 90) || (bp_diast_value > 40 && bp_diast_value <= 60)) {
                        BP_Result.setText("Low Blood Pressure\n(Moderate Hypotension)");
                        Progress_Work(progressBar, 140);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if ((bp_syst_value > 90 && bp_syst_value <= 100) || (bp_diast_value > 60 && bp_diast_value <= 65)) {
                        BP_Result.setText("Low Normal Blood Pressure");
                        Progress_Work(progressBar, 120);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if ((bp_syst_value > 100 && bp_syst_value <= 120) && (bp_diast_value > 65 && bp_diast_value <= 80)) {
                        BP_Result.setText("Normal Blood Pressure");
                        Progress_Work(progressBar, 105);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if ((bp_syst_value > 120 && bp_syst_value <= 130) || (bp_diast_value > 80 && bp_diast_value <= 85)) {
                        BP_Result.setText("High Normal Blood Pressure");
                        Progress_Work(progressBar, 90);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if ((bp_syst_value > 130 && bp_syst_value <= 140) || (bp_diast_value > 85 && bp_diast_value <= 90)) {
                        BP_Result.setText("Pre-High Blood Pressure\n(Pre-Hypertension)");
                        Progress_Work(progressBar, 80);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if ((bp_syst_value > 140 && bp_syst_value <= 160) || (bp_diast_value > 90 && bp_diast_value <= 100)) {
                        BP_Result.setText("High Blood Pressure\n(Hypertension - Stage I)");
                        Progress_Work(progressBar, 70);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if ((bp_syst_value > 160 && bp_syst_value <= 180) || (bp_diast_value > 100 && bp_diast_value <= 110)) {
                        BP_Result.setText("High Blood Pressure\n(Hypertension - Stage II)");
                        Progress_Work(progressBar, 50);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if ((bp_syst_value > 180 && bp_syst_value <= 210) || (bp_diast_value > 110 && bp_diast_value <= 120)) {
                        BP_Result.setText("High Blood Pressure\n(Hypertension - Stage III)");
                        Progress_Work(progressBar, 30);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if (bp_syst_value > 210 && bp_diast_value > 120) {
                        BP_Result.setText("High Blood Pressure\n(Hypertension - Stage IV)");
                        Progress_Work(progressBar, 0);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else {
                        BP_Result.setText("Please! make sure your given blood pressure is accurate");
                        Progress_Work(progressBar, 210);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);
                    }
                } else {
                    BP_Result.setText(null);
                    Progress_Work(progressBar, 210);
                    progressBar.setVisibility(View.GONE);
                    BP_Result_Layout.setVisibility(View.GONE);
                }
            }
        });


        /* Diastolic Pressure */
        BP_Diast_Etxt.setFilters(new InputFilter[]{new InputFilter_Min_Max_Integer("0", "200")});

        BP_Diast_Etxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BP_Diast_Etxt.setFocusableInTouchMode(true);
                return false;
            }
        });

        BP_Diast_Etxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                try {

                    if (s.toString().length() == 2) {
                        if (!BP_Syst_Etxt.getText().toString().isEmpty()) {
                            hideKeyboardFrom(context, BP_Diast_Etxt);
                        }
                    }

                    if (s.toString().length() == 3) {

                        if (BP_Syst_Etxt.getText().toString().isEmpty()) {
                            Selection.setSelection(BP_Diast_Etxt.getText(), BP_Syst_Etxt.getSelectionStart());
                            BP_Syst_Etxt.requestFocus();

                        } else {
                            BP_Syst_Etxt.setFocusable(false);
                            BP_Diast_Etxt.setFocusable(false);
                            hideKeyboardFrom(context, BP_Diast_Etxt);
                            hideKeyboardFrom(context, BP_Syst_Etxt);
                        }
                    }
                } catch (Exception ex) {
                    Log.i(Utils.TAG, "BP Diast Exception: " + ex.getMessage());
                    ex.printStackTrace();
                }


                if (!BP_Syst_Etxt.getText().toString().isEmpty() && !BP_Diast_Etxt.getText().toString().isEmpty()) {

                    BP_Scroll.post(new Runnable() {
                        @Override
                        public void run() {
                            BP_Scroll.scrollTo(BP_Scroll.getBottom(), BP_Result_Layout.getBottom());
                        }
                    });

                    final double bp_syst_value = Double.parseDouble(BP_Syst_Etxt.getText().toString().trim());
                    final double bp_diast_value = Double.parseDouble(BP_Diast_Etxt.getText().toString().trim());

                    if (bp_syst_value <= 50 && bp_diast_value <= 35) {
                        BP_Result.setText("Extremely Low Blood Pressure\n(Extremely Severe Hypotension)");
                        Progress_Work(progressBar, 210);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if ((bp_syst_value > 50 && bp_syst_value <= 70) || (bp_diast_value > 35 && bp_diast_value <= 40)) {
                        BP_Result.setText("Too Low Blood Pressure\n(Severe Hypotension)");
                        Progress_Work(progressBar, 160);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if ((bp_syst_value > 70 && bp_syst_value <= 90) || (bp_diast_value > 40 && bp_diast_value <= 60)) {
                        BP_Result.setText("Low Blood Pressure\n(Moderate Hypotension)");
                        Progress_Work(progressBar, 140);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if ((bp_syst_value > 90 && bp_syst_value <= 100) || (bp_diast_value > 60 && bp_diast_value <= 65)) {
                        BP_Result.setText("Low Normal Blood Pressure");
                        Progress_Work(progressBar, 120);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if ((bp_syst_value > 100 && bp_syst_value <= 120) && (bp_diast_value > 65 && bp_diast_value <= 80)) {
                        BP_Result.setText("Normal Blood Pressure");
                        Progress_Work(progressBar, 105);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if ((bp_syst_value > 120 && bp_syst_value <= 130) || (bp_diast_value > 80 && bp_diast_value <= 85)) {
                        BP_Result.setText("High Normal Blood Pressure");
                        Progress_Work(progressBar, 90);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if ((bp_syst_value > 130 && bp_syst_value <= 140) || (bp_diast_value > 85 && bp_diast_value <= 90)) {
                        BP_Result.setText("Pre-High Blood Pressure\n(Pre-Hypertension)");
                        Progress_Work(progressBar, 80);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if ((bp_syst_value > 140 && bp_syst_value <= 160) || (bp_diast_value > 90 && bp_diast_value <= 100)) {
                        BP_Result.setText("High Blood Pressure\n(Hypertension - Stage I)");
                        Progress_Work(progressBar, 70);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if ((bp_syst_value > 160 && bp_syst_value <= 180) || (bp_diast_value > 100 && bp_diast_value <= 110)) {
                        BP_Result.setText("High Blood Pressure\n(Hypertension - Stage II)");
                        Progress_Work(progressBar, 50);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if ((bp_syst_value > 180 && bp_syst_value <= 210) || (bp_diast_value > 110 && bp_diast_value <= 120)) {
                        BP_Result.setText("High Blood Pressure\n(Hypertension - Stage III)");
                        Progress_Work(progressBar, 30);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else if (bp_syst_value > 210 && bp_diast_value > 120) {
                        BP_Result.setText("High Blood Pressure\n(Hypertension - Stage IV)");
                        Progress_Work(progressBar, 0);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);

                    } else {
                        BP_Result.setText("Please! make sure your given blood pressure is accurate");
                        Progress_Work(progressBar, 210);
                        progressBar.setVisibility(View.VISIBLE);
                        BP_Result_Layout.setVisibility(View.VISIBLE);
                    }
                } else {
                    BP_Result.setText(null);
                    Progress_Work(progressBar, 210);
                    progressBar.setVisibility(View.GONE);
                    BP_Result_Layout.setVisibility(View.GONE);
                }
            }
        });


        /* Dialog Box */
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(BP_Dialog_View);
        alertDialogBuilder.setCancelable(false);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(alertDialog.getWindow()).getDecorView().setBackgroundColor(TRANSPARENT);
        alertDialog.show();


        /* Cancel Dialog */
        cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator();
                alertDialog.dismiss();
            }
        });
    }

    private void Progress_Work(final SeekBar seekBar, final int value) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                for (progress = 0; progress <= (seekBar.getMax() - value); progress += 5) {

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            seekBar.setProgress(progress);
                        }
                    });

                    try {
                        Thread.sleep(50);

                    } catch (InterruptedException ex) {
                        Log.i(Utils.TAG, "Progress Exception: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            }
        });

        thread.start();

    }


    /* Animation */
    private void Open_Close_Animation(View Layout, ImageButton[] buttons) {
        if(isNewData) return;
        if (Layout.getVisibility() == android.view.View.GONE) {

            for(ImageButton button : buttons){
                button.setRotation(0);
                button.animate().rotationBy(360).setDuration(750);
            }

            Animation leftToRight = AnimationUtils.loadAnimation(getContext(), R.anim.left_to_right);
            Layout.setAnimation(leftToRight);

            Layout.setVisibility(android.view.View.VISIBLE);

        } else {
            for(ImageButton button : buttons){
                button.animate().rotationBy(-360).setDuration(750);
            }

            Animation rightToLeft = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_left);
            Layout.setAnimation(rightToLeft);

            Layout.setVisibility(android.view.View.GONE);
        }
    }

    private void Bounce_Animation() {

        if (getActivity() == null) return;

        Bounce_Animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.025, 0.5);
        Bounce_Animation.setInterpolator(interpolator);
    }


    /* Profile Data Delete */
    private void Delete_Data(String Key, EditText Value) {
        Value.setText(null);
        new MySQLiteDB(getContext()).deleteProfile(_id, Key);
    }


    /* Input Filter */
    public class InputFilter_Min_Max_Integer implements InputFilter {
        private int min, max;

        public InputFilter_Min_Max_Integer(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilter_Min_Max_Integer(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) {
                Log.i(Utils.TAG, "Min Max Integer Exception: " + nfe.getMessage());
                nfe.printStackTrace();
            }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }

    public class InputFilter_Min_Max_Decimal implements InputFilter {
        private double min, max;
        private int before, after;

        public InputFilter_Min_Max_Decimal(int before, int after, double min, double max) {
            this.min = min;
            this.max = max;
            this.before = before;
            this.after = after;
        }

        public InputFilter_Min_Max_Decimal(String before, String after, String min, String max) {
            this.min = Double.parseDouble(min);
            this.max = Double.parseDouble(max);
            this.before = Integer.parseInt(before);
            this.after = Integer.parseInt(after);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {

                double input = Double.parseDouble(dest.toString() + source.toString());

                Pattern mPattern = Pattern.compile("[0-9]{0," + (before - 1) + "}+((\\.[0-9]{0," + (after - 1) + "})?)||(\\.)?");
                Matcher matcher = mPattern.matcher(dest);

                if (isInRange(min, max, input) && matcher.matches())
                    return null;

            } catch (NumberFormatException nfe) {
                Log.i(Utils.TAG, "Min Max Decimal Exception: " + nfe.getMessage());
                nfe.printStackTrace();
            }
            return "";
        }

        private boolean isInRange(double a, double b, double c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }


    /* Share */
    private void Share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        String subject = "Event 38";
        String body = "\nLet me recommend you this application\n\n" + "Get this website:  " + Uri.parse("https://play.google.com/store/apps/details?id=" + Objects.requireNonNull(activity).getApplicationContext().getPackageName());

        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(intent, "Share With"));
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < permissions.length; i++) {

            if (requestCode == 111) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

                    /* If user rejected the permission */
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if (!shouldShowRequestPermissionRationale(permissions[i])) {

                            /* If user also CHECKED "never ask again */
                            new AlertDialog.Builder(getContext(), R.style.CustomDialogTheme)
                                    .setCancelable(true)
                                    .setTitle("Permission necessary")
                                    .setMessage("Storage Permission is needed for sharing app.")
                                    .setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getContext().getPackageName()));
                                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            getContext().startActivity(intent);
                                        }
                                    })
                                    .create()
                                    .show();
                        } else if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[i])) {
                            /*If user did NOT check "never ask again*/
                        }
                    }
                } else {
                    Share_Apk();
                }
            }
        }
    }

    private boolean isPermissionGranted(int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
                return false;
            }
        }
        else {
            return true;
        }
    }

    private void Share_Apk() {
        try {
            File initialApkFile = new File(context.getPackageManager().getApplicationInfo(Objects.requireNonNull(getContext()).getPackageName(), 0).sourceDir);

            File tempFile = new File(getContext().getExternalCacheDir() + "/ExtractedApk");

            if (!tempFile.isDirectory())
                if (!tempFile.mkdirs())
                    return;

            tempFile = new File(tempFile.getPath() + "/" + getResources().getString(R.string.app_name) + ".apk");

            if (!tempFile.exists()) {
                if (!tempFile.createNewFile()) {
                    return;
                }
            }

            InputStream in = new FileInputStream(initialApkFile);
            OutputStream out = new FileOutputStream(tempFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();

            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setType("*/*");
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
            startActivity(Intent.createChooser(share, "Share " + getResources().getString(R.string.app_name) + " Via"));
        }
        catch (Exception ex) {
            Log.i(Utils.TAG, "Apk Share Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    /* Vibrate when button is clicked */
    private void vibrator() {

        if (getActivity() == null) return;

        Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        if (vibe != null) vibe.vibrate(35);
    }


    /* Hide Soft Keyboard */
    private void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof Activity) activity = (Activity) this.context;
    }
}
