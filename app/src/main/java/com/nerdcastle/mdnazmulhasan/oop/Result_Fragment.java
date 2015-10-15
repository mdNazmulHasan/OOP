package com.nerdcastle.mdnazmulhasan.oop;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Result_Fragment extends Fragment {
    Button nxtBtn;
    String token;
    String userId;
    String questionNumber;
    Boolean next=false;
    int questionId;
    String questionid;
    String correctAnswerId;
    String optionId;
    TextView answerTV;
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_result_, container, false);
        answerTV= (TextView) rootView.findViewById(R.id.textViewAnswer);
       // String myValue = this.getArguments().getString("data");
        questionId=this.getArguments().getInt("questionId");
        correctAnswerId=this.getArguments().getString("correctAnswerId");
        optionId=this.getArguments().getString("optionId");
        if(optionId.equalsIgnoreCase(correctAnswerId)){
            Toast.makeText(getActivity(),"correct",Toast.LENGTH_LONG).show();
            answerTV.setText("Correct");
        }else{
            Toast.makeText(getActivity(),"wrong",Toast.LENGTH_LONG).show();
            answerTV.setText("wrong");
        }
        questionid=String.valueOf(questionId);



        Toast.makeText(getActivity(),questionid,Toast.LENGTH_LONG).show();

        nxtBtn= (Button) rootView.findViewById(R.id.btnNxt);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"button clicked",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), QuestionActivity.class);
                intent.putExtra("questionNow",questionId+1);
                next=true;
                intent.putExtra("next",next);
                startActivity(intent);
            }
        });

        //Inflate the layout for this fragment

        return rootView;
    }

}