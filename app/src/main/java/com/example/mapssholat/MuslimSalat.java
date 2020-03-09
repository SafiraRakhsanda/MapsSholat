package com.example.mapssholat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MuslimSalat extends AppCompatActivity{

    private static final String TAG = "tag";
    String url, mEbi;
    String json_tag_obj = "json_obj_req";
    ProgressDialog pDialog;
    TextView mSubuhTv, mSyuruqTv, mDzuhurTv, mAsharTv, mMaghribTv, mIsyaTv, textView, mLocationTv, mDateTv;
    Button mSrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muslim_salat);

        mSubuhTv = findViewById(R.id.fajrTv);
        mSyuruqTv = findViewById(R.id.syuruqTv);
        mDzuhurTv = findViewById(R.id.dzuhurTv);
        mAsharTv = findViewById(R.id.asharTv);
        mMaghribTv = findViewById(R.id.maghribTv);
        mIsyaTv = findViewById(R.id.isyaTv);

        mLocationTv = findViewById(R.id.locationTv);
        mDateTv = findViewById(R.id.dateTv);
        textView = findViewById(R.id.edTxt);

        Intent intent = getIntent();
        textView.setText(intent.getStringExtra("mycity"));
        mEbi = String.valueOf(intent.getStringExtra("mycity"));

        url = "https://muslimsalat.com/"+mEbi+".json?key=45178e26e94d5ad42a6ca0520dea954a";
        searchLoc();

    }

    private void searchLoc() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Ini loding, maap maap nehhh");
        pDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String country = response.get("country").toString();
                            String state = response.get("state").toString();
                            String city = response.get("city").toString();
                            String location = country + ", " + state + ", " + city;

                            String date = response.getJSONArray("items").getJSONObject(0).get("date_for").toString();

                            String subuh = response.getJSONArray("items").getJSONObject(0).get("fajr").toString();
                            String syuruq = response.getJSONArray("items").getJSONObject(0).get("shurooq").toString();
                            String dzuhur = response.getJSONArray("items").getJSONObject(0).get("dhuhr").toString();
                            String ashar = response.getJSONArray("items").getJSONObject(0).get("asr").toString();
                            String maghrib = response.getJSONArray("items").getJSONObject(0).get("maghrib").toString();
                            String isya = response.getJSONArray("items").getJSONObject(0).get("isha").toString();

                            mSubuhTv.setText(subuh);
                            mSyuruqTv.setText(syuruq);
                            mDzuhurTv.setText(dzuhur);
                            mAsharTv.setText(ashar);
                            mMaghribTv.setText(maghrib);
                            mIsyaTv.setText(isya);

                            mLocationTv.setText(location);
                            mDateTv.setText(date);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error : " + error.getMessage());
                Toast.makeText(MuslimSalat.this, "Error", Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, json_tag_obj);
    }
}
