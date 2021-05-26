package com.example.soccerleauge;

import android.content.Context;
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
  private ItemClickListener mOnClickListener;


    public TeamAdapter(Context context, List<Team> teams, ItemClickListener OnClickListener){
        this.context=context;
        teamList = teams;
        this.mOnClickListener = OnClickListener;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent,false);
        return  new TeamViewHolder(view, mOnClickListener);
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

    public class TeamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView rank, name;
        ItemClickListener itemClickListener;
        public TeamViewHolder(@NonNull View itemView,ItemClickListener itemClickListener) {
            super(itemView);
            rank = itemView.findViewById(R.id.rank);
            name =itemView.findViewById(R.id.name);
            this.itemClickListener= itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mOnClickListener.onItemClick(getAbsoluteAdapterPosition());
        }
    }

    interface ItemClickListener{

        void onItemClick(int position);
    }
}
