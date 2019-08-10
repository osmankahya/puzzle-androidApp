package com.example.osman.puzzletrial;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class BoardActivity extends AppCompatActivity {

    private int n;
    private char[] symbols;
    private Button[][] board;
    private Random rand;
    private Button btn1, btn2;
    CountDownTimer timer, timerForClick, gameTimer;
    private int moveCount = 0, correctCount = 0, time = 0, level = 1;
    private TextView countText;
    private Button backButton;
    private String mod;
    String allHistory;
    int gameCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        setBoard();
        createInitialTimer();
        createGameTimer();
        //Toast.makeText(this,"Status: Board Create", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        timer.start();
        gameTimer.start();
       //Toast.makeText(this,"Status: Board Start", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        setClickEventForButtons();
        resumeTimer();
        //Toast.makeText(this,"Status: Board Resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(this,"Status: Board Pause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(this,"Status: Board Stop", Toast.LENGTH_SHORT).show();
    }

    public void setBoard(){
        LinearLayout rootLayout = findViewById(R.id.boardLayout);

        LinearLayout bottomLay = new LinearLayout(this);
        bottomLay.setOrientation(LinearLayout.HORIZONTAL);
        bottomLay.setLayoutParams(new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));


        countText = new TextView(this);
        countText.setText("Count: 0/0");

        LinearLayout.LayoutParams textStyle = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        textStyle.setMargins(20,20,0,20);
        textStyle.gravity = Gravity.CENTER;


        backButton = new Button(this);
        backButton.setText("Back");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCalculatedValues();
            }
        });

        LinearLayout.LayoutParams buttonStyle = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonStyle.setMargins(20,20,20,20);
        buttonStyle.gravity = Gravity.CENTER;

        Intent myIntent = getIntent();
        n = myIntent.getIntExtra("BoardSize", 0);
        mod = myIntent.getStringExtra("Level");
        gameCount = myIntent.getIntExtra("GameCount",0);
        allHistory = myIntent.getStringExtra("AllHistory");

        if (mod.contains("Easy")) {
            level = 4;
        }
        else if (mod.contains("Normal")) {
            level = 2;
        }


        symbols = new char[n * n];
        for (int i = 0; i < n * n; i++) {
            symbols[i] = (char) (i / 2 + 65);
        }


        rand = new Random();
        board = new Button[n][n];

        for (int i = 0; i < n * n; i++) {
            int r = rand.nextInt(n * n);
            int r1 = rand.nextInt(n * n);
            char temp = symbols[r];
            symbols[r] = symbols[r1];
            symbols[r1] = temp;
        }
        int k = 0;
        for (int i = 0; i < n; i++) {
            LinearLayout myLayout = new LinearLayout(this);
            myLayout.setOrientation(LinearLayout.HORIZONTAL);
            myLayout.setGravity(Gravity.CENTER);
            myLayout.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < n; j++) {
                Button btn = new Button(this);
                btn.setText(Character.toString(symbols[k++]));
                btn.setLayoutParams(new LinearLayout.LayoutParams(90, 90));
                btn.setTextSize(15);
                myLayout.addView(btn);
                board[i][j] = btn;
            }

            rootLayout.addView(myLayout);
        }
        bottomLay.addView(countText,textStyle);
        bottomLay.addView(backButton,buttonStyle);
        rootLayout.addView(bottomLay);
    }

    private void createInitialTimer(){
        timer = new CountDownTimer(1000*level,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        board[i][j].setTag(board[i][j].getText().toString());
                        board[i][j].setText("");
                    }
                }

            }
        };

    }

    private void resumeTimer(){

        timerForClick = new CountDownTimer(1000*level, 8000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                btn1.setText("");
                btn2.setText("");
                btn1 = btn2 = null;
            }
        };
    }

    private void createGameTimer(){
        gameTimer = new CountDownTimer(600000,1000) {
            @Override
            public void onTick(long l) {
                time++;
            }

            @Override
            public void onFinish() {

            }
        };
    }

    private void setClickEventForButtons() {

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j].setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       Button current = (Button) view;
                                                       if (btn1 == null) {
                                                           btn1 = current;
                                                           current.setText(btn1.getTag().toString());
                                                       }
                                                       else if (btn2 == null && btn1 != btn2 && current != btn1) {
                                                           btn2 = current;
                                                           current.setText(btn2.getTag().toString());

                                                           if (btn1.getText().toString().equals(btn2.getText().toString())) {
                                                               btn1.setTextColor(Color.RED);
                                                               btn2.setTextColor(Color.RED);
                                                               btn1 = btn2 = null;
                                                               correctCount++;
                                                           }
                                                           else {
                                                               timerForClick.start();
                                                           }
                                                           moveCount++;
                                                       }
                                                       if (n*(n/2) != correctCount){
                                                       countText.setText("Count: " +correctCount +"/"+ moveCount);}
                                                       else{
                                                           countText.setText("WIN !\r\nClick Back");}
                                                   }
                                               }
                );
            }
        }
    }

    private void sendCalculatedValues(){
        Intent myIntent = new Intent(this, MainActivity.class);

        String minuteSecond = (time / 60 +":" + time % 60);
        myIntent.putExtra("PuzzleSize",n);
        myIntent.putExtra("MoveCount",moveCount);
        myIntent.putExtra("GameTime",minuteSecond);
        myIntent.putExtra("GameCount",gameCount);
        myIntent.putExtra("AllHistory",allHistory);

        startActivity(myIntent);
    }
}