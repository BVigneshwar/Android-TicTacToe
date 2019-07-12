package com.example.tictactoe;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class TossFragment extends Fragment implements View.OnClickListener{

    Button head, tail;
    FragmentInfo fragmentInfo;

    public interface FragmentInfo{
        public  void onSentText(int player_count, int first_player);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentInfo = (FragmentInfo)context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_toss, container, false);

        head = (Button) v.findViewById(R.id.head);
        tail = (Button) v.findViewById(R.id.tail);

        head.setOnClickListener(this);
        tail.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        int rand = (int)(Math.random()*100);
        if(rand%2 == 0){
            fragmentInfo.onSentText(1, 1);
        }else{
            fragmentInfo.onSentText(1, 2);
        }

    }
}
