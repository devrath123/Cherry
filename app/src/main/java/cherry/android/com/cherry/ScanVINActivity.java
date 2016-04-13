package cherry.android.com.cherry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import Utils.CherryAPI;
import Utils.Connectivity;
import Utils.Constants;
import Utils.GsonRequest;
import Utils.Utilities;
import beans.SearchVinBean;


public class ScanVINActivity extends ActionBarActivity implements View.OnClickListener{

    Button searchVINButton,cancelButton,saveButton;
    EditText vinNumberEditText;
    public  static SearchVinBean searchVinBean;
    static String vinNumber = "";
    ProgressDialog progressDialog;
    boolean scanEnd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_vin);

        initialiseUI();
    }

    private void initialiseUI()
    {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);


        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setBackgroundResource(0);

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setBackgroundResource(0); ;


        searchVINButton = (Button)findViewById(R.id.searchVinButton);
        vinNumberEditText = (EditText)findViewById(R.id.vinNumberEditText);

        searchVINButton.setOnClickListener(this);


        if(Connectivity.isConnected(ScanVINActivity.this))
            scanVIN();

        else
            Utilities.internetConnectionError(ScanVINActivity.this);
    }

    @Override
    public void onClick(View v) {

       /* if(v.getId() == R.id.scanvinButton)
        {
            if(Connectivity.isConnected(ScanVINActivity.this))
                scanVIN();

            else
                Utilities.internetConnectionError(ScanVINActivity.this);
        }
        else */
        if(v.getId() == R.id.searchVinButton)
        {
            if(vinNumberEditText.getText().toString().trim().length() > 0 && (vinNumberEditText.getText().toString().trim().length() == 17))
            {

                if(Connectivity.isConnected(ScanVINActivity.this)) {

                    progressDialog.show();

                    String url = CherryAPI.ROOT_URL + CherryAPI.SEARCH_VIN_URL + "/" + vinNumberEditText.getText().toString().trim() +
                            "/" + LoginActivity.loginBean.getData().getUser().getId() + "/" + LoginActivity.loginBean.getData().getUserBranch().get(0).getId();

                    GsonRequest<SearchVinBean> vinSearchGsonRequest = new GsonRequest<SearchVinBean>(Request.Method.GET, url, SearchVinBean.class, null, new Response.Listener<SearchVinBean>() {
                        @Override
                        public void onResponse(SearchVinBean response) {

                            progressDialog.dismiss();

                            Constants.CURRENT_VIN = vinNumberEditText.getText().toString().trim();

                            searchVinBean = response;
                            Constants.CUSTOMER_INFO_FROM = Constants.FROM_SCAN_VIN;

                            if(response.getStatus()) {
                                Constants.SAVE_UPDATE_REQUIRED = Constants.CUSTOMER_NOTHING_REQUIRED;
                                startActivity(new Intent(ScanVINActivity.this, CustomerInfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            }
                            else
                            {
                                vinNumber = vinNumberEditText.getText().toString().trim();

                                if(searchVinBean.getData() == null)
                                    Constants.SAVE_UPDATE_REQUIRED = Constants.CUSTOMER_SAVE;

                                else
                                    Constants.SAVE_UPDATE_REQUIRED = Constants.CUSTOMER_PARTIAL;

                                startActivity(new Intent(ScanVINActivity.this, CustomerInfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Utilities.serverError(ScanVINActivity.this);
                        }
                    });

                    vinSearchGsonRequest.setShouldCache(false);
                    Utilities.getRequestQueue(ScanVINActivity.this).add(vinSearchGsonRequest);

                }
                else
                    Utilities.internetConnectionError(ScanVINActivity.this);
            }
            else
                Toast.makeText(ScanVINActivity.this,"VIN is InValid",Toast.LENGTH_SHORT).show();



        }
    }

    private void scanVIN()
    {
        try {
            if(!scanEnd)
            new IntentIntegrator(this).initiateScan();
    }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case IntentIntegrator.REQUEST_CODE:
                    if (resultCode == Activity.RESULT_OK) {
                        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                        String resultString = result.getContents();
                        if(resultString.length() == 18)
                            resultString = resultString.substring(1);

                        vinNumberEditText.setText(resultString);
                        scanEnd = true;
                    }
                    break;
        }
    }



}
