
package cherry.android.com.cherry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapters.PendingInspectionsAdapter;
import Utils.Constants;
import Utils.Utilities;
import beans.SearchVinBean;

//import Utils.ListViewSwipeGesture;


public class PendingInspectionsActivity extends ActionBarActivity implements View.OnClickListener{

    static SearchVinBean.Data searchBeanPendingInspection;
    Button cancelButton,saveButton;
    TextView headerTextView,noPendingInspectionsTextView;
    RecyclerView pendingInspectionsRecyclerView;
    PendingInspectionsAdapter pendingInspectionsAdapter;
    ArrayList<SearchVinBean.Data> customerInfoArrayList;

    RelativeLayout newCustomerRelativeLayout,branchRelativeLayout,inspectionsRelativeLayout;
    TextView branchTextView;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_inspections);

        initialiseUI();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.saveButton)
        {
            if(v.getId() == R.id.saveButton)
            {
                setContentView(R.layout.activity_dashboard);

                newCustomerRelativeLayout = (RelativeLayout) findViewById(R.id.newCustomerRelativeLayout);
                newCustomerRelativeLayout.setOnClickListener(this);

                branchRelativeLayout = (RelativeLayout) findViewById(R.id.branchRelativeLayout);
                branchRelativeLayout.setOnClickListener(this);

                inspectionsRelativeLayout = (RelativeLayout) findViewById(R.id.inspectionsRelativeLayout);
                inspectionsRelativeLayout.setOnClickListener(this);

                branchTextView = (TextView) findViewById(R.id.branchTextView);
                if(!(LoginActivity.loginBean.getData() == null))
                    branchTextView.setText(LoginActivity.loginBean.getData().getUserBranch().get(0).getName());

                logoutButton = (Button) findViewById(R.id.logoutButton);
                logoutButton.setOnClickListener(this);

            }
        }

        if(v.getId() == R.id.newCustomerRelativeLayout)
            startActivity(new Intent(PendingInspectionsActivity.this,ScanVINActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        else if(v.getId() == R.id.branchRelativeLayout)
        {
            //  startActivity(new Intent(DashboardActivity.this,CustomerInfoActivity.class));
        }
        else if(v.getId() == R.id.inspectionsRelativeLayout)
            startActivity(new Intent(PendingInspectionsActivity.this,PendingInspectionsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


        else if(v.getId() == R.id.logoutButton)
            startActivity(new Intent(PendingInspectionsActivity.this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int i) {

            try {

                Button settingButton = (Button)viewHolder.itemView.findViewById(R.id.settingButton);
                settingButton.setVisibility(View.VISIBLE);
                settingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         Constants.SAVE_UPDATE_REQUIRED = Constants.CUSTOMER_NOTHING_REQUIRED;
                         Constants.CUSTOMER_INFO_FROM = Constants.FROM_PENDING_INSPECTION;
                         searchBeanPendingInspection  = customerInfoArrayList.get(viewHolder.getAdapterPosition());
                         startActivity(new Intent(PendingInspectionsActivity.this, CustomerInfoActivity.class));
                    }
                });

                pendingInspectionsAdapter.notifyDataSetChanged();

               /* AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage("Are you sure you want to delete this Notification ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String id = beanArrayList.get(viewHolder.getAdapterPosition()).getId();
                        notificationDatabase.deleteNotification(id);
                        beanArrayList.remove(viewHolder.getAdapterPosition());
                        mAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAdapter.notifyDataSetChanged();
                    }
                }).show();*/


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
            return false;
        }
    };



    private void initialiseUI() {

        try{

        headerTextView = (TextView) findViewById(R.id.headerTextView);
        headerTextView.setText("Inspections to Complete");

        noPendingInspectionsTextView = (TextView) findViewById(R.id.pendingInspectionsTextView);

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setText("");
        saveButton.setOnClickListener(this);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setVisibility(View.GONE);

        pendingInspectionsRecyclerView = (RecyclerView) findViewById(R.id.pendingInspectionListView);

        if(customerInfoArrayList == null)
            customerInfoArrayList = new ArrayList<>();

        customerInfoArrayList.clear();

        HashMap<String, SearchVinBean.Data> pendingHashMap = Utilities.customerInfoHashMap;


        if(!(pendingHashMap == null)) {
            for (Map.Entry<String, SearchVinBean.Data> entry : pendingHashMap.entrySet()) {
                customerInfoArrayList.add(entry.getValue());
            }

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

            if (customerInfoArrayList.size() > 0) {
                noPendingInspectionsTextView.setVisibility(View.GONE);
                pendingInspectionsAdapter = new PendingInspectionsAdapter(this, customerInfoArrayList);
                pendingInspectionsRecyclerView.setAdapter(pendingInspectionsAdapter);
                itemTouchHelper.attachToRecyclerView(pendingInspectionsRecyclerView);
                pendingInspectionsRecyclerView.setHasFixedSize(true);

                LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
                pendingInspectionsRecyclerView.setLayoutManager(mLayoutManager);
                pendingInspectionsAdapter.notifyDataSetChanged();
            } else {
                noPendingInspectionsTextView.setVisibility(View.VISIBLE);

                customerInfoArrayList.clear();
                pendingInspectionsAdapter = new PendingInspectionsAdapter(this, customerInfoArrayList);
                pendingInspectionsRecyclerView.setAdapter(pendingInspectionsAdapter);
                pendingInspectionsRecyclerView.setHasFixedSize(true);

                LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
                pendingInspectionsRecyclerView.setLayoutManager(mLayoutManager);
                pendingInspectionsAdapter.notifyDataSetChanged();
            }
        }
        else
        {
            noPendingInspectionsTextView.setVisibility(View.VISIBLE);

            pendingInspectionsAdapter = new PendingInspectionsAdapter(this, customerInfoArrayList);
            pendingInspectionsRecyclerView.setAdapter(pendingInspectionsAdapter);
            pendingInspectionsRecyclerView.setHasFixedSize(true);

            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            pendingInspectionsRecyclerView.setLayoutManager(mLayoutManager);
            pendingInspectionsAdapter.notifyDataSetChanged();
        }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }




}
