package com.backingnd.mohamedali.bakingnd.Services;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.backingnd.mohamedali.bakingnd.Database.RecipeContract;
import com.backingnd.mohamedali.bakingnd.R;

public class WidgetListViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewRemoteViewsFactory(this.getApplicationContext());
    }
}



class ListViewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
    Context mContext;
    Cursor cursor;

    public ListViewRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Uri uri = RecipeContract.IngredientsEntry.CONTENT_URI;


        cursor = mContext.getContentResolver().query(
                uri,
                null,
                null,
                null,
                null);



    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (cursor == null)return  0;
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (cursor == null || cursor.getCount() == 0) return null;
        cursor.moveToPosition(position);

        String ingredientName = cursor.getString(cursor.getColumnIndex(RecipeContract.IngredientsEntry.INGREDIENT_COLUMN));
        String ingredientDetails=
                cursor.getString(
                        cursor.getColumnIndex(RecipeContract.IngredientsEntry.MEASURE_COLUMN))
                        +" "
                        + cursor.getString(cursor.getColumnIndex(RecipeContract.IngredientsEntry.MEASURE_COLUMN
                ))
                ;

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredient_item);
        views.setTextViewText(R.id.ingredient_name,ingredientName);
        views.setTextViewText(R.id.ingredient_details, ingredientDetails);


        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}



