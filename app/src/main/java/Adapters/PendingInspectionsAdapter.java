
package Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Utils.Constants;
import beans.SearchVinBean;
import cherry.android.com.cherry.CustomerInfoActivity;
import cherry.android.com.cherry.InspectionsToCompleteActivity;
import cherry.android.com.cherry.R;


/**
 * Created by devrath.rathee on 3/21/2016.
 */

public class PendingInspectionsAdapter extends RecyclerView.Adapter<PendingInspectionsAdapter.ViewHolder> {

    Context context;
    List<SearchVinBean.Data> customersInfoArrayList;
    public static String pendingSelectedProcedure = "";

    public PendingInspectionsAdapter(Context context, List<SearchVinBean.Data> objects) {
        super();

        this.context = context;
        customersInfoArrayList = new ArrayList<>();
        customersInfoArrayList.addAll(objects);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = (View)LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pending_inspections_row,viewGroup,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder vi, final int position) {

        SearchVinBean.Data searchVinBean = customersInfoArrayList.get(position);

        vi.customerNamePendingInspectionsTextView.setText(searchVinBean.getName());
        vi.customerInfoPendingInspectionsTextView.setText(searchVinBean.getYear() + " " + searchVinBean.getMake_id() + " " + searchVinBean.getModel_id());

        vi.pendingInspectionsRowLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pendingSelectedProcedure = customersInfoArrayList.get(position).getCreated_at();
                Constants.CURRENT_VIN = customersInfoArrayList.get(position).getVin();
                CustomerInfoActivity.customerName = customersInfoArrayList.get(position).getName();
                context.startActivity(new Intent(context, InspectionsToCompleteActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra(Constants.INSPECTIONS_LIST_FROM,Constants.FROM_DASHBOARD));

            }
        });

    }

    @Override
    public int getItemCount() {
        return customersInfoArrayList.size();
    }

   /* @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        SearchVinBean.Data searchVinBean = customersInfoArrayList.get(position);
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (LinearLayout) inflater.inflate(R.layout.pending_inspections_row, parent, false);
        }

        TextView nameTextView = (TextView) view.findViewById(R.id.customerNamePendingInspectionsTextView);
        nameTextView.setText(searchVinBean.getName());

        TextView infoTextView = (TextView) view.findViewById(R.id.customerInfoPendingInspectionsTextView);
        infoTextView.setText(searchVinBean.getYear() + " " + searchVinBean.getMake_id() + " " + searchVinBean.getModel_id());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pendingSelectedProcedure = customersInfoArrayList.get(position).getCreated_at();
                Constants.CURRENT_VIN = customersInfoArrayList.get(position).getVin();
                CustomerInfoActivity.customerName = customersInfoArrayList.get(position).getName();
                context.startActivity(new Intent(context, InspectionsToCompleteActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra(Constants.INSPECTIONS_LIST_FROM,Constants.FROM_DASHBOARD));

            }
        });

        return view;
    }*/

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout pendingInspectionsRowLinearLayout;
        TextView customerNamePendingInspectionsTextView,customerInfoPendingInspectionsTextView;
        Button settingButton;

        public ViewHolder(View itemView) {
            super(itemView);

            pendingInspectionsRowLinearLayout = (LinearLayout) itemView.findViewById(R.id.pendingInspectionRowLinearLayout);
            customerNamePendingInspectionsTextView = (TextView)itemView.findViewById(R.id.customerNamePendingInspectionsTextView);
            customerInfoPendingInspectionsTextView = (TextView) itemView.findViewById(R.id.customerInfoPendingInspectionsTextView);
            settingButton = (Button) itemView.findViewById(R.id.settingButton);
        }
    }

}

