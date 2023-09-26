package com.reconosersdk.reconosersdk.ui.questions.view;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.reconosersdk.reconosersdk.R;
import com.reconosersdk.reconosersdk.databinding.FragmentQuestionRecyclerBinding;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.OpcionesRespuesta;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.Pregunta;
import com.reconosersdk.reconosersdk.ui.questions.adapters.QuestionsAdapter;
import com.reconosersdk.reconosersdk.ui.questions.interfaces.OnNumberItemClickListener;
import com.reconosersdk.reconosersdk.utils.custom.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestionFragments extends Fragment implements OnNumberItemClickListener {

    private FragmentQuestionRecyclerBinding binding;
    private static final String INDEX = "index";
    private static final String SIZE = "sizequestions";
    private Pregunta data;
    private QuestionsAdapter questionsAdapter;
    private List<OpcionesRespuesta> questions = new ArrayList<>();
    private int sizeQuestion;
    private int position;
    private String idResponse = null;
    private OnSelectAnswer listener;


    public static Object newInstance(int position, Pregunta data, int sizeQuestion) {
        QuestionFragments fragment = new QuestionFragments();

        Bundle bundle = new Bundle();
        bundle.putParcelable("data", data);
        bundle.putInt(INDEX, position);
        bundle.putInt(SIZE, sizeQuestion);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question_recycler, container, false);
        sizeScreen();
        data = Objects.requireNonNull(getArguments()).getParcelable("data");
        sizeQuestion = getArguments().getInt(SIZE);
        position = getArguments().getInt(INDEX);
        binding.txtQuestionNum.setText("PREGUNTA "+ (position+1) + " DE "+ sizeQuestion);
        binding.txtQuestion.setText(data.getTextoPregunta());

        for (int i = 0; i < data.getOpcionesRespuestas().size(); i++) {
            questions.add(data.getOpcionesRespuestas().get(i));
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerQuestion.setLayoutManager(layoutManager);
        binding.recyclerQuestion.addItemDecoration(new SpacesItemDecoration((SpacesItemDecoration.DEFAULT_SPACE), SpacesItemDecoration.TYPE_30));
        binding.recyclerQuestion.setHasFixedSize(true);
        questionsAdapter = new QuestionsAdapter(questions);
        questionsAdapter.setOnItemCLickListener(this);
        binding.recyclerQuestion.setAdapter(questionsAdapter);
        return binding.getRoot();
    }

    private void sizeScreen() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float ratio = ((float)metrics.heightPixels / (float)metrics.widthPixels);
        int background = R.drawable.bg_white;
        if (ratio > 1.7f){   //TODO 1.7 normal 16:9 si es mayor 18:9
            background = R.drawable.bg_white_long;
        }
        binding.containerQuestion.setBackgroundResource(background);
    }

    public String getIdResponse() {
        return idResponse;
    }

    public String getIdQuestion() {
        return data.getIdPregunta();
    }

    @Override
    public void onItemClick(@NonNull final String idAnswer) {
        idResponse = idAnswer;
        listener.onItemSelect(data.getIdPregunta(), idAnswer);
    }

    public interface OnSelectAnswer {
        void onItemSelect(@NonNull String idQuestion, @NonNull String idAnswer);
    }

    public void setOnItemSelectListener(OnSelectAnswer listener) {
        this.listener = listener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (OnSelectAnswer) context;
    }
}
