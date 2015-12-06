package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.Constants;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

/**
 * Created by SHAEDI on 06/12/2015.
 */
public class PersistentExpenseManager extends ExpenseManager {
    SQLiteDatabase db = null;
    Context context = null;

    public PersistentExpenseManager(Context context) {
        setup();
        this.context = context;
    }

    @Override
    public void setup() {
        db = new DBHandler(context).getWritableDatabase();

        TransactionDAO persistentTransactionDAO = new PersistentTransactionDAO(db);
        setTransactionsDAO(persistentTransactionDAO);

        AccountDAO persistentAccountDAO = new PersistentAccountDAO(db);
        setAccountsDAO(persistentAccountDAO);

        /*** Begin generating dummy data for Persistent implementation ***/
        Account dummyAcct1 = new Account("130147J", "UOM Bank", "Shanika Ediriweera", 20000.0);
        Account dummyAcct2 = new Account("987456Z", "XYZ Bank", "Account Owner", 50000.0);
        getAccountsDAO().addAccount(dummyAcct1);
        getAccountsDAO().addAccount(dummyAcct2);
        /*** End ***/
    }
}
