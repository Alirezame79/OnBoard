package com.method.speaker.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.method.speaker.Data.AuthenticationLiveData;
import com.method.speaker.Data.Notification;
import com.method.speaker.R;
import com.method.speaker.RecyclerViews.MemberAdapter;
import com.method.speaker.RecyclerViews.NotificationAdapter;
import com.method.speaker.Retrofit.Global;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationRoomFragment extends Fragment {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notification_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadNotifications(view);
    }

    private void loadNotifications(final View view) {
        Global.getMyAPI().getChannelNotifications(AuthenticationLiveData.getChannel() + getString(R.string.access_notification_server)).enqueue(new Callback<ArrayList<Notification>>() {
            @Override
            public void onResponse(Call<ArrayList<Notification>> call, Response<ArrayList<Notification>> response) {
                if (response.body() != null) {
                    Collections.reverse(response.body());
                }
                setRecyclerView(view, response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Notification>> call, Throwable t) {
                Log.d("TAG", "sth went wrong!");
            }
        });
    }

    private void setRecyclerView(View view, ArrayList<Notification> arrayList) {
        recyclerView = view.findViewById(R.id.notification_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new NotificationAdapter(arrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}