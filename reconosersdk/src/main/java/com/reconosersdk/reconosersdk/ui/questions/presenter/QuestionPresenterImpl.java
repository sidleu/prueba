package com.reconosersdk.reconosersdk.ui.questions.presenter;

import android.os.Bundle;

import com.reconosersdk.reconosersdk.R;
import com.reconosersdk.reconosersdk.http.OlimpiaInterface;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.RespuestasIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.in.ValidarRespuestaIn;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.SolicitarPreguntasDemograficas;
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidarRespuestaDemografica;
import com.reconosersdk.reconosersdk.ui.base.BasePresenter;
import com.reconosersdk.reconosersdk.ui.questions.interfaces.QuestionContract;
import com.reconosersdk.reconosersdk.ui.servicesOlimpia.ServicesOlimpia;
import com.reconosersdk.reconosersdk.utils.Constants;
import com.reconosersdk.reconosersdk.utils.IntentExtras;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestionPresenterImpl<V extends QuestionContract.QuestionsMvpView> extends BasePresenter<V> implements QuestionContract.QuestionsMvpPresenter<V> {

    private List<RespuestasIn> answers;
    private ValidarRespuestaIn repuesta;

    @Override
    public void init(@NotNull Bundle extras) {
        if (extras.isEmpty() || !extras.containsKey(IntentExtras.GUID) || Objects.requireNonNull(extras.getString(IntentExtras.GUID)).isEmpty()) {
            getMvpView().onFinishError(Constants.ERROR_R107);
            return;
        }
        if (extras.containsKey("image")) {
            getMvpView().onChangeIconToolbar(extras.getInt("image"));
        }

            getMvpView().showProgessDialog(R.string.send_data);
        final String param = extras.getString(IntentExtras.GUID);
        ServicesOlimpia.getInstance().solicitarPreguntasDemograficas(param, new OlimpiaInterface.CallbackRequestQuestions() {
            @Override
            public void onSuccess(SolicitarPreguntasDemograficas requestQuestions) {
                answers = new ArrayList<>();
                repuesta = new ValidarRespuestaIn();
                repuesta.setGuidCiudadano(param);
                repuesta.setIdCuestionario(requestQuestions.getIdCuestionario());
                repuesta.setRegistroCuestionario(requestQuestions.getRegistroCuestionario());
                getMvpView().dismissProgressDialog();
                getMvpView().initUI(requestQuestions);
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                getMvpView().dismissProgressDialog();
                getMvpView().onFinishError(transactionResponse);
            }
        });
    }

    @Override
    public void onSendAnswers() {
        getMvpView().showProgessDialog(R.string.send_data);
        repuesta.setRespuestas(answers);

        ServicesOlimpia.getInstance().validarRespuestaDemograficas(repuesta, new OlimpiaInterface.CallbackValidateResponse() {
            @Override
            public void onSuccess(ValidarRespuestaDemografica validateResponse) {
                getMvpView().onNextNavigation(validateResponse);
            }

            @Override
            public void onError(RespuestaTransaccion transactionResponse) {
                getMvpView().dismissProgressDialog();
            }
        });
    }

    @Override
    public void onSaveAnswer(@NotNull String idQuestion, @NotNull String idAnswer) {
        getMvpView().showProgessDialog(R.string.save_answer);
        answers.add(new RespuestasIn(idQuestion, idAnswer));
        getMvpView().nextQuestion();
    }
}
