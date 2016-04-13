package cherry.android.com.cherry;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapters.InspectionsToCompleteRecyclerViewAdapter;
import Adapters.PendingInspectionsAdapter;
import Utils.CherryAPI;
import Utils.Connectivity;
import Utils.Constants;
import Utils.GsonRequest;
import Utils.Utilities;
import beans.SelectedInspection;
import beans.ServiceBean;


public class InspectionsToCompleteActivity extends ActionBarActivity implements  View.OnClickListener {

    TextView headerTextView;
    Button saveButton, submitButton,cancelButton;
    RecyclerView inspectionsListRecyclerView;

    InspectionsToCompleteRecyclerViewAdapter adapter;
    static ArrayList<ServiceBean.Services>  inspectionServiceArrayList ;
    static ArrayList<ServiceBean.Service_Categories> inspectionServiceCategoriesArrayList;

    public static boolean lightInspectionChecked, generalInspectionChecked;
    ProgressDialog progressDialog;
    String transitionFrom = "";

    RelativeLayout newCustomerRelativeLayout,branchRelativeLayout,inspectionsRelativeLayout;
    TextView branchTextView;
    Button logoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspections_to_complete);

        transitionFrom = getIntent().getStringExtra(Constants.INSPECTIONS_LIST_FROM);
        initialiseUI();
    }


    private void initialiseUI() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        headerTextView = (TextView) findViewById(R.id.headerTextView);
        headerTextView.setText(CustomerInfoActivity.customerName);

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setText("");
        saveButton.setOnClickListener(this);
        //  saveButton.setBackground(getDrawable(R.drawable.ic_drawer));

        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);

        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);

        inspectionsListRecyclerView = (RecyclerView) findViewById(R.id.inspectionsRecyclerView);
        inspectionsListRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        inspectionsListRecyclerView.setLayoutManager(mLayoutManager);
      //  inspectionsListRecyclerView.setItemAnimator(new DefaultItemAnimator());


       if (Connectivity.isConnected(InspectionsToCompleteActivity.this))
            getServicesAndCategoriesList();

        else
            Utilities.internetConnectionError(InspectionsToCompleteActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!(adapter == null))
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.newCustomerRelativeLayout)
        {
            startActivity(new Intent(InspectionsToCompleteActivity.this,ScanVINActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
        else if(v.getId() == R.id.branchRelativeLayout)
        {
            //  startActivity(new Intent(DashboardActivity.this,CustomerInfoActivity.class));
        }
        else if(v.getId() == R.id.inspectionsRelativeLayout)
        {
            startActivity(new Intent(InspectionsToCompleteActivity.this,PendingInspectionsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }

        else if(v.getId() == R.id.logoutButton)
        {
            startActivity(new Intent(InspectionsToCompleteActivity.this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }

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

        if(v.getId() == R.id.cancelButton)
        finish();

        if (v.getId() == R.id.submitButton) {
            if (lightInspectionChecked) {
                if (generalInspectionChecked) {
                    if (!(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN) == null)) {

                        if (Connectivity.isConnected(InspectionsToCompleteActivity.this)) {
                            progressDialog.show();
                            new AuthAync(InspectionsToCompleteActivity.this).execute(CherryAPI.ROOT_URL + CherryAPI.SAVE_INSPECTIONS);
                        }else
                            Utilities.internetConnectionError(InspectionsToCompleteActivity.this);
                    }
                } else
                    Toast.makeText(InspectionsToCompleteActivity.this, "Select General Inspections.", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(InspectionsToCompleteActivity.this, "Select Lights Inspections.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getServicesAndCategoriesList() {
        progressDialog.show();

        String selectedProcedure;

        if(transitionFrom.equals(Constants.FROM_CUSTOMER_INFO)) {
            lightInspectionChecked = false;
            generalInspectionChecked = false;
            selectedProcedure = CustomerInfoActivity.selectedProcedure;
        }
        else {
            lightInspectionChecked = true;
            generalInspectionChecked = true;

            selectedProcedure = PendingInspectionsAdapter.pendingSelectedProcedure;

            if (!(Utilities.getTempArrayList() == null))
                Utilities.clearTempArrayList();

        }

        String url = CherryAPI.ROOT_URL + CherryAPI.SERVICES_AND_CATEGORIES + "?" + Constants.SERVICE_POSITION_ID + "=" + selectedProcedure +
                "&" + Constants.SAVE_CUSTOMER_BRANCHID + "=" + LoginActivity.loginBean.getData().getUserBranch().get(0).getId() + "&" +
                Constants.SAVE_CUSTOMER_CREATEDBY + "=" + LoginActivity.loginBean.getData().getUser().getId();

        GsonRequest<ServiceBean> serviceBeanGsonRequest = new GsonRequest<ServiceBean>(Request.Method.GET, url, ServiceBean.class, null, new Response.Listener<ServiceBean>() {
            @Override
            public void onResponse(ServiceBean response) {

                progressDialog.dismiss();
                ServiceBean serviceBean = response;

                if(!(serviceBean.getService_categories() == null)) {
                    if(serviceBean.getService_categories().size() > 0) {
                        if (inspectionServiceCategoriesArrayList == null)
                            inspectionServiceCategoriesArrayList = new ArrayList<>();

                        inspectionServiceCategoriesArrayList.clear();
                        inspectionServiceCategoriesArrayList = serviceBean.getService_categories();

                        if (inspectionServiceCategoriesArrayList.size() > 0) {
                            adapter = new InspectionsToCompleteRecyclerViewAdapter(InspectionsToCompleteActivity.this, inspectionServiceCategoriesArrayList,transitionFrom);
                            inspectionsListRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }
                    }
                }

                if(!(serviceBean.getServices() == null))
                {
                   if(serviceBean.getServices().size() > 0)
                   {
                       if (inspectionServiceArrayList == null)
                           inspectionServiceArrayList = new ArrayList<>();

                       inspectionServiceArrayList.clear();
                       inspectionServiceArrayList = serviceBean.getServices();
                   }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Utilities.serverError(InspectionsToCompleteActivity.this);
            }
        });

        serviceBeanGsonRequest.setShouldCache(false);
        Utilities.getRequestQueue(InspectionsToCompleteActivity.this).add(serviceBeanGsonRequest);
    }



    //AsyncTask

    public class AuthAync extends AsyncTask<String,Void,JSONObject> {

        Context mContext;


        public AuthAync(Context context)
        {
            this.mContext = context;
        }

        @Override
        protected JSONObject doInBackground(String[] urls) {

            JSONObject responseJsonObject=null;
            JSONArray jsonArray = null;

            try {

                ArrayList<SelectedInspection> selectedInspectionArrayList = Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN);

                if(selectedInspectionArrayList.size() > 0)
                    jsonArray = new JSONArray();

                for(int i=0;i<selectedInspectionArrayList.size();i++)
                {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("service_id", selectedInspectionArrayList.get(i).getId());
                    jsonObject.put("qty",selectedInspectionArrayList.get(i).getQuantity());
                    jsonObject.put("custom_comment",selectedInspectionArrayList.get(i).getComment());

                    jsonArray.put(jsonObject);
                }

                JSONObject uploadServicesJsonObject = new JSONObject();
                uploadServicesJsonObject.put("branch_id", LoginActivity.loginBean.getData().getUserBranch().get(0).getId());
                uploadServicesJsonObject.put("created_by",LoginActivity.loginBean.getData().getUserBranch().get(0).getCreated_by());
                uploadServicesJsonObject.put("car_id",Utilities.getCustomerInfo(Constants.CURRENT_VIN).getId());
                uploadServicesJsonObject.put("services", jsonArray);


                HttpClient httpClient = new DefaultHttpClient();

                HttpParams myParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(myParams, 10000);
                HttpConnectionParams.setSoTimeout(myParams, 10000);


                HttpPost httpPost = new HttpPost(urls[0]);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");


                StringEntity se = new StringEntity(uploadServicesJsonObject.toString());
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(se);

                HttpResponse httpResponse =  httpClient.execute(httpPost);
                String temp = EntityUtils.toString(httpResponse.getEntity());

                Log.i("Response : ",temp);

                responseJsonObject = new JSONObject(temp);

            }catch (Exception e)
            {
                 e.printStackTrace();
            }

            return responseJsonObject;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(JSONObject response) {
            super.onPostExecute(response);

            try {
             //  if(!(response == null))
                {

                   Log.i("Response : ",response.toString());

                    progressDialog.dismiss();

                   // boolean status = (boolean)response.get("status");
                   // if(status)
                    {
                       Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).clear();
                       Utilities.customerInfoHashMap.remove(Constants.CURRENT_VIN);

                        Toast.makeText(InspectionsToCompleteActivity.this,"Data Saved",Toast.LENGTH_SHORT).show();

                       // if(transitionFrom.equals(Constants.FROM_CUSTOMER_INFO))
                        startActivity(new Intent(InspectionsToCompleteActivity.this,DashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                        /*else
                            startActivity(new Intent(InspectionsToCompleteActivity.this,PendingInspectionsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                */    }
                   /* else
                        Utilities.serverError(InspectionsToCompleteActivity.this);*/
                }
               /* else
                {
                    progressDialog.dismiss();
                    Utilities.serverError(InspectionsToCompleteActivity.this);
                }*/


            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}
