package com.example.chhots.User_Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chhots.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    public BottomSheetDialog() {
    }

    public static final String TAG = "ActionBottomDialog";
    private ItemClickListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet,container,false);
    }




    @Override
    public void onClick(View view) {

    }

    public interface ItemClickListener {
        void onItemClick(String item);
    }

}
