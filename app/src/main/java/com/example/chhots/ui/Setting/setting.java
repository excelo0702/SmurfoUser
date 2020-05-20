package com.example.chhots.ui.Setting;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.chhots.R;
import com.example.chhots.onBackPressed;
import com.example.chhots.ui.home.HomeFragment;

public class setting extends Fragment implements onBackPressed {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        return root;
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