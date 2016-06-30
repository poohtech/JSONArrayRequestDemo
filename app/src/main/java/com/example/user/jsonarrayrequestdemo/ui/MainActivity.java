package com.example.user.jsonarrayrequestdemo.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.esp.jsonparsing.R;
import com.example.user.jsonarrayrequestdemo.backend.LoginAPI;
import com.example.user.jsonarrayrequestdemo.backend.ResponseListener;
import com.example.user.jsonarrayrequestdemo.utils.Config;
import com.example.user.jsonarrayrequestdemo.utils.Utils;


public class MainActivity extends Activity implements ResponseListener {

    private LoginAPI loginAPI;
    private ProgressDialog mProgressDialog;

    private String requestString;

    private String customerIdValue = "015a48q0";
    private String isoCountryCodeValue = "IN";
    private String msisdnValue = "9601011454";
    private String appKeyValue = "3d44cdfede8044fdd4401";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestString = "[";
        requestString += "\"customerId\":\"" + customerIdValue + "\"," +
                "\"isoCountryCode\":\"" + isoCountryCodeValue + "\"," +
                "\"msisdn\":\"" + msisdnValue + "\"," +
                "\"appKey\":\"" + appKeyValue + "\"";
        requestString += "]";

        /*API call */
        callAPI();

    }

    private void callAPI() {
        if (Utils.isOnline(MainActivity.this)) {

            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Please Wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            loginAPI = new LoginAPI(MainActivity.this, requestString, MainActivity.this);
            loginAPI.execute();

        } else {
            Toast.makeText(MainActivity.this, "Connection Error",
                    Toast.LENGTH_LONG).show();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onResponce(String tag, int result, Object obj) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }

        if (tag.equals(Config.TAG_LOGIN)) {
            if (result == Config.API_SUCCESS) {

                Toast.makeText(MainActivity.this, "Successfully submitted...!", Toast.LENGTH_LONG).show();

            }
        }
    }
}
