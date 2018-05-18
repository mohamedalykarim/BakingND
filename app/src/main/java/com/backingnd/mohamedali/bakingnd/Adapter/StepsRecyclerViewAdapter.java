package com.backingnd.mohamedali.bakingnd.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.backingnd.mohamedali.bakingnd.Models.RecipeStep;
import com.backingnd.mohamedali.bakingnd.R;
import com.backingnd.mohamedali.bakingnd.StepActivity;
import com.backingnd.mohamedali.bakingnd.Utils.ConstantUtils;

import java.util.ArrayList;
import java.util.List;

public class StepsRecyclerViewAdapter extends RecyclerView.Adapter<StepsRecyclerViewAdapter.StepViewHolder> {
    Context context;
    List<RecipeStep> steps;
    private OnListItemClickListeaner onListItemClickListeaner;

    public StepsRecyclerViewAdapter(Context context, List<RecipeStep> stepsList, OnListItemClickListeaner onListItemClickListeaner) {
        this.context = context;
        this.steps = new ArrayList<>();
        if (stepsList != null){
            this.steps.addAll(stepsList);
        }
        this.onListItemClickListeaner = onListItemClickListeaner;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recipe_step_item,parent,false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        RecipeStep step = steps.get(position);
        holder.bind(
                step.getId(),
                step.getShortDescription(),
                step.getDescription()
        );
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView idTV, shortDescriptionTV, descriptionTV;

        public StepViewHolder(View itemView) {
            super(itemView);

            idTV = itemView.findViewById(R.id.id_tv);
            shortDescriptionTV = itemView.findViewById(R.id.short_description_tv);
            descriptionTV = itemView.findViewById(R.id.description_tv);
            itemView.setOnClickListener(this);
        }

        public void bind(String id, String shortDescription, String description){
            if (description.length() > 100){
                description = description.substring(0,100) + "...";
            }
            if (id != null) idTV.setText(id);
            if (shortDescription != null)  shortDescriptionTV.setText(shortDescription);
            if (description != null) descriptionTV.setText(description);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            onListItemClickListeaner.onListItemClicked(adapterPosition);
        }
    }

    public interface OnListItemClickListeaner{
        void onListItemClicked(int position);
    }
}
