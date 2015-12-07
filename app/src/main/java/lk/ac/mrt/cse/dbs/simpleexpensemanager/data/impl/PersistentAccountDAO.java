package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.Constants;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.DBHandler;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * Created by SHAEDI on 06/12/2015.
 */
public class PersistentAccountDAO implements AccountDAO {
    private SQLiteDatabase db = null;

    public PersistentAccountDAO(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public List<String> getAccountNumbersList() {
        List<String> accountNumberList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT Account_No FROM " + Constants.TABLE_ACCOUNT+";";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String acc_no = cursor.getString(0);
                // Adding contact to list
                accountNumberList.add(acc_no);
            } while (cursor.moveToNext());
        }

        // return contact list
        return accountNumberList;
    }

    @Override
    public List<Account> getAccountsList() {
        List<Account> accountList = new ArrayList<Account>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + Constants.TABLE_ACCOUNT+";";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Account account = new Account(cursor.getString(0),cursor.getString(1),cursor.getString(2),Double.parseDouble(cursor.getString(3)));
                // Adding contact to list
                accountList.add(account);
            } while (cursor.moveToNext());
        }

        // return contact list
        return accountList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        Cursor cursor = db.rawQuery("Select * from Account where Account_No='" + accountNo+"';", null);
        if(cursor.moveToFirst()) {
            return new Account(cursor.getString(0), cursor.getString(1), cursor.getString(2), Double.parseDouble(cursor.getString(3)));
        }
        throw new InvalidAccountException("Account No:" + accountNo + " is not valid!");
    }

    @Override
    public void addAccount(Account account) {

        ContentValues values = new ContentValues();
        values.put("Account_No", account.getAccountNo());
        values.put("Account_holder_name", account.getBankName());
        values.put("Bank_name", account.getAccountHolderName());
        values.put("Balance", account.getBalance());

        db.insert(Constants.TABLE_ACCOUNT, null, values);

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        db.execSQL("DELETE from Account where Account_No = '"+accountNo+"';");
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        double NewBalance = getAccount(accountNo).getBalance();

        if (ExpenseType.INCOME == expenseType) {
            NewBalance += amount;
        } else {
            NewBalance -= amount;
        }

        ContentValues v = new ContentValues();
        v.put("Balance", NewBalance);

        db.update(Constants.TABLE_ACCOUNT, v, "Account_No" + "='" + accountNo + "'", null);
    }
}
