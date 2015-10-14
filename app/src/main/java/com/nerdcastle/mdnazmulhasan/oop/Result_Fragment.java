package com.nerdcastle.mdnazmulhasan.oop;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Result_Fragment extends Fragment {
    Button nxtBtn;
    String token;
    String userId;
    String questionNumber;
    Boolean next=false;
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       // String myValue = this.getArguments().getString("data");
        final int questionId=this.getArguments().getInt("questionId");
        String questionid=String.valueOf(questionId);

        View rootView = inflater.inflate(
                R.layout.fragment_result_, container, false);
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