package com.example.health_tracker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterTopics extends RecyclerView.Adapter<AdapterTopics.ViewHolderExpand> {

    private Context context;
    private List<DataModel> dataModelList=new ArrayList<>();
    private DataModel dataModel;

    public AdapterTopics(Context context, List<DataModel> dataModelList) {
        this.context = context;
        this.dataModelList = dataModelList;

    }

    @NonNull
    @Override
    public ViewHolderExpand onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.
                from(viewGroup.getContext()).inflate(R.layout.trending_topics_layout, viewGroup, false);
        return new ViewHolderExpand(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderExpand viewHolderExpand, int i) {

        dataModel=dataModelList.get(i);

        viewHolderExpand.Title.setText(dataModel.getName());
         viewHolderExpand.Title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,WebViewActivity.class).
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("Url",dataModelList.get(viewHolderExpand.getAdapterPosition()).getUrl()));
                notifyDataSetChanged();

            }
        });


    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolderExpand extends RecyclerView.ViewHolder {

        private TextView Title;
        private RelativeLayout relativeLayout;
        public ViewHolderExpand(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.titleTopicExpand);

            relativeLayout=itemView.findViewById(R.id.webviewRelative);
        }
    }
}
