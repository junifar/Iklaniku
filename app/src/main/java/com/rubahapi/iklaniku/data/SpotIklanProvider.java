package com.rubahapi.iklaniku.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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
                retCursor = getVehicle(uri, projection, selection, selectionArgs, sortOrder);
                break;
            }
        }
        return retCursor;
    }

    private Cursor getVehicle(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
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

        final int match = sUriMatcher.match(uri);

        switch (match){
            case  VEHICLE:
                return SpotIklanContract.VehicleEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = spotIklanDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match){
            case VEHICLE:{
                long _id = db.insert(SpotIklanContract.VehicleEntry.TABLE_NAME, null, contentValues);
                if(_id > 0)
                    returnUri = SpotIklanContract.VehicleEntry.buildVehicleUri(_id);
                else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = spotIklanDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        if (null == selection) selection = "1";
        switch (match){
            case  VEHICLE:
                rowsDeleted = db.delete(SpotIklanContract.VehicleEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(rowsDeleted != 0){
            if (getContext() != null) getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = spotIklanDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match){
            case VEHICLE:
                rowsUpdated = db.update(SpotIklanContract.VehicleEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);
        }
        if (rowsUpdated != 0){
            if (getContext() != null) getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = spotIklanDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match){
            case VEHICLE:
                db.beginTransaction();
                int returnCount = 0;
                try {
                   for (ContentValues value : values){
                       long _id = db.insert(SpotIklanContract.VehicleEntry.TABLE_NAME, null, value);
                       if(_id != -1){
                           returnCount++;
                       }
                       db.setTransactionSuccessful();
                   }
                }finally {
                    db.endTransaction();
                }
                if(getContext() != null) getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public void shutdown() {
        spotIklanDbHelper.close();
        super.shutdown();
    }
}
