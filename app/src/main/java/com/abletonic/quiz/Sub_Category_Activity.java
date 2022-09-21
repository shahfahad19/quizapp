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
    int category_ID;
    String categoryName;
    DatabaseHelper databaseHelper;
    List<String> subCategories = new ArrayList<>();
    List<String> subCat_IDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_category_main);

        rv_sub_category = findViewById(R.id.rv_sub_category);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            category_ID = (Integer) b.get("category");
            categoryName = (String) b.get("categoryName");
        }

        databaseHelper = new DatabaseHelper(this, 1);
        Cursor subCatsCursor = databaseHelper.getSubCategories(category_ID);
        while (subCatsCursor.moveToNext()) {
            subCat_IDs.add(subCatsCursor.getString(0));
            subCategories.add(subCatsCursor.getString(1));
        }

        RecyclerView rv = findViewById(R.id.rv_sub_category);
        rv.setLayoutManager(new LinearLayoutManager(this));

        String[] subCatIDs_Array = subCat_IDs.toArray(new String[0]);
        String[] subCategoriesArray = subCategories.toArray(new String[0]);
        rv.setAdapter(new SubCategoryAdapter(subCatIDs_Array, subCategoriesArray));



    }//END OF ONCREATE



    public void onClickCalled(int subCategory_ID, String subCategoryName) {
        Intent i = new Intent(getApplicationContext(), Questions_Activity.class);
        i.putExtra("category", category_ID);
        i.putExtra("categoryName", categoryName);
        i.putExtra("subCategory", subCategory_ID);
        i.putExtra("subCategoryName", subCategoryName);
        startActivity(i);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


}