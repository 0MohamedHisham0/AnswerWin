package com.osama.answerwin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osama.answerwin.Activities.Add_Questions_Screen;
import com.osama.answerwin.Models.UserModel;
import com.osama.answerwin.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import carbon.widget.Button;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class WinnersDashAdapter extends RecyclerView.Adapter<WDVH> {


    public interface OnButtonClickListener{
        void onButtonClicked(String prize,int position);
    }

    private List<UserModel> list;
    private Context context;
    private View viewD;
    public final OnButtonClickListener listener;


    public WinnersDashAdapter(List<UserModel> list, Context context, OnButtonClickListener onButtonClickListener) {
        this.list = list;
        this.context = context;
        this.listener = onButtonClickListener;
    }

    @NonNull
    @Override
    public WDVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewD = LayoutInflater.from(context).inflate(R.layout.item_win_dash, parent, false);

        return new WDVH(viewD);
    }

    @Override
    public void onBindViewHolder(@NonNull WDVH holder, int position) {
        UserModel model = list.get(position);

        holder.tv_Name.setText(model.getName());
        holder.tv_Phone.setText(model.getPhone());
        holder.tv_Points.setText(model.getPoints()+"");
        holder.tv_Jewels.setText(model.getJewels()+"");
        holder.bListener = this.listener;
//        holder.bu_Done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.et_Prize.getText() == null)
//                Toast.makeText(context, "من فضلك, أدخل قيمة الجائزة.", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

}

class WDVH extends RecyclerView.ViewHolder {
     TextView tv_Name, tv_Phone, tv_Points, tv_Jewels;
     Button bu_Done;
     EditText et_Prize;
     WinnersDashAdapter.OnButtonClickListener bListener;
    public WDVH(@NonNull View itemView) {
        super(itemView);
        tv_Name = itemView.findViewById(R.id.tv_price_item_name_dash);
        tv_Phone = itemView.findViewById(R.id.tv_price_item_phone_dash);
        tv_Points = itemView.findViewById(R.id.tvPointsW);
        tv_Jewels = itemView.findViewById(R.id.tvJewelsW);
        bu_Done = itemView.findViewById(R.id.buDone);
        et_Prize = itemView.findViewById(R.id.etPrize);
        bu_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bListener.onButtonClicked(et_Prize.getText().toString(),getAdapterPosition());
            }
        });
    }


}
