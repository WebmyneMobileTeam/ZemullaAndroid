package com.zemulla.android.app.transaction;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zemulla.android.app.R;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView mainRecyler;
    private ArrayList<CyberSourceBean> cyberSourceBeanList = new ArrayList<>();
    private CyberSourceAdapter cyberSourceAdapter;
    public HistoryFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_history, container, false);
        init(fragmentView);

        prepareData();
        cyberSourceAdapter = new CyberSourceAdapter(cyberSourceBeanList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mainRecyler.setLayoutManager(mLayoutManager);
        mainRecyler.setItemAnimator(new DefaultItemAnimator());
        mainRecyler.setAdapter(cyberSourceAdapter);

        return fragmentView;
    }

    private void prepareData() {
        CyberSourceBean bean = new CyberSourceBean("101","16/07/2016","Krishna","Patel","ZMB 50.0","ZMB 5.0","ZMB 55.0","Success");
        cyberSourceBeanList.add(bean);

        bean = new CyberSourceBean("102","10/07/2016 23:15:45","John","Doe","ZMB 100.0","ZMB 10.0","ZMB 110.0","Success");
        cyberSourceBeanList.add(bean);

        bean = new CyberSourceBean("103","12/07/2016 21:18:26","Mark","Smith","ZMB 20.0","ZMB 3.0","ZMB 23.0","Fail");
        cyberSourceBeanList.add(bean);

        bean = new CyberSourceBean("104","15/05/2016 07:41:02","Daniel","Brick","ZMB 650.0","ZMB 70.0","ZMB 720.0","Sucess");
        cyberSourceBeanList.add(bean);

        bean = new CyberSourceBean("105","01/06/2016 15:50:54","Zuze","Walter","ZMB 250.0","ZMB 45.0","ZMB 295.0","Fail");
        cyberSourceBeanList.add(bean);
    }

    private void init(View v) {
        mainRecyler = (RecyclerView)v.findViewById(R.id.mainRecyler);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
