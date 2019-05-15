package com.example.administrator.wanandroid.home.homepage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.article.view.ArticleActivity;
import com.example.administrator.wanandroid.home.homepage.model.ArticleInfo;
import com.example.administrator.wanandroid.home.homepage.model.BannerInfo;

public class BannerFragment extends Fragment {
    ImageView image;
    View view;
    String articleUrl;
    String imageUrl;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner,container,false);
        image = view.findViewById(R.id.banner_image);
        imageUrl = getArguments().getString("imageUrl");
        articleUrl = getArguments().getString("articleUrl");
        Glide.with(view.getContext()).load(imageUrl).into(image);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ArticleActivity.class);
                intent.putExtra("url",articleUrl);
            }
        });
    }
}
