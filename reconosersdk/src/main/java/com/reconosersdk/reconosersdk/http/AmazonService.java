package com.reconosersdk.reconosersdk.http;

import javax.inject.Inject;

public class AmazonService implements AmazonInterface {

    private ServiceApi serviceApi;

    @Inject
    public AmazonService(ServiceApi serviceApi) {
        this.serviceApi = serviceApi;
    }
}
