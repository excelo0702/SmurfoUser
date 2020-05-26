package com.example.SmurfoUser.ui.Setting;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.SmurfoUser.BuildConfig;
import com.example.SmurfoUser.MainActivity;
import com.example.SmurfoUser.R;
import com.example.SmurfoUser.User_Profile.user_account;
import com.example.SmurfoUser.onBackPressed;
import com.example.SmurfoUser.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class setting extends Fragment implements onBackPressed {


    public setting() {
        // Required empty public constructor
    }

    LinearLayout invite;
    FirebaseUser user;
    TextView user_name,editProfileLink;
    FirebaseAuth firebaseAuth;
    ImageView imageView,call,whatsapp,mail;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        user_name = (TextView)view.findViewById(R.id.user_name_setting);
        imageView = view.findViewById(R.id.imageView_setting);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        call=view.findViewById(R.id.call);
        whatsapp=view.findViewById(R.id.whatsapp);
        invite=view.findViewById(R.id.invite);
        mail=view.findViewById(R.id.mail);
        LinearLayout linearLayout =view.findViewById(R.id.user_account_details);
        editProfileLink = view.findViewById(R.id.edit_profile_link);

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent callintent= new Intent(Intent.ACTION_DIAL);
                callintent.setData(Uri.parse("tel:7985025413"));
                startActivity(callintent);


            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mailintent = new Intent(Intent.ACTION_SEND);
                mailintent.setType("*/*");
                mailintent.putExtra(Intent.EXTRA_EMAIL, "gtyujhg@gmjui.com");
                mailintent.putExtra(Intent.EXTRA_SUBJECT, "hello nice to see you , hope you are well");
                startActivity(mailintent);


            }
        });

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("smsto:" + "+917985025413");
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO,uri );
                sendIntent.setPackage("com.whatsapp");
                sendIntent.putExtra("address","how are you");
                startActivity(sendIntent);


            }
        });





       /* editProfileLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.drawer_layout,new edit_profile(),"1");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });*/

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.drawer_layout,new user_account(),"1");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();


        Query query = databaseReference.child("users").orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("23561","accha");


                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String image = ""+ds.child("mImageUrl").getValue();
                    Log.d("22231",image);
                    try{
                        if(image.equals(""))
                        {
                            Picasso.get().load(R.drawable.image).into(imageView);
                        }
                        else {
                            Picasso.get().load(image).resize(50,50).into(imageView);
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

        user_name.setText(user.getEmail());

        return view;
    }



    private void checkUserStatus()
    {
        if(user!=null)
        {

        }
        else
        {
            startActivity(new Intent(getContext(), MainActivity.class));
        }
    }

    @Override
    public void onStart() {
        checkUserStatus();
        super.onStart();
    }


    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.drawer_layout,fragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        setFragment(new HomeFragment());
    }


}