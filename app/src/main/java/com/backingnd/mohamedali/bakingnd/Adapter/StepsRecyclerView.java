package com.backingnd.mohamedali.bakingnd.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.backingnd.mohamedali.bakingnd.Models.RecipeStep;
import com.backingnd.mohamedali.bakingnd.R;

import java.util.List;

public class StepsRecyclerView extends RecyclerView.Adapter<StepsRecyclerView.StepViewHolder> {
    Context context;
    List<RecipeStep> steps;

    public StepsRecyclerView(Context context, List<RecipeStep> steps) {
        this.context = context;
        this.steps = steps;
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
                step.getDescription(),
                step.getShortDescription()
        );
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class StepViewHolder extends RecyclerView.ViewHolder {
        TextView idTV, shortDescriptionTV, descriptionTV;

        public StepViewHolder(View itemView) {
            super(itemView);

            idTV = itemView.findViewById(R.id.id_tv);
            shortDescriptionTV = itemView.findViewById(R.id.short_description_tv);
            descriptionTV = itemView.findViewById(R.id.description_tv);
        }

        public void bind(String id, String shortDescription, String description){
            if (id != null) idTV.setText(id);
            if (shortDescription != null)  shortDescriptionTV.setText(shortDescription);
            if (description != null) descriptionTV.setText(description);
        }
    }
}
