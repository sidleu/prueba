package com.reconosersdk.reconosersdk.di.component;

import android.app.Application;

import com.reconosersdk.reconosersdk.di.module.AppModule;
import com.reconosersdk.reconosersdk.ui.bioFacial.views.LivePreviewActivity;
import com.reconosersdk.reconosersdk.ui.document.views.DocumentAdActivity;
import com.reconosersdk.reconosersdk.ui.document.views.ExtraDocumentActivity;
import com.reconosersdk.reconosersdk.ui.document.views.GeneralDocumentActivity;
import com.reconosersdk.reconosersdk.ui.document.views.RequestDocumentActivity;
import com.reconosersdk.reconosersdk.ui.questions.view.QuestionActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    Application app();

    void inject(DocumentAdActivity activity);

    void inject(RequestDocumentActivity activity);

    void inject(LivePreviewActivity activity);

    void inject(QuestionActivity activity);

    void inject(GeneralDocumentActivity activity);

    void inject(ExtraDocumentActivity activity);

}
