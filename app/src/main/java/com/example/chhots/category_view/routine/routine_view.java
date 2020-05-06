package com.example.chhots.category_view.routine;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chhots.MainActivity;
import com.example.chhots.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class routine_view extends Fragment {


    public routine_view() {
        // Required empty public constructor
    }

    TextView txt1,txt2;
    ImageView img;
    EditText edtxt1,edxt2;
    Button buy_now;
    String s1,s2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_routine_view, container, false);

        Bundle bundle = this.getArguments();
        String txtt1 = bundle.getString("name");
        String txtt2 = bundle.getString("danceform");
        int imgg = bundle.getInt("image",0);

        buy_now = (Button)view.findViewById(R.id.routine_buy_now);
        edtxt1 = (EditText)view.findViewById(R.id.merchat_id);
        edxt2 = (EditText)view.findViewById(R.id.order_id);

        Toast.makeText(getContext(),txtt1+txtt2+" fffff",Toast.LENGTH_SHORT).show();

        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(),"dddd",Toast.LENGTH_SHORT).show();

                s1 = edtxt1.getText().toString();
                s2 = edxt2.getText().toString();
             //   Intent intent = new Intent(getContext(), checksum.class);
               // intent.putExtra("orderid", s1);
                //intent.putExtra("custid", s2);
                //startActivity(intent);

            }
        });


     /*   txt1.setText(txtt1);
        txt2.setText(txtt2);
        img.setImageResource(imgg);
*/

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);

        }
        return view;
    }

}
