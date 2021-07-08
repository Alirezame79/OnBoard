package com.method.speaker.View.LoginPages;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.method.speaker.Data.Admin;
import com.method.speaker.Data.Channel;
import com.method.speaker.R;
import com.method.speaker.Retrofit.Global;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterAdminFragment extends Fragment {

    EditText firstName;
    EditText lastName;
    EditText username;
    EditText password;
    EditText channel;
    Button confirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        btnClicked();
    }

    private void btnClicked() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.getMyAPI().getChannel(channel.getText().toString()).enqueue(new Callback<ArrayList<Channel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Channel>> call, Response<ArrayList<Channel>> response) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle(getString(R.string.registering_title));

                        if (response.body().size() > 0){
                            Log.d("TAG", "channel exist!");
                            builder.setMessage(getString(R.string.registering_same_channel_message));
                        }else {
                            Log.d("TAG", "There is no channel!");
                            builder.setMessage(getString(R.string.registering_new_channel_message));
                        }
                        builder.setPositiveButton(getString(R.string.registering_positive), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Global.getMyAPI().insertToRegisteringList(firstName.getText().toString(), lastName.getText().toString(),
                                        username.getText().toString(), password.getText().toString(), channel.getText().toString()).enqueue(new Callback<Admin>() {
                                    @Override
                                    public void onResponse(Call<Admin> call, Response<Admin> response) {
                                        Log.d("TAG", "registering data sent!");
                                    }

                                    @Override
                                    public void onFailure(Call<Admin> call, Throwable t) {
                                        Log.d("TAG", "sth went wrong!");
                                    }
                                });
                            }
                        });
                        builder.setNegativeButton(getString(R.string.registering_negative), null);
                        builder.create();
                        builder.show();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Channel>> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void findViews(View view) {
        firstName = view.findViewById(R.id.first_name_edit_text);
        lastName = view.findViewById(R.id.last_name_edit_text);
        username = view.findViewById(R.id.register_username_edit_text);
        password = view.findViewById(R.id.register_password_edit_text);
        channel = view.findViewById(R.id.register_channel_edit_text);
        confirm = view.findViewById(R.id.confirm_registration_btn);
    }
}
