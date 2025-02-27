package com.abhiroid.cocsit_network.fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abhiroid.cocsit_network.R;
import com.abhiroid.cocsit_network.model_response.PostModel;
import com.abhiroid.cocsit_network.util.RetrofitClient;
import com.abhiroid.cocsit_network.util.SharedPrefManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostFragment extends Fragment {

    private CardView postCard;
    private ImageView imgPreview;
    private ImageView imgPin , imgCamera;
    private AppCompatButton btnPost;
    private EditText etPost;
    private SharedPrefManager sharedPrefManager;

    private final int CAMERA_REQ_CODE = 3000;
    private final int GALLERY_REQ_CODE = 4000;
    Bitmap bitmap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_post, container, false);

        btnPost = view.findViewById(R.id.btnPost);
        etPost = view.findViewById(R.id.etPost);
        postCard = view.findViewById(R.id.postCard);
        imgPreview = (ImageView) view.findViewById(R.id.imgPreview);
        imgPin = view.findViewById(R.id.imgPin);
        imgCamera = view.findViewById(R.id.imgCamera);

        sharedPrefManager = new SharedPrefManager(getContext());
        imgPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage2();
            }
        });


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPost();
            }
        });
        
        return view;
    }

    private void registerPost() {
        int userId = sharedPrefManager.getUser().getId();
        String postText = etPost.getText().toString();
        String image = imageToString();

        if (postText.isEmpty()) {
            etPost.requestFocus();
            etPost.setError("Please enter your name");
            return;
        }

        if (image.equals("?")) {
//            Toast.makeText(getContext(), "Please upload image", Toast.LENGTH_SHORT).show();
            image = "";
        }

        Call<PostModel> call;
        if(image.isEmpty()){
           call = RetrofitClient.getInstance().getPostApi().insertPost(230, postText);

        }else {
            call = RetrofitClient.getInstance().getPostApi().insertPost(230, postText, image);
        }
        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                PostModel postModel = response.body();
                if(response.isSuccessful() && response.body() != null){
                    if(postModel.getError() == "000"){
                        Toast.makeText(getContext(), postModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), postModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Select way")
                .setMessage("Select camera or gallery from belo buttons")
                .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent iGallery = new Intent();
                        iGallery.setType("image/*");
                        iGallery.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(iGallery, GALLERY_REQ_CODE);
                    }
                })
                .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(iCamera, CAMERA_REQ_CODE);
                    }
                })
                .setNeutralButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });
        builder.show();

    }


    private void selectImage2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Select way")
                .setMessage("Select camera or gallery from belo buttons")
                .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(iCamera, CAMERA_REQ_CODE);
                    }
                })
                .setNeutralButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });
        builder.show();

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE && data != null) {

                Uri path = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap((getActivity()).getContentResolver(), path);
                    imgPreview.setImageBitmap(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (requestCode == CAMERA_REQ_CODE && data != null) {
                bitmap = (Bitmap) (data.getExtras().get("data"));
                imgPreview.setImageBitmap(bitmap);
            }
        }
    }

    //compress the image and decode it ...
    @SuppressLint("WrongThread")
    private String imageToString() {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imageByte = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(imageByte, Base64.DEFAULT);
        }
        return "?";
    }
}