package com.example.administrator.wanandroid.home.navigation.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.wanandroid.R;

public class NavigationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation,container,false);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.e("FragmentNavigation","onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("FragmentNavigation","onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("FragmentNavigation","onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("FragmentNavigation","onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("FragmentNavigation","onDestroy()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("FragmentNavigation","onCreate()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("FragmentNavigation","onDetach()");
    }
}
