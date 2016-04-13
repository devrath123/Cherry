package cherry.android.com.cherry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Utils.Constants;
import Utils.Utilities;
import beans.SelectedInspection;


public class LightsInspectionActivity extends ActionBarActivity implements View.OnClickListener {

    Button cornerDriveFrontButton, brightDriveFrontButton, brightPassFrontButton, cornerPassFrontButton, sideTurnDriveFrontButton, headlightDriveFrontButton, headlightPassFrontButton,
            sideTurnPassFrontButton, turnSignalDriveFrontButton, hoodButton, turnSignalPassFrontButton, turnSignalDriveRearButton, highMountButton, turnSignalPassRearButton,
            tailLightDriveRearButton, driveCargoDriveRearButton, passCargoPassRearButton, tailLightPassRearButton, reverseDriveRearButton, driveLicenceDriveRearButton,
            passLicencePassRearButton, reversePassRearButton, allOkButton, saveButton, cancelButton, drawerButton;
    static boolean cornerDriveFrontButtonSelected, brightDriveFrontButtonSelected, brightPassFrontButtonSelected, cornerPassFrontButtonSelected, sideTurnDriveFrontButtonSelected,
            headlightDriveFrontButtonSelected, headlightPassFrontButtonSelected, sideTurnPassFrontButtonSelected, turnSignalDriveFrontButtonSelected, hoodButtonSelected,
            turnSignalPassFrontButtonSelected, turnSignalDriveRearButtonSelected, highMountButtonSelected, turnSignalPassRearButtonSelected,
            tailLightDriveRearButtonSelected, driveCargoDriveRearButtonSelected, passCargoPassRearButtonSelected, tailLightPassRearButtonSelected, reverseDriveRearButtonSelected,
            driveLicenceDriveRearButtonSelected, passLicencePassRearButtonSelected, reversePassRearButtonSelected;

    RelativeLayout newCustomerRelativeLayout, branchRelativeLayout, inspectionsRelativeLayout;
    TextView branchTextView;
    Button logoutButton;

