package com.example.health_tracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterDashboard extends RecyclerView.Adapter<AdapterDashboard.ViewHolderDashboard> {

    private Context context;
    private DataModel dataModel;
    private List<DataModel> dataModelList=new ArrayList<>();

    public AdapterDashboard(Context context, List<DataModel> dataModelList) {
        this.context = context;
        this.dataModelList = dataModelList;
    }

    @NonNull
    @Override
    public ViewHolderDashboard onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.

                from(viewGroup.getContext()).inflate(R.layout.card_menu_dashboard, viewGroup, false);


        return new ViewHolderDashboard(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDashboard viewHolderDashboard, int i) {

        dataModel=dataModelList.get(i);

        viewHolderDashboard.imageDashboard.setCompoundDrawablesWithIntrinsicBounds(dataModel.getImage(), 0, 0, 0);
        viewHolderDashboard.textViewText.setText(dataModel.getName());



    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public  class  ViewHolderDashboard extends RecyclerView.ViewHolder {


        private TextView imageDashboard,textViewText;
        private RelativeLayout dashBoard;
        public ViewHolderDashboard(@NonNull View itemView) {
            super(itemView);
            imageDashboard=itemView.findViewById(R.id.menu_dashboard);
            textViewText=itemView.findViewById(R.id.menu_dashboard_text);
            dashBoard=itemView.findViewById(R.id.card_dashboard);

        }
    }




}
