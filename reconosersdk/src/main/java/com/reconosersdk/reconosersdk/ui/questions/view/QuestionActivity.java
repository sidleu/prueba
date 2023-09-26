package com.reconosersdk.reconosersdk.ui.questions.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.reconosersdk.reconosersdk.R;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.SolicitarPreguntasDemograficas;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidarRespuestaDemografica;
import com.reconosersdk.reconosersdk.ui.LibraryReconoSer;
import com.reconosersdk.reconosersdk.ui.base.BaseActivity;
import com.reconosersdk.reconosersdk.ui.questions.adapters.QuestionPageAdapter;
import com.reconosersdk.reconosersdk.ui.questions.interfaces.QuestionContract;
import com.reconosersdk.reconosersdk.utils.IntentExtras;
import com.reconosersdk.reconosersdk.utils.custom.CustomNotSwipeableViewPager;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class QuestionActivity extends BaseActivity implements QuestionContract.QuestionsMvpView, QuestionFragments.OnSelectAnswer {

    private QuestionPageAdapter pageAdapter;
    /**
     * all fragments that are to be created
     */
    private int questionsSize = 0;
    /**
     * ViewPager counter to move to another fragment
     */
    private int nextCount = 1;

    private CustomNotSwipeableViewPager customNotViewPager;

    private ImageView imgExit;

    @Inject
    QuestionContract.QuestionsMvpPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_question);
        customNotViewPager = (CustomNotSwipeableViewPager) findViewById(R.id.custom_not_view_pager);

        LibraryReconoSer.getComponent().inject(this);
        presenter.onAttach(this);
        imgExit = findViewById(R.id.imgExit);
        presenter.init(getIntent().getExtras());

    }

    @Override
    public void initUI(SolicitarPreguntasDemograficas requestQuestions) {

        imgExit.setOnClickListener(view -> onExitQuestion());

        questionsSize = requestQuestions.getPreguntas().size();
        pageAdapter = new QuestionPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        for(int i = 0; i < questionsSize; i++) {
            pageAdapter.addFragment((Fragment) QuestionFragments.newInstance(i, requestQuestions.getPreguntas().get(i), questionsSize));
        }
        customNotViewPager.setAdapter(pageAdapter);

        customNotViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position <= questionsSize+1) {
                    nextCount = position + 1;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onItemSelect(@NonNull String idQuestion, @NonNull String idAnswer) {
        presenter.onSaveAnswer(idQuestion, idAnswer);
    }

    @Override
    public void nextQuestion() {
        if (nextCount <= questionsSize) {
            dismissProgressDialog();
            QuestionFragments currentFragment = (QuestionFragments) pageAdapter.getItem(nextCount -1);
            currentFragment.setOnItemSelectListener(this);
            if (nextCount == questionsSize) {
                presenter.onSendAnswers();
            }
            customNotViewPager.setCurrentItem(nextCount, false);
        }
    }

    @Override
    public void onNextNavigation(@NotNull ValidarRespuestaDemografica validateResponse) {
        Intent res = new Intent();
        res.putExtra(IntentExtras.RESULT_VALIDATE, validateResponse);
        setResult(RESULT_OK, res);
        dismissProgressDialog();
        finish();
    }

    @Override
    public void onExitQuestion() {
        Intent res = new Intent();
        setResult(RESULT_CANCELED, res);
        dismissProgressDialog();
        finish();
    }

    @Override
    public void onChangeIconToolbar(int image) {
        imgExit.setImageDrawable(ContextCompat.getDrawable(this, image));
    }
}
