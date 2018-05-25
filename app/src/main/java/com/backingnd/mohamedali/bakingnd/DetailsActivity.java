package com.backingnd.mohamedali.bakingnd;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.backingnd.mohamedali.bakingnd.Fragments.RecipeStepsFragment;
import com.backingnd.mohamedali.bakingnd.Fragments.StepDetailsFragment;
import com.backingnd.mohamedali.bakingnd.Models.Ingredient;
import com.backingnd.mohamedali.bakingnd.Models.Recipe;
import com.backingnd.mohamedali.bakingnd.Models.RecipeStep;
import com.backingnd.mohamedali.bakingnd.Utilities.ConstantUtils;
import com.backingnd.mohamedali.bakingnd.Utilities.RecipeJSONUtils;


import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements RecipeStepsFragment.OnClickItemInTwoPane {
    List<RecipeStep> steps;
    List<Ingredient> ingredients;
    RecipeStepsFragment stepsFragment;
    StepDetailsFragment stepDetailsFragment;

    boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Recipe recipe = getIntent().getParcelableExtra(Intent.EXTRA_COMPONENT_NAME);

        steps = new ArrayList<>();
        ingredients = new ArrayList<>();
        steps.addAll(RecipeJSONUtils.getStepsFromRecipe(recipe));
        ingredients.addAll(RecipeJSONUtils.getIngredientsFromRecipe(recipe));



        if (savedInstanceState == null){

            /**
             * Two Pane handling
             */

            if(findViewById(R.id.steps_linear_layout) != null){
                mTwoPane = true;


                Bundle data = new Bundle();
                data.putParcelableArrayList(ConstantUtils.STEPS, (ArrayList<? extends Parcelable>) steps);
                data.putParcelableArrayList(ConstantUtils.INGREDIENTS, (ArrayList<? extends Parcelable>) ingredients);
                data.putInt(ConstantUtils.RECIPE_ID,recipe.getId());
                data.putBoolean(ConstantUtils.IS_TWO_PANE, mTwoPane);


                stepsFragment = new RecipeStepsFragment();
                stepsFragment.setArguments(data);


                getSupportFragmentManager().beginTransaction()
                        .add(R.id.steps_fragment,stepsFragment)
                        .commit();


                stepDetailsFragment = new StepDetailsFragment();

                stepDetailsFragment.setStep(steps.get(0));
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.stepDetailsFragment,stepDetailsFragment)
                        .commit();

            } else{
                /**
                 *    One Pane Handling
                 */

                mTwoPane = false;

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(ConstantUtils.STEPS, (ArrayList<? extends Parcelable>) steps);
                bundle.putParcelableArrayList(ConstantUtils.INGREDIENTS, (ArrayList<? extends Parcelable>) ingredients);
                bundle.putInt(ConstantUtils.RECIPE_ID,recipe.getId());
                bundle.putBoolean(ConstantUtils.IS_TWO_PANE, mTwoPane);


                stepsFragment = new RecipeStepsFragment();
                stepsFragment.setArguments(bundle);


                getSupportFragmentManager().beginTransaction()
                        .add(R.id.steps_fragment,stepsFragment)
                        .commit();
            }
        }




    }


    /**
     * Handlin click item on two pane
     */

    @Override
    public void onClickItemInTwoPane(RecipeStep step) {

        StepDetailsFragment newStepDetailsFragment = new StepDetailsFragment();
        newStepDetailsFragment.setStep(step);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.stepDetailsFragment,newStepDetailsFragment)
                .commit();

    }


}
