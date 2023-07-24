package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestion,tvScore,tvQuestionNo, tvTimer;
    private RadioGroup radioGroup;
    private RadioButton rb1,rb2,rb3;
    private Button btnNext;
    int totalQuestions;
    int qCounter=0;
    int score=0;
    ColorStateList  dfRbColor;
    boolean answered;
    CountDownTimer countDownTimer;

    private Questionmodel currentQuestion;


    private List<Questionmodel> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionList=new ArrayList<>();
        tvQuestion=findViewById(R.id.textQuestion);
        tvScore=findViewById(R.id.textScore);
        tvQuestionNo=findViewById(R.id.textQuestionNo);
        tvTimer=findViewById(R.id.textTimer);

        radioGroup=findViewById(R.id.radioGroup);
        rb1=findViewById(R.id.rb1);
        rb2=findViewById(R.id.rb2);
        rb3=findViewById(R.id.rb3);
        btnNext=findViewById(R.id.btnNext);

        dfRbColor=rb1.getTextColors();

        addQuestions();
        totalQuestions=questionList.size();
        showNextQuestion();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answered==false)
                {
                    if(rb1.isChecked()||rb2.isChecked()||rb3.isChecked()){
                        checkAnswer();
                        countDownTimer.cancel();
                    }else{
                        Toast.makeText(QuizActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    showNextQuestion();
                }
            }
        });
    }

    private void checkAnswer() {

        answered = true;
        RadioButton rbSelected=findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo=radioGroup.indexOfChild(rbSelected)+1;
        if(answerNo==currentQuestion.getCorrectAnsNo()){
            score++;
            tvScore.setText("Score: "+score);

        }
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        switch(currentQuestion.getCorrectAnsNo()){
            case 1:
                rb1.setTextColor(Color.GREEN);
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                break;
        }
        if(qCounter<totalQuestions){
            btnNext.setText("Next");
        }else{
            btnNext.setText("Finish");
        }
    }
    private void showNextQuestion() {

        radioGroup.clearCheck();
        rb1.setTextColor(dfRbColor);
        rb2.setTextColor(dfRbColor);
        rb3.setTextColor(dfRbColor);


        if (qCounter < totalQuestions) {
            timer();
            currentQuestion = questionList.get(qCounter);
            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            qCounter++;
            btnNext.setText("Submit");
            tvQuestionNo.setText("Question: "+qCounter+"/"+totalQuestions);
            answered=false;
        }else
        {
            finish();
            Toast.makeText(QuizActivity.this, "Your score: " + score + " out of 5", Toast.LENGTH_SHORT).show();

        }
    }
    private void timer()
    {
        countDownTimer=new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long l) {

                tvTimer.setText("00: "+ l/1000);
            }

            @Override
            public void onFinish() {
                showNextQuestion();

            }
        }.start();
    }


    private void addQuestions(){
        questionList.add(new Questionmodel("Who is known as the Father of Computers?","James Gosling","Charles Babbage","Dennis Ritchie",2));
        questionList.add(new Questionmodel("Which of the following is not a type of computer code?","EDIC","ASCII","BCD",1));
        questionList.add(new Questionmodel("Which of the following can access the server?","User","Web Client","Web browser",2));
        questionList.add(new Questionmodel("Which of the following is created when a user opens an account in the computer system?","SFD","MFD","Subdirectory",3));
        questionList.add(new Questionmodel("Which of the following computers gave birth to the uch cheaper microcomputers?","Microprocessors","PDAs","Mainframes",1));


    }

}