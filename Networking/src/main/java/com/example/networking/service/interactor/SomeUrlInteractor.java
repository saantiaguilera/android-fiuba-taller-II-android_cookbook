package com.example.networking.service.interactor;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.networking.model.Project;
import com.example.networking.service.RestClient;
import com.example.networking.service.repository.SomeUrlRepository;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

/**
 * The main point of interactors is to interact with the repositories and to provide an exposed
 * and clean api to our application classes. This way they just ask for a specific resource or
 * start listening to them.
 */
public class SomeUrlInteractor {

    /**
     * This is just a singleton instance of ourselves. Since we want to communicate with just one
     * instance for each interactor
     */
    private static final @NonNull SomeUrlInteractor instance = new SomeUrlInteractor();

    /**
     * Create a behavior subject that will notify all listeners about new data.
     * Note that not necessarily all api endpoints should have a behavior subject.
     * Only the ones that classes will listen to should have.
     */
    @NonNull
    BehaviorSubject<Project> currentProjectSubject;
    @NonNull
    BehaviorSubject<Project> createdProjectSubject;

    private SomeUrlInteractor() {
        currentProjectSubject = BehaviorSubject.create();
        createdProjectSubject = BehaviorSubject.create();
    }

    public static @NonNull SomeUrlInteractor instance() {
        return instance;
    }

    public @NonNull Observable<Project> observeCurrentProject() {
        return currentProjectSubject;
    }

    public @NonNull Observable<Project> observeCreatedProject() {
        return createdProjectSubject;
    }

    public @NonNull Observable<Project> currentProject(@NonNull Context context) {
        return RestClient.with(context)
                .noAuth() // We dont need auth for this request.. Because its an example
                .create(SomeUrlRepository.class)
                .currentProject()
                .doOnNext(new Consumer<Project>() {
                    @Override
                    public void accept(Project project) throws Exception {
                        currentProjectSubject.onNext(project);
                    }
                });
    }

    public @NonNull Observable<Project> create(@NonNull Context context, @NonNull Project project) {
        /* If you need to send a multipart lets say, with images/videos/media. This is a proper example

        final String MULTIPART_FORM_DATA = "multipart/form-data";

        Map<String, RequestBody> params = new HashMap<>();
        params.put("userName", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), user.name()));
        params.put("password", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), password));
        params.put("firstName", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), user.firstName()));
        params.put("lastName", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), user.lastName()));
        params.put("country", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), user.country()));
        params.put("email", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), user.email()));

        params.put("birthdate", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), user.birthday()));

        for (int pos = 0; pos < user.pictures().size(); pos++) {
            if (!TextUtils.isEmpty(user.pictures().get(pos))) {
                RequestBody requestBody = RequestBody.create(
                        MediaType.parse(MULTIPART_FORM_DATA), new File(user.pictures().get(pos)));
                String key = String.format("%1$s\"; filename=\"%1$s", "avatar");
                params.put(key, requestBody);
            }
        }
        */

        return RestClient.with(context)
                .create(SomeUrlRepository.class)
                .createProject(project)
                .doOnNext(new Consumer<Project>() {
                    @Override
                    public void accept(Project project) throws Exception {
                        createdProjectSubject.onNext(project);
                    }
                });
    }

}
