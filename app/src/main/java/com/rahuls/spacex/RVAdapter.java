package com.rahuls.spacex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    Context context;
    List<Crew> arrayList;

    public RVAdapter(Context context, List<Crew> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Crew repo = arrayList.get(position);

        holder.name.setText(repo.getName());
        holder.status.setText("Status: " + repo.getStatus());
        holder.agency.setText("Agency: " + repo.getAgency());
        holder.wikipedia.setText("Know more: " + repo.getWikipedia());

        Glide.with(context)
                .load(repo.getImage())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name, agency, wikipedia, status;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            status = itemView.findViewById(R.id.status);
            agency = itemView.findViewById(R.id.agency);
            wikipedia = itemView.findViewById(R.id.wikipedia);
            image = itemView.findViewById(R.id.image);

        }
    }
}
