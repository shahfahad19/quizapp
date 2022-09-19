package com.abletonic.quiz;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder> {

    private String[] categories;
    Context context;
    SharedPreferences sharedPreferences;

    public CategoryAdapter(String categories[]) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.itemview_category,parent,false);
        context = parent.getContext();
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        String categoryStr = categories[position];
        sharedPreferences = context.getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        String completed = sharedPreferences.getString(categoryStr, "0");


        String completedCategories = sharedPreferences.getString(categoryStr, "0");

        DatabaseHelper databaseHelper = new DatabaseHelper(context, 1);
        int subCategories = databaseHelper.getSubCategoriesLength(categoryStr);

        int progress = 0;
        try {
            progress = (parseInt(completedCategories) * 100) / subCategories;
        }
        catch (Exception e ) {}

        holder.categoryText.setText(categoryStr);
        holder.stats.setText(String.valueOf(completed+"/"+subCategories));
        holder.progressBar.setProgress(progress);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) v.getContext()).onClickCalled(categoryStr);

            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView categoryText, stats;
        ProgressBar progressBar;
        public viewHolder (View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.tv_category);
            stats = itemView.findViewById(R.id.tv_category_stats);
            progressBar = itemView.findViewById(R.id.pb_category_stats);
        }
    }
}

