package com.example.chhots.User_Profile;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chhots.R;
import com.google.android.gms.tasks.OnSuccessListener;
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

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class edit_profile extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView mimageProfile,change_userName;
    private EditText userProfile;


    private ImageView change_userAbout;
    private EditText userAbout;


    private TextView userEmail;

    private TextView userPhoneNo;

    private Uri mImageUri;


    private StorageReference mStorageRef;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    private StorageTask mUploadTask;



    public edit_profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit_profile, container, false);
        mimageProfile = view.findViewById(R.id.ProfileImage);
        change_userName = view.findViewById(R.id.change_userName);
        userProfile = view.findViewById(R.id.user_name_profile);

        userAbout = view.findViewById(R.id.user_decription_profile);
        userEmail = view.findViewById(R.id.user_email_profile);
        userPhoneNo = view.findViewById(R.id.user_phone_profile);

        change_userAbout = view.findViewById(R.id.change_userAbout);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("");
        mStorageRef = FirebaseStorage.getInstance().getReference();




        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        // retrieve data from firebase
        Query query = databaseReference.child("users").child(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("5869","AAchaa");

                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String image = ""+ds.child("mImageUrl").getValue();
                    Log.d("22231",image);
                    try{
                        if(image.equals(""))
                        {
                            Picasso.get().load(R.drawable.image).into(mimageProfile);
                        }
                        else {
                            Picasso.get().load(image).resize(50,50).into(mimageProfile);
                        }
                    }
                    catch (Exception e){

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        change_userAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child("users").child(user.getUid()).child("about").setValue(userAbout.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.drawer_layout, new userprofile(), "1");
                        fragmentTransaction.commit();
                        Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        change_userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("users").child(user.getUid()).child("name").setValue(userProfile.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.drawer_layout, new userprofile(), "1");
                                fragmentTransaction.commit();
                                Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        mimageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });


        return view;
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFile()
    {
        if(mImageUri!=null)
        {
        }

    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(mimageProfile);
        }
    }

    public void updateUser(final String userName,final String email,final String about,final String phone)
    {
         /*   final StorageReference fileReference = mStorageRef.child(user.getUid() + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    UserModel model = new UserModel(userName, email, about, phone, uri.toString());

                                    databaseReference.child("users").child(user.getUid()).setValue(model)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new userprofile(), "1");
                                                    fragmentTransaction.commit();
                                                    Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                        }
                    });*/
        UserModel model = new UserModel(userName, email, about, phone, mImageUri.toString());
        String uploadId = databaseReference.push().getKey();


    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d("222334",String.valueOf(user.getUid()));

        databaseReference.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("222333",String.valueOf(dataSnapshot.getChildrenCount()));
                if(dataSnapshot.getChildrenCount()!=0) {
                    UserModel model = dataSnapshot.getValue(UserModel.class);
                    userProfile.setText(model.getName());
                    userAbout.setText(model.getAbout());
                    userEmail.setText(model.getEmail());
                    userPhoneNo.setText(model.getPhone());
                    try {
                        if (model.getImage().equals("")) {
                            Picasso.get().load(R.drawable.image).into(mimageProfile);
                        } else {
                            Picasso.get().load(model.getImage()).resize(200, 200).into(mimageProfile);
                        }
                    } catch (Exception e) {
                    }
                }
                else
                {
                    userEmail.setText(user.getEmail());
                }
                Log.d("2323","success");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
