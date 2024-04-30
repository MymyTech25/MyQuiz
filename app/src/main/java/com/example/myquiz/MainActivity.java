package com.example.myquiz;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTv;
    TextView questionsTv;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn;

    int score = 0;
    int total = QuestionsAnswers.questions.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalQuestionsTv = findViewById(R.id.total_questions);
        questionsTv = findViewById(R.id.questions);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        totalQuestionsTv.setText("Total questions : "+total);

        loadNewQuestion();

    }

    @Override
    public void onClick(View view) {

        //Initial background of the buttons
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);


        Button clickedButton = (Button) view;

            //Management of the quiz once the answer is sent
            if (clickedButton.getId() == R.id.submit_btn){

                //Launch of the new question
                //If the answer is correct: increase of the score
                if (selectedAnswer.equals(QuestionsAnswers.correctA[currentQuestionIndex])){
                    score++;
                }
                currentQuestionIndex++;
                loadNewQuestion();

            }else {
                //choices button clicked
                selectedAnswer = clickedButton.getText().toString();
                clickedButton.setBackgroundColor(Color.MAGENTA); //Background of the btns when they are clicked
            }

    }

    void loadNewQuestion(){

        //App management: once the 4 questions are finished, so that it doesn't crash!!
        if (currentQuestionIndex == total){
            finishQuiz();
            return;
        }

        questionsTv.setText(QuestionsAnswers.questions[currentQuestionIndex]);
        ansA.setText(QuestionsAnswers.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionsAnswers.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionsAnswers.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionsAnswers.choices[currentQuestionIndex][3]);
    }

    void finishQuiz(){
        String passStatus = "";
        if(score > total*0.60 ){
            passStatus = "Congrattts :-)";
        } else {
            passStatus = "Failed :-(";
        }
        new  AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Your score is "+ score + "/"+ total)
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();

    }

    void restartQuiz(){
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }
}