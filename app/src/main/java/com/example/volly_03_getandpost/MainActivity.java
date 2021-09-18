package com.example.volly_03_getandpost;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    String imei = "";
    TextView textView;
    String deviceApi;
    BufferedReader reader;
    String line;

    public static Context context;
    StringBuffer responseConstant = new StringBuffer();
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "Live247";
    private static final String KEY_NAME = "devapi";

//    private static HttpsURLConnection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();
//        textView = (TextView) findViewById(R.id.text);
//
////getMethod
////        final TextView textView = (TextView)findViewById(R.id.text);
//
//        RequestQueue queue = Volley.newRequestQueue(this);
////        String url ="https://projects.vishnusivadas.com/testing/read.php";
//        String url ="https://saas.live247.ai/api/device/?limit=10&offset=0&filter=serial_number%3Db3a16e99d4bd1655";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), "Response: " + response,Toast.LENGTH_LONG).show();

//                        try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    textView.setText(String.valueOf(response));
//                            if(response.getString("result") == "FETCH_DEVICE_SUCCESS"){
//                                JSONObject newResponse = response.getJSONObject("response");
//                                JSONArray  newArray = newResponse.getJSONArray("devices_data");
//                                for(int i=0;i<newArray.length();i++){
//                                    JSONObject fResponse = newArray.getJSONObject(0);
//                                    deviceApi = fResponse.getString("api");
//                                    Toast.makeText(MainActivity.this, deviceApi, Toast.LENGTH_LONG).show();
//                                    textView.setText(String.valueOf(deviceApi));
//                                }
//                            }

//                        if(response.getString("result").equals("FETCH_DEVICE_SUCCESS")){
//                            JSONObject newResponse = response.getJSONObject("response");
//                            JSONArray  newArray = newResponse.getJSONArray("devices_data");
//                            for(int i=0;i<newArray.length();i++){
//                                JSONObject fResponse = newArray.getJSONObject(0);
//                                deviceApi = fResponse.getString("api");
//                                Toast.makeText(MainActivity.this, deviceApi, Toast.LENGTH_LONG).show();
//                                textView.setText(String.valueOf(deviceApi));

//                        try{
//                            URL url = new URL("https://saas.live247.ai/api/device/?limit=10&offset=0&filter=serial_number%3Db3a16e99d4bd1655");
//                            connection = (HttpsURLConnection)url.openConnection();
//
////                            Request setup
//                            connection.setRequestMethod("GET");
//                            int status = connection.getResponseCode();
//                            Log.d("sudks","response - " + status);
//                            if(status>299){
//                                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
//                                while((line = reader.readLine())!=null){
//                                    responseConstant.append(line);
//                                }
//                                reader.close();
//                            }else{
//                                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                                while((line = reader.readLine())!=null){
//                                    responseConstant.append(line);
//                                }
//                                reader.close();
//                            }
//                            Log.d("sudks","resoponse - "+ responseConstant.toString());
//                        }
//                        catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                textView.setText("That didn't work!");
//                Log.d("sudks","\"That didn't work!\"");
//            }
//        });
//
//        queue.add(stringRequest);

//PostMethod

