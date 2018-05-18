package com.backingnd.mohamedali.bakingnd.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.backingnd.mohamedali.bakingnd.R;

public class RecipeStepsFragment extends Fragment {
    public RecipeStepsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_steps_fragmet,container,false);
        RecyclerView recipeDetailsRecyclerView = view.findViewById(R.id.steps_recycler_view);


        return view;
    }


}
