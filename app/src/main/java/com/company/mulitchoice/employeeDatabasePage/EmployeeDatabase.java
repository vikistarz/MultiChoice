package com.company.mulitchoice.employeeDatabasePage;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.company.mulitchoice.networkServices.EmployeeDAO;

@Database(entities = {EmployeeModel.class}, version = 1)
public abstract class EmployeeDatabase extends RoomDatabase {

    // below line is to create instance
    // for our database class.
    private static EmployeeDatabase instance;

    // below line is to create
    // abstract variable for dao.
    public abstract EmployeeDAO getEmployeeDAO();

    public static synchronized EmployeeDatabase getInstance(Context myContext) {

        if (instance == null) {
            instance = Room.databaseBuilder(myContext.getApplicationContext(),
                            EmployeeDatabase.class, "employee_database").
                    fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}


