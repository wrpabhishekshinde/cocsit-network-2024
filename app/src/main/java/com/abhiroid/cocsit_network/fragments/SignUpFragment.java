package com.abhiroid.cocsit_network.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.abhiroid.cocsit_network.R;
import com.abhiroid.cocsit_network.apis.UsersAPI;
import com.abhiroid.cocsit_network.model_response.CreateUser;
import com.abhiroid.cocsit_network.util.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment  implements View.OnClickListener {

    private final int CAMERA_REQ_CODE = 1000;
    private final int GALLERY_REQ_CODE = 2000;
    Bitmap bitmap;

    ShapeableImageView profileImg;
    FloatingActionButton btnAddImg;
    EditText etName, etSurname, etDOB, etMobile, etEmail;
    ImageView btnImgCalender, btnMaleImg, btnFemaleImg;
    Spinner classSpin, divSpin;
    MaterialButton btnProceed;

    ArrayList<String> classList = new ArrayList<String>();
    ArrayList<String> divList = new ArrayList<String>();

    String classs = "";
    String div = "";
    String gender = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        etDOB = view.findViewById(R.id.etDOB);
        etMobile = view.findViewById(R.id.etMobile);
        etEmail = view.findViewById(R.id.etEmail);
        btnImgCalender = view.findViewById(R.id.btnImgCalender);
        btnMaleImg = view.findViewById(R.id.btnMaleImg);
        btnFemaleImg = view.findViewById(R.id.btnFemaleImg);
        classSpin = view.findViewById(R.id.classSpin);
        divSpin = view.findViewById(R.id.divSpin);


        profileImg = view.findViewById(R.id.profileImg);
        btnAddImg = view.findViewById(R.id.btnAddImg);
        etName = view.findViewById(R.id.etName);
        etSurname = view.findViewById(R.id.etSurname);
        btnProceed = view.findViewById(R.id.btnProceed);

        classList.add("Select Class");
        classList.add("BCA");
        classList.add("BCS");
        classList.add("BCOM");
        classList.add("BBA");
        classList.add("BVOC");
        divList.add("Select Div");
        divList.add("A");
        divList.add("B");
        divList.add("C");
        divList.add("D");

        //for spinner
        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, classList);
        classSpin.setAdapter(classAdapter);
        ArrayAdapter<String> divAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, divList);
        divSpin.setAdapter(divAdapter);

        //for picking date
        btnImgCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dayOfMonth = 0, month = 0, year = 0;
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        String date1 = date + "/" + (month + 1) + "/" + year;
                        etDOB.setText(date1);
                    }
                }, dayOfMonth, month, year);
                dialog.show();
            }
        });


        //for selecting class...
        classSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                classs = classList.get(position).toString();
                Toast.makeText(getContext(), classs, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getContext(), "Select class", Toast.LENGTH_SHORT).show();
            }
        });
        //for selecting division..
        divSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                div = divList.get(position);
                Toast.makeText(getContext(), div, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getContext(), "Select division", Toast.LENGTH_SHORT).show();
            }
        });

        //for selecting gender
        btnMaleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "male";
                Toast.makeText(getContext(), "Male", Toast.LENGTH_SHORT).show();
            }
        });

        btnFemaleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "female";
                Toast.makeText(getContext(), "Female", Toast.LENGTH_SHORT).show();
            }
        });

        btnAddImg.setOnClickListener(this);
        btnProceed.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnAddImg) {
            selectImage();
        } else if (id == R.id.btnProceed) {

            registerUser();
        } else {
            Toast.makeText(getContext(), "In Development", Toast.LENGTH_SHORT).show();
        }
    }


    private void registerUser() {

        String userName = etName.getText().toString();
        String userSurname = etSurname.getText().toString();
        String dob = etDOB.getText().toString();
        String mobile = etMobile.getText().toString();
        String email = etEmail.getText().toString();


        if (userName.isEmpty()) {
            etName.requestFocus();
            etName.setError("Please enter your name");
            return;
        }
        if (userSurname.isEmpty()) {
            etSurname.requestFocus();
            etSurname.setError("Please enter your surname");
            return;
        }
        if (dob.isEmpty()) {
            etDOB.requestFocus();
            etDOB.setError("Please enter your birth date");
            return;
        }
        if (mobile.isEmpty()) {
            etMobile.requestFocus();
            etMobile.setError("Please enter your mobile");
            return;
        }
        if (!Patterns.PHONE.matcher(mobile).matches()) {
            etMobile.requestFocus();
            etMobile.setError("Please enter valid mobile number");
            return;
        }
        if (email.isEmpty()) {
            etEmail.requestFocus();
            etEmail.setError("Please enter email");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.requestFocus();
            etEmail.setError("Please enter valid email address");
            return;
        }
        if (gender.isEmpty()) {
            btnMaleImg.requestFocus();
            btnFemaleImg.requestFocus();
            Toast.makeText(getContext(), "Choose gender", Toast.LENGTH_SHORT).show();
            return;
        }
        if (classs.isEmpty()) {
            classSpin.requestFocus();
            Toast.makeText(getContext(), "Choose class", Toast.LENGTH_SHORT).show();
            return;
        }
        if (div.isEmpty()) {
            divSpin.requestFocus();
            Toast.makeText(getContext(), "Choose division", Toast.LENGTH_SHORT).show();
            return;
        }

        //register user..

        String image = imageToString();
        if (image.isEmpty()) {
            Toast.makeText(getContext(), "Please upload image", Toast.LENGTH_SHORT).show();
        } else {
            String title = etName.getText().toString().trim();
            String title2 = title.replaceAll("\\s+", "");

            UsersAPI usersAPI = RetrofitClient.getInstance().getUserApi();
            Call<CreateUser> call = usersAPI.createUser(title2, image, userName, userSurname, email);

            call.enqueue(new Callback<CreateUser>() {
                @Override
                public void onResponse(Call<CreateUser> call, Response<CreateUser> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        CreateUser createUser = response.body();

                        if (createUser.getError().equals("000")) {

                            Toast.makeText(getContext(), createUser.getMessage(), Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getContext(), createUser.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } else {

                        Toast.makeText(getContext(), "Upload Failed", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<CreateUser> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE && data != null) {

                Uri path = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap((getActivity()).getContentResolver(), path);
                    profileImg.setImageBitmap(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (requestCode == CAMERA_REQ_CODE && data != null) {
                bitmap = (Bitmap) (data.getExtras().get("data"));
                profileImg.setImageBitmap(bitmap);
            }
        }
    }

    //compress the image and decode it ...
    private String imageToString() {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imageByte = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(imageByte, Base64.DEFAULT);
        }
        return "";
    }

}