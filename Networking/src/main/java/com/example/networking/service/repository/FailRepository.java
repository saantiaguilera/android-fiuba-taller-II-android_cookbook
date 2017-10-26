package com.example.networking.service.repository;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * This repository just provides an endpoint that will fail with a custom message..
 *
 * Note that repositories are made with annotations, this makes it human readable, less
 * boilerplated and very type safe.
 */
public interface FailRepository {

    @GET("fail/url")
    Observable<Void> fail();

}
