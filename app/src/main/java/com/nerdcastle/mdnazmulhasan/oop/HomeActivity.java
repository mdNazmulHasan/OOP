package com.nerdcastle.mdnazmulhasan.oop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import utils.AppController;

/**
 * Created by mdnazmulhasan on 8/16/15.
 */
public class HomeActivity extends AppCompatActivity {
    String userId;
    String token;
    String questionNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        userId=getIntent().getStringExtra("id");
        //Toast.makeText(getApplicationContext(),userId,Toast.LENGTH_LONG).show();

    }
    public void start(View view) throws JSONException {
        JSONObject id=new JSONObject();
        id.put("UserId",userId);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, "http://dotnet.nerdcastlebd.com/OOP/api/users", id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    token=response.getString("Token");
                    questionNumber=response.getString("NoOfQuestion");
                    //Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();
                    Intent i=new Intent(getApplicationContext(),QuestionActivity.class);
                    i.putExtra("token",token);
                    i.putExtra("questionNumber",questionNumber);
                    i.putExtra("id",userId);
                    startActivity(i);
                } catch (JSONException e) {
                    //Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof TimeoutError) {
                    String msg = "Request Timed Out, Pls try again";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
                else if(error instanceof NoConnectionError) {
                    String msg = "No internet Access, Check your internet connection.";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(request);

    }
}
