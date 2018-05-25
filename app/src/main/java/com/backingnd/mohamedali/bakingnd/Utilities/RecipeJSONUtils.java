package com.backingnd.mohamedali.bakingnd.Utilities;

import com.backingnd.mohamedali.bakingnd.Models.Ingredient;
import com.backingnd.mohamedali.bakingnd.Models.Recipe;
import com.backingnd.mohamedali.bakingnd.Models.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecipeJSONUtils {

    /**
     * Get the recipe from json String
     * @param json : JSON String that retrieve it from internet
     * @return List of the Recipes
     */
    public static List<Recipe> getRecipes(String json){

        List<Recipe> recipes = new ArrayList<>();

        JSONArray recipesJsonArray = null;
        try {
            recipesJsonArray = new JSONArray(json);
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

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return recipes;

    }

    /**
     * Get the recipe steps from the recipe
     * @param recipe : the recipe that want to get the steps from it
     * @return List of the recipe steps
     */
    public static List<RecipeStep> getStepsFromRecipe(Recipe recipe){
        List<RecipeStep> steps = new ArrayList<>();

        if (recipe == null){
            return null;
        }

        try {
            String json = recipe.getSteps();
            JSONArray stepsJsonArray = new JSONArray(json);

            for (int i = 0; i < stepsJsonArray.length(); i++) {

                JSONObject stepJsonObject = stepsJsonArray.getJSONObject(i);
                int id = stepJsonObject.getInt(ConstantUtils.ID);
                String description = stepJsonObject.getString(ConstantUtils.DESCRIPTION);
                String shortDescription = stepJsonObject.getString(ConstantUtils.SHORT_DESCRIPTION);
                String videoURL = stepJsonObject.getString(ConstantUtils.VIDEO_URL);
                String thumbnailURL = stepJsonObject.getString(ConstantUtils.THUMBNAIL_URL);
                steps.add(new RecipeStep(String.valueOf(id),shortDescription,description,videoURL,thumbnailURL));
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }


        return steps;
    }

    /**
     * Get the ingredients from the recipe
     * @param recipe : the recipe that want to get the ingredients from it
     * @return List of the ingredients
     */

    public static List<Ingredient> getIngredientsFromRecipe(Recipe recipe) {
        List<Ingredient> ingredients = new ArrayList<>();

        if (recipe == null){
            return null;
        }

        try {
            String json = recipe.getIngredients();
            JSONArray ingredientsJsonArray = new JSONArray(json);

            for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                JSONObject ingredientJsonObject = ingredientsJsonArray.getJSONObject(i);
                int quantity = ingredientJsonObject.getInt(ConstantUtils.QUABTITY);
                String measure = ingredientJsonObject.getString(ConstantUtils.MEASURE);
                String ingredient = ingredientJsonObject.getString(ConstantUtils.INGREDIENT);

                ingredients.add(new Ingredient(quantity,measure,ingredient));

            }

        } catch (JSONException e) {
            e.printStackTrace();

        }


        return ingredients;
    }
}
