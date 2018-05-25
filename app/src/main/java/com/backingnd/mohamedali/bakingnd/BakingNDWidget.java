package com.backingnd.mohamedali.bakingnd;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.backingnd.mohamedali.bakingnd.Database.RecipeContract;
import com.backingnd.mohamedali.bakingnd.Models.Ingredient;
import com.backingnd.mohamedali.bakingnd.Services.RecipeWidgetService;
import com.backingnd.mohamedali.bakingnd.Utilities.ConstantUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingNDWidget extends AppWidgetProvider {

    static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, List<Ingredient> ingredients) {


        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_ndwidget);




        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

        views.setOnClickPendingIntent(R.id.widgetContainer,pendingIntent);




        /**
         * When updating the widget
         */

        views.removeAllViews(R.id.widget_ingridient_container);



        for (int i = 0 ; i < ingredients.size(); i++){
            RemoteViews newView = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient_item);
            newView.setTextViewText(R.id.ingredient_name,ingredients.get(i).getIngredient());
            newView.setTextViewText(
                    R.id.ingredient_quantity,
                    ingredients.get(i).getQuantity()
                            + " " + ingredients.get(i).getMeasure()
            );



            views.addView(R.id.widget_ingridient_container,newView);
        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] ids, List<Ingredient> ingredients){


        for (int id: ids){
            updateAppWidget(context,appWidgetManager,id,ingredients);
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RecipeWidgetService.startUpdateRecipeWidgetService(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

