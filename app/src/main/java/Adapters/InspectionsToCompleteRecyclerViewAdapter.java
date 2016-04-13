package Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import beans.ServiceBean;
import cherry.android.com.cherry.GeneralInspectionsActivity;
import cherry.android.com.cherry.InspectionsToCompleteActivity;
import cherry.android.com.cherry.LightsInspectionActivity;
import cherry.android.com.cherry.R;

/**
 * Created by devrath.rathee on 3/9/2016.
 */
public class InspectionsToCompleteRecyclerViewAdapter extends RecyclerView.Adapter<InspectionsToCompleteRecyclerViewAdapter.ViewHolder> {

    Context mContext;
    ArrayList<ServiceBean.Service_Categories> inspectionsArrayList;
    String transitionFrom;

    public InspectionsToCompleteRecyclerViewAdapter(Context context,ArrayList<ServiceBean.Service_Categories> inspections,String transitionFrom) {
        super();

        this.mContext = context;
        this.transitionFrom = transitionFrom;
        inspectionsArrayList = new ArrayList<>();
        for(int i=0;i<inspections.size();i++)
        {
              if(inspections.get(i).getIs_subcategory().equals("0"))
                  this.inspectionsArrayList.add(inspections.get(i));
        }

    }

    @Override
    public int getItemCount() {
        return inspectionsArrayList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = (View)LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inspections_to_complete_row,viewGroup,false);

        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder vi, final int position) {

        vi.inspectionTextView.setText(inspectionsArrayList.get(position).getName());

        vi.inspectionsRowLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inspectionsArrayList.get(position).getName().equals("Lights Inspection"))
                    mContext.startActivity(new Intent(mContext, LightsInspectionActivity.class));

                if(inspectionsArrayList.get(position).getName().equals("General Inspection"))
                    mContext.startActivity(new Intent(mContext, GeneralInspectionsActivity.class));
            }
        });

       /* if(transitionFrom.equals(Constants.FROM_CUSTOMER_INFO))
            vi.inspectionCheckBox.setChecked(false);

        else
            vi.inspectionCheckBox.setChecked(true);*/

        if(InspectionsToCompleteActivity.lightInspectionChecked)
        {
            if(inspectionsArrayList.get(position).getName().equals("Lights Inspection"))
             vi.inspectionCheckBox.setChecked(true);
        }

        if(InspectionsToCompleteActivity.generalInspectionChecked)
        {
            if(inspectionsArrayList.get(position).getName().equals("General Inspection"))
                vi.inspectionCheckBox.setChecked(true);
        }

        vi.inspectionCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    if(position == 0)
                        InspectionsToCompleteActivity.lightInspectionChecked = true;

                    else
                        InspectionsToCompleteActivity.generalInspectionChecked = true;
                }
                else
                {
                    if(position == 0)
                        InspectionsToCompleteActivity.lightInspectionChecked = false;

                    else
                        InspectionsToCompleteActivity.generalInspectionChecked = false;
                }


            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout inspectionsRowLinearLayout;
        TextView inspectionTextView;
        CheckBox inspectionCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);

            inspectionsRowLinearLayout = (LinearLayout) itemView.findViewById(R.id.inspectionsRowLinearLayout);
            inspectionTextView = (TextView)itemView.findViewById(R.id.inspectionNameTextView);
            inspectionCheckBox = (CheckBox) itemView.findViewById(R.id.inspectionCheckBox);
        }
    }
}
