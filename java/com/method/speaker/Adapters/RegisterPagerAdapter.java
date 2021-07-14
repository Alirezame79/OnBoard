package com.method.speaker.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.method.speaker.View.LoginPages.RegisterExistingChannelAdminFragment;
import com.method.speaker.View.LoginPages.RegisterNewChannelAdminFragment;

public class RegisterPagerAdapter extends FragmentStatePagerAdapter {

    public RegisterPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RegisterNewChannelAdminFragment();
            case 1:
                return new RegisterExistingChannelAdminFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Create New Board";
            case 1:
                return "Join Existing Board";
        }
        return null;
    }
}
