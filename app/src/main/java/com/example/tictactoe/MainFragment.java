package com.example.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainFragment extends Fragment implements View.OnClickListener {
    Button one_player, two_player;
    TossFragment.FragmentInfo fragmentInfo;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentInfo = (TossFragment.FragmentInfo) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        one_player = (Button) v.findViewById(R.id.one_player);
        two_player = (Button) v.findViewById(R.id.two_players);

        one_player.setOnClickListener(this);
        two_player.setOnClickListener(this);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.one_player){
            TossFragment tossFragment = new TossFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, tossFragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else{
            fragmentInfo.onSentText(2, 1);
        }
    }

}
