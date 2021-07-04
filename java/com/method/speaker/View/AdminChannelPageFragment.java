package com.method.speaker.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.method.speaker.RecyclerViews.BoardAdapter;
import com.method.speaker.Data.AuthenticationLiveData;
import com.method.speaker.Data.Post;
import com.method.speaker.R;
import com.method.speaker.Retrofit.Global;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminChannelPageFragment extends Fragment {

    ImageView channelImage;
    Button addPost;

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.admin_channel_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        loadImage();
        getPosts(view);
        imageClicked(view);
        addPostClicked(view);
    }

    private void addPostClicked(View view) {
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewPostDialog dialog = new AddNewPostDialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
            }
        });
        getPosts(view);
    }

    private void imageClicked(final View view) {
        channelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_adminChannelPageFragment_to_adminChannelDetailsFragment);

            }
        });

        channelImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                getPosts(view);
                return true;
            }
        });
    }

    private void getPosts(final View view) {
        Global.getMyAPI().getAllPosts(AuthenticationLiveData.getChannel()).enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                Log.d("TAG", "post list get! " + response.body().toString());
                Collections.reverse(response.body());
                setRecyclerView(view, response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Log.d("TAG", "sth went wrong");
            }
        });
    }

    private void setRecyclerView(View view, ArrayList<Post> postArrayList) {
        recyclerView = view.findViewById(R.id.post_recyclerview_admin);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new BoardAdapter(postArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void loadImage() {
        Picasso.get().load(AuthenticationLiveData.getImageUrl()).resize(100, 100).centerCrop()
                .transform(new CropCircleTransformation()).into(channelImage);

    }

    private void findViews(View view) {
        channelImage = view.findViewById(R.id.channel_image_for_admin);
        addPost = view.findViewById(R.id.add_post_admin);
    }
}
