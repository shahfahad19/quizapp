package com.abletonic.quiz;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Questions_Activity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    DatabaseHelper databaseHelper;
    TextView questionIndex, questionText, totalText, correctText, wrongText;
    Button optA, optB, optC, optD;

    int questionId = 1;
    int questionsLength = 0;
    int correct = 0;
    int wrong = 0;
    String correctOption, catName, subCatName;
    int category, subCategory;
    Cursor questionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        questionIndex = findViewById(R.id.questionIndex);
        questionText = findViewById(R.id.questionText);
        totalText = findViewById(R.id.totalQuestions);
        correctText = findViewById(R.id.correctQuestions);
        wrongText = findViewById(R.id.wrongQuestions);
        optA = findViewById(R.id.optA);
        optB = findViewById(R.id.optB);
        optC = findViewById(R.id.optC);
        optD = findViewById(R.id.optD);

        sharedPreferences = getSharedPreferences("Prefs", Context.MODE_PRIVATE);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            category = (Integer) b.get("category");
            subCategory = (Integer) b.get("subCategory");
            catName = (String) b.get("categoryName");
            subCatName = (String) b.get("subCategoryName");
        }

        databaseHelper = new DatabaseHelper(this, 1);
        questionData = databaseHelper.getQuestion(subCategory);
        questionsLength = databaseHelper.getSubCatQuestionsCount(subCategory);

        totalText.setText("Total: "+ questionsLength);
        getQuestion();


        //When an option is clicked
        optA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("A");
            }
        });
        optB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("B");
            }
        });
        optC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("C");
            }
        });
        optD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("D");
            }
        });



    }
    public void getQuestion() {
        questionIndex.setText("Question. "+ questionId);
        questionData.moveToNext();
        questionText.setText(questionData.getString(1));
        optA.setText(questionData.getString(3));
        optB.setText(questionData.getString(4));
        optC.setText(questionData.getString(5));
        optD.setText(questionData.getString(6));
        correctOption = questionData.getString(7);

    }

    public void checkAnswer(String selectedOption) {
        if (questionId <= questionsLength) {
            if (correctOption.contains(selectedOption)) {
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
                correct++;
                correctText.setText("Correct: "+ correct);
            }
            else {
                Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
                wrong++;
                wrongText.setText("Wrong:  "+ wrong);
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();

            //Updating question counter only if new questions are answered
            if (questionId > parseInt(sharedPreferences.getString(subCatName, "0"))) {

                //getting main category completed questions count
                int categoryCompletedQuestionsCount = parseInt(sharedPreferences.getString(catName, "0"));
                categoryCompletedQuestionsCount+=1;
                editor.putString(subCatName, String.valueOf(questionId));
                editor.putString(catName, String.valueOf(categoryCompletedQuestionsCount));
            }

            editor.commit();
            questionId++;

            //Load next question if questionID is in range of questions length
            if (questionId<=questionsLength) {
                getQuestion();
            }

        }
        else {
            Toast.makeText(this, "Questions Finished", Toast.LENGTH_SHORT).show();
        }
    }
}