package com.example.chhots.category_view.Courses;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.chhots.R;
import com.example.chhots.bottom_navigation_fragments.Explore.VideoModel;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class upload_course extends AppCompatActivity {


    private Button choosebtn;
    private Button uploadBtn;
    private Button Done;
    private EditText video_title;
    private EditText video_sequence;
    private VideoView videoView;
    private Uri videouri;
    private MediaController mediaController;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private FirebaseUser user;
    final String time = System.currentTimeMillis()+"";

    private static final String TAG = "Upload_Course";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_course);

        init();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mP) {
                mP.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {

                        mediaController = new MediaController(getApplicationContext());
                        videoView.setMediaController(mediaController);
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });

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
                choosebtn.setEnabled(false);
                uploadBtn.setEnabled(false);
                uploadVideo();
            }
        });

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Course Uploaded",Toast.LENGTH_SHORT).show();
        super.onBackPressed();
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
        startActivityForResult(intent,1);
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
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(videoUri));
    }


    private void uploadVideo()
    {
        if(videouri!=null && video_sequence!=null && video_title!=null)
        {
            final String title = video_title.getText().toString();
            final String sequence = video_sequence.getText().toString();
            final String time2 = System.currentTimeMillis()+"";
            final StorageReference reference = storageReference.child("Course").child(time+"courseName").child(sequence+"."+title+getfilterExt(videouri));
            reference.putFile(videouri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String courseId = time;
                                            UploadCourseModel model = new UploadCourseModel(time,user.getUid(),"courseName",videouri.toString());
                                            databaseReference.child("Courses").child(courseId).child(sequence+title).setValue(model);
                                            VideoModel video_model = new VideoModel(user.getUid(),title,"category",time,"No Comment Yet",uri.toString(),0,0,0);
                                            databaseReference.child("videos").child(time2).setValue(video_model);
                                            Toast.makeText(getApplicationContext(),"uploaded",Toast.LENGTH_SHORT).show();

                                        }
                                    });
                        }
                    });
        }
        uploadBtn.setEnabled(true);
        choosebtn.setEnabled(true);
    }



    private void init() {
        videoView = findViewById(R.id.video_course);
        choosebtn = findViewById(R.id.choose_video_course);
        video_title = findViewById(R.id.video_title_course);
        video_sequence =findViewById(R.id.video_sequence_course);
        uploadBtn = findViewById(R.id.upload_video_course);
        Done = findViewById(R.id.done);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

    }
}
