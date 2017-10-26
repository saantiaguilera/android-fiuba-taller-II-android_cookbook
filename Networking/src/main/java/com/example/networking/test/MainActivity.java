package com.example.networking.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.networking.R;
import com.example.networking.model.Dependency;
import com.example.networking.model.Project;
import com.example.networking.service.interactor.FailInteractor;
import com.example.networking.service.interactor.SomeUrlInteractor;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import java.util.ArrayList;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by saguilera on 10/26/17.
 */
public class MainActivity extends AppCompatActivity {

    @NonNull
    private TextView responseTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        responseTextView = findViewById(R.id.activity_main_responseTextView);

        setupFail();
        setupGetProject();
        setupPostProject();

        makeAnonymousListenerOfGet();
    }

    private void setupPostProject() {
        final Project project = new Project();
        project.setName("Test Name");
        project.setDescription("This is written inside MainActivity.java");
        project.setImageUrl("http://no-image.png");
        project.setLicense("MIT");
        project.setDependencies(new ArrayList<Dependency>());

        final View postProjectView = findViewById(R.id.activity_main_postProjectView);
        RxView.clicks(postProjectView)
                // Make the subscription on the main thread
                .subscribeOn(AndroidSchedulers.mainThread())
                // Compose with a lfiecycle so that we dont leak memmory if this request takes longer than the view life
                .compose(RxLifecycleAndroid.<Object>bindView(postProjectView))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object object) throws Exception {
                        // Currently we are in a separate thread. So conserve this one
                        SomeUrlInteractor.instance().create(MainActivity.this, project)
                                // We will run in a separate thread
                                .subscribeOn(Schedulers.computation())
                                // But we want to execute our data in the main thread so:
                                // We will put the text (which requires us to be on the main thread)
                                // -> switch to the main
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Project>() {
                                    @Override
                                    public void accept(Project project) throws Exception {
                                        responseTextView.setText("POST::\n" + project.toString());
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                });
    }

    private void setupGetProject() {
        final View getProjectView = findViewById(R.id.activity_main_getProjectView);
        RxView.clicks(getProjectView)
                // Make the subscription on the main thread
                .subscribeOn(AndroidSchedulers.mainThread())
                // Compose with a lfiecycle so that we dont leak memmory if this request takes longer than the view life
                .compose(RxLifecycleAndroid.<Object>bindView(getProjectView))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object object) throws Exception {
                        // Currently we are in a separate thread. So conserve this one
                        SomeUrlInteractor.instance().currentProject(MainActivity.this)
                                // We will run in a separate thread
                                .subscribeOn(Schedulers.computation())
                                // But we want to execute our data in the main thread so:
                                // We will put the text (which requires us to be on the main thread)
                                // -> switch to the main
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Project>() {
                                    @Override
                                    public void accept(Project project) throws Exception {
                                        responseTextView.setText("GET::\n" + project.toString());
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                });
    }

    private void setupFail() {
        final View failView = findViewById(R.id.activity_main_failView);
        RxView.clicks(failView)
                // Make the subscription on the main thread
                .subscribeOn(AndroidSchedulers.mainThread())
                // Compose with a lfiecycle so that we dont leak memmory if this request takes longer than the view life
                .compose(RxLifecycleAndroid.bindView(failView))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        // Currently we are in a separate thread. So conserve this one
                        FailInteractor.instance().fail(MainActivity.this)
                                // We will run in a separate thread
                                .subscribeOn(Schedulers.computation())
                                // But we want to execute our data in the main thread so:
                                // We will throw a toast (which requires us to be on the main thread)
                                // -> switch to the main thread
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Void>() {
                                    @Override
                                    public void accept(Void aVoid) throws Exception {
                                        throw new RuntimeException("This request should fail");
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                });
    }

    /**
     * This could be anywhere in the app, and it would be able to listen to all performed
     * gets to the project interactor
     */
    private void makeAnonymousListenerOfGet() {
        SomeUrlInteractor.instance()
                .observeCurrentProject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<Project>() {
                    @Override
                    public void accept(Project project) throws Exception {
                        Toast.makeText(MainActivity.this, "GET to project just listened from anonymous place :)", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
