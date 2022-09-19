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

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.viewHolder> {

    private String[] subCategories;
    SharedPreferences sharedPreferences;
    Context context;


    public SubCategoryAdapter(String subCategories[]) {
        this.subCategories = subCategories;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.itemview_sub_category,parent,false);
        context = parent.getContext();
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        String subCategoryStr = subCategories[position];

        sharedPreferences = context.getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        String completedQuestions = sharedPreferences.getString(subCategoryStr, "0");

        DatabaseHelper databaseHelper = new DatabaseHelper(context, 1);
        int totalQuestions = databaseHelper.getQuestionsLength(subCategoryStr);

        int progress = 0;
        try {
            progress = (parseInt(completedQuestions) * 100) / totalQuestions;
        }
        catch (Exception e ) {}
        holder.subCategoryText.setText(subCategoryStr);
        holder.progressBar.setProgress(progress);


        holder.stats.setText(completedQuestions+"/"+totalQuestions);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Sub_Category_Activity) v.getContext()).onClickCalled(subCategoryStr);

            }
        });
    }

    @Override
    public int getItemCount() {
        return subCategories.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView subCategoryText, stats;
        ProgressBar progressBar;
        public viewHolder (View itemView) {
            super(itemView);
            subCategoryText = itemView.findViewById(R.id.tv_sub_category);
            stats = itemView.findViewById(R.id.tv_sub_category_stats);
            progressBar = itemView.findViewById(R.id.pb_sub_category_stats);
        }
    }
}

