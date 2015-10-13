package com.nerdcastle.mdnazmulhasan.oop;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Result_Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        String myValue = this.getArguments().getString("data");
        Toast.makeText(getActivity(),myValue,Toast.LENGTH_LONG).show();

        //Inflate the layout for this fragment

        return inflater.inflate(
                R.layout.fragment_result_, container, false);
    }
}