package com.method.speaker.View;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.method.speaker.Data.AuthenticationLiveData;
import com.method.speaker.Data.Channel;
import com.method.speaker.R;
import com.method.speaker.Data.UserPreference;
import com.method.speaker.Retrofit.Global;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartingLoadingPageFragment extends Fragment {

    ProgressBar loadProgressBar;
    TextView loadingTextView;

    public String internet = "internetDialog";
    public String access = "accessDialog";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.starting_loading_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);

        // loading text blinking
        startAnimation();

        // here check channel and connectivity and app situation
        checkAppSituation(view);

    }

    private void checkAppSituation(final View view) {
        if (checkConnectivity()){

            Global.getMyAPI().getAppSituation().enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("TAG", "starting app response" + response.body());

                    if (response.body().equals("0")){
                        showAlertDialog(access);
                    }else if (response.body().equals("1")){
                        checkLogin(view);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("TAG", "fail" + t);
                    showAlertDialog(internet);
                }
            });

        }
    }

    private void checkLogin(final View view){
        final UserPreference userPreference = new UserPreference(getContext());
        if (userPreference.getUserData() == null){
            // Go to login pages
            Navigation.findNavController(view).navigate(R.id.action_startingLoadingPageFragment_to_chooseLoginFragment);
        }else {
            // Open channel after some seconds...
            if (userPreference.getUserData().isMember()){
                Navigation.findNavController(view).navigate(R.id.action_startingLoadingPageFragment_to_memberChannelListFragment);
            }else {
                Global.getMyAPI().getChannel(userPreference.getUserData().getChannel()).enqueue(new Callback<ArrayList<Channel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Channel>> call, Response<ArrayList<Channel>> response) {
                        if (response.body().size() > 0){
                            Channel channel = response.body().get(0);
                            AuthenticationLiveData.setChannel(channel.getName());
                            AuthenticationLiveData.setAddress(channel.getAddress());
                            AuthenticationLiveData.setMemberCount(channel.getMemberCount());
                            AuthenticationLiveData.setImageUrl(channel.getImageUrl());
                            Log.d("TAF", "receive channel " + AuthenticationLiveData.getChannel() + AuthenticationLiveData.getAddress() +
                                    AuthenticationLiveData.getMemberCount() + AuthenticationLiveData.getImageUrl());

                            Navigation.findNavController(view).navigate(R.id.action_startingLoadingPageFragment_to_adminChannelPageFragment);

                        }else {
                            Toast.makeText(getContext(), "There is no Board with this data!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Channel>> call, Throwable t) {

                        Log.d("TAG", "sth went wrong!");
                    }
                });
            }
        }
    }

    private void startAnimation() {
        ObjectAnimator loadingBlink = ObjectAnimator.ofFloat(
                loadingTextView,
                "Alpha",
                0f, 0.5f, 1f, 0.5f, 0f, 0.5f, 1f, 0.5f, 0f, 1f
        );
        loadingBlink.setDuration(3000);
        loadingBlink.start();
    }

    private void showAlertDialog(String flag) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        Log.d("TAG", "alertdialog" + flag);

        if (flag.equals(internet)){
            builder.setTitle(getString(R.string.no_internet_title))
                    .setMessage(getString(R.string.no_internet_message))
                    .setPositiveButton(getString(R.string.no_internet_retry), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            checkConnectivity();
                        }
                    }).setNegativeButton(getString(R.string.no_internet_exit), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            }).create().show();
        }else if (flag.equals(access)){
            builder.setTitle(getString(R.string.no_access_title))
                    .setMessage(getString(R.string.no_access_message))
                    .setNegativeButton(getString(R.string.no_access_exit), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            }).create().show();
        }

    }

    private boolean checkConnectivity() {
        ConnectivityManager cm =
                (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (!isConnected){
            showAlertDialog(internet);
        }
        return isConnected;
    }

    private void findViews(View view) {
        loadProgressBar = view.findViewById(R.id.load_progress_bar);
        loadingTextView = view.findViewById(R.id.loading_txt_view);
    }

}
