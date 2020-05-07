package com.example.chhots.category_view.Contest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import com.example.chhots.Login;
import com.example.chhots.R;
import com.google.firebase.auth.FirebaseAuth;
import com.paytm.pgsdk.Log;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.view.LayoutInflater.from;
import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

public class ContestAdapter extends PagerAdapter {
    private List<HostModel> models;
    private LayoutInflater layoutInflater;
    private Context context;
    private final String TAG = "ContestAdapter";


    public ContestAdapter(List<HostModel> models, Context context) {
        this.models = models;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        Log.e("ttttttt","ffffff");
        System.out.println(models.size()+"   kkkk  ");
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        View view = layoutInflater.inflate(R.layout.raw_contest_item,container,false);

        Log.e(TAG,models.get(position).getImageUrl());
        System.out.println(models.get(position).getImageUrl()+"  jjjj");

        ImageView img1 = (ImageView)view.findViewById(R.id.contest_view);
        TextView txt1 = (TextView)view.findViewById(R.id.participate);
        Picasso.get().load(Uri.parse(models.get(position).getImageUrl())).placeholder(R.drawable.action_button_bg).into(img1);
        final String info = models.get(position).getInfo();
        final String contestId = models.get(position).getContestId();
        System.out.println(img1+" pppp");


        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();

                if(auth.getCurrentUser()==null)
                {
                    Toast.makeText(context,"To participate in contest you have to first login",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), Login.class);
                    context.startActivity(intent);
                }
                else {
                    Toast.makeText(context,"To participate in contest you have to first logout",Toast.LENGTH_SHORT).show();
                    setFragment(new form_contest(),info,contestId,models.get(position).getImageUrl());
                    //TODO: also need to send data
                }
            }
        });
        container.addView(view);

        return view;
    }



    public void setFragment(Fragment fragment,final String info,final String contestId,final String imageUrl)
    {
        Bundle bundle = new Bundle();
        bundle.putString("info", info);
        bundle.putString("contestId", contestId);
        bundle.putString("imageUrl",imageUrl);
        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.drawer_layout,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
