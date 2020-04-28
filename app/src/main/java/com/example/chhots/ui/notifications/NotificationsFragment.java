package com.example.chhots.ui.notifications;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.chhots.R;
import com.example.chhots.category_view.routine.routine;

public class NotificationsFragment extends Fragment {



    public NotificationsFragment() {
        // Required empty public constructor
    }

    String[] name = {
            "5000 KRW OFF coupon has been issued" ,
            "5000 KRW OFF coupon has been issued" ,
            "5000 KRW OFF coupon has been issued" ,
            "5000 KRW OFF coupon has been issued" ,
            "5000 KRW OFF coupon has been issued" ,
            "5000 KRW OFF coupon has been issued" ,

            "5000 KRW OFF coupon has been issued" ,
            "5000 KRW OFF coupon has been issued" ,
            "5000 KRW OFF coupon has been issued"
    };
    String[] danceform = {
            "Your 5000 KRW OFF coupon is valid untill 2020,07,07 at 8:35 Am.Please use this coupon before the expiration date",
            "Your 5000 KRW OFF coupon is valid untill 2020,07,07 at 8:35 Am.Please use this coupon before the expiration date",
            "Your 5000 KRW OFF coupon is valid untill 2020,07,07 at 8:35 Am.Please use this coupon before the expiration date",
            "Your 5000 KRW OFF coupon is valid untill 2020,07,07 at 8:35 Am.Please use this coupon before the expiration date",
            "Your 5000 KRW OFF coupon is valid untill 2020,07,07 at 8:35 Am.Please use this coupon before the expiration date",
            "Your 5000 KRW OFF coupon is valid untill 2020,07,07 at 8:35 Am.Please use this coupon before the expiration date",

            "Your 5000 KRW OFF coupon is valid untill 2020,07,07 at 8:35 Am.Please use this coupon before the expiration date",
            "Your 5000 KRW OFF coupon is valid untill 2020,07,07 at 8:35 Am.Please use this coupon before the expiration date",
            "Your 5000 KRW OFF coupon is valid untill 2020,07,07 at 8:35 Am.Please use this coupon before the expiration date",
    };

    String[] time = {
            "2020.04.27 5:05AM",
            "2020.04.27 5:05AM",
            "2020.04.27 5:05AM",
            "2020.04.27 5:05AM",
            "2020.04.27 5:05AM",
            "2020.04.27 5:05AM",

            "2020.04.27 5:05AM",
            "2020.04.27 5:05AM",
            "2020.04.27 5:05AM",
    };

    int[] imageId = {
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image,
            R.drawable.image
    };
    ListView listview;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        listview = (ListView)view.findViewById(R.id.list_notification_view);

        Myadapter myadapter = new Myadapter(getActivity(),name,danceform,imageId,time);

        listview.setAdapter(myadapter);


        return view;
    }


    class Myadapter extends ArrayAdapter<String> {
        Context context;
        String[] name;
        String[] description;
        String[] ttime;
        int imgs[];


        public Myadapter(Context context, String[] name, String[] description, int[] imgs,String[] ttime) {
            super(context, R.layout.raw_fragment_notification, R.id.name_notification, name);
            this.context = context;
            this.name = name;
            this.description = description;
            this.imgs = imgs;
            this.ttime = ttime;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.raw_fragment_notification, parent, false);
            ImageView imageView = row.findViewById(R.id.imageNotification);
            TextView title = row.findViewById(R.id.name_notification);
            TextView desc = row.findViewById(R.id.description_notification);
            TextView txttime = row.findViewById(R.id.timing_notification);

            imageView.setImageResource(imgs[position]);
            title.setText(name[position]);
            desc.setText(description[position]);
            txttime.setText(ttime[position]);
            return row;
        }
    }
}


