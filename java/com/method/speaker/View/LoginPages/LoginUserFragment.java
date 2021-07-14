package com.method.speaker.View.LoginPages;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.method.speaker.Data.Admin;
import com.method.speaker.Data.Member;
import com.method.speaker.Data.User;
import com.method.speaker.Data.UserPreference;
import com.method.speaker.R;
import com.method.speaker.Data.AuthenticationLiveData;
import com.method.speaker.Retrofit.Global;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUserFragment extends Fragment {

    EditText username;
    EditText password;
    Button login;
    TextView alert;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        loginBtnClicked(view);
        disappearAlert();
    }

    private void disappearAlert() {
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                alert.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                alert.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loginBtnClicked(final View view) {
        // make new object of LiveData

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                // get edit text data
                final String usernameTxt = username.getText().toString();
                final String passwordTxt = password.getText().toString();

                if (AuthenticationLiveData.isMember()){
                    // user is member
                    // we have to add member data to server
                    Global.getMyAPI().getMemberChannelName(usernameTxt, passwordTxt).enqueue(new Callback<ArrayList<Member>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Member>> call, Response<ArrayList<Member>> response) {
                            if (response.body().size() > 0){
                                // member has a channel and have to login to that particular channel
                                Log.d("TAG", "member has channel " + response.body().get(0).toString());
                                Member member = response.body().get(0);
                                AuthenticationLiveData.setUsername(member.getUsername());
                                AuthenticationLiveData.setPassword(member.getPassword());
                                AuthenticationLiveData.setChannel(member.getChannel());
                            }else {
                                Log.d("TAG", "member has NO channel");
                                // member has no channel yet
                                AuthenticationLiveData.setUsername(usernameTxt);
                                AuthenticationLiveData.setPassword(passwordTxt);
                                AuthenticationLiveData.setChannel("");
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                            Navigation.findNavController(view).navigate(R.id.action_loginUserFragment_to_findChannelFragment);
                        }

                        @Override
                        public void onFailure(Call<ArrayList<Member>> call, Throwable t) {
                            Toast.makeText(getContext(), "Something went wrong!" + t.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "user admin error" + t);
                        }
                    });
                } else {
                    // user is admin
                    // we have to check admin data and get his/her channel name
                    Global.getMyAPI().getAdminChannelAccess(usernameTxt, passwordTxt).enqueue(new Callback<ArrayList<Admin>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Admin>> call, Response<ArrayList<Admin>> response) {
                            if (response.body().size() > 0){
                                // admin channel is received
                                Log.d("TAG", "user admin" + response.body().get(0).toString());
                                Admin admin = response.body().get(0);
                                AuthenticationLiveData.setUsername(admin.getUsername());
                                AuthenticationLiveData.setPassword(admin.getPassword());
                                AuthenticationLiveData.setChannel(admin.getChannel());
                                Navigation.findNavController(view).navigate(R.id.action_loginUserFragment_to_findChannelFragment);
                            }else {
                                // there is no admin with this data
                                Log.d("TAG", "there is no admin with this data");
                                alert.setVisibility(View.VISIBLE);
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(Call<ArrayList<Admin>> call, Throwable t) {
                            Toast.makeText(getContext(), "Something went wrong!" + t.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "user admin error" + t);
                        }
                    });
                }
            }
        });
    }

    private void findViews(View view) {
        username = view.findViewById(R.id.enter_username_edit_txt);
        password = view.findViewById(R.id.enter_password_edit_txt);
        login = view.findViewById(R.id.login_btn);
        alert = view.findViewById(R.id.login_alert_txt);
        progressBar = view.findViewById(R.id.login_progress_bar);
    }
}
