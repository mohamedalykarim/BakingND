package com.backingnd.mohamedali.bakingnd.Database;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.backingnd.mohamedali.bakingnd.Database.RecipeContract.IngredientsEntry.TABLE_NAME;

public class RecipeContentProvider extends ContentProvider {

    public static final int INGREDIENTS_CODE = 100;

    private RecipeDbHelper mRecipeDbHelper;
    private UriMatcher mUriMatcher = buildUriMatcher();





    @Override
    public boolean onCreate() {
        mRecipeDbHelper = new RecipeDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (mUriMatcher.match(uri)){
            case INGREDIENTS_CODE:
                cursor = mRecipeDbHelper.getReadableDatabase()
                        .query(
                                TABLE_NAME,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null,
                                sortOrder
                        );
                break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;
        switch (mUriMatcher.match(uri)){
            case INGREDIENTS_CODE:
                numRowsDeleted = mRecipeDbHelper.getWritableDatabase()
                        .delete(
                                TABLE_NAME,
                                selection,
                                selectionArgs
                                );
                break;

                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase database = mRecipeDbHelper.getWritableDatabase();

        switch (mUriMatcher.match(uri)){
            case INGREDIENTS_CODE:
                database.beginTransaction();
                int rowsInserted = 0;

                try {
                    for (ContentValues value: values){
                        long _id = database.insert(
                                TABLE_NAME,
                                null,
                                value);

                        if (_id != -1){
                            rowsInserted ++;
                        }
                    }
                    database.setTransactionSuccessful();
                }finally {
                    database.endTransaction();
                }

                if (rowsInserted > 0){
                    getContext().getContentResolver().notifyChange(uri,null);
                }

                return rowsInserted;


                default:
                    return super.bulkInsert(uri,values);
        }
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mRecipeDbHelper.close();
        super.shutdown();
    }

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = RecipeContract.CONTENT_AUTHORITY;
        uriMatcher.addURI(
                authority,
                RecipeContract.PATH_INGREDIENTS,
                INGREDIENTS_CODE);
        return uriMatcher;
    }
}
