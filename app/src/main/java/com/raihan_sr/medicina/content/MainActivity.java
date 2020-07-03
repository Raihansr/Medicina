package com.raihan_sr.medicina.content;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.raihan_sr.medicina.util.Utils;
import com.raihan_sr.medicina.fragment.Fragment_Medicine;
import com.raihan_sr.medicina.fragment.Fragment_Profile;
import com.raihan_sr.medicina.R;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initId();

        viewPagerSetup();
    }

    private void  initId(){
        Utils.rootLayout = findViewById(R.id.root_Layout);
        tabLayout = findViewById(R.id.tabLayout_id);
        viewPager = findViewById(R.id.viewPager_id);
    }

    private void viewPagerSetup(){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new Fragment_Profile(), getResources().getString(R.string.profile));
        adapter.AddFragment(new Fragment_Medicine(), getResources().getString(R.string.medicine));
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.ic_profile);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.ic_medicine);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
