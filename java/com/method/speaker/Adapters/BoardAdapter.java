package com.method.speaker.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.method.speaker.Data.Post;
import com.method.speaker.R;

import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder>{

    private List<Post> items;

    public BoardAdapter(List<Post> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_structure_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.poster.setText(items.get(position).getPoster() + ":");
        holder.post.setText(items.get(position).getPost());
        holder.detail.setText(items.get(position).getDetail());
//        holder.likes.setText(String.valueOf(items.get(position).getLikes()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView poster;
        public TextView post;
        public TextView detail;
//        public TextView likes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster_data);
            post = itemView.findViewById(R.id.post_txt_data);
            detail = itemView.findViewById(R.id.post_detail_data);
//            likes = itemView.findViewById(R.id.likes_counter);
        }
    }
}
