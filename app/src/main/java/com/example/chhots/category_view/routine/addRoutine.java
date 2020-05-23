package com.example.chhots.category_view.routine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chhots.R;
import com.example.chhots.UserInfoModel;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.view.View.GONE;
import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

public class addRoutine extends AppCompatActivity {

    private Button choose_video,upload_video,upload_routine;
    private EditText video_title,sequence_no,description,routine_Name;
    private TextView seqNo;
    private ImageView thumbnail;
    private RadioButton street,classical,breaking,krump,locking,popping;
    private Spinner spinner;
    private Uri videouri,mImageUri;
    private ProgressBar progress_seekbar1,progressBar2;
    private static final String TAG = "Upload_Routine";
    final String time = System.currentTimeMillis()+"";
    public StringBuffer category=new StringBuffer("00000000");
    String level;

    int flag=0;
    PopupWindow mPopupWindow;
    RelativeLayout relativeLayout;



    private FirebaseUser user;
    private DatabaseReference mDatabaseReference;
    private StorageReference storageReference;
    String userName,userImage;
    private TextView choose_category;


    //exoplayer implementation
    PlayerView playerView;
    SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    ImageView fullScreenButton;
    boolean fullScreen = false;

    private static final int PICK_IMAGE_REQUEST = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routine);


        init();
        fetchUserInfo();

        choose_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseVideo();
            }
        });

        upload_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload_video.setEnabled(false);
                upload_routine.setEnabled(false);

                uploadVideo();
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.level_list,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        choose_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseCategory();
            }
        });

        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        fullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullScreen();
            }
        });

        upload_routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadRoutine();
            }
        });


    }

    private void ChooseCategory()
    {
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.custom_category,null);


        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );


        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }
        Button submit = customView.findViewById(R.id.submit_category);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(relativeLayout, Gravity.CENTER,0,0);
        mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    mPopupWindow.dismiss();
                }
                return false;
            }
        });

        final RadioButton classical = customView.findViewById(R.id.routine_category_classical);
        final boolean classical1=false;
        classical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!classical1)
                {
                    category.setCharAt(1,'1');
                    classical.setBackgroundColor(Color.parseColor("#88111111"));
                    Toast.makeText(getApplicationContext(),"checkde",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    classical.setBackgroundColor(Color.parseColor("#0D111111"));
                    Toast.makeText(getApplicationContext(),"Unchecked",Toast.LENGTH_SHORT).show();
                    category.setCharAt(1,'0');
                }
            }
        });


        final RadioButton breaking = customView.findViewById(R.id.routine_category_breaking);
        final boolean breaking1=false;
        classical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!classical1)
                {
                    category.setCharAt(1,'1');
                    breaking.setBackgroundColor(Color.parseColor("#88111111"));
                    Toast.makeText(getApplicationContext(),"checkde",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    breaking.setBackgroundColor(Color.parseColor("#0D111111"));
                    Toast.makeText(getApplicationContext(),"Unchecked",Toast.LENGTH_SHORT).show();
                    category.setCharAt(1,'0');
                }
            }
        });
    }



    private void fetchUserInfo()
    {
        mDatabaseReference.child("UserInfo").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInfoModel model = dataSnapshot.getValue(UserInfoModel.class);
                userName = model.getUserName();
                userImage = model.getUserImageurl();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void uploadRoutine() {
        if(flag==1 && mImageUri !=null)
        {

            final String routineName = routine_Name.getText().toString();


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    level = adapterView.getItemAtPosition(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            final StorageReference reference = storageReference.child("ROUTINE_THUMBNAILS").child(time+"."+getfilterExt(mImageUri));
            reference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            RoutineThumbnailModel model = new RoutineThumbnailModel(routineName,userName,"0",level,time,uri.toString(),user.getUid(),category.toString());
                                            mDatabaseReference.child("ROUTINE_THUMBNAIL").child(time).setValue(model)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(getApplicationContext(), "Course Created", Toast.LENGTH_SHORT).show();
                                                            onBackPressed();
                                                        }
                                                    });
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
                            double progress = (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            progressBar2.setProgress((int)progress);
                        }
                    });
        }
        else
        {

        }
    }


    private void uploadVideo()
    {
        if(videouri!=null)
        {
            final String title = video_title.getText().toString();
            final String sequenceNo = sequence_no.getText().toString();
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(c);
            final StorageReference reference = storageReference.child("ROUTINEVIDEOS")
                    .child(time).child(sequenceNo+"."+title+getfilterExt(videouri));
            reference.putFile(videouri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String routineId = time;
                                        RoutineModel model = new RoutineModel(title,sequenceNo,user.getUid(),routineId,uri.toString());
                                        mDatabaseReference.child("ROUTINEVIDEOS").child(time).child(sequenceNo+title+getfilterExt(videouri)).setValue(model);
                                        flag=1;
                                        Toast.makeText(getApplicationContext(),"Upload Next Video",Toast.LENGTH_SHORT).show();
                                        progress_seekbar1.setProgress(0);
                                        upload_routine.setEnabled(true);
                                        upload_video.setEnabled(true);

                                    }
                                });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    upload_routine.setEnabled(true);
                    upload_video.setEnabled(true);
                    Toast.makeText(getApplicationContext(),"Upload Failed  " +e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progress_seekbar1.setProgress((int)progress);
                }
            });
        }
    }




    @SuppressLint("WrongViewCast")
    private void init() {
        choose_video = findViewById(R.id.choose_routine_video);
        upload_video =findViewById(R.id.upload_routine_video);
        upload_routine = findViewById(R.id.upload_routine);
        video_title = findViewById(R.id.routine_video_title);
        sequence_no = findViewById(R.id.routine_sequence_no);
        description = findViewById(R.id.routine_description);
        thumbnail = findViewById(R.id.choose_thumbnail);
        classical = findViewById(R.id.routine_category_classical);
        breaking = findViewById(R.id.routine_category_breaking);
        krump = findViewById(R.id.routine_category_krump);
        locking = findViewById(R.id.routine_category_locking);
        spinner = findViewById(R.id.routine_level_spinner);
        progress_seekbar1 = findViewById(R.id.progress_bar_upload_video);
        progressBar2 = findViewById(R.id.progress_bar_upload_routine);
        routine_Name = findViewById(R.id.routine_name);
        choose_category = findViewById(R.id.choose_category);
        relativeLayout = findViewById(R.id.routine_course);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();


        playerView = findViewById(R.id.routine_course_video);
        fullScreenButton = playerView.findViewById(R.id.exo_fullscreen_icon);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        playerView.setPadding(5,0,5,0);


    }


    private void FullScreen() {
        if(fullScreen)
        {
            fullScreenButton.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_fullscreen_black_24dp));
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);

            addRoutine.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            if (getSupportActionBar()!=null)
                getSupportActionBar().show();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)playerView.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = (int)( 330 * getContext().getResources().getDisplayMetrics().density);
            playerView.setLayoutParams(params);
            fullScreen = false;
        }
        else{
            fullScreenButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fullscreen_black_24dp));

            addRoutine.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
            );

            if(getSupportActionBar() != null){
                getSupportActionBar().hide();
            }

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
            addRoutine.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.setMargins(0,0,0,0);
            params.height = params.MATCH_PARENT;

            playerView.setLayoutParams(params);

            fullScreen = true;
        }
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
        if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null)
        {
            videouri = data.getData();
            initializePlayer();
        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(thumbnail);
        }

    }

    private void initializePlayer() {

        player = ExoPlayerFactory.newSimpleInstance(getApplicationContext());
        playerView.setPlayer(player);

        MediaSource mediaSource = buildMediaSource(videouri);

        player.setPlayWhenReady(playWhenReady);
        //  player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, false, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getApplicationContext(), "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private String getfilterExt(Uri videoUri)
    {
        ContentResolver contentResolver =addRoutine.this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(videoUri));
    }


    @Override
    public void onBackPressed() {
     //   player.release();
        super.onBackPressed();
    }


}
