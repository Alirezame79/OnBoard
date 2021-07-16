package com.method.speaker.View;

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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.method.speaker.Data.Channel;
import com.method.speaker.Data.Member;
import com.method.speaker.Data.UserPreference;
import com.method.speaker.R;
import com.method.speaker.Retrofit.Global;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinChannelMemberFragment extends Fragment {

    EditText name;
    EditText address;
    ProgressBar progressBar;
    Button join;
    TextView alert;

    ArrayList<String> joinChannels = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.join_channel_member, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        joinChannels = getArguments().getStringArrayList("channels");
        joinChannel(view);
        disappearAlert(view);
    }

    private void disappearAlert(View view) {
        name.addTextChangedListener(new TextWatcher() {
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
        address.addTextChangedListener(new TextWatcher() {
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

    private void joinChannel(View view) {
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                if (joinChannels.contains(name.getText().toString())){
                    progressBar.setVisibility(View.INVISIBLE);
                    alert.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_yellow));
                    alert.setVisibility(View.VISIBLE);
                    alert.setText(getString(R.string.member_join_before));
                    return;
                }

                Global.getMyAPI().checkChannel(name.getText().toString(), address.getText().toString()).enqueue(new Callback<ArrayList<Channel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Channel>> call, Response<ArrayList<Channel>> response) {
                        if (response.body().size() > 0){
                            Log.d("TAF", "channel existed!");

                            UserPreference userPreference = new UserPreference(getContext());
                            Global.getMyAPI().joinChannel(userPreference.getUserData().getUsername(), userPreference.getUserData().getPassword(), name.getText().toString()).enqueue(new Callback<ArrayList<Member>>() {
                                @Override
                                public void onResponse(Call<ArrayList<Member>> call, Response<ArrayList<Member>> response) {
                                    Log.d("TAF", "member added!");

                                    progressBar.setVisibility(View.INVISIBLE);
                                    alert.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_green));
                                    alert.setVisibility(View.VISIBLE);
                                    alert.setText(getString(R.string.member_join));
                                }

                                @Override
                                public void onFailure(Call<ArrayList<Member>> call, Throwable t) {
                                    Log.d("TAG", "sth went wrong!");

                                }
                            });

                        }else {
                            alert.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_red));
                            alert.setVisibility(View.VISIBLE);
                            alert.setText(getString(R.string.no_channel_found));
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Channel>> call, Throwable t) {

                    }
                });
            }
        });

    }

    private void findViews(View view) {
        name = view.findViewById(R.id.enter_channel_name_edit_txt);
        address = view.findViewById(R.id.enter_channel_address_edit_txt);
        progressBar = view.findViewById(R.id.join_progress_bar);
        join = view.findViewById(R.id.join_btn);
        alert = view.findViewById(R.id.join_alert_txt);
    }
}
