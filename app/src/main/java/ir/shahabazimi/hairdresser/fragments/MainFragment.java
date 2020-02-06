package ir.shahabazimi.hairdresser.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.card.MaterialCardView;

import ir.shahabazimi.hairdresser.R;
import ir.shahabazimi.hairdresser.activities.BuyActivity;
import ir.shahabazimi.hairdresser.dialogs.BrideDialog;
import ir.shahabazimi.hairdresser.dialogs.RegisterDialog;


public class MainFragment extends Fragment {

    private MaterialCardView reg,bride,service;



    public MainFragment() {
        super(R.layout.fragment_main);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        init(v);


    }

    private void init(View v){
        reg = v.findViewById(R.id.main_reg);
        bride = v.findViewById(R.id.main_bride);
        service = v.findViewById(R.id.main_service);


        onClicks(v);
    }

    private void onClicks(View v){
        reg.setOnClickListener(w->{
            RegisterDialog dialog = new RegisterDialog(getContext());
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        });

        bride.setOnClickListener(w->{
            BrideDialog dialog = new BrideDialog(getContext());
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        });

        service.setOnClickListener(w->{
            startActivity(new Intent(getContext(), BuyActivity.class));
            getActivity().overridePendingTransition(R.anim.enter_right,R.anim.exit_left);

        });

    }
}
