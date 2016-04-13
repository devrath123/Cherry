package cherry.android.com.cherry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class DashboardActivity extends ActionBarActivity implements View.OnClickListener{

    RelativeLayout newCustomerRelativeLayout,branchRelativeLayout,inspectionsRelativeLayout;
    TextView branchTextView;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initialiseUI();
    }

    private void initialiseUI() {

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

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.newCustomerRelativeLayout)
        {
               startActivity(new Intent(DashboardActivity.this,ScanVINActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
        else if(v.getId() == R.id.branchRelativeLayout)
        {
           //  startActivity(new Intent(DashboardActivity.this,CustomerInfoActivity.class));
        }
        else if(v.getId() == R.id.inspectionsRelativeLayout)
        {
          startActivity(new Intent(DashboardActivity.this,PendingInspectionsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }

        else if(v.getId() == R.id.logoutButton)
        {
            startActivity(new Intent(DashboardActivity.this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }

    }
}
