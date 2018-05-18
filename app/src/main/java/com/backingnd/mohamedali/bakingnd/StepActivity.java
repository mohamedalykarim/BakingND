package com.backingnd.mohamedali.bakingnd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.backingnd.mohamedali.bakingnd.Fragments.StepDetailsFragment;
import com.backingnd.mohamedali.bakingnd.Models.RecipeStep;
import com.backingnd.mohamedali.bakingnd.Utils.ConstantUtils;


public class StepActivity extends AppCompatActivity {
    StepDetailsFragment stepDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent intent = getIntent();
        RecipeStep step = intent.getParcelableExtra(ConstantUtils.STEP);

        stepDetailsFragment = new StepDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantUtils.STEP, step);
        stepDetailsFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.stepDetailsFragment, stepDetailsFragment)
                .commit();
    }
}
