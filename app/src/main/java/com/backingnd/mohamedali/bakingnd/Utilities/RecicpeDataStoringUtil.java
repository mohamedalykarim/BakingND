package com.backingnd.mohamedali.bakingnd.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class RecicpeDataStoringUtil {

    public static void storeDesiredRecipeId(Context context, int id){
        SharedPreferences.Editor editor =
                context.getSharedPreferences(
                        ConstantUtils.SHARED_PREFERENCES_NAME,
                        Context.MODE_PRIVATE
                ).edit();

        editor.putInt(ConstantUtils.DESIRED_RECIPE_ID,id);
        editor.apply();
    }


    public static int getDesiredRecipeId(Context context){
        int id = 0;

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                ConstantUtils.SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE
        );

        if (sharedPreferences.contains(ConstantUtils.DESIRED_RECIPE_ID)){
            id = sharedPreferences.getInt(ConstantUtils.DESIRED_RECIPE_ID,0);
        }

        return id;
    }
}
