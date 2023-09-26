package com.reconosersdk.reconosersdk.http.di;

import com.reconosersdk.reconosersdk.http.AmazonInterface;
import com.reconosersdk.reconosersdk.http.AmazonService;
import com.reconosersdk.reconosersdk.http.ServiceApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AmazonServiceModule {

    @Singleton
    @Provides
    public AmazonInterface provideAmazonService(ServiceApi serviceApi) {
        return new AmazonService(serviceApi);
    }
}
