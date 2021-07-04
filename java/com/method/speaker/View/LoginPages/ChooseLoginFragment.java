package com.method.speaker.View.LoginPages;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.method.speaker.Data.User;
import com.method.speaker.Data.UserPreference;
import com.method.speaker.R;
import com.method.speaker.Data.AuthenticationLiveData;

public class ChooseLoginFragment extends Fragment {

    TextView memberLoginBtn;
    TextView adminLoginBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.choose_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        chooseLoginBtn(view);


    }

    private void chooseLoginBtn(final View view) {

        memberLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "user choose member mode");
                // save user as member
                AuthenticationLiveData.setMember(true);
                Navigation.findNavController(view).navigate(R.id.action_chooseLoginFragment_to_loginUserFragment);
            }
        });
        adminLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "user choose admin mode");
                // save user as admin
                AuthenticationLiveData.setMember(false);
                Navigation.findNavController(view).navigate(R.id.action_chooseLoginFragment_to_loginUserFragment);
            }
        });
//        sharedPreferences.putUserData(user);
    }

    private void findViews(View view) {
        memberLoginBtn = view.findViewById(R.id.login_member_btn);
        adminLoginBtn = view.findViewById(R.id.login_admin_btn);
    }

}
