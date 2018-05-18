package com.backingnd.mohamedali.bakingnd.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.backingnd.mohamedali.bakingnd.DetailsActivity;
import com.backingnd.mohamedali.bakingnd.Models.Recipe;
import com.backingnd.mohamedali.bakingnd.R;
import com.backingnd.mohamedali.bakingnd.Utils.BackgroundUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.internal.Util;

public class RecipeArrayAdpater extends ArrayAdapter<Recipe> {
    private RelativeLayout recipeItemContent;
    private TextView recipeTitle;

    public RecipeArrayAdpater(@NonNull Context context, List<Recipe> recipes) {
        super(context, 0, recipes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.recipe_list_item,parent,false);

        recipeTitle = view.findViewById(R.id.recipe_title);

        // Change Background color of the item
        recipeItemContent = view.findViewById(R.id.recipe_item_content);
        recipeItemContent.setBackgroundResource(BackgroundUtils.getColoredBackground(position));

        final Recipe recipe = getItem(position);


        recipeItemContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra(Intent.EXTRA_COMPONENT_NAME, recipe);
                getContext().startActivity(intent);
            }
        });

        recipeTitle.setText(recipe.getName());


        return view;

    }
}
