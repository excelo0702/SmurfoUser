package com.example.chhots.category_view.Contest;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.chhots.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class form_contest extends Fragment {


    public form_contest() {
        // Required empty public constructor
    }


    private TextView register,info;
    private String contestId,imageUrl;
    private EditText userName,userEmail;
    private VideoView video;
    private ImageView image_1,image_2;
    private FirebaseUser user;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private MediaController mediaController;
    private Button choose_video;
    private Uri videouri;
    private ProgressBar progress_seekBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form_contest, container, false);
        Bundle bundle = this.getArguments();
        contestId= bundle.getString("contestId");
        imageUrl = bundle.getString("imageUrl");
        init(view);
        userName.setText(user.getUid());
        userEmail.setText(user.getEmail());
        Picasso.get().load(Uri.parse(imageUrl)).into(image_1);
        Picasso.get().load(Uri.parse(imageUrl)).into(image_2);


        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mP) {
                mP.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {

                        mediaController = new MediaController(getContext());
                        video.setMediaController(mediaController);
                        mediaController.setAnchorView(video);
                    }
                });
            }
        });
        video.start();

        choose_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseVideo();
            }
        });


        register = (TextView)view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register.setEnabled(false);
                choose_video.setEnabled(false);
                RegisterUser();
                Toast.makeText(getContext(),"Start uploading",Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }


    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void chooseVideo(){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        getActivity().startActivityForResult(intent,1);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("2323232","111111");
        if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null)
        {
            videouri = data.getData();
            video.setVideoURI(videouri);

        }
    }
    private String getfilterExt(Uri videoUri)
    {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(videoUri));
    }


    private void init(View v)
    {
        info = v.findViewById(R.id.contest_description);
        userName = v.findViewById(R.id.userName);
        userEmail = v.findViewById(R.id.userEmail);
        image_1 = v.findViewById(R.id.image_1_contest);
        image_2 = v.findViewById(R.id.image_2_contest);
        video = v.findViewById(R.id.form_contest_videoView);
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("");
        choose_video = v.findViewById(R.id.choose_video_form);
        progress_seekBar = v.findViewById(R.id.progress_bar_upload_contest_video);
    }

    private void RegisterUser()
    {
        if(videouri!=null&&userEmail!=null && userName!=null)
        {
            final String user_name = userName.getText().toString();
            final String user_email = userEmail.getText().toString();
            final StorageReference reference = storageReference.child("ContestVideos").child(contestId).child(user.getUid()+getfilterExt(videouri));
            reference.putFile(videouri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            FormContestModel model = new FormContestModel(user_name,contestId,uri.toString());
                                            databaseReference.child("ContestVideos").child(contestId).child(user.getUid()).setValue(model);
                                            Toast.makeText(getContext(),"Uploaded",Toast.LENGTH_LONG).show();

                                            register.setEnabled(true);
                                            choose_video.setEnabled(true);
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getBytesTransferred();
                            progress_seekBar.setProgress((int)progress);
                        }
                    });

            register.setEnabled(true);
            choose_video.setEnabled(true);
        }
        register.setEnabled(true);
        choose_video.setEnabled(true);


    }



}
