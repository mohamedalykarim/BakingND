package com.backingnd.mohamedali.bakingnd.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.backingnd.mohamedali.bakingnd.Adapter.StepsRecyclerViewAdapter;
import com.backingnd.mohamedali.bakingnd.DetailsActivity;
import com.backingnd.mohamedali.bakingnd.Models.Ingredient;
import com.backingnd.mohamedali.bakingnd.Models.RecipeStep;
import com.backingnd.mohamedali.bakingnd.R;
import com.backingnd.mohamedali.bakingnd.StepActivity;
import com.backingnd.mohamedali.bakingnd.Utilities.ConstantUtils;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepsFragment extends Fragment implements StepsRecyclerViewAdapter.OnListItemClickListeaner {
    /**
     * This fragment shows all the steps
     */

    Context context;
    RecipeStepsFragment recipeStepsFragment;
    ArrayList<RecipeStep> steps;
    List<Ingredient> ingredients;

    int recipeID;

    Bundle bundle;


    StepsRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recipeDetailsRecyclerView;


    boolean isTwoPane;
    OnClickItemInTwoPane onClickItemInTwoPane;


    public
    RecipeStepsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_steps_fragmet,container,false);
        recipeDetailsRecyclerView = view.findViewById(R.id.steps_recycler_view);

        context = container.getContext();
        recipeStepsFragment = this;

        DetailsActivity activity = (DetailsActivity) getActivity();

        steps = new ArrayList<>();
        ingredients = new ArrayList<>();
        bundle = this.getArguments();

        StepsExecution execution = new StepsExecution();
        execution.execute();


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onClickItemInTwoPane = (OnClickItemInTwoPane) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "Error");
        }
    }

    /**
     * implementation of the clicking interface method that in the
     * recycler view adapter
     * @param position : the position of step item
     */
    @Override
    public void onListItemClicked(int position) {
        if (!isTwoPane){
            Intent intent = new Intent(context, StepActivity.class);
            intent.putExtra(ConstantUtils.STEP, steps.get(position));
            context.startActivity(intent);

        }else{
            onClickItemInTwoPane.onClickItemInTwoPane(steps.get(position));
        }
    }

    public interface OnClickItemInTwoPane{
        void onClickItemInTwoPane(RecipeStep step);
    }


    /**
     * getting the data from the intent
     * handling the Steps Recyclerview and adding the steps item
     * and binding the views by the adapter
     */
    class StepsExecution extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            if (bundle != null) {
                steps = bundle.getParcelableArrayList(ConstantUtils.STEPS);
                ingredients = bundle.getParcelableArrayList(ConstantUtils.INGREDIENTS);
                recipeID = bundle.getInt(ConstantUtils.RECIPE_ID);

                isTwoPane = bundle.getBoolean(ConstantUtils.IS_TWO_PANE);


                recyclerViewAdapter = new StepsRecyclerViewAdapter(
                        getContext(),
                        steps,
                        recipeStepsFragment,
                        ingredients,
                        recipeID
                );
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recipeDetailsRecyclerView.setLayoutManager(linearLayoutManager);
            recipeDetailsRecyclerView.setAdapter(recyclerViewAdapter);
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }






}
