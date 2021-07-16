package com.method.speaker.View.LoginPages;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.method.speaker.Data.Admin;
import com.method.speaker.Data.Channel;
import com.method.speaker.R;
import com.method.speaker.Retrofit.Global;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterExistingChannelAdminFragment extends Fragment {

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText username;
    EditText password;
    EditText channel;
    Button confirm;
    TextView alert;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_existing_channel_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        btnClicked();
        disappearAlert();
    }

    private void disappearAlert() {
        firstName.addTextChangedListener(new TextWatcher() {
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
        lastName.addTextChangedListener(new TextWatcher() {
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
        email.addTextChangedListener(new TextWatcher() {
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
        channel.addTextChangedListener(new TextWatcher() {
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

    private void btnClicked() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                if (username.getText().toString().equals("") || password.getText().toString().equals("") ||
                        email.getText().toString().equals("") || channel.getText().toString().equals("")){
                    return;
                }

                Global.getMyAPI().getChannel(channel.getText().toString()).enqueue(new Callback<ArrayList<Channel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Channel>> call, Response<ArrayList<Channel>> response) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle(getString(R.string.registering_title));

                        if (response.body().size() > 0){
                            Log.d("TAG", "channel exist!");
                            builder.setMessage(getString(R.string.registering_same_channel_message));
                            builder.setPositiveButton(getString(R.string.registering_positive), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Global.getMyAPI().insertToRegisteringList(firstName.getText().toString(), lastName.getText().toString(),
                                            email.getText().toString(), username.getText().toString(),
                                            password.getText().toString(), channel.getText().toString()).enqueue(new Callback<Admin>() {
                                        @Override
                                        public void onResponse(Call<Admin> call, Response<Admin> response) {
                                            Log.d("TAG", "registering data sent!");
                                            progressBar.setVisibility(View.INVISIBLE);
                                            alert.setVisibility(View.VISIBLE);
                                            alert.setText(getString(R.string.register_done));
                                            alert.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_green));
                                        }

                                        @Override
                                        public void onFailure(Call<Admin> call, Throwable t) {
                                            Log.d("TAG", "sth went wrong!");
                                            progressBar.setVisibility(View.INVISIBLE);
                                            alert.setVisibility(View.VISIBLE);
                                            alert.setText(getString(R.string.register_done));
                                            alert.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_green));
                                        }
                                    });
                                }
                            });
                            builder.setNegativeButton(getString(R.string.registering_negative), null).create().show();
                        }else {
                            Log.d("TAG", "There is no channel!");
                            alert.setText(getString(R.string.no_channel_found));
                            alert.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_red));
                            alert.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Channel>> call, Throwable t) {
                        Log.d("TAG", "sth went wrong!");
                    }
                });
            }
        });
    }

    private void findViews(View view) {
        firstName = view.findViewById(R.id.first_name_edit_text);
        lastName = view.findViewById(R.id.last_name_edit_text);
        email = view.findViewById(R.id.register_email_edit_text);
        username = view.findViewById(R.id.register_username_edit_text);
        password = view.findViewById(R.id.register_password_edit_text);
        channel = view.findViewById(R.id.register_channel_edit_text);
        confirm = view.findViewById(R.id.confirm_registration_btn);
        alert = view.findViewById(R.id.existing_channel_alert_txt);
        progressBar = view.findViewById(R.id.exist_registering_progress_bar);
    }
}
