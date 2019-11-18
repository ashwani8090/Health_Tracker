package com.example.health_tracker;

import android.annotation.SuppressLint;
import android.app.Dialog;
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

public class AdapterDashboard extends RecyclerView.Adapter<AdapterDashboard.ViewHolderDashboard> {

    private Context context;
    private DataModel dataModel;
    private List<DataModel> dataModelList = new ArrayList<>();
    private Dialog dialog;

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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolderDashboard viewHolderDashboard, int i) {

        dataModel = dataModelList.get(i);
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_input_layout);
        viewHolderDashboard.imageDashboard.setCompoundDrawablesWithIntrinsicBounds(dataModel.getImage(), 0, 0, 0);
        viewHolderDashboard.textViewText.setText(dataModel.getName());

        viewHolderDashboard.imageDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolderDashboard.getAdapterPosition() == 0) {
                    context.startActivity(new Intent(context, TopicsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } else if (viewHolderDashboard.getAdapterPosition() == 1) {
                    dialog.show();
                } else if (viewHolderDashboard.getAdapterPosition() == 2) {
                    context.startActivity(new Intent(context, DailyStatusActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } else if (viewHolderDashboard.getAdapterPosition() == 3) {
                    context.startActivity(new Intent(context, HealthWeeklyActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } else if (viewHolderDashboard.getAdapterPosition() == 4) {
                    context.startActivity(new Intent(context, HealthMonthlyActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } else if (viewHolderDashboard.getAdapterPosition() == 5) {
                    context.startActivity(new Intent(context, SuggestionActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }

            }
        });


        dialog.findViewById(R.id.bloodPressureRelative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, BloodPressureActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


            }
        });
        dialog.findViewById(R.id.sugarRelative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SugarActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


            }
        });
        dialog.findViewById(R.id.temperatureRelative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, BodyTemperatureActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


            }
        });


        dialog.findViewById(R.id.waterRelative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, WaterIntakeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


            }
        });


        dialog.findViewById(R.id.close_popup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolderDashboard extends RecyclerView.ViewHolder {


        private TextView imageDashboard, textViewText;
        private RelativeLayout dashBoard;

        public ViewHolderDashboard(@NonNull View itemView) {
            super(itemView);
            imageDashboard = itemView.findViewById(R.id.menu_dashboard);
            textViewText = itemView.findViewById(R.id.menu_dashboard_text);
            dashBoard = itemView.findViewById(R.id.card_dashboard);

        }
    }


}
