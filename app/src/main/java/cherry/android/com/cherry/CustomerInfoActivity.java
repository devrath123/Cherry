package cherry.android.com.cherry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import Utils.CherryAPI;
import Utils.Connectivity;
import Utils.Constants;
import Utils.GsonRequest;
import Utils.Utilities;
import beans.SearchVinBean;


public class CustomerInfoActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    TextView headerTextView;
    EditText customerNameEditText, customerStateProvinceEditText, customerLicenseEditText, customerVINEditText,
            customerYearEditText, customerMakeIdEditText, customerModelIdEditText;
    Button cancelCustomerButton, saveCustomerButton;
    Spinner customerProcedureSpinner,customerCountrySpinner;
    //String[] procedureSpinnerArray = new String[]{"1 Person Procedure", "2 Person Procedure", "3 Person Procedure", "4 Person Procedure", "5 Person Procedure"};
    String[] procedureSpinnerArray,countrySpinnerArray ;
    ArrayAdapter<String> procedureAdapter,countryAdapter,stateAdapter;
    public static String selectedProcedure = "1 Person Procedure", customerName = "";
    String selectedCountry = "";
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);

        initialiseUI();

    }

    private void initialiseUI() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        procedureSpinnerArray = getResources().getStringArray(R.array.procedure_array);
        countrySpinnerArray = getResources().getStringArray(R.array.country_array);

        headerTextView = (TextView) findViewById(R.id.headerTextView);
        headerTextView.setText("Inspections To Complete");

        cancelCustomerButton = (Button) findViewById(R.id.cancelButton);
        cancelCustomerButton.setVisibility(View.GONE);
      //  cancelCustomerButton.setText("Cancel");
      // cancelCustomerButton.setBackgroundDrawable(null);
      //  cancelCustomerButton.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //cancelCustomerButton.setOnClickListener(this);

        saveCustomerButton = (Button) findViewById(R.id.saveButton);
        saveCustomerButton.setText("Save");
        saveCustomerButton.setBackgroundDrawable(null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMarginEnd(20);
        saveCustomerButton.setLayoutParams(params);
        saveCustomerButton.setOnClickListener(this);

        customerNameEditText = (EditText) findViewById(R.id.customerNameEditText);
        customerStateProvinceEditText = (EditText) findViewById(R.id.customerStateProvinceEditText);
        customerLicenseEditText = (EditText) findViewById(R.id.customerLicenseEditText);
        customerVINEditText = (EditText) findViewById(R.id.customerVINEditText);
        customerYearEditText = (EditText) findViewById(R.id.customerYearEditText);
        customerMakeIdEditText = (EditText) findViewById(R.id.customerMakeIdEditText);
        customerModelIdEditText = (EditText) findViewById(R.id.customerModelIdEditText);

        customerProcedureSpinner = (Spinner) findViewById(R.id.customerProcedureSpinner);
        customerCountrySpinner = (Spinner) findViewById(R.id.customerCountrySpinner);

        procedureAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, procedureSpinnerArray);
        procedureAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        customerProcedureSpinner.setAdapter(procedureAdapter);
        customerProcedureSpinner.setOnItemSelectedListener(this);

        countryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,countrySpinnerArray);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        customerCountrySpinner.setAdapter(countryAdapter);
        customerCountrySpinner.setOnItemSelectedListener(this);

        setValues();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!(progressDialog == null))
        {
            if(progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }

    private void enableAll()
    {
        customerNameEditText.setEnabled(true);
        customerCountrySpinner.setEnabled(true);
        customerStateProvinceEditText.setEnabled(true);
        customerLicenseEditText.setEnabled(true);
        customerVINEditText.setEnabled(true);
        customerYearEditText.setEnabled(true);
        customerMakeIdEditText.setEnabled(true);
        customerModelIdEditText.setEnabled(true);
    }

    private void setValues() {

        enableAll();

        if (Constants.SAVE_UPDATE_REQUIRED.equals(Constants.CUSTOMER_NOTHING_REQUIRED)) {

            if(Constants.CUSTOMER_INFO_FROM.equals(Constants.FROM_SCAN_VIN)) {
                customerName = ScanVINActivity.searchVinBean.getData().get(0).getName();
                customerNameEditText.setText(customerName);
                customerStateProvinceEditText.setText(ScanVINActivity.searchVinBean.getData().get(0).getState());
                customerLicenseEditText.setText(ScanVINActivity.searchVinBean.getData().get(0).getLicence());
                customerVINEditText.setText(ScanVINActivity.searchVinBean.getData().get(0).getVin());
                customerYearEditText.setText(ScanVINActivity.searchVinBean.getData().get(0).getYear());
                customerMakeIdEditText.setText(ScanVINActivity.searchVinBean.getData().get(0).getMake_id());
                customerModelIdEditText.setText(ScanVINActivity.searchVinBean.getData().get(0).getModel_id());
            }
            else
            {
                customerName = PendingInspectionsActivity.searchBeanPendingInspection.getName();
                customerNameEditText.setText(customerName);
                customerStateProvinceEditText.setText(PendingInspectionsActivity.searchBeanPendingInspection.getState());
                customerLicenseEditText.setText(PendingInspectionsActivity.searchBeanPendingInspection.getLicence());
                customerVINEditText.setText(PendingInspectionsActivity.searchBeanPendingInspection.getVin());
                customerYearEditText.setText(PendingInspectionsActivity.searchBeanPendingInspection.getYear());
                customerMakeIdEditText.setText(PendingInspectionsActivity.searchBeanPendingInspection.getMake_id());
                customerModelIdEditText.setText(PendingInspectionsActivity.searchBeanPendingInspection.getModel_id());

            }

            customerCountrySpinner.setEnabled(false);
            customerStateProvinceEditText.setEnabled(false);
            customerLicenseEditText.setEnabled(false);
            customerVINEditText.setEnabled(false);
            customerYearEditText.setEnabled(false);
            customerMakeIdEditText.setEnabled(false);
            customerNameEditText.setEnabled(false);
            customerModelIdEditText.setEnabled(false);
        }
        else {
            customerVINEditText.setText(ScanVINActivity.vinNumber);
            customerVINEditText.setEnabled(false);
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner)parent;

        if(spinner.getId() == R.id.customerProcedureSpinner) {
            selectedProcedure = (String) parent.getItemAtPosition(position);
        }

        if(spinner.getId() == R.id.customerCountrySpinner)
            selectedCountry = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showMToast(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private boolean checkValidation()
    {
        if(customerNameEditText.getText().toString().trim().length() > 0)
        {
                if(customerStateProvinceEditText.getText().toString().trim().length() > 0)
                {
                    if(customerLicenseEditText.getText().toString().trim().length() > 0)
                    {
                        if(customerVINEditText.getText().toString().trim().length() > 0)
                        {
                            if(customerYearEditText.getText().toString().trim().length() > 0)
                            {
                                if(customerMakeIdEditText.getText().toString().trim().length() > 0)
                                {
                                    if(customerModelIdEditText.getText().toString().trim().length() > 0)
                                    {
                                        return true;
                                    }
                                    else
                                    {
                                        showMToast("Enter Model Id");
                                        return false;
                                    }
                                }
                                else
                                {
                                    showMToast("Enter MakeId");
                                    return false;
                                }
                            }
                            else
                            {
                                showMToast("Enter Year");
                                return false;
                            }
                        }
                        else
                        {
                            showMToast("Enter VIN");
                            return false;
                        }
                    }
                    else
                    {
                        showMToast("Enter License");
                        return false;
                    }
                }
                else
                {
                    showMToast("Enter State/Province");
                    return false;
                }

        }
        else
        {
            showMToast("Enter Customer Name");
            return false;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
           /* case R.id.cancelButton:
                finish();
                break;*/

            case R.id.saveButton:

                customerName = customerNameEditText.getText().toString();

                if (Connectivity.isConnected(CustomerInfoActivity.this)) {

                    if (checkValidation())
                    {
                        if(progressDialog.isShowing())
                        progressDialog.show();

                        Constants.CURRENT_VIN = customerVINEditText.getText().toString();


                    if (!(Constants.SAVE_UPDATE_REQUIRED.equals(Constants.CUSTOMER_NOTHING_REQUIRED))) {
                        String url = CherryAPI.ROOT_URL + CherryAPI.SAVE_CUSTOMER_INFO;



                        Map<String, String> map = new HashMap<>();
                        map.put(Constants.SAVE_CUSTOMER_CUSTOMERID, LoginActivity.loginBean.getData().getUser().getId());
                        map.put(Constants.SAVE_CUSTOMER_CARID, "1");
                        map.put(Constants.SAVE_CUSTOMER_NAME, customerNameEditText.getText().toString());
                        map.put(Constants.SAVE_CUSTOMER_STATE,  customerStateProvinceEditText.getText().toString());
                        map.put(Constants.SAVE_CUSTOMER_COUNTRY, selectedCountry);
                        map.put(Constants.SAVE_CUSTOMER_LICENSE, customerLicenseEditText.getText().toString());
                        map.put(Constants.SAVE_CUSTOMER_BRANCHID, LoginActivity.loginBean.getData().getUserBranch().get(0).getId());
                        map.put(Constants.SAVE_CUSTOMER_CREATEDBY, LoginActivity.loginBean.getData().getUser().getId());
                        map.put(Constants.SAVE_CUSTOMER_VIN, customerVINEditText.getText().toString());
                        map.put(Constants.SAVE_CUSTOMER_MAKEID, customerMakeIdEditText.getText().toString());
                        map.put(Constants.SAVE_CUSTOMER_MODELID, customerModelIdEditText.getText().toString());
                        map.put(Constants.SAVE_CUSTOMER_YEAR, customerYearEditText.getText().toString());

                        GsonRequest<SearchVinBean> customerInfoGsonRequest = new GsonRequest<SearchVinBean>(
                                Request.Method.POST,
                                url,
                                SearchVinBean.class, map,
                                new com.android.volley.Response.Listener<SearchVinBean>() {
                                    @Override
                                    public void onResponse(SearchVinBean res) {

                                        progressDialog.dismiss();
                                        if (res.getStatus()) {

                                            //Save Selected Procedure for Editing
                                            if(!(res.getData().get(0) == null)) {
                                                SearchVinBean.Data data = res.getData().get(0);
                                                data.setCreated_at(selectedProcedure);
                                                Utilities.saveCustomerInfoHashMap(Constants.CURRENT_VIN, data);
                                            }
                                            if (!(Utilities.getTempArrayList() == null))
                                                Utilities.clearTempArrayList();

                                            InspectionsToCompleteActivity.generalInspectionChecked = false;
                                            InspectionsToCompleteActivity.lightInspectionChecked = false;

                                            startActivity(new Intent(CustomerInfoActivity.this, InspectionsToCompleteActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra(Constants.INSPECTIONS_LIST_FROM,Constants.FROM_CUSTOMER_INFO));

                                        } else
                                            Toast.makeText(CustomerInfoActivity.this,
                                                    "Try Again.",
                                                    Toast.LENGTH_SHORT).show();

                                    }
                                },
                                new com.android.volley.Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        progressDialog.dismiss();
                                        Utilities.serverError(CustomerInfoActivity.this);
                                    }
                                });
                        customerInfoGsonRequest.setShouldCache(false);
                        Utilities.getRequestQueue(CustomerInfoActivity.this).add(customerInfoGsonRequest);

                    } else
                    {
                        if (!(Utilities.getTempArrayList() == null))
                            Utilities.clearTempArrayList();

                        //Save Selected Procedure for Editing
                        SearchVinBean.Data data = null;
                        if(Constants.CUSTOMER_INFO_FROM.equals(Constants.FROM_SCAN_VIN)) {
                            data = ScanVINActivity.searchVinBean.getData().get(0);
                            data.setCreated_at(selectedProcedure);
                        }
                        else
                        {
                            data = PendingInspectionsActivity.searchBeanPendingInspection;
                            data.setCreated_at(selectedProcedure);
                        }
                        Utilities.saveCustomerInfoHashMap(Constants.CURRENT_VIN, data);

                        InspectionsToCompleteActivity.generalInspectionChecked = false;
                        InspectionsToCompleteActivity.lightInspectionChecked = false;

                        startActivity(new Intent(CustomerInfoActivity.this, InspectionsToCompleteActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra(Constants.INSPECTIONS_LIST_FROM,Constants.FROM_CUSTOMER_INFO));

                    }

                    }

                } else
                    Utilities.internetConnectionError(CustomerInfoActivity.this);

                break;
        }
    }
}
