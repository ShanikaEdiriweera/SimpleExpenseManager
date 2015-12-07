package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.Constants;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by SHAEDI on 06/12/2015.
 */
public class PersistentTransactionDAO implements TransactionDAO {
    private SQLiteDatabase db = null;

    public PersistentTransactionDAO(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dt = sdf.format(date);

        ContentValues v = new ContentValues();
        v.put("Date", dt);
        v.put("Account_No", accountNo);
        v.put("Expense_type", expenseType.toString());
        v.put("Amount", amount);

        db.insert(Constants.TABLE_TRANSACTION, null, v);
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        Cursor cursor = db.rawQuery("Select * from Transactions", null);
        List<Transaction> transactions = new ArrayList<Transaction>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (cursor.moveToFirst()) {
            do {
                try {
                    Date date = formatter.parse(cursor.getString(0));
                    Transaction transaction = new Transaction(date, cursor.getString(1), ((cursor.getString(2) == "INCOME") ? ExpenseType.INCOME : ExpenseType.EXPENSE), Double.parseDouble(cursor.getString(3)));
                    // Adding contact to list
                    transactions.add(transaction);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        Cursor cursor = db.rawQuery("Select * from Transactions ORDER BY Date LIMIT " + limit, null);
        List<Transaction> transactions = new ArrayList<Transaction>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (cursor.moveToFirst()) {
            do {

                Date date = null;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = simpleDateFormat.parse(cursor.getString(0));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    Log.e("Database Handler", "Error converting String to Date. " + e.toString());
                }
                Transaction transaction = new Transaction(date, cursor.getString(1), ((cursor.getString(2) == "INCOME") ? ExpenseType.INCOME : ExpenseType.EXPENSE), Double.parseDouble(cursor.getString(3)));
                // Adding contact to list
                transactions.add(transaction);

            } while (cursor.moveToNext());
        }
        return transactions;
    }
}
