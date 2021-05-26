package com.example.soccerleauge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchAdapter extends PagerAdapter {
    private List<List<Match>> mMatches;
    private Context mContext;

    public MatchAdapter(Context context, List<List<Match>> matches) {
        mContext = context;
        mMatches = matches;
    }

    @Override
    public int getCount() {
        return mMatches.size();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_fixture, collection,false);;

        RecyclerView rv_fixture = itemView.findViewById(R.id.rv_fixture_item);
        FixtureAdapter weeklyMatches = new FixtureAdapter();
        weeklyMatches.setMatches(mMatches.get(position));
        rv_fixture.setAdapter(weeklyMatches);
        rv_fixture.setLayoutManager(new LinearLayoutManager(mContext));
        collection.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
