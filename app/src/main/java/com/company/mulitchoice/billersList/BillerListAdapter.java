package com.company.mulitchoice.billersList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.mulitchoice.R;
import com.company.mulitchoice.networkServices.BillerAdapterInterface;

import java.util.List;

public class BillerListAdapter extends RecyclerView.Adapter<BillerListAdapter.ViewHolder>{

  private final Context myContext;
  private  List<BillersResponseModel> myBillerModel;
  private final BillerAdapterInterface myBillerAdapterInterface;

    public BillerListAdapter(Context myContext, List<BillersResponseModel> myBillerModel, BillerAdapterInterface myBillerAdapterInterface) {
        this.myContext = myContext;
        this.myBillerModel = myBillerModel;
        this.myBillerAdapterInterface = (BillerAdapterInterface) myContext;
    }

    public void filterList(List<BillersResponseModel> filterTheList){
        myBillerModel = filterTheList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BillerListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater myLayoutInflater = LayoutInflater.from(parent.getContext());
        View myView = myLayoutInflater.inflate(R.layout.biller_design_layout, parent, false);
        return new ViewHolder(myView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BillerListAdapter.ViewHolder holder, int position) {
         BillersResponseModel myBillerList = myBillerModel.get(position);

        holder.id.setText("" + myBillerList.getId());
        holder.name.setText(myBillerList.getName());
        holder.description.setText("" + myBillerList.getDescription());
        holder.categoryId.setText("" + myBillerList.getCategoryId());

        holder.itemView.setOnClickListener(view -> {
            myBillerAdapterInterface.handleBillersClickedItems(myBillerModel.get(position));
        });
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public int getItemCount() {
        if(myBillerModel == null)
            return 0;
            return myBillerModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView id, name, description, categoryId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.tv_id);
            name = itemView.findViewById(R.id.tv_name);
            description = itemView.findViewById(R.id.tv_description);
            categoryId = itemView.findViewById(R.id.tv_category_id);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            myBillerAdapterInterface.handleBillersClickedItems(myBillerModel.get(getAdapterPosition()));
        }
    }
}
