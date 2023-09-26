package com.reconosersdk.reconosersdk.ui.questions.interfaces

import android.os.Bundle
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.SolicitarPreguntasDemograficas
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ValidarRespuestaDemografica
import com.reconosersdk.reconosersdk.ui.base.MvpPresenter
import com.reconosersdk.reconosersdk.ui.base.MvpView

interface QuestionContract {

    interface QuestionsMvpView : MvpView {
        fun initUI(requestQuestions: SolicitarPreguntasDemograficas)
        fun onNextNavigation(validateResponse: ValidarRespuestaDemografica)
        fun onExitQuestion()
        fun nextQuestion()
        fun onChangeIconToolbar(image: Int)
    }

    interface QuestionsMvpPresenter<V: QuestionsMvpView> : MvpPresenter<V> {
        fun init(extras: Bundle)
        fun onSendAnswers()
        fun onSaveAnswer(idQuestion: String, idAnswer: String)
    }
}