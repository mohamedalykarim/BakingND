package com.backingnd.mohamedali.bakingnd.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.backingnd.mohamedali.bakingnd.Adapter.StepsRecyclerViewAdapter;
import com.backingnd.mohamedali.bakingnd.DetailsActivity;
import com.backingnd.mohamedali.bakingnd.Models.RecipeStep;
import com.backingnd.mohamedali.bakingnd.R;
import com.backingnd.mohamedali.bakingnd.StepActivity;
import com.backingnd.mohamedali.bakingnd.Utils.ConstantUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecipeStepsFragment extends Fragment implements StepsRecyclerViewAdapter.OnListItemClickListeaner {
    Context context;
    ArrayList<RecipeStep> steps;


    public RecipeStepsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_steps_fragmet,container,false);
        RecyclerView recipeDetailsRecyclerView = view.findViewById(R.id.steps_recycler_view);

        context = container.getContext();

        DetailsActivity activity = (DetailsActivity) getActivity();

        steps = new ArrayList<>();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
             ArrayList<RecipeStep> s = bundle.getParcelableArrayList(ConstantUtils.STEPS);
             steps.addAll(s);
        }


        StepsRecyclerViewAdapter recyclerViewAdapter = new StepsRecyclerViewAdapter(getContext(),steps,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recipeDetailsRecyclerView.setLayoutManager(linearLayoutManager);
        recipeDetailsRecyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        return view;
    }



    /**
     * implementation of the clicking interface method that in the
     * recycler view adapter
     * @param position : the position of step item
     */
    @Override
    public void onListItemClicked(int position) {
        Intent intent = new Intent(context, StepActivity.class);
        intent.putExtra(ConstantUtils.STEP, steps.get(position));
        context.startActivity(intent);
    }




}
