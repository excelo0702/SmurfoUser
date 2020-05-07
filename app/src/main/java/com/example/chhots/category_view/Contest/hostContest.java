package com.example.chhots.category_view.Contest;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chhots.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class hostContest extends Fragment {


    public hostContest() {
        // Required empty public constructor
    }

    EditText contest_info;
    Button upload;
    ImageView contest_image;
    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private Uri mImageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_host_contest, container, false);
        init(view);


        contest_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadContest();
            }
        });

        return view;
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(contest_image);
        }
    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void uploadContest()
    {
        final String info = contest_info.getText().toString();
        if(mImageUri!=null &&info!=null) {
            final String time = System.currentTimeMillis() + "";
            final StorageReference filereference = storageReference.child("Contest").child(time + "." + getFileExtension(mImageUri));
            filereference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filereference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    HostModel model = new HostModel(info, uri.toString(),time);
                                    databaseReference.child("contest").child(time).setValue(model)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(getContext(), "Contest Hosted", Toast.LENGTH_SHORT).show();
                                                    getFragmentManager().beginTransaction().replace(R.id.drawer_layout, new contest()).commit();
                                                }
                                            });
                                }
                            });
                        }
                    });
        }
    }

    private void init(View v) {
        contest_info = v.findViewById(R.id.ContestInfo);
        upload = v.findViewById(R.id.contest_upload);
        contest_image = v.findViewById(R.id.contest_image);
        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("");
        storageReference = FirebaseStorage.getInstance().getReference();
    }

}
