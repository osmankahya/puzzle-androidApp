package com.example.osman.puzzletrial;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioButton radioB;
    RadioGroup radioG;
    int radioCheck;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();



    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"Status: Main Started", Toast.LENGTH_SHORT).show();

        getLog();

    }

    @SuppressLint("WrongViewCast")
    public void Start(View v){
        EditText boardSize = findViewById(R.id.editText);
        int level = 1;
        size = Integer.parseInt(boardSize.getText().toString());

        radioG = (RadioGroup) findViewById(R.id.radioG);
        radioCheck = radioG.getCheckedRadioButtonId();
        radioB = findViewById(radioCheck);

        if (size % 2 == 1|| size < 2 || size > 8){
            Toast.makeText(this,"Input must be even and in range of 2-8", Toast.LENGTH_LONG).show();
        }
        else{

            Intent boardIntent = new Intent(this,BoardActivity.class);
            boardIntent.putExtra("BoardSize",size);
            boardIntent.putExtra("Level",radioB.getText());
            startActivity(boardIntent);
        }
    }


    private void getLog(){

        int moveCount;
        String minuteSecond;

        TextView log;
        log = findViewById(R.id.reportText);

        Intent mInte = getIntent();
        size = mInte.getIntExtra("PuzzleSize",00);
        moveCount = mInte.getIntExtra("MoveCount",00);
        minuteSecond = mInte.getStringExtra("GameTime");

        if (size != 0) {log.setText("Size :" + size +"x" + size + "\r\n" +
                                    "Move Count :" + moveCount + "\r\n" +
                                    "Time : " + minuteSecond);}
    }
}
