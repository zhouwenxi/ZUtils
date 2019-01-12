package com.qishui.commontoolslibrary.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.core.LogUtils;

public class HomeFragment4 extends Fragment {

    private String mFrom;
    public static HomeFragment4 newInstance(String from){
        LogUtils.e("HomeFragment4 newInstance");
        HomeFragment4 fragment = new HomeFragment4();
        Bundle bundle = new Bundle();
        bundle.putString("from",from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mFrom = getArguments().getString("from");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_layout,null);
        TextView content =view.findViewById(R.id.fragment_content);
        content.setText("Homefragment4");
        return view;
    }
}
