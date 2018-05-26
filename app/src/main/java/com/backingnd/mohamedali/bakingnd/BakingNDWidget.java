package com.backingnd.mohamedali.bakingnd;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.widget.RemoteViews;

import com.backingnd.mohamedali.bakingnd.Models.Ingredient;
import com.backingnd.mohamedali.bakingnd.Services.RecipeWidgetService;
import com.backingnd.mohamedali.bakingnd.Services.WidgetListViewService;
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


        /**
         * Set clicks on the widget intent to main activity
         */

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

        views.setOnClickPendingIntent(R.id.widgetContainer,pendingIntent);

        Intent intent1 = new Intent(context, WidgetListViewService.class);
        views.setRemoteAdapter(R.id.widget_ingridient_container,intent1);



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    /**
     * updating all the widgets
     */
    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] ids, List<Ingredient> ingredients){


        for (int id: ids){
            updateAppWidget(context,appWidgetManager,id,ingredients);
        }

    }

    public RemoteViews getIngredientsListRemoteView(Context context){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.id.widget_ingridient_container);

        return remoteViews;
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

