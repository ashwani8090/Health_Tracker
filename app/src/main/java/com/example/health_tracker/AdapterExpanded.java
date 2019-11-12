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
    private List<DataModel> dataModelList = new ArrayList<>();
    private DataModel dataModel;
    private Getter_Setter_Suggest objectSuggest;

    public AdapterExpanded(Context context, List<DataModel> dataModelList, Getter_Setter_Suggest objectSuggest) {
        this.context = context;
        this.dataModelList = dataModelList;
        this.objectSuggest = objectSuggest;
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

        dataModel = dataModelList.get(i);

        viewHolderExpand.Title.setText(dataModel.getName());
        final boolean isExpanded = dataModelList.get(i).getExpanded();
        viewHolderExpand.Data.setVisibility(isExpanded ? View.VISIBLE : View.GONE);


        //Blood Pressure

        /*

       systolic pressure – the pressure when your heart pushes blood out
       diastolic pressure – the pressure when your heart rests between beats

        normal blood pressure is considered to be between 90/60mmHg and 120/80mmHg
        high blood pressure is considered to be 140/90mmHg or higher
        low blood pressure is considered to be 90/60mmHg or lower
                                                                      sys        dia


        This may involve:
        adopting a healthy, balanced diet and restricting your salt intake
        getting regular exercise
        cutting down on alcohol
        losing weight
        stopping smoking
        taking medication, such as angiotensin-converting enzyme (ACE) inhibitors or calcium channel blockers
        In some cases, you may be referred to a doctor such as a cardiologist (heart specialist) to discuss treatment options.

        Some of these will lower your blood pressure in a matter of weeks, while others may take longer :
            cut your salt intake to less than 6g (0.2oz) a day, which is about a teaspoonful
            eat a low-fat, balanced diet – including plenty of fresh fruit and vegetables
            cut down on alcohol
            lose weight
            drink less caffeine – found in coffee, tea and cola
            stop smoking

High-Normal
Your blood pressure is elevated.
It should be rechecked within 12 months or earlier depending on your risk of developing cardiovascular disease.
Your General Practitioner can advise you about this risk and also on lifestyle risk reduction.

Normal
Your blood pressure should be rechecked within 2 years or earlier depending on your risk of developing cardiovascular disease.
Your General Practitioner can advise you about this risk and also on lifestyle risk reduction.


Isolated systolic hypertension with widened pulse pressure
Your blood pressure is elevated. It should be confirmed within 1 week and you may also need to see a specialist in this time.
 Your General Practitioner can advise you about lifestyle risk reduction and/or medication to lower your blood pressure.


Grade 1 (mild) Hypertension
Your blood pressure is elevated. It should be confirmed within 2 months.
Your General Practitioner should advise you about lifestyle risk reduction and/or medication to lower your blood pressure.
             Normal                                                  < 120        < 80
            High-normal                                             120–139     80–89
            Grade 1 (mild) hypertension                             140–159     90–99
            Grade 2 (moderate) hypertension                         160–179     100–109
            Grade 3 (severe) hypertension                           > 180       > 110
            Isolated systolic hypertension                          > 140       < 90
            Isolated systolic hypertension                           > 160      < 7
            with widened pulse pressure

*/

        if (viewHolderExpand.Title.getText().toString().trim().equals("Blood Pressure")) {
          //  Toast.makeText(context, objectSuggest.getSystolic()+" "+objectSuggest.getDiastolic(), Toast.LENGTH_SHORT).show();
            if ((objectSuggest.getDiastolic() < 80) && (objectSuggest.getSystolic() < 120)) {
                viewHolderExpand.Data.setText(String.format("\nYour blood pressure should be rechecked within 2 years or earlier depending on your risk of developing cardiovascular disease.\nYour General Practitioner can advise you about this risk and also on lifestyle risk reduction."));
            } else if (((objectSuggest.getSystolic() >= 120) && (objectSuggest.getSystolic() < 139)) &&
                    ((objectSuggest.getDiastolic() >= 80) && (objectSuggest.getDiastolic() < 89))) {
                viewHolderExpand.Data.setText(String.format("Your blood pressure is elevated.\nIt should be rechecked within 12 months or earlier depending on your risk of developing cardiovascular disease.\nYour General Practitioner can advise you about this risk and also on lifestyle risk reduction."));
            } else if (((objectSuggest.getSystolic() >= 140) && (objectSuggest.getSystolic() < 159)) &&
                    ((objectSuggest.getDiastolic() >= 90) && (objectSuggest.getDiastolic() < 99))) {
               viewHolderExpand.Data.setText(String.format("Grade 1 (mild) Hypertension\nYour blood pressure is elevated. It should be confirmed within 2 months.\nYour General Practitioner should advise you about lifestyle risk reduction and/or medication to lower your blood pressure."));
            } else if (((objectSuggest.getSystolic() >= 160) && (objectSuggest.getSystolic() < 179)) &&
                    ((objectSuggest.getDiastolic() >= 100) && (objectSuggest.getDiastolic() < 109))) {
                viewHolderExpand.Data.setText("Grade 2 (moderate) hypertension");
            } else if ((objectSuggest.getDiastolic() > 180) && (objectSuggest.getSystolic() > 110)) {
                viewHolderExpand.Data.setText("Grade 3 (severe) hypertension");
            } else if ((objectSuggest.getDiastolic() > 140) && (objectSuggest.getSystolic() > 90)) {
                viewHolderExpand.Data.setText(String.format("Isolated systolic hypertension with widened pulse pressure\nYour blood pressure is elevated. It should be confirmed within 1 week and you may also need to see a specialist in this time.\n Your General Practitioner can advise you about lifestyle risk reduction and/or medication to lower your blood pressure."));
            } else if ((objectSuggest.getDiastolic() > 160) && (objectSuggest.getSystolic() < 7)) {
                viewHolderExpand.Data.setText("Isolated systolic hypertension with widened pulse pressure");
            }else{
                viewHolderExpand.Data.setText("Update data correctly");
            }


        }


        //Temperature
        if (viewHolderExpand.Title.getText().toString().trim().equals("Body Temperature")) {
            /* Classed as:         Celsius         Fahrenheit
        Hypothermia          <35.0°C          95.0°F
        Normal               36.5 - 37.5°C    97.7 - 99.5°F
        Fever / Hyperthermia >37.5 or 38.3°C  99.5 or 100.9°F
        Hyperpyrexia         >40.0 or 41.5°C  104.0 or 106.7°F

        Babies and children. In babies and children, the average body temperature ranges from 97.9°F (36.6°C) to 99°F (37.2°C).
        Adults. Among adults, the average body temperature ranges from 97°F (36.1°C) to 99°F (37.2°C).
        Adults over age 65. In older adults, the average body temperature is lower than 98.6°F (36.2°C).
            */


            if (objectSuggest.getTemp() < 35.0) {

                viewHolderExpand.Data.setText(String.format("It may be case of Hypothermia\nMove the person out of the cold.If the person is wearing wet clothing, remove it. Cut away clothing if necessary to avoid excessive movement.Cover the person with blankets.And consult to your doctor"));
            } else if ((objectSuggest.getTemp() >= 36) && (objectSuggest.getTemp() <= 37.5)) {

                viewHolderExpand.Data.setText("Normal body Temperature");

            } else if ((objectSuggest.getTemp() > 37) || (objectSuggest.getTemp() < 38.3)) {

                viewHolderExpand.Data.setText(String.format("May be Fever or Hyperthermia\nsipping cool water or an electrolyte drinkloosening or removing excess clothinglying down and trying to relaxusing a fan to cool the skin"));

            } else if ((objectSuggest.getTemp() >= 40) || (objectSuggest.getTemp() > 41.5)) {

                viewHolderExpand.Data.setText(String.format("May be Hyperpyrexia\na cool bath or cold, wet sponges put on the skin,liquid hydration through IV or from drinking,fever-reducing medications, such as dantrolene."));

            }


        }


        //bmi data analysis
        if (viewHolderExpand.Title.getText().toString().trim().equals("BMI")) {

                /*
                  BMI = weight (kg) / [height (m)]2
       Under 18.5 – you are considered to be underweight.
       18.5 to 24.9 – you are considered to be within a healthy weight range.
       25.0 to 29.9 – you are considered to be overweight.
       Over 30 – you are considered to be obese.

        Less than 15            Very severely underweight     Eat five to six smaller meals during the day rather than two or three large meals.
                                                              Try smoothies and shakes,Exercise
        Between 15 and 16       Severely underweight          Eat more frequently,better to sip higher calorie beverages along with a meal or snack.
                                                                For others, drinking 30 minutes after a meal, not with it, may work.
        Between 16 and 18.5     Underweight                   An occasional slice of pie with ice cream is OK. But most treats should be healthy and provide nutrients in addition to calories. Bran muffins, yogurt and granola bars are good choices.

        Between 18.5 and 25     Normal (healthy weight)       Do Exercise and Eat Healthy.

        Between 25 and 30       Overweight                    Swap refined carb sources for whole grains, Avoid red meat and opt for lean meat like chicken and salmon.,
                                                              Load up on seasonal vegetables, Stay away from trans fats as they are one of the biggest culprits of growing instances of obesity globally.
                                                              Sugar intake should be less than 10% of your total calories;

        Between 30 and 35       Moderately obese              Do at least 200 minutes each week of moderate-intensity exercise,
                                                              Limit your TV watching to no more than 10 hours each week,
                                                              Make sure that no more than 30% of your nutritional intake is in the form of fat.
        Between 35 and 40       Severely obese
        Over 40                 Very severely obese           You may be referred to a dietician who can help you with a plan to lose one to two pounds per week,
                                                              Consider adding physical activity after reaching a minimum of 10 percent weight-loss goal.
                                                              If changing your diet, getting more physical activity and taking medication haven’t helped you lose enough weight, bariatric or “metabolic” surgery may be an option.

              */


            if (objectSuggest.getBmi() < 15) {
                viewHolderExpand.Data.setText(String.format("Very severely underweight\nEat five to six smaller meals during the day rather than two or three large meals.\nTry smoothies and shakes and exercise Regulary."));
            } else if ((objectSuggest.getBmi() >= 15) && (objectSuggest.getBmi() < 16)) {

                viewHolderExpand.Data.setText(String.format("Severely underweight \nEat more frequently,better to sip higher calorie beverages along with a meal or snack. \nFor others, drinking 30 minutes after a meal, not with it, may work."));
            } else if ((objectSuggest.getBmi() >= 16) && (objectSuggest.getBmi() < 18.5))
                viewHolderExpand.Data.setText(String.format("Underweight\nAn occasional slice of pie with ice cream is OK. But most treats should be healthy and provide nutrients in addition to calories. Bran muffins, yogurt and granola bars are good choices."));
            else if ((objectSuggest.getBmi() >= 18.5) && (objectSuggest.getBmi() < 25)) {

                viewHolderExpand.Data.setText(String.format(" Normal (healthy weight)\nDo Exercise and Eat Healthy."));
            } else if ((objectSuggest.getBmi() >= 25) && (objectSuggest.getBmi() < 30)) {
                viewHolderExpand.Data.setText(String.format("Overweight\nSwap refined carb sources for whole grains.Avoid red meat and opt for lean meat like chicken and salmon.\nLoad up on seasonal vegetables, Stay away from trans fats as they are one of the biggest culprits of growing instances of obesity globally.\nSugar intake should be less than 10%% of your total calories"));
            } else if ((objectSuggest.getBmi() > 30)) {
                viewHolderExpand.Data.setText(String.format("Moderately obese\nDo at least 200 minutes each week of moderate-intensity exercise,\nLimit your TV watching to no more than 10 hours each week,\nMake sure that no more than 30%% of your nutritional intake is in the form of fat."));
            }

        }


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

        private TextView Title, Data;

        public ViewHolderExpand(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.titleSuggestionExpand);
            Data = itemView.findViewById(R.id.suggestionResult);
        }
    }
}
