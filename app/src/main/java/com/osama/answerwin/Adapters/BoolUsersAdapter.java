package com.osama.answerwin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osama.answerwin.Models.BooledModel;
import com.osama.answerwin.R;
import com.osama.answerwin.Utils.Constants;

import java.util.List;

public class BoolUsersAdapter extends RecyclerView.Adapter<BUVH> {

    private List<BooledModel> list;
    private Context context;
    private View view;

    public BoolUsersAdapter(List<BooledModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public BUVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_bool_users, parent, false);

        return new BUVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BUVH holder, int position) {
        BooledModel model = list.get(position);

        holder.tv_Name.setText(model.getUserName());
        holder.tv_Date.setText(Constants.convertToDate(model.getDate() + ""));

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

}

class BUVH extends RecyclerView.ViewHolder {
    TextView tv_Name, tv_Date;

    public BUVH(@NonNull View itemView) {
        super(itemView);
        tv_Name = itemView.findViewById(R.id.tv_name_item_boolUsers);
        tv_Date = itemView.findViewById(R.id.tv_date_item_boolUsers);

    }
}
