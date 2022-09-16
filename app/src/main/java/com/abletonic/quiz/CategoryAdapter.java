package com.abletonic.quiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder> {

    private String[] categories;



    public CategoryAdapter(String categories[]) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.itemview_category,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        String catgeoryStr = categories[position];
        holder.categoryText.setText(catgeoryStr);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) v.getContext()).onClickCalled(catgeoryStr);

            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView categoryText;
        public viewHolder (View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.tv_category);
        }
    }






}

