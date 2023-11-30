package com.company.mulitchoice.employeeDatabasePage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.company.mulitchoice.networkServices.EmployeeDAO;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmployeeRepository {

  private final EmployeeDAO myEmployeeDao;

    private final LiveData<List<EmployeeModel>> myEmployeeLiveData;

    private final ExecutorService myExecutorService;

    public EmployeeRepository(Application application) {
        // Initialize the Room database and DAO.
        EmployeeDatabase myEmployeeDatabase = EmployeeDatabase.getInstance(application);
        myEmployeeDao = myEmployeeDatabase.getEmployeeDAO();
        // Initialize LiveData for all employees.
        myEmployeeLiveData = myEmployeeDao.getAllEmployees();

        // Initialize an ExecutorService for database operations.
        myExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    // Expose LiveData to observe all employees.
    public LiveData<List<EmployeeModel>> getMyEmployeeLiveData(){
        return myEmployeeLiveData;
    }

    // Perform database operations on a background thread.

    public void insert(EmployeeModel myEmployeeModel) {

        myExecutorService.execute(new Runnable() {
            @Override
            public void run() {

                myEmployeeDao.insert(myEmployeeModel);
            }
        });
    }

    public void update(EmployeeModel myEmployeeModel) {
        myExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                myEmployeeDao.update(myEmployeeModel);
            }
        });
    }

    public void delete(EmployeeModel myEmployeeModel) {
        myExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                myEmployeeDao.delete(myEmployeeModel);
            }
        });
    }

    public void deleteAll() {
        myExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                myEmployeeDao.deleteAll();
            }
        });
    }



}




//    public void insertEmployee(EmployeeModel myEmployeeModel) {
//        new InsertEmployeeAsyncTask(dao).execute(myEmployeeModel);
//    }
//
//    // creating a method to update data in database.
//    public void updateEmployee(EmployeeModel myEmployeeModel) {
//        new UpdateEmployeeAsyncTask(dao).execute(myEmployeeModel);
//    }
//
//    // creating a method to delete the data in our database.
//    public void deleteEmployee(EmployeeModel myEmployeeModel) {
//        new DeleteEmployeeAsyncTask(dao).execute(myEmployeeModel);
//    }
//
//    // below is the method to delete all the courses.
//    public void deleteAllCourses() {
//        new DeleteAllEmployeeAsyncTask(dao).execute();
//    }
//
//    // below method is to read all the courses.
//    public LiveData<List<EmployeeModel>> getAllEmployee() {
//        return myEmployeeModelLiveData;
//    }
//
//private static class InsertEmployeeAsyncTask extends AsyncTask<EmployeeModel, Void, Void> {
//    private EmployeeDAO myEmployeeDao;
//
//    public InsertEmployeeAsyncTask(EmployeeDAO myEmployeeDAO) {
//        this.myEmployeeDao = myEmployeeDAO;
//    }
//    @Override
//    protected Void doInBackground(EmployeeModel... employeeModels) {
//        myEmployeeDao.insert(employeeModels[0]);
//        return null;
//    }
//}
//
//private static class UpdateEmployeeAsyncTask extends AsyncTask<EmployeeModel, Void, Void> {
//    private EmployeeDAO myEmployeeDao;
//
//    public UpdateEmployeeAsyncTask(EmployeeDAO myEmployeeDAO) {
//        this.myEmployeeDao = myEmployeeDAO;
//    }
//
//    @Override
//    protected Void doInBackground(EmployeeModel... employeeModels) {
//        myEmployeeDao.update(employeeModels[0]);
//        return null;
//    }
//}
//
//private static class DeleteEmployeeAsyncTask extends AsyncTask<EmployeeModel, Void, Void> {
//    private EmployeeDAO myEmployeeDao;
//
//    public DeleteEmployeeAsyncTask(EmployeeDAO myEmployeeDAO) {
//        this.myEmployeeDao = myEmployeeDAO;
//    }
//
//    @Override
//    protected Void doInBackground(EmployeeModel... employeeModels) {
//        myEmployeeDao.delete(employeeModels[0]);
//        return null;
//    }
//}
//
//private static class DeleteAllEmployeeAsyncTask extends AsyncTask<Void, Void, Void> {
//    private EmployeeDAO myEmployeeDao;
//
//    public DeleteAllEmployeeAsyncTask(EmployeeDAO myEmployeeDAO) {
//        this.myEmployeeDao = myEmployeeDAO;
//    }
//    @Override
//    protected Void doInBackground(Void... voids) {
//        myEmployeeDao.deleteAll();
//        return null;
//    }
//}
//}
