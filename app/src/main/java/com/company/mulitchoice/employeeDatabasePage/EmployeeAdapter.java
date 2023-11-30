package com.company.mulitchoice.employeeDatabasePage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.company.mulitchoice.R;
import com.company.mulitchoice.networkServices.EmployeeAdapterInterface;

import java.util.List;

public class EmployeeAdapter extends ListAdapter<EmployeeModel,EmployeeAdapter.ViewHolder> {


    private static final DiffUtil.ItemCallback<EmployeeModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<EmployeeModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull EmployeeModel oldItem, @NonNull EmployeeModel newItem) {

            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull EmployeeModel oldItem, @NonNull EmployeeModel newItem) {
            // below line is to check the employee full name, job description and year of entry.
            return oldItem.getEmployeeFullNames().equals(newItem.getEmployeeFullNames()) &&
                    oldItem.getJobDescription().equals(newItem.getJobDescription()) &&
                    oldItem.getYearOfEntry().equals(newItem.getYearOfEntry());
        }
    };

    // creating a variable for on item click listener.

    private  List<EmployeeModel> myEmployeeModel;
    private final EmployeeAdapterInterface myEmployeeInterface;
// creating a constructor class for our adapter class.
    public EmployeeAdapter(List<EmployeeModel> myEmployeeModel, Context myContext, EmployeeAdapterInterface myEmployeeInterface) {
        super(DIFF_CALLBACK);
        this.myEmployeeModel = myEmployeeModel;
        this.myEmployeeInterface = (EmployeeAdapterInterface)myContext;
    }

    // creating a call back for item of recycler view.


    public void filterList(List<EmployeeModel> myFilterList){
        myEmployeeModel = myFilterList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater myLayoutInflater = LayoutInflater.from(parent.getContext());
        View myView = myLayoutInflater.inflate(R.layout.employee_design_layout, parent, false);
        return new ViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.ViewHolder holder, int position) {
         EmployeeModel myEmployeeList = getEmployeeAt(position);
         holder.employeeFullName.setText(myEmployeeList.getEmployeeFullNames());
         holder.jobDescription.setText(myEmployeeList.getJobDescription());
         holder.yearOfEntry.setText(myEmployeeList.getYearOfEntry());

        holder.itemView.setOnClickListener(view -> {
               myEmployeeInterface.handleEmployeeClickedItems(myEmployeeModel.get(position));
        });

    }

    public EmployeeModel getEmployeeAt(int position) {

        return myEmployeeModel.get(position);
    }

    @Override
    public int getItemCount() {
        if(myEmployeeModel == null)
           return 0;
        return myEmployeeModel.size();
    }
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView employeeFullName, jobDescription, yearOfEntry;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeFullName = itemView.findViewById(R.id.tv_employee_full_name);
            jobDescription = itemView.findViewById(R.id.tv_job_description);
            yearOfEntry = itemView.findViewById(R.id.tv_year_of_entry);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            myEmployeeInterface.handleEmployeeClickedItems(myEmployeeModel.get(getAdapterPosition()));
        }
    }
}
