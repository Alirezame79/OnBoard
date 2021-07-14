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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.method.speaker.Data.AuthenticationLiveData;
import com.method.speaker.Data.Channel;
import com.method.speaker.Data.User;
import com.method.speaker.Data.UserPreference;
import com.method.speaker.R;
import com.method.speaker.Retrofit.Global;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyChannelFragment extends Fragment {

    EditText channelAddress;
    Button finish;
    ImageView image;
    TextView alert;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.verify_channel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        finishClicked(view);
        disappearAlert();
    }

    private void disappearAlert() {
        channelAddress.addTextChangedListener(new TextWatcher() {
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

    private void finishClicked(final View view) {
        final Channel[] channel = {new Channel()};
        progressBar.setVisibility(View.VISIBLE);

        Global.getMyAPI().getChannel(AuthenticationLiveData.getChannel()).enqueue(new Callback<ArrayList<Channel>>() {
            @Override
            public void onResponse(Call<ArrayList<Channel>> call, Response<ArrayList<Channel>> response) {
                Log.d("TAG", "channel info " + response.body().get(0).toString());
                channel[0] = response.body().get(0);
                AuthenticationLiveData.setName(channel[0].getName());
                AuthenticationLiveData.setAddress(channel[0].getAddress());
                AuthenticationLiveData.setMemberCount(channel[0].getMemberCount());
                AuthenticationLiveData.setImageUrl(channel[0].getImageUrl());

                Picasso.get().load(AuthenticationLiveData.imageUrl).resize(100, 100).centerCrop()
                        .transform(new CropCircleTransformation()).into(image);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ArrayList<Channel>> call, Throwable t) {
                Log.d("TAG", "sth went wrong");
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get channel address text
                progressBar.setVisibility(View.VISIBLE);

                final String channelAddressTxt = channelAddress.getText().toString();

                if (channel[0].getAddress().equals(channelAddressTxt)){
                    Log.d("TAGG", "channel saved" + AuthenticationLiveData.getName() + AuthenticationLiveData.getAddress()
                     + AuthenticationLiveData.getMemberCount() + AuthenticationLiveData.getImageUrl());

                    User user = new User();
                    user.setMember(AuthenticationLiveData.isMember());
                    user.setUsername(AuthenticationLiveData.getUsername());
                    user.setPassword(AuthenticationLiveData.getPassword());
                    user.setChannel(AuthenticationLiveData.getChannel());

                    UserPreference userPreference = new UserPreference(getContext());
                    userPreference.putUserData(user);

                    // correct address
                    if (AuthenticationLiveData.isMember()){
                        progressBar.setVisibility(View.INVISIBLE);
                        Navigation.findNavController(view).navigate(R.id.action_verifyChannelFragment_to_memberChannelPageFragment);
                    }else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Navigation.findNavController(view).navigate(R.id.action_verifyChannelFragment_to_adminChannelPageFragment);
                    }
                }else {
                    // show alert
                    alert.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    private void findViews(View view) {
        channelAddress = view.findViewById(R.id.enter_channel_address_edit_txt);
        finish = view.findViewById(R.id.finish_channel_address_btn);
        image = view.findViewById(R.id.channel_image);
        alert = view.findViewById(R.id.verify_alert_txt);
        progressBar = view.findViewById(R.id.verify_progress_bar);
    }
}
