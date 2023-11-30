package com.company.mulitchoice.dashboardPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.mulitchoice.R;
import com.company.mulitchoice.networkServices.SuperHerosAdapterInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SuperHerosAdapter extends RecyclerView.Adapter<SuperHerosAdapter.ViewHolder>{

    private final Context mycontext;
    private final List<SuperHerosModel> myHerosModel;
    private final SuperHerosAdapterInterface mySuperHerosAdapterInterface;

    public SuperHerosAdapter(Context mycontext, List<SuperHerosModel> myHerosModel, SuperHerosAdapterInterface mySuperHerosAdapterInterface) {
        this.mycontext = mycontext;
        this.myHerosModel = myHerosModel;
        this.mySuperHerosAdapterInterface = (SuperHerosAdapterInterface) mycontext;
    }

    @NonNull
    @Override
    public SuperHerosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater myLayoutInflater = LayoutInflater.from(parent.getContext());
        View myView = myLayoutInflater.inflate(R.layout.heros_design_layout, parent, false);
        return new ViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull SuperHerosAdapter.ViewHolder holder, int position) {
         SuperHerosModel myHerolist = myHerosModel.get(position);
         holder.name.setText(myHerolist.getName());
         Picasso.get().load(myHerolist.getImageurl()).into(holder.picture);

         holder.itemView.setOnClickListener(view -> {
              mySuperHerosAdapterInterface.handleSuperHeroClickedItems(myHerosModel.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return myHerosModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private CircleImageView picture;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            picture = itemView.findViewById(R.id.img_picture);
            name = itemView.findViewById(R.id.tv_name);


           itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mySuperHerosAdapterInterface.handleSuperHeroClickedItems(myHerosModel.get(getAdapterPosition()));
           }
        }
}
