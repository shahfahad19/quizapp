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


    DatabaseHelper databaseHelper;
    RecyclerView rv_category;
    List<String> categories = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_category = findViewById(R.id.rv_category);


        databaseHelper = new DatabaseHelper(this, 1);

        try {
            databaseHelper.CheckDb();
        }
        catch (Exception e) {e.printStackTrace();}
        try {
            databaseHelper.OpenDatabase();
        }
        catch (Exception e) {e.printStackTrace();}


        Cursor allCategories = databaseHelper.getMainCategories();
        while (allCategories.moveToNext()) {
            categories.add(allCategories.getString(1));
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

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }



}