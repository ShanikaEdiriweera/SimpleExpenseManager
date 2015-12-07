package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.Constants;

/**
 * Created by SHAEDI on 06/12/2015.
 */
public class DBHandler extends SQLiteOpenHelper{
    private static DBHandler dbHandler = null;

    public DBHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + Constants.TABLE_ACCOUNT + "( Account_No VARCHAR(10) PRIMARY KEY, Account_holder_name VARCHAR(50) NOT NULL," +
                "Bank_name VARCHAR(30) NOT NULL, Balance double default 0 check(Balance>=0) );";

        String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + Constants.TABLE_TRANSACTION + "( Date text," +
                "Account_No VARCHAR(10) NOT NULL, Expense_type VARCHAR(7), Amount double ," +
                "foreign key(Account_No) references "+Constants.TABLE_ACCOUNT+"(Account_No));";

        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_TRANSACTION);

        // Create tables again
        onCreate(db);
    }
}



