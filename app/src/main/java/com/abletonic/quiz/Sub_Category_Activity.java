package com.abletonic.quiz;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Sub_Category_Activity extends AppCompatActivity {

    RecyclerView rv_sub_category;
    String category;
    DatabaseHelper categoryDBHelper;
    List<String> subCategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_category_main);

        rv_sub_category = findViewById(R.id.rv_sub_category);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            category = (String) b.get("category");
            setTitle(category + " Categories");
        }

        categoryDBHelper = new DatabaseHelper(this, "Quiz.db", 1);
        Cursor subCatsCursor = categoryDBHelper.getSubCategories(category);
        while (subCatsCursor.moveToNext()) {
            subCategories.add(subCatsCursor.getString(1));
        }

        RecyclerView rv = findViewById(R.id.rv_sub_category);
        rv.setLayoutManager(new LinearLayoutManager(this));


        String[] subCategoriesArray = subCategories.toArray(new String[0]);
        rv.setAdapter(new CategoryAdapter(subCategoriesArray));



    }//END OF ONCREATE



    


}