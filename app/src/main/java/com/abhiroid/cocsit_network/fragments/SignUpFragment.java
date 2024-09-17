package com.abhiroid.cocsit_network.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.abhiroid.cocsit_network.MainActivity;
import com.abhiroid.cocsit_network.R;
import com.abhiroid.cocsit_network.apis.UsersAPI;
import com.abhiroid.cocsit_network.model_response.ProfileImage;
import com.abhiroid.cocsit_network.util.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment  implements View.OnClickListener {

    private final int CAMERA_REQ_CODE = 1000;
    private final int GALLERY_REQ_CODE = 2000;
    Bitmap bitmap;

    ShapeableImageView profileImg;
    FloatingActionButton btnAddImg;
    EditText etName , etDOB , etMobile , etEmail;
    ImageView btnImgCalender , btnMaleImg , btnFemaleImg;
    Spinner classSpin , divSpin;
    MaterialButton btnProceed;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        etDOB = view.findViewById(R.id.etDOB);
        etMobile = view.findViewById(R.id.etMobile);
        etEmail =view.findViewById(R.id.etEmail);
        btnImgCalender = view.findViewById(R.id.btnImgCalender);
        btnMaleImg = view.findViewById(R.id.btnMaleImg);
        btnFemaleImg = view.findViewById(R.id.btnFemaleImg);
        classSpin = view.findViewById(R.id.classSpin);
        divSpin = view.findViewById(R.id.divSpin);


        profileImg = view.findViewById(R.id.profileImg);
        btnAddImg = view.findViewById(R.id.btnAddImg);
        etName = view.findViewById(R.id.etName);
        btnProceed = view.findViewById(R.id.btnProceed);




        btnAddImg.setOnClickListener(this);
        btnProceed.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.btnAddImg){
            selectImage();
        }else if(id == R.id.btnProceed) {
            upload();
        }else {
            Toast.makeText(getContext(), "In Development", Toast.LENGTH_SHORT).show();
        }
    }


    private void selectImage(){
        Intent iGallery = new Intent();
        iGallery.setType("image/*");
        iGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(iGallery , GALLERY_REQ_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == GALLERY_REQ_CODE && data != null){

                Uri path = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap((getActivity()).getContentResolver() , path);
                    profileImg.setImageBitmap(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //compress the image and decode it ...
    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG , 100 , byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageByte , Base64.DEFAULT);
    }

    //upload image to the server...
    private void upload(){
        String image = imageToString();
        String title = etName.getText().toString().trim();
        String title2 = title.replaceAll("\\s+", "");

        UsersAPI usersAPI = RetrofitClient.getInstance().getUserApi();
        Call<ProfileImage> call = usersAPI.uploadProfileImg(title2 , image);

        call.enqueue(new Callback<ProfileImage>() {
            @Override
            public void onResponse(Call<ProfileImage> call, Response<ProfileImage> response) {
                ProfileImage profileImage = response.body();

                if(response.isSuccessful() && response.body() != null){

                    if(profileImage.getError().equals("000")){

                        Toast.makeText(getContext(), profileImage.getMessage(), Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getContext(), profileImage.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Toast.makeText(getContext(), profileImage.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileImage> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}