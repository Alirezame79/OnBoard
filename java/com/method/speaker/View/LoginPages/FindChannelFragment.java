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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.method.speaker.Data.Channel;
import com.method.speaker.R;
import com.method.speaker.Data.AuthenticationLiveData;
import com.method.speaker.Retrofit.Global;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindChannelFragment extends Fragment {

    EditText channelName;
    Button next;
    TextView alert;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.find_channel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        nextBtnClicked(view);
        disappearAlert();
    }

    private void disappearAlert() {
        channelName.addTextChangedListener(new TextWatcher() {
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

    private void nextBtnClicked(final View view) {
        Log.d("TAF", AuthenticationLiveData.isMember() + AuthenticationLiveData.getUsername()
                + AuthenticationLiveData.getPassword() + AuthenticationLiveData.getChannel());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get channel name text
                progressBar.setVisibility(View.VISIBLE);
                final String channelNameTxt = channelName.getText().toString();

                if (AuthenticationLiveData.isMember()){
                    if (AuthenticationLiveData.getChannel().equals("")){
                        Global.getMyAPI().getChannel(channelNameTxt).enqueue(new Callback<ArrayList<Channel>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Channel>> call, Response<ArrayList<Channel>> response) {
                                if (response.body().size() > 0){
                                    Log.d("TAG", "add new channel name");
                                    AuthenticationLiveData.setChannel(channelNameTxt);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Navigation.findNavController(view).navigate(R.id.action_findChannelFragment_to_verifyChannelFragment);
                                }else {
                                    Log.d("TAG", "no channel with this name");
                                }
                            }

                            @Override
                            public void onFailure(Call<ArrayList<Channel>> call, Throwable t) {
                                Log.d("TAG", "sth went wrong");
                            }
                        });

                    }else if (AuthenticationLiveData.getChannel().equals(channelNameTxt)){
                        Log.d("TAG", "correct channel name");
                        progressBar.setVisibility(View.INVISIBLE);
                        Navigation.findNavController(view).navigate(R.id.action_findChannelFragment_to_verifyChannelFragment);
                    }

                }else{
                    if (AuthenticationLiveData.getChannel().equals(channelNameTxt)){
                        // channel name correctly entered!
                        Log.d("TAG", "correct channel name");
                        progressBar.setVisibility(View.INVISIBLE);
                        Navigation.findNavController(view).navigate(R.id.action_findChannelFragment_to_verifyChannelFragment);
                    }else {
                        Log.d("TAG", "wrong channel name");
                        // show alert
                        progressBar.setVisibility(View.INVISIBLE);
                        alert.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void findViews(View view) {
        channelName = view.findViewById(R.id.enter_channel_name_edit_txt);
        next = view.findViewById(R.id.next_channel_name_btn);
        alert = view.findViewById(R.id.find_alert_txt);
        progressBar = view.findViewById(R.id.find_progress_bar);
    }
}