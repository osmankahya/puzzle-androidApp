package com.example.osman.puzzletrial;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RadioButton radioB;
    private RadioGroup radioG;
    private int radioCheck;
    private int size;
    private String allHistory = "-";
    private int gameCount = 0;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createSpinner();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //Toast.makeText(this,"Status: Main Resume", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(this,"Status: Main Start", Toast.LENGTH_SHORT).show();

        getLog();
    }

    private void createSpinner(){
        spinner = (Spinner) findViewById(R.id.editText);
        spinner.setPrompt("Enter Size");
        ArrayList<String> spinnerList = new ArrayList<>();
        spinnerList.add("2");
        spinnerList.add("4");
        spinnerList.add("6");
        spinnerList.add("8");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.dropdown_layout, R.id.textView, spinnerList);
        spinner.setAdapter(spinnerAdapter);
    }

    @SuppressLint("WrongViewCast")
    public void Start(View v){
        //EditText boardSize = findViewById(R.id.editText);

        size = (int) Integer.parseInt(String.valueOf(spinner.getSelectedItem()));
        //size = Integer.parseInt(boardSize.getText().toString());

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
            boardIntent.putExtra("GameCount",gameCount);
            boardIntent.putExtra("AllHistory",allHistory);
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
        gameCount = mInte.getIntExtra("GameCount",0);
        allHistory = mInte.getStringExtra("AllHistory");

        if (size != 0) {
            gameCount++;

            if (moveCount != 0){
                if (gameCount == 1){
                    allHistory = " - - - - - - - Game " + gameCount +" - - - - - - - \n"+
                            "Size :" + size +"x" + size + "\n" +
                            "Move Count :" + moveCount + "\n" +
                            "Time : " + minuteSecond+"\n" +
                            "played it.\n";
                }
                else{
                    allHistory += " - - - - - - - Game " + gameCount +" - - - - - - - \n"+
                            "Size :" + size +"x" + size + "\n" +
                            "Move Count :" + moveCount + "\n" +
                            "Time : " + minuteSecond+"\n" +
                            "played it.\n";
                }

            }
            else{
                if (gameCount == 1){
                    allHistory = " - - - - - - - Game " + gameCount +" - - - - - - - \n"+
                            "Size :" + size +"x" + size + "\n" +
                            "Move Count :" + moveCount + "\n" +
                            "Time : " + minuteSecond+"\n" +
                            "not played it.\n";
                }
                else{
                    allHistory += " - - - - - - - Game " + gameCount +" - - - - - - - \n"+
                            "Size :" + size +"x" + size + "\n" +
                            "Move Count :" + moveCount + "\n" +
                            "Time : " + minuteSecond+"\n" +
                            "not played it.\n";
                }
            }
           log.setText(allHistory);
        }
    }
}
