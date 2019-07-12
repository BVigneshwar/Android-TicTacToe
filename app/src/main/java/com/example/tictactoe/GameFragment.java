package com.example.tictactoe;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Arrays;


public class GameFragment extends Fragment implements View.OnClickListener{

    int player_count;
    int first;
    int grid[][];
    boolean isPlayerOne = false;
    int n = 3;
    int moveCount = 9;
    int buttonId[] = {R.id.button_00, R.id.button_01, R.id.button_02,
            R.id.button_10, R.id.button_11, R.id.button_12,
            R.id.button_20, R.id.button_21, R.id.button_22};
    boolean gameOver = false;
    //Computer Move
    int max = 0;
    String order;
    int starting_position;
    int com_i, com_j;

    public void sendInfo(){
        Bundle bundle = getArguments();
        player_count = bundle.getInt("player_count");
        if(bundle.getInt("first_player") == 1){
            isPlayerOne = true;
        }else{
            isPlayerOne = false;
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game, container, false);

        FrameLayout grid_container =(FrameLayout) v.findViewById(R.id.fragment_container);
        int width = grid_container.getWidth();
        grid_container.getDi
        v.findViewById(R.id.button_00).setOnClickListener(this);
        v.findViewById(R.id.button_01).setOnClickListener(this);
        v.findViewById(R.id.button_02).setOnClickListener(this);
        v.findViewById(R.id.button_10).setOnClickListener(this);
        v.findViewById(R.id.button_11).setOnClickListener(this);
        v.findViewById(R.id.button_12).setOnClickListener(this);
        v.findViewById(R.id.button_20).setOnClickListener(this);
        v.findViewById(R.id.button_21).setOnClickListener(this);
        v.findViewById(R.id.button_22).setOnClickListener(this);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        grid = new int[n][n];
        for(int[] i : grid)
            Arrays.fill(i, 0);

        if(player_count == 1 && isPlayerOne == false){
            comTurn();
            isPlayerOne = true;
            moveCount--;
        }
    }

    @Override
    public void onClick(View v) {
        if(!gameOver && moveCount > 0){
            String pos = v.getTag().toString();
            int i = Character.getNumericValue(pos.charAt(0));
            int j = Character.getNumericValue(pos.charAt(1));
            if(player_count == 1){
                onePlayerGame(i, j);
            }else{
                twoPlayerGame(i, j);
            }
        }
        if(moveCount == 0 && gameOver == false){
                gameOverAlertDialog("Game Over");
        }
    }

    void onePlayerGame(int i, int j){
        if(isPlayerOne){
            setGridElement(i, j, 1);
            isPlayerOne = false;
            moveCount--;
            if(checkForGameOver(1, 2)){
                gameOver = true;
                return;
            }
            if(moveCount >0){
                comTurn();
                isPlayerOne = true;
                moveCount--;
            }
        }
    }

    void twoPlayerGame(int i, int j){
        if(isPlayerOne){
            setGridElement(i, j, 1);
            isPlayerOne = false;
            if(checkForGameOver(1, 2)){
                gameOver = true;
            }
        }else{
            setGridElement(i, j, 2);
            isPlayerOne = true;
            if(checkForGameOver(2, 1)){
                gameOver = true;
            }
        }
        moveCount--;
    }

    boolean checkForGameOver(int player, int opponent){
        int diagonal = 0, rev_diagonal = 0;
        for(int i=0; i<n; i++){
            int horizontalCheck = 0;
            int verticalCheck = 0;
            for(int j=0; j<n; j++){
                if(grid[i][j] == player){
                    horizontalCheck++;
                }else if(grid[i][j] == opponent){
                    horizontalCheck--;
                }
                if(grid[j][i] == player){
                    verticalCheck++;
                }else if(grid[j][i] == opponent){
                    verticalCheck--;
                }
                if(i == j && grid[i][j] == player){
                    diagonal++;
                }else if(i == j && grid[i][j] == opponent){
                    diagonal--;
                }
                if((i+j) == (n-1) && grid[i][j] == player){
                    rev_diagonal++;
                }else if((i+j) == (n-1) && grid[i][j] == opponent){
                    rev_diagonal--;
                }
            }
            if(horizontalCheck == n || verticalCheck == n){
                gameOverAlertDialog(player+"");
                return true;
            }
            if(verticalCheck > max){
                max = verticalCheck;
                order = "vertical";
                starting_position = i;
            }
            if(horizontalCheck > max){
                max = horizontalCheck;
                order = "horizontal";
                starting_position = i;
            }
        }
        if(diagonal == n || rev_diagonal == n){
            gameOverAlertDialog(player+"");
            return true;
        }
        if(diagonal > max){
            max = diagonal;
            order = "diagonal";
            starting_position = 0;
        }
        if(rev_diagonal > max){
            max = rev_diagonal;
            order = "rev_diagonal";
            starting_position = n-1;
        }
        return false;
    }

