package com.rubahapi.iklaniku.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by prasetia on 1/10/2017
 */

public class SpotIklanProvider extends ContentProvider {

    private static  final UriMatcher sUriMatcher = buildUriMatcher();
    private SpotIklanDbHelper spotIklanDbHelper;

    private static final int VEHICLE = 100;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SpotIklanContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, SpotIklanContract.PATH_SPOT_IKLAN + "/" + SpotIklanContract.VehicleEntry.PATH_VEHICLE, VEHICLE);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        spotIklanDbHelper = new SpotIklanDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor = null;

        switch (sUriMatcher.match(uri)){
            case VEHICLE: {
                retCursor = getWeather(uri, projection, selection, selectionArgs, sortOrder);
                break;
            }
        }
        return retCursor;
    }

    private Cursor getWeather(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor = spotIklanDbHelper.getReadableDatabase().query(
                SpotIklanContract.VehicleEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        if (getContext()!=null){
            retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        }
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