//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "https://projects.vishnusivadas.com/testing/write.php";
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        textView.setText("Response is: " + response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
//            }
//        }) {
//            protected Map<String, String> getParams() {
//                Map<String, String> paramV = new HashMap<>();
//                paramV.put("param", "abc");
//                return paramV;
//            }
//        };
//        queue.add(stringRequest);
        volleyPost();
//        getDemoResponse();
//        volleyGet01();
    }
    public static Context getAppContext() {
        return MainActivity.context;
    }
    //works
    public void volleyPost(){
        Log.d("sudks","volleyPost entered welcomeActivity");
        imei = getIMEIDeviceId(getApplicationContext());//put it to shared pref
        Log.d("sudks","IMEI No. "  + imei);
        String postUrl = "https://saas.live247.ai/api/device/?limit=10&offset=0&filter=serial_number%3Db3a16e99d4bd1655";//replace with saas
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("IMEI", imei);
            Log.d("sudks","IMEI No. posted "  + imei);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponseApi: " + response);
                Toast.makeText(MainActivity.this, " response x1 -"+ response , Toast.LENGTH_LONG).show();
                try {
                    String newResponse = response.getString("result");
                    Log.d("sudks"," devApi response "  +newResponse);
                    Toast.makeText(MainActivity.this, "newResponsex(2) -"+newResponse , Toast.LENGTH_LONG).show();
                    if(newResponse.equals("CREATE_DEVICE_SUCCESS")){
                        volleyGet01();
                    }
                    Log.d("sudks"," devApi response "  +deviceApi);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
//works
    public void volleyGet01(){
        String url = "https://saas.live247.ai/api/device/?limit=10&offset=0&filter=serial_number%3Db3a16e99d4bd1655";
        List<String> jsonResponses = new ArrayList<>();

        imei = getIMEIDeviceId(getApplicationContext());//put it to shared pref
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("IMEI", imei);
            Log.d("sudks","IMEI No. posted "  + imei);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject newResponse = response.getJSONObject("response");
                    JSONArray  newArray = newResponse.getJSONArray("devices_data");
                    for(int i=0;i<newArray.length();i++){
                            JSONObject fResponse = newArray.getJSONObject(0);
                            deviceApi = fResponse.getString("api");
                        Log.d("sudks","deviceApi - "+    deviceApi );
                        Toast.makeText(context, "deviceApi3 - "+    deviceApi , Toast.LENGTH_LONG).show();
                    }
                    sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                    sharedpreferences.edit().putString(KEY_NAME, deviceApi).apply();
                    String name = sharedpreferences.getString(KEY_NAME,null);
                    Toast.makeText(MainActivity.this, "shared name - "+name, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

//    public void volleyPost(){
//        Log.d("sudks","volleyPost entered welcomeActivity");
////        imei = getIMEIDeviceId(getApplicationContext());//put it to shared pref
//        Log.d("sudks","IMEI No. "  + imei);
//        String postUrl = "https://saas.live247.ai/api/device/?limit=10&offset=0&filter=serial_number%3Db3a16e99d4bd1655";//replace with saas
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        JSONObject postData = new JSONObject();
//        try {
//            postData.put("IMEI", imei);
//            Log.d("sudks","IMEI No. posted "  + imei);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d(TAG, "onResponseApi: " + response);
////                System.out.println(response);
//                try {
////                    JSONObject jsonObject = new JSONObject(response);
////                    textView.setText(String.valueOf(response));
//                    if(response.getString("result").equals("FETCH_DEVICE_SUCCESS")){
//                        JSONObject newResponse = response.getJSONObject("response");
//                        JSONArray  newArray = newResponse.getJSONArray("devices_data");
//                        for(int i=0;i<newArray.length();i++){
//                            JSONObject fResponse = newArray.getJSONObject(0);
//                            deviceApi = fResponse.getString("api");
//                            Toast.makeText(MainActivity.this, deviceApi, Toast.LENGTH_LONG).show();
////                            textView.setText(String.valueOf(deviceApi));
//                        }
//                    }
////                    String devApi = null;
////                    devApi = response.getString("devapi");
//                    Log.d("sudks"," devApi response "  +deviceApi);
////                    sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
////                    sharedpreferences.edit().putString(KEY_NAME, devApi).apply();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//
//        requestQueue.add(jsonObjectRequest);
//
//    }

    @SuppressLint("HardwareIds")
    public static String getIMEIDeviceId(Context context) {

        String deviceId;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return "";
                }
            }
            assert mTelephony != null;
            if (mTelephony.getDeviceId() != null)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    deviceId = mTelephony.getImei();
                }else {
                    deviceId = mTelephony.getDeviceId();
                }
            } else {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }
        Log.d("sudks", "deviceId"+deviceId);
        return deviceId;
    }
    //getIMEIDeviceId( MainActivity.getAppContext());


//    private void getDemoResponse(){
//        RequestQueue queue = Volley.newRequestQueue(this);
////        String url ="https://projects.vishnusivadas.com/testing/read.php";
//        String url ="https://saas.live247.ai/api/device/?limit=10&offset=0&filter=serial_number%3Db3a16e99d4bd1655";
//        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Toast.makeText(getApplicationContext(), "Response: x " + response,Toast.LENGTH_LONG).show();
//                        try{
//
//                            JSONObject response1 = response.getJSONObject("response");
//                            Toast.makeText(context, response1.toString(), Toast.LENGTH_SHORT).show();
////                        Toast.makeText(getApplicationContext(), "Response x: " + response,Toast.LENGTH_LONG).show();
////                            JSONArray devices_data = response1.getJSONArray("devices_data");
//////                            for(int i=0;i<devices_data.length();i++){
//////                                JSONObject deviceObj = devices_data.getJSONObject(0);
//////                                StringRequest FinalApi = deviceObj.getString("api");
////                                Toast.makeText(context, devices_data.toString(), Toast.LENGTH_LONG).show();
//////                            }
//
//
////                            URL url = new URL("https://saas.live247.ai/api/device/?limit=10&offset=0&filter=serial_number%3Db3a16e99d4bd1655");
////                            connection = (HttpsURLConnection)url.openConnection();
////
//////                            Request setup
////                            connection.setRequestMethod("GET");
////                            int status = connection.getResponseCode();
////                            Log.d("sudks","response - " + status);
////                            if(status>299){
////                                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
////                                while((line = reader.readLine())!=null){
////                                    responseConstant.append(line);
////                                }
////                                reader.close();
////                            }else{
////                                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
////                                while((line = reader.readLine())!=null){
////                                    responseConstant.append(line);
////                                }
////                                reader.close();
////                            }
////                            Log.d("sudks","vollyresoponse - "+ responseConstant.toString());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("sudks","Volly failed");
//            }
//        });
//
//        queue.add(stringRequest);
//
//
//    }

}