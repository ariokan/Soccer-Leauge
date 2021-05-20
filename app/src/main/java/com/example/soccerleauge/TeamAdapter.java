package com.example.soccerleauge;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    List<Team> teamList;
    Context context;

    public TeamAdapter(Context context, List<Team> teams){
        this.context=context;
        teamList = teams;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_team, parent,false);
        return  new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {

        Team team = teamList.get(position);
        holder.rank.setText(Integer.toString(team.getRank()));
        holder.name.setText(team.getName());
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    public class TeamViewHolder extends RecyclerView.ViewHolder {
        TextView rank, name;
        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.rank);
            name =itemView.findViewById(R.id.name);
        }
    }
}
