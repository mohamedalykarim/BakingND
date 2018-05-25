package com.backingnd.mohamedali.bakingnd.Database;

import android.net.Uri;
import android.provider.BaseColumns;

public class RecipeContract {

    /**
     * Recipe Contract that will use in database handling and in content providers
     * it contains Ingredients Entry
     */

    public static final String CONTENT_AUTHORITY = "com.backingnd.mohamedali.bakingnd";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_INGREDIENTS = "ingredients";

    public static final class IngredientsEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_INGREDIENTS)
                .build();

        public static final String TABLE_NAME = "ingredients";

        public static final String RECIPE_ID = "recipe_id";
        public static final String QUANTITY_COLUMN = "quantity";
        public static final String MEASURE_COLUMN = "measure";
        public static final String INGREDIENT_COLUMN = "ingredient";

    }
}
