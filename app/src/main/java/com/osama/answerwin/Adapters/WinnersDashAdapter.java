package com.osama.answerwin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osama.answerwin.Models.WinnersDashModel;
import com.osama.answerwin.Models.WinnersModel;
import com.osama.answerwin.R;

import java.util.List;

public class WinnersDashAdapter extends RecyclerView.Adapter<WDVH> {

    private List<WinnersDashModel> list;
    private Context context;
    private View viewD;

    public WinnersDashAdapter(List<WinnersDashModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public WDVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewD = LayoutInflater.from(context).inflate(R.layout.item_win_dash, parent, false);

        return new WDVH(viewD);
    }

    @Override
    public void onBindViewHolder(@NonNull WDVH holder, int position) {
        WinnersDashModel model = list.get(position);

        holder.tv_Name.setText(model.getName());
        holder.tv_Phone.setText(model.getPhone());
        holder.tv_Points.setText(model.getPoints());
        holder.tv_Jewels.setText(model.getJewels());

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

}

class WDVH extends RecyclerView.ViewHolder {
     TextView tv_Name, tv_Phone, tv_Points, tv_Jewels;

    public WDVH(@NonNull View itemView) {
        super(itemView);
        tv_Name = itemView.findViewById(R.id.tv_price_item_name_dash);
        tv_Phone = itemView.findViewById(R.id.tv_price_item_phone_dash);
        tv_Points = itemView.findViewById(R.id.tvPointsW);
        tv_Jewels = itemView.findViewById(R.id.tvJewelsW);

    }
}
