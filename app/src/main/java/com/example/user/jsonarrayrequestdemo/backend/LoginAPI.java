package com.example.user.jsonarrayrequestdemo.backend;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.user.jsonarrayrequestdemo.utils.Config;
import com.example.user.jsonarrayrequestdemo.utils.Log;

import org.json.JSONObject;

import java.util.HashMap;


public class LoginAPI {
    private Context mCaller;
    private HashMap<String, String> mParams = null;
    private Adapter mAdapter = null;
    private ResponseListener responseListener;

    public LoginAPI(Context caller, String sqlRequest,
                    ResponseListener responseListener) {
        this.mCaller = caller;

        this.mParams = new HashMap<String, String>();
        this.mParams.put("sql", sqlRequest);

        this.responseListener = responseListener;
    }

    public void execute() {

        this.mAdapter = new Adapter(this.mCaller);
        this.mAdapter.doPost(Config.TAG_LOGIN, Config.API_LOGIN, mParams,
                new APIResponseListener() {

                    @Override
                    public void onResponse(String response) {
                        mParams = null;
                        // Parse Response and Proceed Further
                        parse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mParams = null;
                        // Inform Caller that the API call is failed
                        responseListener.onResponce(Config.TAG_LOGIN,
                                Config.API_FAIL, null);
                    }
                });
    }

    /*
     * Parse the response and prepare for callback
     */
    private void parse(String response) {
        int code = 0;
        String mesg = "";
        JSONObject jsonDoc = null;

        try {

            jsonDoc = new JSONObject(response);

            code = jsonDoc.getInt("code");
            mesg = jsonDoc.getString("message");

            if (code == 0) {
             /*Response : parsing */
            }
        } catch (Exception e) {
            code = -1;
            mesg = "Exception :: " + this.getClass() + " :: parse() :: "
                    + e.getLocalizedMessage();
            Log.error(this.getClass() + " :: Exception :: ", e);
            Log.print(this.getClass() + " :: Exception :: ", e);
        } finally {
            response = null;
            /** release variables */
            jsonDoc = null;
        }

        this.doCallBack(code, mesg);
    }

    /*
     * Send control back to the caller which includes
     *
     * Status: Successful or Failure Message: Its an Object, if required
     */
    private void doCallBack(int code, String mesg) {

        try {
            if (code == 0) {
                responseListener.onResponce(Config.TAG_LOGIN,
                        Config.API_SUCCESS, null);
            } else if (code > 0) {
//              AlertDailogView.showAlert(mCaller, mesg, true).show();
                Toast.makeText(mCaller, mesg, Toast.LENGTH_LONG).show();
                responseListener.onResponce(Config.TAG_LOGIN, Config.API_FAIL,
                        null);
            } else if (code < 0) {
                responseListener.onResponce(Config.TAG_LOGIN, Config.API_FAIL,
                        null);
            }
        } catch (Exception e) {
            Log.error(this.getClass() + " :: Exception :: ", e);
            Log.print(this.getClass() + " :: Exception :: ", e);
        } finally {
            mAdapter = null;
            mesg = null;
        }
    }

    /*
     * Cancel API Request
     */
    public void doCancel() {
        if (mAdapter != null)
            mAdapter.doCancel(Config.TAG_LOGIN);
    }
}