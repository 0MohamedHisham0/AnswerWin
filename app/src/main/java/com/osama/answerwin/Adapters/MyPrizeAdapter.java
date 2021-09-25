package com.osama.answerwin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osama.answerwin.Models.myPrizeModel;
import com.osama.answerwin.R;
import com.osama.answerwin.Utils.Constants;

import java.util.List;

public class MyPrizeAdapter extends RecyclerView.Adapter<VH> {

    private List<myPrizeModel> list;
    private Context context;
    private View view;

    public MyPrizeAdapter(List<myPrizeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_my_prize, parent, false);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        myPrizeModel model = list.get(position);

        holder.tv_Prize.setText(model.getPrize());
        holder.tv_Date.setText(Constants.convertToDate(model.getDate()));

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

}

class VH extends RecyclerView.ViewHolder {
     TextView tv_Prize, tv_Date;

    public VH(@NonNull View itemView) {
        super(itemView);
        tv_Prize = itemView.findViewById(R.id.tv_price_item_myPrize);

        if (tv_Date == null)
            tv_Date = itemView.findViewById(R.id.tv_price_item_Date);

    }
}
