package ftmk.bitp3453.labtest;

import static android.content.Context.MODE_PRIVATE;
import static android.media.CamcorderProfile.getAll;

import static java.lang.Long.getLong;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Menu;

import java.util.ArrayList;
import java.util.Random;

public class DBHandler extends SQLiteOpenHelper {

    int latestRowInsert;
    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "pizzabumps";

    // below int is our database version
    private static final int DB_VERSION = 1;

    private static final String TABLE_ORDER = "orderpizza";
    private static final String ORDERID_COL = "orderID";
    private static final String PIZZAID_COL = "pizzaID";
    private static final String ORDERQTY_COL = "quantity";

    private static final String TABLE_ADMIN = "admin";
    private static final String USER_COL = "usernameadmin";
    private static final String PASS_COL = "passwordadmin";

    private static final String TABLE_PIZZA = "pizza";
    private static final String PIZZANAME_COL = "pizzaName";
    private static final String PIZZAPRICE_COL = "pizzaPrice";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_ORDER + " ("
                + ORDERID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PIZZAID_COL + " TEXT,"
                + ORDERQTY_COL + " INTEGER)";
        db.execSQL(query);

        String query2 = "CREATE TABLE " + TABLE_ADMIN + " ("
                + USER_COL + " TEXT PRIMARY KEY,"
                + PASS_COL + " TEXT)";
        db.execSQL(query2);

        String query3 = "CREATE TABLE " + TABLE_PIZZA + " ("
                + PIZZAID_COL +	" TEXT PRIMARY KEY,"
                + PIZZANAME_COL + " TEXT,"
                + PIZZAPRICE_COL + " REAL)";
        db.execSQL(query3);
    }

    public void insertOrder(String pizzaID, int quantity)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PIZZAID_COL, pizzaID);
        values.put(ORDERQTY_COL, quantity);

        db.insert(TABLE_ORDER, null, values);

        db.close();
    }

    public boolean checkAdmin (String usernameadmin, String passwordadmin) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM admin WHERE usernameadmin = ? and passwordadmin = ?", new String[]{usernameadmin, passwordadmin});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor getMenu(String pizza) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM (SELECT * FROM pizza ORDER BY pizzaID DESC LIMIT 2) ORDER BY pizzaID ASC";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getLastRows(String orderpizza) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + orderpizza, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        if(count == 1){
            return db.rawQuery("SELECT * FROM (SELECT * FROM orderpizza ORDER BY orderID DESC LIMIT 2) ORDER BY orderID ASC", null);
        }

        else {
            return db.rawQuery("SELECT * FROM " + orderpizza + " ORDER BY orderID DESC LIMIT 1", null);
        }
    }

    // we have created a new method for reading all the courses.
    public ArrayList<Pizza> readOrders() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorOrder = db.rawQuery("SELECT * FROM " + TABLE_ORDER, null);

        // on below line we are creating a new array list.
        ArrayList<Pizza> orderModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorOrder.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                orderModalArrayList.add(new Pizza(cursorOrder.getString(0),
                        cursorOrder.getString(1),
                        cursorOrder.getString(2)));
            } while (cursorOrder.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorOrder.close();
        return orderModalArrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PIZZA);
        onCreate(db);
    }
}
