package com.reconosersdk.reconosersdk.di.module;

import android.app.Application;

import com.reconosersdk.reconosersdk.ui.bioFacial.interfaces.LivePreviewContract;
import com.reconosersdk.reconosersdk.ui.bioFacial.presenters.LivePreviewPresenterImpl;
import com.reconosersdk.reconosersdk.ui.document.interfaces.DocumentAdContract;
import com.reconosersdk.reconosersdk.ui.document.interfaces.ExtraDocumentContract;
import com.reconosersdk.reconosersdk.ui.document.interfaces.GeneralDocumentContract;
import com.reconosersdk.reconosersdk.ui.document.interfaces.RequestDocumentContract;
import com.reconosersdk.reconosersdk.ui.document.presenters.DocumentAdPresenterImpl;
import com.reconosersdk.reconosersdk.ui.document.presenters.ExtraDocumentPresenterImpl;
import com.reconosersdk.reconosersdk.ui.document.presenters.GeneralDocumentPresenterImpl;
import com.reconosersdk.reconosersdk.ui.document.presenters.RequestDocumentPresenterImpl;
import com.reconosersdk.reconosersdk.ui.questions.interfaces.QuestionContract;
import com.reconosersdk.reconosersdk.ui.questions.presenter.QuestionPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return app;
    }


    @Provides
    public DocumentAdContract.DocumentAdMvpPresenter provideDocumentAdPresenter() {
        return new DocumentAdPresenterImpl();
    }

    @Provides
    public RequestDocumentContract.RequestDocumentMvpPresenter provideRequestDocumentPresenter() {
        return new RequestDocumentPresenterImpl();
    }

    @Provides
    public LivePreviewContract.LivePreviewPresenter provideLivePreviewPresenter() {
        return new LivePreviewPresenterImpl();
    }

    @Provides
    public QuestionContract.QuestionsMvpPresenter provideQuestionPresenter() {
        return new QuestionPresenterImpl();
    }

    @Provides
    public GeneralDocumentContract.GeneralDocumentPresenter provideGeneralDocumentPresenter() {
        return new GeneralDocumentPresenterImpl();
    }

    @Provides
    public ExtraDocumentContract.ExtraDocumentPresenter provideExtraDocumentPresenter() {
        return new ExtraDocumentPresenterImpl();
    }

}