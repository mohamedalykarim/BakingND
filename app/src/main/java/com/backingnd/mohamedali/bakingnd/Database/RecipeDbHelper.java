package com.backingnd.mohamedali.bakingnd.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import static com.backingnd.mohamedali.bakingnd.Database.RecipeContract.IngredientsEntry.TABLE_NAME;

public class RecipeDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "recipe.db";
    public static final int DATABASE_VERSION = 1;

    public static final String  CREATE_INGREDIENTS_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
     RecipeContract.IngredientsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            RecipeContract.IngredientsEntry.RECIPE_ID + " INTEGER NOT NULL, " +
            RecipeContract.IngredientsEntry.INGREDIENT_COLUMN + " TEXT NOT NULL, " +
            RecipeContract.IngredientsEntry.MEASURE_COLUMN + " TEXT NOT NULL, "+
        RecipeContract.IngredientsEntry.QUANTITY_COLUMN + " INTEGER NOT NULL"+
            ");";

    public static final String  DROP_INGREDIENTS_TABLE = "DROP TABLE IF EXISTS " +
            RecipeContract.IngredientsEntry.TABLE_NAME;

    public RecipeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_INGREDIENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_INGREDIENTS_TABLE);
        onCreate(db);
    }
}
