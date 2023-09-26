package com.reconosersdk.reconosersdk.ui.questions.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.reconosersdk.reconosersdk.R;
import com.reconosersdk.reconosersdk.databinding.ViewQuestionBinding;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.OpcionesRespuesta;
import com.reconosersdk.reconosersdk.ui.questions.interfaces.OnNumberItemClickListener;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.BaseViewHolder> {
    private static final String ID = "id";
    private static final String TEXT = "texto";

    private OnNumberItemClickListener listener;
    private List<OpcionesRespuesta> preguntas;

    public QuestionsAdapter(List<OpcionesRespuesta> preguntas) {
        this.preguntas = preguntas;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_question, parent, false);
        return new BaseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.getBinding().txtAnswer.setText(preguntas.get(position).getTextoRespuesta());
        holder.getBinding().consAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                               listener.onItemClick(preguntas.get(position).getIdRespuesta());
            }
        });
    }

    @Override
    public int getItemCount() {
        return preguntas.size();
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        private ViewQuestionBinding binding;

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ViewQuestionBinding getBinding() { return binding;}

    }

    public void setOnItemCLickListener(OnNumberItemClickListener listener) {
        this.listener = listener;
    }
}
