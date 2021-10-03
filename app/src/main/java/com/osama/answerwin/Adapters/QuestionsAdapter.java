package com.osama.answerwin.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osama.answerwin.Models.Questions_Model;
import com.osama.answerwin.R;
import com.osama.answerwin.Utils.Constants;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<quVH> {

    private final List<Questions_Model> list;
    private final List<String> listUid;
    private final Context context;
    private View view;


    public QuestionsAdapter(List<Questions_Model> list, List<String> listUid, Context context) {
        this.list = list;
        this.listUid = listUid;
        this.context = context;
    }

    @NonNull
    @Override
    public quVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_qs_dash, parent, false);
        return new quVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull quVH holder, int position) {
        Questions_Model model = list.get(position);

        holder.tv_q.setText(model.getMain_question());
        holder.tv_t.setText(model.getT_answer_1());
        holder.tv_f1.setText(model.getF_answer_2());
        holder.tv_f2.setText(model.getF_answer_3());
        holder.tv_f3.setText(model.getF_answer_4());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                openDeleteDialog(context, position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void deleteQu(int position) {
        Constants.GetFireStoneDb().collection("Questions").document(listUid.get(position))
                .delete();
        removeAt(position);
        Toast.makeText(context, "تم مسح السؤال", Toast.LENGTH_SHORT).show();
    }

    public void removeAt(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

    public void openDeleteDialog(Context context, int position) {
        Dialog dialog = new Dialog(context); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_delete_qu);

        Button buttonY = dialog.findViewById(R.id.bu_dialogYes_deleteQ);
        Button buttonN = dialog.findViewById(R.id.bu_dialogNo_deleteQ);

        buttonY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteQu(position);
                dialog.dismiss();
            }
        });

        buttonN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setTitle("Delete");
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }


}

class quVH extends RecyclerView.ViewHolder {

    TextView tv_q, tv_t, tv_f1, tv_f2, tv_f3;

    public quVH(@NonNull View itemView) {
        super(itemView);
        tv_q = itemView.findViewById(R.id.tvQDash);
        tv_t = itemView.findViewById(R.id.tvAns1Dash);
        tv_f1 = itemView.findViewById(R.id.tvAns2Dash);
        tv_f2 = itemView.findViewById(R.id.tvAns3Dash);
        tv_f3 = itemView.findViewById(R.id.tvAns4Dash);

    }


}
