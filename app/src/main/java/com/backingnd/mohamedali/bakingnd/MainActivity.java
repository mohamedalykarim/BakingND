package com.backingnd.mohamedali.bakingnd;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.backingnd.mohamedali.bakingnd.Adapter.RecipeRecyclerViewAdapter;
import com.backingnd.mohamedali.bakingnd.Models.Recipe;
import com.backingnd.mohamedali.bakingnd.Network.RecipeClient;
import com.backingnd.mohamedali.bakingnd.Network.RecipeService;
import com.backingnd.mohamedali.bakingnd.Utilities.RecipeJSONUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    List<Recipe> recipes;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    RecipeRecyclerViewAdapter recipeRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recipes_list_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recipes = new ArrayList<>();
        recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(this,recipes);
        recyclerView.setAdapter(recipeRecyclerViewAdapter);

        getRecipes();

    }


    /**
     * Get the recipes from the internet and add them to the list view
     */

    private class GetRecipes extends AsyncTask<Call,Void,String>{

        @Override
        protected String doInBackground(Call... calls) {
            try {
                Call<ResponseBody> call = calls[0];
                Response<ResponseBody> response = call.execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            recipes.addAll(RecipeJSONUtils.getRecipes(s));
            recipeRecyclerViewAdapter.notifyDataSetChanged();
        }
    }


    public void getRecipes(){
        final RecipeService recipeService = RecipeClient
                .getClient().create(RecipeService.class);
        Call<ResponseBody> call = recipeService.getRecipeJsonString();

        GetRecipes getRecipes = new GetRecipes();
        getRecipes.execute(call);

    }
}
