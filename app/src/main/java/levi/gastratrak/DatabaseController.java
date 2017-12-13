package levi.gastratrak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;

class DatabaseController extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 7;

    // Database Name
    private static final String DATABASE_NAME = "pain_and_food_diary";

    // Contacts table name
    private static final String TABLE_PAIN = "pain";
    private static final String TABLE_FOOD = "food";
    private static final String TABLE_STOOL = "stool";

    // Pain Table Columns names
    private static final String KEY_PAIN_ID = "id";
    private static final String KEY_PAIN_TIME = "time"; //long - standard time format(milliseconds since 1970)
    private static final String KEY_PAIN_TOTAL = "TotalPain";
    private static final String KEY_PAIN_OTHER = "OtherPain";
    private static final String KEY_PAIN_UPPER = "UpperStomachPain";
    private static final String KEY_PAIN_LOWER = "LowerStomachPain";

    // Food Table Columns names
    private static final String KEY_FOOD_ID = "id";
    private static final String KEY_FOOD_TIME = "time"; //long - standard time format(milliseconds since 1970)
    private static final String KEY_FOOD_ITEM = "oldItem";

    // Stool Table Columns names
    private static final String KEY_STOOL_ID = "id";
    private static final String KEY_STOOL_TIME = "time"; //long - standard time format(milliseconds since 1970)
    private static final String KEY_STOOL_CONSISTENCY = "consistency";
    private static final String KEY_STOOL_WETNESS = "wetness";
    private static final String KEY_STOOL_DIFFICULTY = "difficulty";


    public DatabaseController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PAIN_TABLE = "CREATE TABLE " + TABLE_PAIN + "(" + KEY_PAIN_ID +
                " INTEGER PRIMARY KEY," + KEY_PAIN_TIME +
                " INTEGER, " + KEY_PAIN_TOTAL + " INTEGER, " + KEY_PAIN_OTHER + " INTEGER, " +
                KEY_PAIN_UPPER + " INTEGER, " + KEY_PAIN_LOWER + " INTEGER )";

        String CREATE_FOOD_TABLE = "CREATE TABLE " + TABLE_FOOD + " ( " + KEY_FOOD_ID +
                " INTEGER PRIMARY KEY, " + KEY_FOOD_TIME +
                " INTEGER, " + KEY_FOOD_ITEM + " TEXT )";

        String CREATE_STOOL_TABLE = "CREATE TABLE " + TABLE_STOOL + "(" + KEY_STOOL_ID +
                " INTEGER PRIMARY KEY, " + KEY_STOOL_TIME +
                " INTEGER, " + KEY_STOOL_CONSISTENCY + " INTEGER, " + KEY_STOOL_WETNESS + " INTEGER, " +
                KEY_STOOL_DIFFICULTY + " INTEGER )";

        db.execSQL(CREATE_PAIN_TABLE);
        db.execSQL(CREATE_FOOD_TABLE);
        db.execSQL(CREATE_STOOL_TABLE);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOOL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAIN);

        // Create tables again
        onCreate(db);
    }

    public void addFoodItem(FoodItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FOOD_TIME, item.getFoodTime().getTime()); // Time Consumed
        values.put(KEY_FOOD_ITEM, item.getFoodItem()); // Food Name

        db.insert(TABLE_FOOD, null, values);
        db.close();
    }

    public void removeFoodItem(FoodItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_FOOD, KEY_FOOD_ITEM + " = ?", new String[]{item.getFoodItem()});
        db.close();
}

    public ArrayList<FoodItem> getAllFoodItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<FoodItem> result = new ArrayList<>();

        try {
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_FOOD;

            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    FoodItem item = new FoodItem(cursor.getString(2), new Time(cursor.getLong(1)));
                    result.add(item);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return result;
        } catch (Exception e) {
            Log.e("allfooditems", "" + e);
        }

        return result;
    }


    public void addPainRecording(PainItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        int[] painArray = item.getPainLevel();
        ContentValues values = new ContentValues();

        values.put(KEY_PAIN_TIME, item.getPainTime().getTime()); // Time recorded
        values.put(KEY_PAIN_TOTAL, painArray[0]);
        values.put(KEY_PAIN_OTHER, painArray[1]);
        values.put(KEY_PAIN_UPPER, painArray[2]);
        values.put(KEY_PAIN_LOWER, painArray[3]);
        db.insert(TABLE_PAIN, null, values);
        db.close();
    }
    public ArrayList<PainItem> getAllPainItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<PainItem> result = new ArrayList<>();

        try {
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_PAIN;

            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    int[] painArray = new int[] {cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5)};
                    PainItem item = new PainItem(painArray, new Time(cursor.getLong(1)));
                    result.add(item);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return result;
        } catch (Exception e) {
            Log.e("allpainitems", "" + e);
        }

        return result;
    }



    public void addStoolRecording(StoolItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        int[] stoolArray = item.getStoolArray();
        ContentValues values = new ContentValues();

        values.put(KEY_STOOL_CONSISTENCY, stoolArray[0]);
        values.put(KEY_STOOL_WETNESS, stoolArray[1]);
        values.put(KEY_STOOL_DIFFICULTY, stoolArray[2]);
        values.put(KEY_STOOL_TIME, item.getStoolTime().getTime());
        db.insert(TABLE_STOOL, null, values);
        db.close();
    }
}