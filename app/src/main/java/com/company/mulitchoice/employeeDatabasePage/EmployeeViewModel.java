package com.company.mulitchoice.employeeDatabasePage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EmployeeViewModel extends AndroidViewModel {

    private EmployeeRepository myEmployeeRepository;
    private LiveData<List<EmployeeModel>> myEmployeeModelLiveData;
    public EmployeeViewModel(@NonNull Application application) {
        super(application);
        myEmployeeRepository = new EmployeeRepository(application);
        myEmployeeModelLiveData = myEmployeeRepository.getMyEmployeeLiveData();
    }
    public LiveData<List<EmployeeModel>> getAllEmployeeModelLiveData(){
        return myEmployeeModelLiveData;
    }
    public void insert(EmployeeModel myEmployeeModel){
        myEmployeeRepository.insert(myEmployeeModel);
    }

    public void update(EmployeeModel myEmployeeModel){
        myEmployeeRepository.update(myEmployeeModel);
    }

    public void delete(EmployeeModel myEmployeeModel){
        myEmployeeRepository.delete(myEmployeeModel);
    }

    public void deleteAll(){
        myEmployeeRepository.deleteAll();
    }
}
