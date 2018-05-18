package com.krishna.logindot.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;
import static com.krishna.logindot.utils.AppConstants.device_id;
import static com.krishna.logindot.utils.AppConstants.device_type;

/**
 * Created by Krishna R on 18/5/18.
 */

public class AppServices {

    public static void Login(final String email, final String password, Context context, final ServiceCallback callback){
        String loginResponse = "";
        String url = AppConstants.loginUrl;
        String  REQUEST_TAG = "loginRequest";
        String device_id = AppConstants.device_id;
        int device_type = AppConstants.device_type;

        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();

        /*StringRequest strReq = new StringRequest(POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onResponse(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                        callback.onResponse(String.valueOf(obj));
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
                //callback.onResponse(error.toString());
            }
        })
        {
            // set headers
            @Override
            public Map < String, String > getHeaders() throws com.android.volley.AuthFailureError {
                Map< String, String > params = new HashMap< String, String >();
                //params.put("Authorization: Basic", TOKEN);
                params.put("email", email);
                params.put("password", password);
                params.put("device_id", "b4191ec717ee49f6");
                params.put("device_type", "1");
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(strReq, REQUEST_TAG);*/


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("device_id", device_id);
            jsonObject.put("device_type", device_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*Map< String, String > params = new HashMap< String, String >();
        //params.put("Authorization: Basic", TOKEN);
        params.put("email", email);
        params.put("password", password);
        params.put("device_type", String.valueOf(device_type));
        params.put("device_id", device_id);
        JSONObject jsonObject = new JSONObject(params);*/

        Log.e("jsonObject ", String.valueOf(jsonObject));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onResponse(String.valueOf(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                        callback.onResponse(String.valueOf(obj));
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String,String> headers = new HashMap();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest, REQUEST_TAG);
    }

}
