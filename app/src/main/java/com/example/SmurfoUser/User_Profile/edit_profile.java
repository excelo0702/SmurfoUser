package com.example.SmurfoUser.User_Profile;


import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SmurfoUser.BuildConfig;
import com.example.SmurfoUser.Login;
import com.example.SmurfoUser.MainActivity;
import com.example.SmurfoUser.R;
import com.example.SmurfoUser.Signup;
import com.example.SmurfoUser.UserInfoModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class edit_profile extends Fragment {

    private ImageView userImage_signup;
    private EditText user_name_signup,user_profession_signup,phoneE;
    private Spinner user_dancer_level;
    private RadioButton r1,r2,r3,r4,r5,r6;
    private Button finalsignUp;
    ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference mDatabaseReference;

    private String Semail,Spassword,profession,level,name,user_name;
    private int R1=0,R2=0,R3=0,R4=0,R5=0,R6=0;
    private static final int PICK_IMAGE_REQUEST = 2;
    private Uri mImageUri;
    private StorageReference storageReference;

    Spinner spinner;
    StringBuffer category=new StringBuffer("00000000000000000000");


    private StorageReference mStorageRef;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    UserInfoModel model;
    private MenuItem item_edit,item_save;


    public edit_profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_edit_profile, container, false);

        init(view);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();



        fetchUserInfo();

        Visibility(false);

        userImage_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        finalsignUp.setVisibility(View.GONE);
        finalsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    private void UpdateProfile() {
        if(mImageUri==null)
        {

            UserInfoModel mode = new UserInfoModel(auth.getCurrentUser().getUid(), model.getUserEmail(), user_profession_signup.getText().toString(), level, model.getUserImageurl(),phoneE.getText().toString(),user_name_signup.getText().toString(),model.getPoints(),model.getBadge(),String.valueOf(category),"" );
            mDatabaseReference.child("UserInfo").child(auth.getCurrentUser().getUid()).setValue(mode);
            Toast.makeText(getContext(),"Updated",Toast.LENGTH_LONG).show();
        }
        else
        {
            final StorageReference reference = storageReference.child("UserProfileImage").child(auth.getCurrentUser().getUid()+getfilterExt(mImageUri));
            reference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Visibility(false);
                                            UserInfoModel mode = new UserInfoModel(auth.getCurrentUser().getUid(), model.getUserEmail(), user_profession_signup.getText().toString(), level, uri.toString(),phoneE.getText().toString(),user_name_signup.getText().toString(),model.getPoints(),model.getBadge(),String.valueOf(category),"" );
                                            mDatabaseReference.child("InstructorInfo").child(auth.getCurrentUser().getUid()).setValue(mode);
                                            Toast.makeText(getContext(),"Updated",Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    });
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


    private void fetchUserInfo()
    {
        databaseReference.child("UserInfo").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                model = dataSnapshot.getValue(UserInfoModel.class);
                user_name_signup.setText( model.getUserName());
                Picasso.get().load(Uri.parse(model.getUserImageurl())).into(userImage_signup);
                user_profession_signup.setText(model.getUserProfession());
                phoneE.setText(model.getPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        String interest = model.getInterest();
        //      String temp = interest.substring(0,3);
        //put check on radio Button
    }

    private void Visibility(boolean value)
    {
        userImage_signup.setEnabled(value);
        user_name_signup.setEnabled(value);
        user_profession_signup.setEnabled(value);
        phoneE.setEnabled(value);
        r1.setEnabled(value);
        r2.setEnabled(value);
        r3.setEnabled(value);
        r4.setEnabled(value);
        r5.setEnabled(value);
        r6.setEnabled(value);
        finalsignUp.setEnabled(value);
    }

    private void init(View view) {
        userImage_signup = view.findViewById(R.id.userImage_signupp);
        user_name_signup = view.findViewById(R.id.user_name_signupp);
        user_profession_signup = view.findViewById(R.id.user_profession_signupp);
        phoneE = view.findViewById(R.id.user_phonee);
        r1 = view.findViewById(R.id.r11);
        r2 = view.findViewById(R.id.r22);
        r3 = view.findViewById(R.id.r33);
        r4 = view.findViewById(R.id.r44);
        r5 = view.findViewById(R.id.r55);
        r6 = view.findViewById(R.id.r66);
        finalsignUp = view.findViewById(R.id.signup_next_submitt);
        progressBar = view.findViewById(R.id.progressBar_signup_nextt);
        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }


    private String getfilterExt(Uri videoUri)
    {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(videoUri));
    }


    public void onRadioButtonClick(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.r11:
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
            case R.id.r22:
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
            case R.id.r33:
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
            case R.id.r44:
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
            case R.id.r55:
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
            case R.id.r66:
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.edit_menu,menu);
        item_edit = menu.findItem(R.id.edit_profile_pencil);
        item_save = menu.findItem(R.id.edit_save_pencil);
        item_save.setVisible(false);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        switch (item.getItemId())
        {
            case R.id.edit_profile_pencil:
                item_save.setVisible(true);
                item_edit.setVisible(false);
                Visibility(true);
                break;

            case R.id.edit_save_pencil:
                item_save.setVisible(false);
                item_edit.setVisible(true);
                UpdateProfile();
                break;


            case R.id.logout:
                Toast.makeText(getContext(),"Logout",Toast.LENGTH_LONG).show();
                auth.signOut();
                Toast.makeText(getContext(), "logged out", Toast.LENGTH_SHORT).show();


                if(auth.getCurrentUser()==null)
                {
                    startActivity(new Intent(getActivity(), Login.class));
                }

// this listener will be called when there is change in firebase user session
                FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user == null) {
                            // user auth state is changed - user is null
                            // launch login activity
                            startActivity(new Intent(getActivity(), Login.class));
                        }
                    }
                };

                break;

            case R.id.delete_Account:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Toast.makeText(getContext(), "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getActivity(), Signup.class));

                                    } else {
                                        Toast.makeText(getContext(), "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                break;

            case R.id.invite:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Smurfo");
                    String shareMessage= "\nHey Friend CheckOut This new cool app its called smurfo.\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
                break;
        }
        return true;
    }



}
