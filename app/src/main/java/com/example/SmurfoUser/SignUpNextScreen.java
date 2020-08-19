package com.example.SmurfoUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.SmurfoUser.ui.Category.category;
import com.example.SmurfoUser.ui.Dashboard.PointModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class SignUpNextScreen extends AppCompatActivity {

    private ImageView userImage_signup;
    private EditText user_name_signup,user_profession_signup, phoneE;
    private Spinner user_dancer_level;
    private RadioButton r1,r2,r3,r4,r5,r6;
    private Button finalsignUp;
    private FirebaseAuth auth;
    private DatabaseReference mDatabaseReference;

    private String Semail,Spassword,profession,level,name,user_name;
    private static final int PICK_IMAGE_REQUEST = 2;
    private Uri mImageUri;
    private StorageReference storageReference;
    StringBuffer category=new StringBuffer("00000000000000000000");
    Spinner spinner;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_next_screen);

        init();
        Intent intent = getIntent();
        Semail = intent.getStringExtra("email");
        Spassword = intent.getStringExtra("password");


        userImage_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.level_list,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        finalsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalsignUp.setEnabled(false);
                profession = user_profession_signup.getText().toString();
                user_dancer_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        level = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        level = "Beignner";
                    }
                });

                if(mImageUri==null) {
                    Toast.makeText(getApplicationContext(), "Choose Image", Toast.LENGTH_SHORT).show();
                    finalsignUp.setEnabled(true);
                    return;
                }
                name = user_name_signup.getText().toString();
                Log.d("ggggg",name+" m m  "+profession);
                if (user_name_signup==null|| name.equals("")) {
                    Toast.makeText(getApplicationContext(), "UserName cant be null", Toast.LENGTH_SHORT).show();
                    finalsignUp.setEnabled(true);
                    return;
                }
                if (phoneE.getText().toString().equals("") || phoneE.getText().length()!=10) {
                    Toast.makeText(getApplicationContext(), "Phone Number is not valid", Toast.LENGTH_SHORT).show();
                    finalsignUp.setEnabled(true);
                    return;
                }
                if (user_profession_signup==null || profession.equals("")) {
                    Toast.makeText(getApplicationContext(), "Profession cant be null", Toast.LENGTH_SHORT).show();
                    finalsignUp.setEnabled(true);
                    return;
                }
                loadingDialog.startLoadingDialog();
                auth.createUserWithEmailAndPassword(Semail, Spassword)
                        .addOnCompleteListener(SignUpNextScreen.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    loadingDialog.DismissDialog();
                                    Toast.makeText(SignUpNextScreen.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    final StorageReference reference = storageReference.child("UserProfileImage").child(auth.getCurrentUser().getUid()+getfilterExt(mImageUri));
                                    reference.putFile(mImageUri)
                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    reference.getDownloadUrl()
                                                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {

                                                                    UserInfoModel model = new UserInfoModel(auth.getCurrentUser().getUid(), Semail, profession, level, uri.toString(),phoneE.getText().toString().trim(),name,"","beginner",String.valueOf(category),"");
                                                                    mDatabaseReference.child("UserInfo").child(auth.getCurrentUser().getUid()).setValue(model);

                                                                    PointModel popo = new PointModel(auth.getCurrentUser().getUid(),name,40);
                                                                    mDatabaseReference.child("UsersPoint").child("weekly").child(auth.getCurrentUser().getUid()).setValue(popo);
                                                                    mDatabaseReference.child("UsersPoint").child("OverAll").child(auth.getCurrentUser().getUid()).setValue(popo);

                                                                    auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            Intent intent = new Intent(SignUpNextScreen.this,Verification_page.class);
                                                                            startActivity(intent);
                                                                            Toast.makeText(getApplicationContext(),"Verification Email Sent",Toast.LENGTH_SHORT);
                                                                        }
                                                                    });


                                                                    new Handler().postDelayed(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            loadingDialog.DismissDialog();
                                                                        }
                                                                    },1000);

                                                                }
                                                            });
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    loadingDialog.DismissDialog();

                                                }
                                            });
                                }
                            }
                        });


            }




        });
    }

        @Override
        protected void onResume() {
            super.onResume();
        }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.r1:
                if (checked) {
                    category.setCharAt(0, '1');
                    category.setCharAt(1, '1');
                    category.setCharAt(2, '1');
                }
                else
                {
                    category.setCharAt(0, '0');
                    category.setCharAt(1, '0');
                    category.setCharAt(2, '0');
                    break;
                }
            case R.id.r2:
                if (checked) {
                    category.setCharAt(3, '1');
                    category.setCharAt(4, '1');
                    category.setCharAt(5, '1');
                }
                else
                {
                    category.setCharAt(3, '0');
                    category.setCharAt(4, '0');
                    category.setCharAt(5, '0');
                    break;
                }
            case R.id.r3:
                if (checked) {
                    category.setCharAt(6, '1');
                    category.setCharAt(7, '1');
                    category.setCharAt(8, '1');
                }
                else
                {
                    category.setCharAt(6, '0');
                    category.setCharAt(7, '0');
                    category.setCharAt(8, '0');
                    break;
                }
            case R.id.r4:
                if (checked) {
                    category.setCharAt(9, '1');
                    category.setCharAt(10, '1');
                    category.setCharAt(11, '1');
                }
                else
                {
                    category.setCharAt(9, '0');
                    category.setCharAt(10, '0');
                    category.setCharAt(11, '0');
                    break;
                }
            case R.id.r5:
                if (checked) {
                    category.setCharAt(12, '1');
                    category.setCharAt(13, '1');
                    category.setCharAt(14, '1');
                }
                else
                {
                    category.setCharAt(12, '0');
                    category.setCharAt(13, '0');
                    category.setCharAt(14, '0');
                    break;
                }
            case R.id.r6:
                if (checked) {
                    category.setCharAt(15, '1');
                    category.setCharAt(16, '1');
                    category.setCharAt(17, '1');
                }
                else
                {
                    category.setCharAt(15, '0');
                    category.setCharAt(16, '0');
                    category.setCharAt(17, '0');
                    break;
                }

        }
    }


    private String getfilterExt(Uri videoUri)
    {
        ContentResolver contentResolver = SignUpNextScreen.this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(videoUri));
    }

    private void init()
    {
        userImage_signup = findViewById(R.id.userImage_signup);
        user_name_signup = findViewById(R.id.user_name_signup);
        user_profession_signup = findViewById(R.id.user_profession_signup);
        phoneE = findViewById(R.id.user_phone);
        user_dancer_level = findViewById(R.id.user_dancer_level);
        spinner =findViewById(R.id.user_dancer_level);
        loadingDialog = new LoadingDialog(this);
        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);
        r3 = findViewById(R.id.r3);
        r4 = findViewById(R.id.r4);
        r5 = findViewById(R.id.r5);
        r6 = findViewById(R.id.r6);
        finalsignUp = findViewById(R.id.signup_next_submit);
        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }



    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("2323232","111111");

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).placeholder(R.mipmap.ic_logo).into(userImage_signup);
        }

    }




}
