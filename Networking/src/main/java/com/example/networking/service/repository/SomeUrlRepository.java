package com.example.networking.service.repository;

import com.example.networking.model.Project;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Simple interface to communicate with the 'SomeUrl' repository. A repository should be ideally
 * a container of requests to a specific endpoint (in this case 'some/url/')
 *
 * In a post, you can also provide a map with @FieldMap or @Field for formurlencoded,
 * or a whole body with @Body. It also supports @Multipart for resources and well..
 * Pretty much everything
 *
 * Note that repositories are made with annotations, this makes it human readable, less
 * boilerplated and very type safe.
 */
public interface SomeUrlRepository {

    @GET("some/url")
    Observable<Project> currentProject();

    @POST("some/url")
    Observable<Project> createProject(@Body Project project);

}
