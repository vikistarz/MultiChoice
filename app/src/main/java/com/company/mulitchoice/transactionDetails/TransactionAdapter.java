package com.company.mulitchoice.transactionDetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.mulitchoice.R;
import com.company.mulitchoice.billersList.BillersResponseModel;
import com.company.mulitchoice.networkServices.BillerAdapterInterface;
import com.company.mulitchoice.networkServices.EventsAdapterInterface;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{

  private final Context myContext;
  private  List<Result> myEventLog;
  private final EventsAdapterInterface myEventInterface;

    public TransactionAdapter(Context myContext, List<Result> myBillerModel, EventsAdapterInterface myEventInterface) {
        this.myContext = myContext;
        this.myEventLog = myBillerModel;
        this.myEventInterface = (EventsAdapterInterface) myContext;
    }

//    public void filterList(List<B> filterTheList){
//        myBillerModel = filterTheList;
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater myLayoutInflater = LayoutInflater.from(parent.getContext());
        View myView = myLayoutInflater.inflate(R.layout.event_design_layout, parent, false);
        return new ViewHolder(myView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {
         Result myEventLogList = myEventLog.get(position);

        holder.id.setText("" + myEventLogList.getId());
        holder.title.setText(myEventLogList.getTitle());
        holder.description.setText("" + myEventLogList.getDescription());
        holder.customerId.setText("" + myEventLogList.getCustomerId());
        holder.date.setText("" + myEventLogList.getDate());
        holder.v.setText("" + myEventLogList.getV());

        holder.itemView.setOnClickListener(view -> {
           myEventInterface.handleBillersClickedItems(myEventLog.get(position));
        });
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public int getItemCount() {
        if(myEventLog == null)
            return 0;
            return myEventLog.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView id, title, v, customerId, description, date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.tv_id);
            title = itemView.findViewById(R.id.tv_title);
            customerId = itemView.findViewById(R.id.tv_customer_id);
            v = itemView.findViewById(R.id.tv_v);
            description = itemView.findViewById(R.id.tv_description);
            date = itemView.findViewById(R.id.tv_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            myEventInterface.handleBillersClickedItems(myEventLog.get(getAdapterPosition()));
        }
    }
}