    public void setGridElement(int i, int j, int player){
        if(grid[i][j] == 0){
            grid[i][j] = player;
            int selected_button_id = buttonId[i*3 + j];
            Button bt = (Button) getActivity().findViewById(selected_button_id);
            bt.setEnabled(false);
            if(player == 1){
                bt.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.x_button));
            }else{
                bt.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.o_button));
            }
        }
    }
    void comTurn(){
        int comMove_i = -1;
        int comMove_j = -1;
        max = 0;
        com_i = com_j = -1;
        if(!checkForGameOver(1, 2)){
            if(max == n-1){
                comMove();
                comMove_i = com_i;
                comMove_j = com_j;
                max = 0;
                com_i = com_j = -1;
            }
            if(!checkForGameOver(2,1)){
                if(max == n-1){
                    comMove();
                    setGridElement(com_i, com_j, 2);
                    if(checkForGameOver(2,1))
                        gameOver = true;
                }else{
                    if(comMove_i != -1 && comMove_j != -1){
                        setGridElement(comMove_i, comMove_j, 2);
                    }else{
                        boolean checkOccupied = true;
                        while(checkOccupied){
                            int temp_i = (int)(Math.random()*100) % n;
                            int temp_j = (int)(Math.random()*100) % n;
                            if(grid[temp_i][temp_j] == 0){
                                setGridElement(temp_i, temp_j, 2);
                                checkOccupied = false;
                            }
                        }
                    }
                }
            }else{
                gameOver = true;
            }
        }else{
            gameOver = true;
        }

    }
    void  comMove(){
        if(order.equals("vertical")){
            for(int i=0; i<n; i++){
                if(grid[i][starting_position] == 0){
                    com_i = i;
                    com_j = starting_position;
                    break;
                }
            }
        }else if(order.equals("horizontal")){
            for(int i=0; i<n; i++){
                if(grid[starting_position][i] == 0){
                    com_i = starting_position;
                    com_j = i;
                    break;
                }
            }
        }else if(order.equals("diagonal")){
            for(int i=0; i<n; i++){
                if(grid[i][i] == 0){
                    com_i = com_j = i;
                    break;
                }
            }
        }else{
            for(int i=0; i<n; i++){
                if(grid[i][n-1-i] == 0){
                    com_i = i;
                    com_j = n-1-i;
                    break;
                }
            }
        }
    }

    private void gameOverAlertDialog(String result){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.alert_dialog);

        TextView resultText = (TextView) dialog.findViewById(R.id.result);
        if(result.equals("1")){
            if(player_count == 1){
                result = "Congratulations !!!";
            }else{
                result = "Player 1 Won";
            }
        }else if(result.equals("2")){
            if(player_count == 1){
                result = "Loser !!!";
            }else{
                result = "Player 2 Won";
            }
        }
        resultText.setText(result);

        Button restart = (Button) dialog.findViewById(R.id.restart);
        Button exit = (Button) dialog.findViewById(R.id.exit);

        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.container));

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                GameFragment gameFragment = new GameFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("player_count", player_count);
                int first_player = 1;
                if(player_count == 1){
                    int rand = (int)(Math.random()*100);
                    if(rand%2 == 1){
                        first_player = 1;
                    }else{
                        first_player = 2;
                    }
                }
                bundle.putInt("first_player", first_player);
                gameFragment.setArguments(bundle);
                getFragmentManager().popBackStack();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, gameFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
                gameFragment.sendInfo();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        dialog.show();
    }
}
