package com.backingnd.mohamedali.bakingnd.Utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.backingnd.mohamedali.bakingnd.Models.Recipe;
import com.backingnd.mohamedali.bakingnd.Models.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeJSONUtils {

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

}
