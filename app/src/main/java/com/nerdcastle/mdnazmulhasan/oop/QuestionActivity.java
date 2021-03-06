package com.nerdcastle.mdnazmulhasan.oop;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import utils.AppController;

public class QuestionActivity extends AppCompatActivity {
    ListView option;
    ArrayList<String> optionList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();
    int questionId = 1;
    int TotalQuestion;
    String questionFromJson;
    TextView question;
    JSONArray answerArray;
    JSONObject correctAnswer;
    String token;
    String correctAnswerId;
    String userId;
    String questionNumber;
    Boolean next = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        try {
            token = getIntent().getStringExtra("token");
            userId = getIntent().getStringExtra("id");
            questionNumber = getIntent().getStringExtra("questionNumber");
            TotalQuestion = Integer.parseInt(questionNumber);
            next = getIntent().getBooleanExtra("next", false);

        } catch (Exception e) {

        }

        try {
            questionId = getIntent().getIntExtra("questionNow", 1);
        } catch (Exception e) {
            questionId = 2;
        }

        option = (ListView) findViewById(R.id.optionLV);

        try {
            showService();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showService() throws JSONException {
        JSONObject dataForValidation = new JSONObject();
        dataForValidation.put("QuestionId", questionId);
        dataForValidation.put("Token", token);
        dataForValidation.put("UserId", userId);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                "http://dotnet.nerdcastlebd.com/OOP/api/home", dataForValidation,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        try {
                            questionFromJson = response.getString("Description");
                            question = (TextView) findViewById(R.id.questionTV);
                            question.setVisibility(View.VISIBLE);
                            question.setText(questionFromJson);
                            answerArray = response.getJSONArray("AnswerList");
                            correctAnswer = response.getJSONObject("CorrectAnswer");
                            correctAnswerId = correctAnswer.getString("Id");
                            for (int i = 0; i < answerArray.length(); i++) {
                                optionList.add(answerArray.getJSONObject(i).getString("Description"));
                                idList.add(answerArray.getJSONObject(i).getString("Id"));

                            }
                            ArrayAdapter<String> optionAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.question_row, R.id.optinTV, optionList);
                            option.setAdapter(optionAdapter);
                            option.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Fragment fr = new Result_Fragment();
                                    Bundle data = new Bundle();
                                    data.putString("data", "ok");
                                    data.putString("token", token);
                                    data.putString("id", userId);
                                    data.putString("totalquestion", questionNumber);
                                    data.putInt("questionId", questionId);
                                    data.putString("correctAnswerId", correctAnswerId);
                                    data.putString("optionId", idList.get(position));
                                    fr.setArguments(data);
                                    FragmentManager fm = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                    fragmentTransaction.replace(R.id.questionFrame, fr);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    fragmentTransaction.commit();
                                }
                            });

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    String msg = "Request Timed Out, Pls try again";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {
                    String msg = "No internet Access, Check your internet connection.";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(request);

    }

}
