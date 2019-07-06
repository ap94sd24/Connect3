package com.logintestapp.adam.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // 0 is yellow, 1 is red, 2 is empty
    int currPlayer = 0;

    int[] gameState = {2,2,2,2,2,2,2,2,2};

    boolean gameActive = true;

   // boolean boardFull = false;

    int[][] winningStates = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0, 4, 8},
            {2, 4, 6}};

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        Log.i("Tag", counter.getTag().toString());

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameActive) {


            gameState[tappedCounter] = currPlayer;

            counter.setTranslationY(-1500);
            counter.setRotation(-3600);

            if (currPlayer == 0) {
                currPlayer = 1;
                counter.setImageResource(R.drawable.yellow);
            } else {
                currPlayer = 0;
                counter.setImageResource(R.drawable.red);
            }
            counter.animate().translationYBy(1500).rotation(3600).setDuration(400);

            for (int[] winningPosit : winningStates) {
                if (gameState[winningPosit[0]] == gameState[winningPosit[1]] &&
                        gameState[winningPosit[1]] == gameState[winningPosit[2]] &&
                        gameState[winningPosit[0]] != 2) {
                    // someone won

                    String winner;
                    gameActive = false;

                    if (currPlayer == 0) {
                        winner = "Red";
                    } else {
                        winner = "Yellow";
                    }

                    Toast.makeText(this, winner + " has won!",
                            Toast.LENGTH_SHORT).show();

                    Button playAgainBtn = (Button) findViewById(R.id.playAgainBtn);
                    TextView winnerTexts = (TextView) findViewById(R.id.winnerTextView);

                    winnerTexts.setText(winner + " has won!");

                    playAgainBtn.setVisibility(View.VISIBLE);
                    winnerTexts.setVisibility(View.VISIBLE);

                } else if (isTied(gameState) && !(gameState[winningPosit[0]] == gameState[winningPosit[1]] &&
                        gameState[winningPosit[1]] == gameState[winningPosit[2]])) {
                    Log.i("TAG","Game is tied!!!");

                    Toast.makeText(this, "No winner!",
                            Toast.LENGTH_SHORT).show();

                    Button playAgainBtn = (Button) findViewById(R.id.playAgainBtn);
                    TextView winnerTexts = (TextView) findViewById(R.id.winnerTextView);

                    winnerTexts.setText("It's a tie!");

                    playAgainBtn.setVisibility(View.VISIBLE);
                    winnerTexts.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public boolean isTied(int[] board) {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 2) {
                return false;
            }
        }

        return true;
    }

    public void playAgain(View view) {

       Log.i("TAG", "CLICKED!");
       Button playAgainBtn = (Button) findViewById(R.id.playAgainBtn);
       TextView winnerTexts = (TextView) findViewById(R.id.winnerTextView);

        playAgainBtn.setVisibility(View.INVISIBLE);
        winnerTexts.setVisibility(View.INVISIBLE);

        android.support.v7.widget.GridLayout gridLayout =  findViewById(R.id.boardLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView image = (ImageView) gridLayout.getChildAt(i);

            image.setImageDrawable(null);
        }

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        currPlayer = 0;
        gameActive = true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
