package com.backingnd.mohamedali.bakingnd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.backingnd.mohamedali.bakingnd.Adapter.RecipeArrayAdpater;
import com.backingnd.mohamedali.bakingnd.Models.Recipe;
import com.backingnd.mohamedali.bakingnd.Network.RecipeClient;
import com.backingnd.mohamedali.bakingnd.Network.RecipeService;
import com.backingnd.mohamedali.bakingnd.Utils.ConstantUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        int i = 2/6;


    }

    /**
     *
     */

    public void getRecipes(){
        final RecipeService recipeService = RecipeClient
                .getClient().create(RecipeService.class);
        Call<ResponseBody> call = recipeService.getRecipeJsonString();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {



                    JSONArray recipesJsonArray = new JSONArray(response.body().string());
                    for (int i=0; i<recipesJsonArray.length();i++){
                        JSONObject recipeJsonObject = recipesJsonArray.getJSONObject(i);
                        int id = recipeJsonObject.getInt(ConstantUtils.ID);
                        String name = recipeJsonObject.getString(ConstantUtils.NAME);
                        String ingredients = recipeJsonObject.getString(ConstantUtils.INGREDIENTS);
                        String steps = recipeJsonObject.getString(ConstantUtils.STEPS);
                        int serving = recipeJsonObject.getInt(ConstantUtils.SERVING);
                        String image = recipeJsonObject.getString(ConstantUtils.IMAGE);

                        recipes.add(new Recipe(
                                id,
                                serving,
                                name,
                                image,
                                ingredients,
                                steps
                                ));
                    }

                    recipeArrayAdpater.notifyDataSetChanged();







                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
