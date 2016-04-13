package cherry.android.com.cherry;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Adapters.GeneralInspectionsExpandableListAdapter;
import beans.ServiceBean;


public class GeneralInspectionsActivity extends ActionBarActivity implements View.OnClickListener {

    ExpandableListView generalInspectionsExpandableListView;
    TextView headerTextView;
    int lastExpandedPosition;
    GeneralInspectionsExpandableListAdapter adapter;
    Button saveButton, cancelButton;
    ArrayList<ServiceBean.Services> generalInspectionsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_inspections);

        initialiseUI();
    }

    private void initialiseUI() {

        headerTextView = (TextView) findViewById(R.id.headerTextView);
        headerTextView.setText(CustomerInfoActivity.customerName);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setText("Save");
        saveButton.setBackgroundDrawable(null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMarginEnd(20);
        saveButton.setLayoutParams(params);
        saveButton.setOnClickListener(this);

        generalInspectionsExpandableListView = (ExpandableListView) findViewById(R.id.generalInspectionsExpandableListView);

        if(!(InspectionsToCompleteActivity.inspectionServiceArrayList == null) && InspectionsToCompleteActivity.inspectionServiceArrayList.size() > 0)
        {
            generalInspectionsArrayList = new ArrayList<>();
            generalInspectionsArrayList.clear();
        }

        for(int i=0;i<InspectionsToCompleteActivity.inspectionServiceArrayList.size();i++)
        {
            if(!(InspectionsToCompleteActivity.inspectionServiceArrayList.get(i).getService_category_id().equals("1")))
                generalInspectionsArrayList.add(InspectionsToCompleteActivity.inspectionServiceArrayList.get(i));
        }

        adapter = new GeneralInspectionsExpandableListAdapter(GeneralInspectionsActivity.this, InspectionsToCompleteActivity.inspectionServiceCategoriesArrayList, generalInspectionsArrayList);
        generalInspectionsExpandableListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        generalInspectionsExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    generalInspectionsExpandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!(adapter == null))
            adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cancelButton:
                finish();
                break;

            case R.id.saveButton:
                InspectionsToCompleteActivity.generalInspectionChecked = true;

               /* if (!(Utilities.getSelectedInspectionArrayList() == null)) {
                    for (int i = 0; i < Utilities.getSelectedInspectionArrayList().size(); i++) {
                        Log.i("Id : ", Utilities.getSelectedInspectionArrayList().get(i).getId());
                    }
                }
*/
                finish();

                break;

        }


    }
}
