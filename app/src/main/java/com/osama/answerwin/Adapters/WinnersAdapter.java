package com.osama.answerwin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osama.answerwin.Models.WinnersModel;
import com.osama.answerwin.Models.myPrizeModel;
import com.osama.answerwin.R;
import com.osama.answerwin.Utils.Constants;

import java.util.List;

public class WinnersAdapter extends RecyclerView.Adapter<WVH> {

    private List<WinnersModel> list;
    private Context context;
    private View view;

    public WinnersAdapter(List<WinnersModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public WVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_winners, parent, false);

        return new WVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WVH holder, int position) {
        WinnersModel model = list.get(position);

        holder.tv_Name.setText(model.getName());
        holder.tv_Prize.setText(model.getPrize());

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

}

class WVH extends RecyclerView.ViewHolder {
     TextView tv_Name, tv_Prize;

    public WVH(@NonNull View itemView) {
        super(itemView);
        tv_Name = itemView.findViewById(R.id.tv_price_item_name);
        tv_Prize = itemView.findViewById(R.id.tv_price_item_prize);

    }
}
