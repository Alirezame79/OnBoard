package com.method.speaker.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.method.speaker.Data.AuthenticationLiveData;
import com.method.speaker.Data.UserPreference;
import com.method.speaker.R;
import com.method.speaker.Retrofit.Global;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewPostDialog extends Dialog {

    public Button add;
    public EditText text;

    public AddNewPostDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_post);
        getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        findViews();
        btnClicked();


    }

    private void btnClicked() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // post text
                String post = text.getText().toString();
                if (post.equals("")){
                    return;
                }

                // post date
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date date = new Date();
                Log.d("TAF", formatter.format(date));

                // preferences object
                UserPreference userPreference = new UserPreference(getContext());

                Global.getMyAPI().sendNewPost(AuthenticationLiveData.getChannel(), userPreference.getUserData().getUsername(), post, formatter.format(date)).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("TAG", response.body() + "app send data");
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("TAG", "sth happen wrong!" + t.getMessage());
                        dismiss();
                    }
                });
            }
        });
    }

    private void findViews() {
        add = findViewById(R.id.add_post_btn);
        text = findViewById(R.id.new_post_box_edit_txt);
    }
}