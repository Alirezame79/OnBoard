package com.method.speaker.View.LoginPages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.method.speaker.Adapters.RegisterPagerAdapter;
import com.method.speaker.R;

public class RegisteringAdminContainer extends AppCompatActivity {

    public ViewPager viewPager;
    public TabLayout tabLayout;

    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering_admin_container);
        findViews();
        ViewPagerHandle();
    }

    private void ViewPagerHandle() {
        RegisterPagerAdapter adapter = new RegisterPagerAdapter(fragmentManager);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void findViews() {
        viewPager = findViewById(R.id.register_view_pager);
        tabLayout = findViewById(R.id.register_tab_layout);
    }
}