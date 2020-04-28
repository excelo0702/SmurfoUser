package com.example.chhots.category_view.routine;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chhots.R;

public class routine extends Fragment {

    public routine() {
        // Required empty public constructor
    }

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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_routine, container, false);

        listview = (ListView)view.findViewById(R.id.list_routine_view);

        Myadapter myadapter = new Myadapter(getActivity(),name,danceform,imageId);

        listview.setAdapter(myadapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                routine_view fragment = new routine_view();
                Bundle args = new Bundle();
                args.putString("name",name[i]);
                args.putString("danceform",danceform[i]);
                args.putInt("image",imageId[i]);
                fragment.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).addToBackStack(null)
                        .commit();
            }
        });



        return view;
    }
    class Myadapter extends ArrayAdapter<String> {
        Context context;
        String[] name;
        String[] description;
        int imgs[];


        public Myadapter(Context context, String[] name, String[] description, int[] imgs) {
            super(context,R.layout.raw_routine_item,R.id.name_routine,name);
            this.context = context;
            this.name = name;
            this.description = description;
            this.imgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.raw_routine_item,parent,false);
            ImageView imageView = row.findViewById(R.id.imageRoutine);
            TextView title = row.findViewById(R.id.name_routine);
            TextView desc = row.findViewById(R.id.description_routine);


            imageView.setImageResource(imgs[position]);
            title.setText(name[position]);
            desc.setText(description[position]);




            return row;
        }
    }


}
