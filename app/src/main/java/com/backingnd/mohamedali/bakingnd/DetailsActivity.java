package com.backingnd.mohamedali.bakingnd;

import android.app.Fragment;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.backingnd.mohamedali.bakingnd.Fragments.RecipeStepsFragment;
import com.backingnd.mohamedali.bakingnd.Models.Recipe;
import com.backingnd.mohamedali.bakingnd.Models.RecipeStep;
import com.backingnd.mohamedali.bakingnd.Utils.ConstantUtils;
import com.backingnd.mohamedali.bakingnd.Utils.RecipeJSONUtils;


import java.util.ArrayList;
import java.util.List;

import static com.backingnd.mohamedali.bakingnd.Utils.ConstantUtils.STEPS;

public class DetailsActivity extends AppCompatActivity {
    List<RecipeStep> steps;
    RecipeStepsFragment stepsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        Recipe recipe = getIntent().getParcelableExtra(Intent.EXTRA_COMPONENT_NAME);
        steps = new ArrayList<>();
        steps.addAll(RecipeJSONUtils.getStepsFromRecipe(recipe));

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ConstantUtils.STEPS, (ArrayList<? extends Parcelable>) steps);


            if (getSupportFragmentManager().getFragments().size() == 0){
                stepsFragment = new RecipeStepsFragment();
                stepsFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.steps_fragment,stepsFragment)
                        .commit();
            }


    }


}
