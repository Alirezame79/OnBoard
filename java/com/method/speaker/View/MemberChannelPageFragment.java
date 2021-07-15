package com.method.speaker.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.method.speaker.Data.AuthenticationLiveData;
import com.method.speaker.Data.Post;
import com.method.speaker.R;
import com.method.speaker.Adapters.BoardAdapter;
import com.method.speaker.Retrofit.Global;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberChannelPageFragment extends Fragment {

    ImageView channelImage;
    TextView channelName;
    TextView channelMemberCount;
    ProgressBar progressBar;

    String channel;
    String count;
    String url;

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.member_channel_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        progressBar.setVisibility(View.VISIBLE);
        channel = getArguments().getString("channel");
        count = getArguments().getString("count");
        url = getArguments().getString("image");
        loadTopic();
        getPosts(view);
        refreshClicked(view);
    }

    private void refreshClicked(final View view) {
        channelImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                getPosts(view);
                return true;
            }
        });
    }

    private void loadTopic() {
        Picasso.get().load(url).resize(250, 250).centerCrop()
                .transform(new CropCircleTransformation()).into(channelImage);

        channelName.setText(channel);

        channelMemberCount.setText(count + "\t" + getString(R.string.channel_member_counter_topic));

    }

    private void getPosts(final View view) {
        progressBar.setVisibility(View.VISIBLE);
        Global.getMyAPI().getAllPosts(channel).enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                Log.d("TAG", "post list get! " + response.body().toString());
                Collections.reverse(response.body());
                progressBar.setVisibility(View.INVISIBLE);
                setRecyclerView(view, response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Log.d("TAG", "sth went wrong");
            }
        });
    }

    private void setRecyclerView(View view, ArrayList<Post> postArrayList) {
        recyclerView = view.findViewById(R.id.post_recyclerview_member);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new BoardAdapter(postArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void findViews(View view) {
        channelImage = view.findViewById(R.id.channel_image_topic_member);
        channelName = view.findViewById(R.id.channel_name_txt_view);
        channelMemberCount = view.findViewById(R.id.channel_member_counter_txt_view);
        progressBar = view.findViewById(R.id.member_posts_progress_bar);
    }
}
