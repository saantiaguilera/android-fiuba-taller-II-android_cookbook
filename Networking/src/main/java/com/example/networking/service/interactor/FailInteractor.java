package com.example.networking.service.interactor;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.networking.service.RestClient;
import com.example.networking.service.repository.FailRepository;

import io.reactivex.Observable;

/**
 * The main point of interactors is to interact with the repositories and to provide an exposed
 * and clean api to our application classes. This way they just ask for a specific resource or
 * start listening to them.
 */
public class FailInteractor {

    /**
     * This is just a singleton instance of ourselves. Since we want to communicate with just one
     * instance for each interactor
     */
    private static final @NonNull FailInteractor instance = new FailInteractor();

    public static @NonNull FailInteractor instance() {
        return instance;
    }

    public @NonNull Observable<Void> fail(@NonNull Context context) {
        return RestClient.with(context)
                .create(FailRepository.class)
                .fail();
    }

}
