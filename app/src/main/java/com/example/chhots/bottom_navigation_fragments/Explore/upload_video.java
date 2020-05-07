package com.example.chhots.bottom_navigation_fragments.Explore;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.chhots.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class upload_video extends Fragment {


    private Button choosebtn;
    private Button uploadBtn;
    private ImageView thumb_nail;
    private Button DemotwoBtn;
    private EditText video_title;
    private EditText choose_category;
    private VideoView videoView;
    private Uri videouri;
    private MediaController mediaController;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ProgressBar progressBar,progress_seekBar;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private static final String TAG = "Upload_Video";

    public upload_video() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_video, container, false);


        choosebtn = (Button)view.findViewById(R.id.chhose_btn);
        uploadBtn = (Button)view.findViewById(R.id.upload_btn);
        DemotwoBtn = (Button)view.findViewById(R.id.demo2_btn);
        video_title = view.findViewById(R.id.video_title);
        choose_category = view.findViewById(R.id.choose_category);
        thumb_nail = view.findViewById(R.id.thumbnail);

        videoView = view.findViewById(R.id.video_view);
        progress_seekBar = view.findViewById(R.id.progress_bar);
        progressBar = view.findViewById(R.id.progress_Bar);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();



        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mP) {
                mP.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {

                        mediaController = new MediaController(getContext());
                        videoView.setMediaController(mediaController);
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });


        storageReference = FirebaseStorage.getInstance().getReference().child("videos");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("videos");

        videoView.start();


        choosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseVideo();

            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(user==null)
                {
                    Toast.makeText(getContext(),"user cant be null",Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadVideo();
                    uploadBtn.setEnabled(false);
                    choosebtn.setEnabled(false);
                }
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
            videoView.setVideoURI(videouri);

        }
    }
    private String getfilterExt(Uri videoUri)
    {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(videoUri));
    }

    private void uploadVideo()
    {
        progressBar.setVisibility(View.VISIBLE);
        if(videouri!=null)
        {
            //   Toast.makeText(getApplicationContext(),"upl88",Toast.LENGTH_SHORT).show();

            final String title = video_title.getText().toString();
            final String category = choose_category.getText().toString();
            if(title.equals(""))
            {
                Toast.makeText(getContext(),"Title cant be null",Toast.LENGTH_SHORT).show();
                return;
            }
            if(category.equals(""))
            {
                Toast.makeText(getContext(),"Category cant be null",Toast.LENGTH_SHORT).show();
                return;
            }
            final String upload = System.currentTimeMillis()+"";
            Log.d("222222","2222222");
            final StorageReference reference = storageReference.child(user.getUid()).child(upload+getfilterExt(videouri));
            reference.putFile(videouri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    progressBar.setVisibility(View.INVISIBLE);
                                    VideoModel model = new VideoModel(user.getUid(),title,category,upload,"No Comment yet",uri.toString(),0,0,0);
                                    databaseReference.child(upload).setValue(model);
                                    Toast.makeText(getContext(),"uploaded",Toast.LENGTH_SHORT).show();
                                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);

                            Toast.makeText(getContext(),e.getMessage()+"Failed",Toast.LENGTH_SHORT).show();

                        }
                    });

            uploadBtn.setEnabled(true);
            choosebtn.setEnabled(true);
        }
    }
}