    ArrayList<String> tempArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lights_inspection);

        initialiseUI();
    }

    private void initialiseUI() {

        if (tempArrayList == null)
            tempArrayList = new ArrayList<>();

        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);

        drawerButton = (Button) findViewById(R.id.saveButton);
        drawerButton.setOnClickListener(this);

        TextView headerTextView = (TextView) findViewById(R.id.headerTextView);
        headerTextView.setText(CustomerInfoActivity.customerName);

        allOkButton = (Button) findViewById(R.id.allOkLightsInspectionButton);
        allOkButton.setOnClickListener(this);

        saveButton = (Button) findViewById(R.id.saveLightsInspectionButton);
        saveButton.setOnClickListener(this);

        cornerDriveFrontButton = (Button) findViewById(R.id.cornerDriveFrontButton);
        cornerDriveFrontButton.setOnClickListener(this);

        brightDriveFrontButton = (Button) findViewById(R.id.brightDriveFrontButton);
        brightDriveFrontButton.setOnClickListener(this);

        brightPassFrontButton = (Button) findViewById(R.id.brightPassFrontButton);
        brightPassFrontButton.setOnClickListener(this);

        cornerPassFrontButton = (Button) findViewById(R.id.cornerPassFrontButton);
        cornerPassFrontButton.setOnClickListener(this);

        sideTurnDriveFrontButton = (Button) findViewById(R.id.sideTurnDriveFrontButton);
        sideTurnDriveFrontButton.setOnClickListener(this);

        headlightDriveFrontButton = (Button) findViewById(R.id.headlightDriveFrontButton);
        headlightDriveFrontButton.setOnClickListener(this);

        headlightPassFrontButton = (Button) findViewById(R.id.headlightPassFrontButton);
        headlightPassFrontButton.setOnClickListener(this);

        sideTurnPassFrontButton = (Button) findViewById(R.id.sideTurnPassFrontButton);
        sideTurnPassFrontButton.setOnClickListener(this);

        turnSignalDriveFrontButton = (Button) findViewById(R.id.turnSignalDriveFrontButton);
        turnSignalDriveFrontButton.setOnClickListener(this);

        hoodButton = (Button) findViewById(R.id.hoodButton);
        hoodButton.setOnClickListener(this);

        turnSignalPassFrontButton = (Button) findViewById(R.id.turnSignalPassFrontButton);
        turnSignalPassFrontButton.setOnClickListener(this);

        turnSignalDriveRearButton = (Button) findViewById(R.id.turnSignalDriveRearButton);
        turnSignalDriveRearButton.setOnClickListener(this);

        highMountButton = (Button) findViewById(R.id.highMountButton);
        highMountButton.setOnClickListener(this);

        turnSignalPassRearButton = (Button) findViewById(R.id.turnSignalPassRearButton);
        turnSignalPassRearButton.setOnClickListener(this);

        tailLightDriveRearButton = (Button) findViewById(R.id.tailLightDriveRearButton);
        tailLightDriveRearButton.setOnClickListener(this);

        driveCargoDriveRearButton = (Button) findViewById(R.id.driveCargoDriveRearButton);
        driveCargoDriveRearButton.setOnClickListener(this);

        passCargoPassRearButton = (Button) findViewById(R.id.passCargoPassRearButton);
        passCargoPassRearButton.setOnClickListener(this);

        tailLightPassRearButton = (Button) findViewById(R.id.tailLightPassRearButton);
        tailLightPassRearButton.setOnClickListener(this);

        reverseDriveRearButton = (Button) findViewById(R.id.reverseDriveRearButton);
        reverseDriveRearButton.setOnClickListener(this);

        driveLicenceDriveRearButton = (Button) findViewById(R.id.driveLicenceDriveRearButton);
        driveLicenceDriveRearButton.setOnClickListener(this);

        passLicencePassRearButton = (Button) findViewById(R.id.passLicensePassRearButton);
        passLicencePassRearButton.setOnClickListener(this);

        reversePassRearButton = (Button) findViewById(R.id.reversePassRearButton);
        reversePassRearButton.setOnClickListener(this);

        if (!(InspectionsToCompleteActivity.inspectionServiceArrayList == null) && InspectionsToCompleteActivity.inspectionServiceArrayList.size() > 0 &&
                !(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN) == null) && Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).size() > 0)
            checkSelectedButtons();
    }

    private void getSelectedButton(String title, String desc) {
        if (title.equals("Corner") && desc.equals("Driver Front")) {
            cornerDriveFrontButtonSelected = true;
            cornerDriveFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            cornerDriveFrontButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Bright") && desc.equals("Driver Front")) {
            brightDriveFrontButtonSelected = true;
            brightDriveFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            brightDriveFrontButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Bright") && desc.equals("Pass Front")) {
            brightPassFrontButtonSelected = true;
            brightPassFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            brightPassFrontButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Corner") && desc.equals("Pass Front")) {
            cornerPassFrontButtonSelected = true;
            cornerPassFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            cornerPassFrontButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Sideturn") && desc.equals("Driver Front")) {
            sideTurnDriveFrontButtonSelected = true;
            sideTurnDriveFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            sideTurnDriveFrontButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Sideturn") && desc.equals("Pass Front")) {
            sideTurnPassFrontButtonSelected = true;
            sideTurnPassFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            sideTurnPassFrontButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Headlight") && desc.equals("Driver Front")) {
            headlightDriveFrontButtonSelected = true;
            headlightDriveFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            headlightDriveFrontButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Headlight") && desc.equals("Pass Front")) {
            headlightPassFrontButtonSelected = true;
            headlightPassFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            headlightPassFrontButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Turn Signal") && desc.equals("Driver Front")) {
            turnSignalDriveFrontButtonSelected = true;
            turnSignalDriveFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            turnSignalDriveFrontButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Hood") && desc.equals("Hood")) {
            hoodButtonSelected = true;
            hoodButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            hoodButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Turn Signal") && desc.equals("Pass Front")) {
            turnSignalPassFrontButtonSelected = true;
            turnSignalPassFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            turnSignalPassFrontButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Turn Signal") && desc.equals("Driver Rear")) {
            turnSignalDriveRearButtonSelected = true;
            turnSignalDriveRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            turnSignalDriveRearButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("High Mount") && desc.equals("High Mount")) {
            highMountButtonSelected = true;
            highMountButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            highMountButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Turn Signal") && desc.equals("Pass Rear")) {
            turnSignalPassRearButtonSelected = true;
            turnSignalPassRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            turnSignalPassRearButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Tail Light") && desc.equals("Driver Rear")) {
            tailLightDriveRearButtonSelected = true;
            tailLightDriveRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            tailLightDriveRearButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Driver Cargo") && desc.equals("Driver Rear")) {
            driveCargoDriveRearButtonSelected = true;
            driveCargoDriveRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            driveCargoDriveRearButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Pass Cargo") && desc.equals("Pass Rear")) {
            passCargoPassRearButtonSelected = true;
            passCargoPassRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            passCargoPassRearButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Tail Light") && desc.equals("Pass Rear")) {
            tailLightPassRearButtonSelected = true;
            tailLightPassRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            tailLightPassRearButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Reverse") && desc.equals("Driver Rear")) {
            reverseDriveRearButtonSelected = true;
            reverseDriveRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            reverseDriveRearButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Drive License") && desc.equals("Driver Rear")) {
            driveLicenceDriveRearButtonSelected = true;
            driveLicenceDriveRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            driveLicenceDriveRearButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Pass License") && desc.equals("Pass Rear")) {
            passLicencePassRearButtonSelected = true;
            passLicencePassRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            passLicencePassRearButton.setTextColor(getResources().getColor(R.color.app_white));

        } else if (title.equals("Reverse") && desc.equals("Pass Rear")) {
            reversePassRearButtonSelected = true;
            reversePassRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
            reversePassRearButton.setTextColor(getResources().getColor(R.color.app_white));

        }


    }

    private void checkSelectedButtons() {
        for (int i = 0; i < InspectionsToCompleteActivity.inspectionServiceArrayList.size(); i++) {
            for (int j = 0; j < Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).size(); j++) {
                if (InspectionsToCompleteActivity.inspectionServiceArrayList.get(i).getTitle().equals(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).get(j).getTitle()) &&
                        InspectionsToCompleteActivity.inspectionServiceArrayList.get(i).getDesc().equals(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).get(j).getDesc())) {
                    getSelectedButton(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).get(j).getTitle(), Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).get(j).getDesc());

                }
            }
        }
    }

    private String getServiceId(String title, String desc) {
        for (int i = 0; i < InspectionsToCompleteActivity.inspectionServiceArrayList.size(); i++) {
            if (InspectionsToCompleteActivity.inspectionServiceArrayList.get(i).getTitle().equals(title) && InspectionsToCompleteActivity.inspectionServiceArrayList.get(i).getDesc().equals(desc))
                return InspectionsToCompleteActivity.inspectionServiceArrayList.get(i).getId();
        }

        return "";

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.newCustomerRelativeLayout:
                startActivity(new Intent(LightsInspectionActivity.this, ScanVINActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;

            case R.id.branchRelativeLayout:
                break;

            case R.id.inspectionsRelativeLayout:
                startActivity(new Intent(LightsInspectionActivity.this, PendingInspectionsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;

            case R.id.logoutButton:
                startActivity(new Intent(LightsInspectionActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                  break;

            case R.id.saveButton:

                setContentView(R.layout.activity_dashboard);

                newCustomerRelativeLayout = (RelativeLayout) findViewById(R.id.newCustomerRelativeLayout);
                newCustomerRelativeLayout.setOnClickListener(this);

                branchRelativeLayout = (RelativeLayout) findViewById(R.id.branchRelativeLayout);
                branchRelativeLayout.setOnClickListener(this);

                inspectionsRelativeLayout = (RelativeLayout) findViewById(R.id.inspectionsRelativeLayout);
                inspectionsRelativeLayout.setOnClickListener(this);

                branchTextView = (TextView) findViewById(R.id.branchTextView);
                if (!(LoginActivity.loginBean.getData() == null))
                    branchTextView.setText(LoginActivity.loginBean.getData().getUserBranch().get(0).getName());

                logoutButton = (Button) findViewById(R.id.logoutButton);
                logoutButton.setOnClickListener(this);


                break;

            case R.id.cancelButton:
                finish();
                break;


            case R.id.allOkLightsInspectionButton:

                tempArrayList.clear();

                for (int i = 0; i < Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).size(); i++) {
                    if (Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).get(i).getTitle().length() > 0 && Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).get(i).getDesc().length() > 0)
                        tempArrayList.add("" + i);
                    //  Utilities.removeInspection(Utilities.getSelectedInspectionArrayList().get(i).getTitle(), Utilities.getSelectedInspectionArrayList().get(i).getDesc());
                }

                for (int i = 0; i < tempArrayList.size(); i++) {
                    int loc = Integer.parseInt(tempArrayList.get(i)) - i;
                    Utilities.removeInspection(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).get(loc).getTitle(), Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).get(loc).getDesc());
                }

               /* for (int i = 0; i < Utilities.getSelectedInspectionArrayList().size(); i++) {
                    if(Utilities.getSelectedInspectionArrayList().get(i).getTitle().length() > 0 && Utilities.getSelectedInspectionArrayList().get(i).getDesc().length() > 0)
                        Utilities.removeInspection(Utilities.getSelectedInspectionArrayList().get(i).getTitle(), Utilities.getSelectedInspectionArrayList().get(i).getDesc());
                }*/

                InspectionsToCompleteActivity.lightInspectionChecked = true;
                finish();

                break;

            case R.id.saveLightsInspectionButton:

                if (!(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN) == null)) {
                    InspectionsToCompleteActivity.lightInspectionChecked = true;

                    for (int i = 0; i < Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).size(); i++) {
                        Log.i("Selected Id : ", Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).get(i).getId());
                    }

                    finish();
                } else
                    Toast.makeText(LightsInspectionActivity.this, "Select Inspections", Toast.LENGTH_SHORT).show();

                break;

            case R.id.cornerDriveFrontButton:

                if (cornerDriveFrontButtonSelected) {
                    Utilities.removeInspection("Corner", "Driver Front");
                    cornerDriveFrontButtonSelected = false;
                    cornerDriveFrontButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    cornerDriveFrontButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Corner", "Driver Front", getServiceId("Corner", "Driver Front"), "", ""));
                    cornerDriveFrontButtonSelected = true;
                    cornerDriveFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    cornerDriveFrontButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.brightDriveFrontButton:

                if (brightDriveFrontButtonSelected) {
                    Utilities.removeInspection("Bright", "Driver Front");
                    brightDriveFrontButtonSelected = false;
                    brightDriveFrontButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    brightDriveFrontButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Bright", "Driver Front", getServiceId("Bright", "Driver Front"), "", ""));
                    brightDriveFrontButtonSelected = true;
                    brightDriveFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    brightDriveFrontButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.brightPassFrontButton:

                if (brightPassFrontButtonSelected) {
                    Utilities.removeInspection("Bright", "Pass Front");
                    brightPassFrontButtonSelected = false;
                    brightPassFrontButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    brightPassFrontButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Bright", "Pass Front", getServiceId("Bright", "Pass Front"), "", ""));
                    brightPassFrontButtonSelected = true;
                    brightPassFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    brightPassFrontButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.cornerPassFrontButton:

                if (cornerPassFrontButtonSelected) {
                    Utilities.removeInspection("Corner", "Pass Front");
                    cornerPassFrontButtonSelected = false;
                    cornerPassFrontButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    cornerPassFrontButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Corner", "Pass Front", getServiceId("Corner", "Pass Front"), "", ""));
                    cornerPassFrontButtonSelected = true;
                    cornerPassFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    cornerPassFrontButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.sideTurnDriveFrontButton:

                if (sideTurnDriveFrontButtonSelected) {
                    Utilities.removeInspection("Sideturn", "Driver Front");
                    sideTurnDriveFrontButtonSelected = false;
                    sideTurnDriveFrontButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    sideTurnDriveFrontButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Sideturn", "Driver Front", getServiceId("Sideturn", "Driver Front"), "", ""));
                    sideTurnDriveFrontButtonSelected = true;
                    sideTurnDriveFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    sideTurnDriveFrontButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.headlightDriveFrontButton:

                if (headlightDriveFrontButtonSelected) {
                    Utilities.removeInspection("Headlight", "Driver Front");
                    headlightDriveFrontButtonSelected = false;
                    headlightDriveFrontButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    headlightDriveFrontButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Headlight", "Driver Front", getServiceId("Headlight", "Driver Front"), "", ""));
                    headlightDriveFrontButtonSelected = true;
                    headlightDriveFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    headlightDriveFrontButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.headlightPassFrontButton:

                if (headlightPassFrontButtonSelected) {
                    Utilities.removeInspection("Headlight", "Pass Front");
                    headlightPassFrontButtonSelected = false;
                    headlightPassFrontButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    headlightPassFrontButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Headlight", "Pass Front", getServiceId("Headlight", "Pass Front"), "", ""));
                    headlightPassFrontButtonSelected = true;
                    headlightPassFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    headlightPassFrontButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.sideTurnPassFrontButton:

                if (sideTurnPassFrontButtonSelected) {
                    Utilities.removeInspection("Sideturn", "Pass Front");
                    sideTurnPassFrontButtonSelected = false;
                    sideTurnPassFrontButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    sideTurnPassFrontButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Sideturn", "Pass Front", getServiceId("Sideturn", "Pass Front"), "", ""));
                    sideTurnPassFrontButtonSelected = true;
                    sideTurnPassFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    sideTurnPassFrontButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.turnSignalDriveFrontButton:

                if (turnSignalDriveFrontButtonSelected) {
                    Utilities.removeInspection("Turn Signal", "Driver Front");
                    turnSignalDriveFrontButtonSelected = false;
                    turnSignalDriveFrontButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    turnSignalDriveFrontButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Turn Signal", "Driver Front", getServiceId("Turn Signal", "Driver Front"), "", ""));
                    turnSignalDriveFrontButtonSelected = true;
                    turnSignalDriveFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    turnSignalDriveFrontButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.hoodButton:

                if (hoodButtonSelected) {
                    Utilities.removeInspection("Hood", "Hood");
                    hoodButtonSelected = false;
                    hoodButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    hoodButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Hood", "Hood", getServiceId("Hood", "Hood"), "", ""));
                    hoodButtonSelected = true;
                    hoodButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    hoodButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.turnSignalPassFrontButton:

                if (turnSignalPassFrontButtonSelected) {
                    Utilities.removeInspection("Turn Signal", "Pass Front");
                    turnSignalPassFrontButtonSelected = false;
                    turnSignalPassFrontButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    turnSignalPassFrontButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Turn Signal", "Pass Front", getServiceId("Turn Signal", "Pass Front"), "", ""));
                    turnSignalPassFrontButtonSelected = true;
                    turnSignalPassFrontButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    turnSignalPassFrontButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.turnSignalDriveRearButton:

                if (turnSignalDriveRearButtonSelected) {
                    Utilities.removeInspection("Turn Signal", "Driver Rear");
                    turnSignalDriveRearButtonSelected = false;
                    turnSignalDriveRearButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    turnSignalDriveRearButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Turn Signal", "Driver Rear", getServiceId("Turn Signal", "Driver Rear"), "", ""));
                    turnSignalDriveRearButtonSelected = true;
                    turnSignalDriveRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    turnSignalDriveRearButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.highMountButton:

                if (highMountButtonSelected) {
                    Utilities.removeInspection("High Mount", "High Mount");
                    highMountButtonSelected = false;
                    highMountButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    highMountButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("High Mount", "High Mount", getServiceId("High Mount", "High Mount"), "", ""));
                    highMountButtonSelected = true;
                    highMountButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    highMountButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.turnSignalPassRearButton:

                if (turnSignalPassRearButtonSelected) {
                    Utilities.removeInspection("Turn Signal", "Pass Rear");
                    turnSignalPassRearButtonSelected = false;
                    turnSignalPassRearButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    turnSignalPassRearButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Turn Signal", "Pass Rear", getServiceId("Turn Signal", "Pass Rear"), "", ""));
                    turnSignalPassRearButtonSelected = true;
                    turnSignalPassRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    turnSignalPassRearButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.tailLightDriveRearButton:

                if (tailLightDriveRearButtonSelected) {
                    Utilities.removeInspection("Tail Light", "Driver Rear");
                    tailLightDriveRearButtonSelected = false;
                    tailLightDriveRearButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    tailLightDriveRearButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Tail Light", "Driver Rear", getServiceId("Tail Light", "Driver Rear"), "", ""));
                    tailLightDriveRearButtonSelected = true;
                    tailLightDriveRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    tailLightDriveRearButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.driveCargoDriveRearButton:

                if (driveCargoDriveRearButtonSelected) {
                    Utilities.removeInspection("Driver Cargo", "Driver Rear");
                    driveCargoDriveRearButtonSelected = false;
                    driveCargoDriveRearButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    driveCargoDriveRearButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Driver Cargo", "Driver Rear", getServiceId("Driver Cargo", "Driver Rear"), "", ""));
                    driveCargoDriveRearButtonSelected = true;
                    driveCargoDriveRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    driveCargoDriveRearButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.passCargoPassRearButton:

                if (passCargoPassRearButtonSelected) {
                    Utilities.removeInspection("Pass Cargo", "Pass Rear");
                    passCargoPassRearButtonSelected = false;
                    passCargoPassRearButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    passCargoPassRearButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Pass Cargo", "Pass Rear", getServiceId("Pass Cargo", "Pass Rear"), "", ""));
                    passCargoPassRearButtonSelected = true;
                    passCargoPassRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    passCargoPassRearButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.tailLightPassRearButton:

                if (tailLightPassRearButtonSelected) {
                    Utilities.removeInspection("Tail Light", "Pass Rear");
                    tailLightPassRearButtonSelected = false;
                    tailLightPassRearButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    tailLightPassRearButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Tail Light", "Pass Rear", getServiceId("Tail Light", "Pass Rear"), "", ""));
                    tailLightPassRearButtonSelected = true;
                    tailLightPassRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    tailLightPassRearButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.reverseDriveRearButton:

                if (reverseDriveRearButtonSelected) {
                    Utilities.removeInspection("Reverse", "Driver Rear");
                    reverseDriveRearButtonSelected = false;
                    reverseDriveRearButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    reverseDriveRearButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Reverse", "Driver Rear", getServiceId("Reverse", "Driver Rear"), "", ""));
                    reverseDriveRearButtonSelected = true;
                    reverseDriveRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    reverseDriveRearButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.driveLicenceDriveRearButton:

                if (driveLicenceDriveRearButtonSelected) {
                    Utilities.removeInspection("Drive License", "Driver Rear");
                    driveLicenceDriveRearButtonSelected = false;
                    driveLicenceDriveRearButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    driveLicenceDriveRearButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Drive License", "Driver Rear", getServiceId("Drive License", "Driver Rear"), "", ""));
                    driveLicenceDriveRearButtonSelected = true;
                    driveLicenceDriveRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    driveLicenceDriveRearButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.passLicensePassRearButton:

                if (passLicencePassRearButtonSelected) {
                    Utilities.removeInspection("Pass License", "Pass Rear");
                    passLicencePassRearButtonSelected = false;
                    passLicencePassRearButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    passLicencePassRearButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Pass License", "Pass Rear", getServiceId("Pass License", "Pass Rear"), "", ""));
                    passLicencePassRearButtonSelected = true;
                    passLicencePassRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    passLicencePassRearButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;

            case R.id.reversePassRearButton:

                if (reversePassRearButtonSelected) {
                    Utilities.removeInspection("Reverse", "Pass Rear");
                    reversePassRearButtonSelected = false;
                    reversePassRearButton.setBackgroundColor(getResources().getColor(R.color.app_light_grey));
                    reversePassRearButton.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    Utilities.saveInspection(new SelectedInspection("Reverse", "Pass Rear", getServiceId("Reverse", "Pass Rear"), "", ""));
                    reversePassRearButtonSelected = true;
                    reversePassRearButton.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    reversePassRearButton.setTextColor(getResources().getColor(R.color.app_white));
                }

                break;


        }
    }
}
