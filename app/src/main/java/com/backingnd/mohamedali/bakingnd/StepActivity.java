package com.backingnd.mohamedali.bakingnd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.backingnd.mohamedali.bakingnd.Fragments.StepDetailsFragment;
import com.backingnd.mohamedali.bakingnd.Models.RecipeStep;
import com.backingnd.mohamedali.bakingnd.Utilities.ConstantUtils;


public class StepActivity extends AppCompatActivity {
    StepDetailsFragment stepDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent intent = getIntent();
        RecipeStep step = intent.getParcelableExtra(ConstantUtils.STEP);

        if (savedInstanceState == null){
            stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.setStep(step);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.stepDetailsFragment, stepDetailsFragment)
                    .commit();
        }
    }
}
