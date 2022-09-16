package com.abletonic.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    DatabaseHelper categoryDBHelper;
    RecyclerView rv_category;
    List<String> categories = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_category = findViewById(R.id.rv_category);


        categoryDBHelper = new DatabaseHelper(this, "Quiz.db", 1);

        try {
            categoryDBHelper.CheckDb();
        }
        catch (Exception e) {e.printStackTrace();}
        try {
            categoryDBHelper.OpenDatabase();
        }
        catch (Exception e) {e.printStackTrace();}


        Cursor myClasses = categoryDBHelper.getMainCategories();
        while (myClasses.moveToNext()) {
            categories.add(myClasses.getString(1));
        }

        RecyclerView rv = findViewById(R.id.rv_category);
        rv.setLayoutManager(new LinearLayoutManager(this));


        String[] categoriesArray = categories.toArray(new String[0]);
        rv.setAdapter(new CategoryAdapter(categoriesArray));



    }//END OF ONCREATE


    public void onClickCalled(String category) {
        Intent i = new Intent(getApplicationContext(), Sub_Category_Activity.class);
        i.putExtra("category", category);
        startActivity(i);

    }


}