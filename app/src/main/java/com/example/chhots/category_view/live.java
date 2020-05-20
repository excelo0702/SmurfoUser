package com.example.chhots.category_view;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chhots.Login;
import com.example.chhots.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class live extends Fragment {


    public live() {
        // Required empty public constructor
    }

    FirebaseAuth auth;

    String[] name = {"tanish" ,"tanish" ,"tanish" ,"tanish" ,"tanish" ,"tanish" , "mommy"};
    String[] danceform = {"tanish" ,"tanish" ,"tanish" ,"tanish" ,"tanish" ,"tanish" , "mommy"};;
    int[] imageId = {
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image
    };

    ListView listview;
    Button go_live;
    String link;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_live, container, false);

        auth = FirebaseAuth.getInstance();
        listview = (ListView)view.findViewById(R.id.list_live_view);
        go_live = (Button)view.findViewById(R.id.live_button);
        go_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(auth.getCurrentUser()==null)
                {
                    Toast.makeText(getContext(),"You are not login",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), Login.class);
                    startActivity(intent);
                }

                validateMobileLiveIntent(getContext());
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        alertDialog();
                    }
                }, 200);
            }
        });

        Myadapter myadapter = new Myadapter(getActivity(),name,danceform,imageId);

        listview.setAdapter(myadapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                if(auth.getCurrentUser()==null)
                {
                    Toast.makeText(getContext(),"You are not login",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), Login.class);
                    startActivity(intent);
                }

                Toast.makeText(getContext(),"Go live",Toast.LENGTH_SHORT).show();
                //     validateMobileLiveIntent(getContext());
                String id = "l_NIgnb9J2g";
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/watch?v="+id));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);
            }
        });
        return view;
    }

    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(getContext());
        dialog.setMessage("Please Select any option");
        dialog.setTitle("Dialog Box");

        final EditText input = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        dialog.setView(input);

        dialog.setPositiveButton("Submit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        link = input.getText().toString();
                        Toast.makeText(getContext(),link,Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    private boolean canResolveMobileLiveIntent(Context context) {
        Intent intent = new Intent("com.google.android.youtube.intent.action.CREATE_LIVE_STREAM")
                .setPackage("com.google.android.youtube");
        PackageManager pm = context.getPackageManager();
        List resolveInfo =
                pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo != null && !resolveInfo.isEmpty();
    }
    private void validateMobileLiveIntent(Context context) {
        if (canResolveMobileLiveIntent(context)) {
            // Launch the live stream Activity
            Intent intent = createMobileLiveIntent(context,"huhu");
            startMobileLive(context);

        } else {
            // Prompt user to install or upgrade the YouTube app
        }
    }
    private Intent createMobileLiveIntent(Context context, String description) {
        Intent intent = new Intent("com.google.android.youtube.intent.action.CREATE_LIVE_STREAM")
                .setPackage("com.google.android.youtube");
        Uri referrer = new Uri.Builder()
                .scheme("android-app")
                .appendPath(context.getPackageName())
                .build();

        intent.putExtra(Intent.EXTRA_REFERRER, referrer);
        if (!TextUtils.isEmpty(description)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, description);
        }
        return intent;
    }


    private void startMobileLive(Context context) {
        Intent mobileLiveIntent = createMobileLiveIntent(context, "Streaming via ...");
        startActivity(mobileLiveIntent);
    }



    class Myadapter extends ArrayAdapter<String> {
        Context context;
        String[] name;
        String[] description;
        int imgs[];


        public Myadapter(Context context, String[] name, String[] description, int[] imgs) {
            super(context,R.layout.raw_live_item,R.id.name_live,name);
            this.context = context;
            this.name = name;
            this.description = description;
            this.imgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.raw_live_item,parent,false);
            ImageView imageView = row.findViewById(R.id.imageLive);
            TextView title = row.findViewById(R.id.name_live);
            TextView desc = row.findViewById(R.id.description_live);

            imageView.setImageResource(imgs[position]);
            title.setText(name[position]);
            desc.setText(description[position]);

            return row;
        }
    }


}