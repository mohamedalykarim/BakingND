package com.backingnd.mohamedali.bakingnd.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.backingnd.mohamedali.bakingnd.DetailsActivity;
import com.backingnd.mohamedali.bakingnd.Models.Recipe;
import com.backingnd.mohamedali.bakingnd.R;
import com.backingnd.mohamedali.bakingnd.Utilities.BackgroundUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.RecipeViewHolder>{
    /**
     * Recipe Adapter.
     */

    Context context;
    List<Recipe> recipes;

    public RecipeRecyclerViewAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_list_item,parent,false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        final Recipe recipe = recipes.get(position);

        holder.recipeItemContent.setBackgroundResource(BackgroundUtils.getColoredBackground(position));
        holder.recipeItemContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(Intent.EXTRA_COMPONENT_NAME, recipe);
                context.startActivity(intent);
            }
        });

        holder.bind(recipe.getName());


        if (!TextUtils.isEmpty(recipe.getImage())){
            Picasso.get().load(recipe.getImage()).into(holder.circle);
        }

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipeTitle;
        RelativeLayout recipeItemContent;
        CircleImageView circle;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            recipeTitle = itemView.findViewById(R.id.recipe_title);
            recipeItemContent = itemView.findViewById(R.id.recipe_item_content);
            circle = itemView.findViewById(R.id.circle_image);

        }

        public void bind(String title){
            recipeTitle.setText(title);
        }
    }
}
