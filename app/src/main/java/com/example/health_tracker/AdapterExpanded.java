package com.example.health_tracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterExpanded extends RecyclerView.Adapter<AdapterExpanded.ViewHolderExpand> {

    private Context context;
    private List<DataModel> dataModelList=new ArrayList<>();
    private DataModel dataModel;

    public AdapterExpanded(Context context, List<DataModel> dataModelList) {
        this.context = context;
        this.dataModelList = dataModelList;

    }

    @NonNull
    @Override
    public ViewHolderExpand onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.
                from(viewGroup.getContext()).inflate(R.layout.expandable_view, viewGroup, false);
        return new ViewHolderExpand(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderExpand viewHolderExpand, int i) {

        dataModel=dataModelList.get(i);

        viewHolderExpand.Title.setText(dataModel.getName());
        final boolean isExpanded=dataModelList.get(i).getExpanded();
         viewHolderExpand.Data.setVisibility(isExpanded ? View.VISIBLE :View.GONE);
         viewHolderExpand.Title.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dataModelList.get(viewHolderExpand.getAdapterPosition()).setExpanded(!isExpanded);
                 notifyDataSetChanged();

             }
         });


    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolderExpand extends RecyclerView.ViewHolder {

        private TextView Title,Data;
        public ViewHolderExpand(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.titleSuggestionExpand);
            Data=itemView.findViewById(R.id.suggestionResult);
        }
    }
}
