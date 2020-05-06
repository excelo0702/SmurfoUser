package com.example.chhots.category_view.Contest;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

public class Adapter extends PagerAdapter {
    private List<Model> models;
    private LayoutInflater layoutInflater;
    private Context context;


    public Adapter(List<Model> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.raw_contest_item,container,false);


        ImageView img1 = (ImageView)view.findViewById(R.id.contest_view);
        TextView txt1 = (TextView)view.findViewById(R.id.participate);
        img1.setImageResource(models.get(position).getImageId());


        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();

                if(auth.getCurrentUser()==null)
                {
                    Toast.makeText(getContext(),"To participate in contest you have to first login",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), Login.class);
                    context.startActivity(intent);
                }

                setFragment(new form_contest());
                //TODO: also need to send data
            }
        });



        container.addView(view,0);

        return view;
    }



    public void setFragment(Fragment fragment)
    {
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
