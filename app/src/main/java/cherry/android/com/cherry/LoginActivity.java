package cherry.android.com.cherry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import beans.LoginBean;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener{

    EditText emailIdEditText,passwordEditText;
    Button loginButton;
    static LoginBean loginBean;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialiseUI();

    }


    private void initialiseUI()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        emailIdEditText = (EditText)findViewById(R.id.login_emailIdEditText);
        passwordEditText = (EditText)findViewById(R.id.login_passwordEditText);

        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.loginButton)
        {
            if(emailIdEditText.getText().toString().trim().length() > 0 && passwordEditText.getText().toString().trim().length() > 0)
            {

                if(Connectivity.isConnected(LoginActivity.this)) {
                    progressDialog.show();
                    String url = CherryAPI.ROOT_URL + CherryAPI.LOGIN_URL;
                    Map<String, String> map = new HashMap<>();
                    map.put(Constants.LOGIN_EMAIL_ID, emailIdEditText.getText().toString().trim());
                    map.put(Constants.LOGIN_PASSWORD, passwordEditText.getText().toString().trim());

                    GsonRequest<LoginBean> loginGsonRequest = new GsonRequest<LoginBean>(
                            Request.Method.POST,
                            url,
                            LoginBean.class, map,
                            new com.android.volley.Response.Listener<LoginBean>() {
                                @Override
                                public void onResponse(LoginBean res) {

                                    if (res.getStatus()) {
                                        progressDialog.dismiss();
                                        loginBean = res;
                                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity.this,
                                                "Enter valid credentials",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                    Utilities.serverError(LoginActivity.this);
                                }
                            });
                    loginGsonRequest.setShouldCache(false);
                    Utilities.getRequestQueue(LoginActivity.this).add(loginGsonRequest);
                }
                else
                    Utilities.internetConnectionError(LoginActivity.this);


            }
            else
                Toast.makeText(this,"Enter both Email and Password.",Toast.LENGTH_SHORT).show();
        }

    }
}
