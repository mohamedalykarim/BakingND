package com.backingnd.mohamedali.bakingnd;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.backingnd.mohamedali.bakingnd.Adapter.RecipeArrayAdpater;
import com.backingnd.mohamedali.bakingnd.Database.RecipeContract;
import com.backingnd.mohamedali.bakingnd.Models.Ingredient;
import com.backingnd.mohamedali.bakingnd.Models.Recipe;
import com.backingnd.mohamedali.bakingnd.Network.RecipeClient;
import com.backingnd.mohamedali.bakingnd.Network.RecipeService;
import com.backingnd.mohamedali.bakingnd.Utilities.RecicpeDataStoringUtil;
import com.backingnd.mohamedali.bakingnd.Utilities.RecipeJSONUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    ListView recipesListView;
    List<Recipe> recipes;
    RecipeArrayAdpater recipeArrayAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipesListView = findViewById(R.id.recipes_list_view);

        getRecipes();

        recipes = new ArrayList<>();
        recipeArrayAdpater = new RecipeArrayAdpater(this,recipes);

        recipesListView.setAdapter(recipeArrayAdpater);

    }


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
            recipeArrayAdpater.notifyDataSetChanged();
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
