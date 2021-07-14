package com.method.speaker.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.method.speaker.Data.AuthenticationLiveData;
import com.method.speaker.NewInventions.FindUsername;
import com.method.speaker.Data.Notification;
import com.method.speaker.R;
import com.method.speaker.Retrofit.Global;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    private List<Notification> items;

    public NotificationAdapter(List<Notification> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_structure_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.message.setText(items.get(position).getMessage());
        if (items.get(position).getMode() == 11){
            holder.decisionContainer.setVisibility(View.GONE);
        }

        holder.agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAF",FindUsername.achieveUsername(items.get(position).getMessage()));
                Global.getMyAPI().acceptNewAdmin(FindUsername.achieveUsername(items.get(position).getMessage()), AuthenticationLiveData.getChannel(), items.get(position).getMessage()).enqueue(new Callback<ArrayList<String>>() {
                    @Override
                    public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                        Log.d("TAF", "New Admin saved!");
                        holder.layout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                        Log.d("TAF", "sth went wrong!");
                    }
                });
            }
        });

        holder.disAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAF",FindUsername.achieveUsername(items.get(position).getMessage()));
                Global.getMyAPI().rejectNewAdmin(FindUsername.achieveUsername(items.get(position).getMessage()), AuthenticationLiveData.getChannel(), items.get(position).getMessage()).enqueue(new Callback<ArrayList<String>>() {
                    @Override
                    public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                        Log.d("TAF", "New Admin saved!");
                        holder.layout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                        Log.d("TAF", "sth went wrong!");
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout layout;
        public TextView title;
        public TextView message;
        public Button agree;
        public Button disAgree;
        public LinearLayout decisionContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.notification_card_layout);
            title = itemView.findViewById(R.id.notification_title_txt_view);
            message = itemView.findViewById(R.id.notification_message_txt_view);
            agree = itemView.findViewById(R.id.agree_btn);
            disAgree = itemView.findViewById(R.id.disagree_btn);
            decisionContainer = itemView.findViewById(R.id.decision_container);
        }
    }
}
