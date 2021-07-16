package com.method.speaker.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.method.speaker.Adapters.MemberChannelListAdapter;
import com.method.speaker.Data.Channel;
import com.method.speaker.Data.Member;
import com.method.speaker.Data.UserPreference;
import com.method.speaker.R;
import com.method.speaker.Retrofit.Global;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberChannelListFragment extends Fragment {

    ProgressBar progressBar;
    TextView noChannel;
    Button join;

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;

    public ArrayList<Channel> channelList = new ArrayList<>();
    public ArrayList<String> joinChannel = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.member_channel_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        progressBar.setVisibility(View.VISIBLE);
        receiveChannels(view);
        joinChannel(view);
    }

    private void joinChannel(final View view){
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("channels", joinChannel);
                Navigation.findNavController(view).navigate(R.id.action_memberChannelListFragment_to_joinChannelMemberFragment, bundle);
            }
        });
    }

    private void findViews(View view) {
        progressBar = view.findViewById(R.id.member_channel_list_progress_bar);
        noChannel = view.findViewById(R.id.no_channel_found_txt);
        join = view.findViewById(R.id.join_channel_btn);
    }

    private void receiveChannels(final View view) {
        channelList.clear();

        Log.d("TAF", "response2");
        UserPreference userPreference = new UserPreference(getContext());
        Global.getMyAPI().getMemberChannelList(userPreference.getUserData().getUsername()).enqueue(new Callback<ArrayList<Member>>() {
            @Override
            public void onResponse(Call<ArrayList<Member>> call, Response<ArrayList<Member>> response) {
                Log.d("TAF", "response3" + response.body());

                if (response.body().size() > 0) {
                    for (final Member member : response.body()) {
                        joinChannel.add(member.getChannel());
                        Global.getMyAPI().getChannel(member.getChannel()).enqueue(new Callback<ArrayList<Channel>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Channel>> call, Response<ArrayList<Channel>> response1) {
                                noChannel.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.VISIBLE);
                                Log.d("TAF", "response4" + member.getChannel());
                                Log.d("TAF", "response4" + response1.body());
                                channelList.add(response1.body().get(0));

                                Log.d("TAF", "response5" + channelList);
                                setRecyclerView(view, channelList);
                            }

                            @Override
                            public void onFailure(Call<ArrayList<Channel>> call, Throwable t) {
                                Log.d("TAG", "sth went wrong!");
                            }
                        });
                    }
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    noChannel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Member>> call, Throwable t) {
                Log.d("TAG", "sth went wrong!" + t.getMessage());
            }
        });
    }

    private void setRecyclerView(View view, ArrayList<Channel> arrayList) {
        recyclerView = view.findViewById(R.id.member_channel_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 2);
        adapter = new MemberChannelListAdapter(arrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.INVISIBLE);
    }

}
