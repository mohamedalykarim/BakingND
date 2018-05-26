package com.backingnd.mohamedali.bakingnd.Services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.backingnd.mohamedali.bakingnd.BakingNDWidget;
import com.backingnd.mohamedali.bakingnd.Database.RecipeContract;
import com.backingnd.mohamedali.bakingnd.Models.Ingredient;
import com.backingnd.mohamedali.bakingnd.R;
import com.backingnd.mohamedali.bakingnd.Utilities.ConstantUtils;

import java.util.ArrayList;

public class RecipeWidgetService extends IntentService {


    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }

    public static void startUpdateRecipeWidgetService(Context context){
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ConstantUtils.UPDATE_INGREDIANTS_ACTION);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null){
            final String action = intent.getAction();
            if (ConstantUtils.UPDATE_INGREDIANTS_ACTION.equals(action)){
                handleUpdate();
            }
        }
    }

    /**
     * Updating the widget with new ingredients
     */

    private void handleUpdate() {

        Uri uri = RecipeContract.IngredientsEntry.CONTENT_URI;
        Cursor cursor  = getContentResolver().query(
                uri,
                null,
                null,
                null,
                null,
                null);

        ArrayList<Ingredient> ingredientsArray = new ArrayList<>();

        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                ingredientsArray.add(new Ingredient(
                        cursor.getInt(cursor.getColumnIndex(RecipeContract.IngredientsEntry.QUANTITY_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(RecipeContract.IngredientsEntry.MEASURE_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(RecipeContract.IngredientsEntry.INGREDIENT_COLUMN))
                ));
            }

            cursor.close();

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingNDWidget.class));

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_ingridient_container);

            BakingNDWidget.updateWidgets(this, appWidgetManager, appWidgetIds,ingredientsArray);

        }

    }


}
