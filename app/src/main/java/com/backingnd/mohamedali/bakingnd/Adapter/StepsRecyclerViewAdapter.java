package com.backingnd.mohamedali.bakingnd.Adapter;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.backingnd.mohamedali.bakingnd.BakingNDWidget;
import com.backingnd.mohamedali.bakingnd.Database.RecipeContract;
import com.backingnd.mohamedali.bakingnd.Models.Ingredient;
import com.backingnd.mohamedali.bakingnd.Models.RecipeStep;
import com.backingnd.mohamedali.bakingnd.R;
import com.backingnd.mohamedali.bakingnd.Services.RecipeWidgetService;

import java.util.ArrayList;
import java.util.List;

public class StepsRecyclerViewAdapter extends RecyclerView.Adapter<StepsRecyclerViewAdapter.StepViewHolder> {
    private static final int FIRST_VIEW = 0;
    private static final int OTHER_VIEWS = 1;


    private Context context;
    private List<RecipeStep> steps;
    private List<Ingredient> ingredients;
    private int recipeID;
    private OnListItemClickListeaner onListItemClickListeaner;


    /**
     *  the constructor of the adapter
     *
     * @param context : the current context
     * @param stepsList List of Recipes class
     * @param onListItemClickListeaner : click listener
     * @param ingredientsList List of ingredients class
     * @param recipeID The recipe id
     */
    public StepsRecyclerViewAdapter(Context context, List<RecipeStep> stepsList, OnListItemClickListeaner onListItemClickListeaner, List<Ingredient> ingredientsList, int recipeID) {
        this.context = context;
        this.steps = new ArrayList<>();
        if (stepsList != null){
            this.steps.addAll(stepsList);
        }

        if (ingredientsList != null){
            this.ingredients = ingredientsList;
        }
        this.onListItemClickListeaner = onListItemClickListeaner;
        this.recipeID = recipeID;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);


        // initialize the resource depending on the the view FIRST_VIEW or OTHER_VIEW
        int resource;
        switch (viewType){
            case FIRST_VIEW:
                resource = R.layout.first_recipe_step_item;
                break;

            case OTHER_VIEWS:
                resource = R.layout.recipe_step_item;
                break;

            default:
                throw new IllegalArgumentException("Invalid view type");

        }

        View view = layoutInflater.inflate(resource,parent,false);


        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StepViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        RecipeStep step = steps.get(position);

        switch (viewType){

            // handling first item view
            case FIRST_VIEW:
                for (int i = 0 ; i < ingredients.size(); i++){
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View view = inflater.inflate(R.layout.ingredient_item,null);
                    TextView ingredientNameTV = view.findViewById(R.id.ingredient_name);
                    TextView ingredientQuantityTV = view.findViewById(R.id.ingredient_quantity);

                    ingredientNameTV.setText(ingredients.get(i).getIngredient());
                    ingredientQuantityTV.setText(ingredients.get(i).getQuantity()+context.getString(R.string.space)+ ingredients.get(i).getMeasure());

                    holder.ingridientContainer.addView(view);
                }


                /** handling the status of the switch
                 * make it checked if there is ingredients or unchecked if there is no
                  */

                final Uri uri = RecipeContract.IngredientsEntry.CONTENT_URI;

                final Cursor cursor = context.getContentResolver().query(
                        uri,
                        null,
                        null,
                        null,
                        null,
                        null
                );


                if (cursor.getCount() > 0){
                    cursor.moveToFirst();
                    int id = cursor.getInt(cursor.getColumnIndex(RecipeContract.IngredientsEntry.RECIPE_ID));
                    if (id == recipeID)
                    holder.widgetSwitch.setChecked(true);
                }else{
                    holder.widgetSwitch.setChecked(false);
                }


                /**
                 *  Widget switch check handling
                 *  when check if it's checked it will delete the ingredients from the database
                 *  if it's not checked it will delete the ingredients and add new ingredients
                 */

                holder.widgetSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            if (cursor.getCount() > 0){
                                context.getContentResolver().delete(
                                        uri,
                                        null,
                                        null
                                );
                            }

                            ContentValues[] values = new ContentValues[ingredients.size()];
                            for (int i = 0; i < values.length; i++){
                                values[i] = new ContentValues();
                                values[i].put(RecipeContract.IngredientsEntry.RECIPE_ID, recipeID);
                                values[i].put(RecipeContract.IngredientsEntry.QUANTITY_COLUMN, ingredients.get(i).getQuantity());
                                values[i].put(RecipeContract.IngredientsEntry.MEASURE_COLUMN, ingredients.get(i).getMeasure());
                                values[i].put(RecipeContract.IngredientsEntry.INGREDIENT_COLUMN, ingredients.get(i).getIngredient());
                            }
                            int count = context.getContentResolver().bulkInsert(uri,values);
                            if (count > 0){
                                RecipeWidgetService.startUpdateRecipeWidgetService(context);
                            }

                        }else{
                            if (cursor.getCount() > 0){
                                context.getContentResolver().delete(
                                        uri,
                                        null,
                                        null
                                );

                            }


                        }

                    }
                });

                cursor.close();



                holder.bind(
                        step.getId(),
                        step.getShortDescription(),
                        step.getDescription()
                );
                break;



            // handling other items views

            case OTHER_VIEWS:

                holder.bind(
                        step.getId(),
                        step.getShortDescription(),
                        step.getDescription()
                );

                break;
        }

    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    /**
     * Step View Holder
     */

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView idTV, shortDescriptionTV, descriptionTV;
        LinearLayout ingridientContainer;
        Switch widgetSwitch;

        private StepViewHolder(View itemView) {
            super(itemView);

            idTV = itemView.findViewById(R.id.id_tv);
            shortDescriptionTV = itemView.findViewById(R.id.short_description_tv);
            descriptionTV = itemView.findViewById(R.id.description_tv);
            ingridientContainer = itemView.findViewById(R.id.ingridient_container);
            widgetSwitch = itemView.findViewById(R.id.widget_switch);
            itemView.setOnClickListener(this);
        }

        /**
         * binding the data with view
         * @param id : Step Id
         * @param shortDescription : Step Short Description
         * @param description : Step Description
         */
        private void bind(String id, String shortDescription, String description){
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


    @Override
    public int getItemViewType(int position){
        if (position == 0){
            return FIRST_VIEW;
        }else {
            return OTHER_VIEWS;
        }
    }
}


