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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.method.speaker.Data.AuthenticationLiveData;
import com.method.speaker.Data.Member;
import com.method.speaker.R;
import com.method.speaker.Adapters.MemberAdapter;
import com.method.speaker.Retrofit.Global;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminChannelDetailsFragment extends Fragment {

    ImageView image;
    TextView name;
    TextView address;
    ImageView notificationBtn;
    ProgressBar progressBar;

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.admin_channel_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        loadParts(view);
        btnClicked(view);
    }

    private void btnClicked(final View view) {
        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_adminChannelDetailsFragment_to_notificationRoomFragment);
            }
        });
    }

    private void loadParts(final View view) {
        progressBar.setVisibility(View.VISIBLE);

        // load image
        Picasso.get().load(AuthenticationLiveData.getImageUrl()).resize(250, 250).centerCrop()
                .transform(new CropCircleTransformation()).into(image);

        // load name
        name.setText(AuthenticationLiveData.getChannel());
        // load address
        address.setText(getText(R.string.channel_address_equal) + AuthenticationLiveData.getAddress());

        // load members
        Global.getMyAPI().getMembersForAdmin(AuthenticationLiveData.getChannel()).enqueue(new Callback<ArrayList<Member>>() {
            @Override
            public void onResponse(Call<ArrayList<Member>> call, Response<ArrayList<Member>> response) {
                if (response.body().size() > 0) {
                    Log.d("TAG", "members list " + response.body().toString());
                }else {
                    Log.d("TAG", "Shit!");
                }

                ArrayList<String> members = new ArrayList<>();
                for (Member x: response.body()){
                    members.add(x.getUsername());
                }
                progressBar.setVisibility(View.INVISIBLE);
                setRecyclerView(view, members);
            }

            @Override
            public void onFailure(Call<ArrayList<Member>> call, Throwable t) {

            }
        });

    }

    private void setRecyclerView(View view, ArrayList<String> arrayList) {
        recyclerView = view.findViewById(R.id.members_for_admin_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new MemberAdapter(arrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void findViews(View view) {
        image = view.findViewById(R.id.channel_image_dtl);
        name = view.findViewById(R.id.channel_name_dtl);
        address = view.findViewById(R.id.channel_address_dtl);
        notificationBtn = view.findViewById(R.id.notification_btn);
        progressBar = view.findViewById(R.id.admin_detail_progress_bar);
    }
}
