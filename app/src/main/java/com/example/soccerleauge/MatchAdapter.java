package com.example.soccerleauge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchHolder> {
    List<Match> matches;
    @NonNull
    @Override
    public MatchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fixture, parent,false);
        return new MatchHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchHolder holder, int position) {

        Match currentMatch =matches.get(position);
        holder.home_name.setText(currentMatch.getHome_name());
        holder.away_name.setText(currentMatch.getAway_name());
        holder.location.setText(currentMatch.getLocation());
        holder.location.setText(currentMatch.getScore());
        holder.location.setText(String.valueOf(currentMatch.getWeek()));
    }

    @Override
    public int getItemCount() {
        return  matches.size();
    }
    public void setMatches(List<Match> matches) {
        this.matches = matches;
        notifyDataSetChanged();
    }

    class MatchHolder extends RecyclerView.ViewHolder{
        private TextView home_name;
        private TextView away_name;
        private TextView location;
        private TextView score;
        private TextView week;

        public MatchHolder(@NonNull View itemView) {
            super(itemView);
            home_name= itemView.findViewById(R.id.text_view_home);
            away_name =itemView.findViewById(R.id.text_view_away);
            location=itemView.findViewById(R.id.text_view_location);
            score=itemView.findViewById(R.id.text_view_score);
            week=itemView.findViewById(R.id.text_view_week);
        }
    }
}
