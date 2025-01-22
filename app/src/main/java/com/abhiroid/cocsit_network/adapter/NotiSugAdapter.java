package com.abhiroid.cocsit_network.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhiroid.cocsit_network.R;
import com.abhiroid.cocsit_network.model.NotiSugMdoel;

import java.util.ArrayList;

public class NotiSugAdapter extends RecyclerView.Adapter<NotiSugAdapter.ViewHolder> {

    private ArrayList<NotiSugMdoel> notiSugMdoelArrayList ;
    private Context context;

    public NotiSugAdapter(Context context, ArrayList<NotiSugMdoel> notiSugMdoelArrayList){
        this.context = context;
        this.notiSugMdoelArrayList = notiSugMdoelArrayList;
    }

    @NonNull
    @Override
    public NotiSugAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.search_card , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotiSugAdapter.ViewHolder holder, int position) {
        holder.profileImage.setImageResource(notiSugMdoelArrayList.get(position).getProfileImage());
        holder.tvName.setText(notiSugMdoelArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return notiSugMdoelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView profileImage;
        private TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImgNoti);
            tvName = itemView.findViewById(R.id.tvNameNoti);
        }
    }
}
