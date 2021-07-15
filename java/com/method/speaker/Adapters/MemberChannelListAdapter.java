package com.method.speaker.Adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.method.speaker.Data.Channel;
import com.method.speaker.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MemberChannelListAdapter extends RecyclerView.Adapter<MemberChannelListAdapter.ViewHolder>{

    private List<Channel> items;

    public MemberChannelListAdapter(List<Channel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_channel_list_structure_cardview, parent, false);
        return new MemberChannelListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MemberChannelListAdapter.ViewHolder holder, final int position) {
        Picasso.get().load(items.get(position).getImageUrl()).resize(100, 100).centerCrop()
                .transform(new CropCircleTransformation()).into(holder.image);

        if (items.get(position).getName().length() > 20){
            holder.name.setTextSize(11);
        }
        holder.name.setText(items.get(position).getName());
        holder.memberCount.setText(items.get(position).getMemberCount() + " Viewer");

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("channel", items.get(position).getName());
                bundle.putString("count", String.valueOf(items.get(position).getMemberCount()));
                bundle.putString("image", items.get(position).getImageUrl());
                Navigation.findNavController(v).navigate(R.id.action_memberChannelListFragment_to_memberChannelPageFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;
        public TextView name;
        public TextView memberCount;
        public ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.channel_image_topic_member_str);
            name = itemView.findViewById(R.id.channel_name_txt_view_str);
            memberCount = itemView.findViewById(R.id.channel_member_counter_txt_view_str);
            layout = itemView.findViewById(R.id.channel_list_structure_layout);
        }
    }
}
