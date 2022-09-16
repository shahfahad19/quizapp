package com.abletonic.quiz;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.viewHolder> {

    private String[] subCategories;



    public SubCategoryAdapter(String subCategories[]) {
        this.subCategories = subCategories;
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
        String subCatgeoryStr = subCategories[position];
        holder.subCategoryText.setText(subCatgeoryStr);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity) v.getContext()).onClickCalled(subCatgeoryStr);

            }
        });
    }

    @Override
    public int getItemCount() {
        return subCategories.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView subCategoryText;
        public viewHolder (View itemView) {
            super(itemView);
            subCategoryText = itemView.findViewById(R.id.tv_sub_category);
        }
    }






}

