package com.method.speaker.View.LoginPages;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.method.speaker.R;
import com.method.speaker.Retrofit.Global;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class RegisterNewChannelAdminFragment extends Fragment {

    private static final int RESULT_LOAD_IMAGE = 11;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText username;
    EditText password;
    EditText channel;
    Button confirm;
    ImageButton addImage;

    ImageView test;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_new_channel_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        btnClicked();
    }

    private void btnClicked() {
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Log.d("TAF", "image returned successfully!");
            Uri uri = data.getData();
            test.setImageURI(uri);
            Bitmap image = ((BitmapDrawable) test.getDrawable()).getBitmap();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            String encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            Log.d("TAF", "encodeImage: " + encodeImage);
        }
    }

    private void findViews(View view) {
        firstName = view.findViewById(R.id.first_name_edit_text);
        lastName = view.findViewById(R.id.last_name_edit_text);
        username = view.findViewById(R.id.register_username_edit_text);
        password = view.findViewById(R.id.register_password_edit_text);
        channel = view.findViewById(R.id.register_channel_edit_text);
        confirm = view.findViewById(R.id.confirm_registration_btn);
        email = view.findViewById(R.id.register_email_edit_text);
        addImage = view.findViewById(R.id.receive_suggested_image);
        test = view.findViewById(R.id.test);
    }
}
