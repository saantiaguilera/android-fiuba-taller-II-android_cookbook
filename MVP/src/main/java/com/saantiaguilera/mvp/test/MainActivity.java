package com.saantiaguilera.mvp.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.saantiaguilera.mvp.R;
import com.saantiaguilera.mvp.contract.product.ProductDescriptionContract;
import com.saantiaguilera.mvp.contract.product.TextBolderContract;
import com.saantiaguilera.mvp.model.Product;
import com.saantiaguilera.mvp.presenter.product.ProductDescriptionPresenter;
import com.saantiaguilera.mvp.presenter.product.TextBolderPresenter;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private @NonNull ProductDescriptionContract.Presenter descriptionPresenter;
    private @NonNull TextBolderContract.Presenter bolderPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Create our presenters, check that the variables are the contracts!!
        bolderPresenter = new TextBolderPresenter();
        descriptionPresenter = new ProductDescriptionPresenter(mockProduct());

        // Start observing the bolder state
        bolderPresenter.observeState()
                // We will execute our stuff in the main thread, since we need to change ui!
                .observeOn(AndroidSchedulers.mainThread())
                // But we subscribe on a different thread (subscribe = run all the subscribing
                // methods in X thread)
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        descriptionPresenter.setTextBold(aBoolean);
                    }
                });
        // Note that this stream doesnt need to compose to avoid leaking, since it lives the same as
        // the application (its a single activity)

        // Bind the view to the presenter. This will automatically manage the presenter-view
        // logic whenever the view is attached or detached from a layout / root
        Coordinators.bind(
                findViewById(R.id.activity_main_textBolderView),
                new CoordinatorProvider() {
                    @Nullable
                    @Override
                    public Coordinator provideCoordinator(View view) {
                        return (Coordinator) bolderPresenter;
                    }
                }
        );

        // Bind the view to the presenter. This will automatically manage the presenter-view
        // logic whenever the view is attached or detached from a layout / root
        Coordinators.bind(
                findViewById(R.id.activity_main_descriptionView),
                new CoordinatorProvider() {
                    @Nullable
                    @Override
                    public Coordinator provideCoordinator(View view) {
                        return (Coordinator) descriptionPresenter;
                    }
                }
        );
    }

    @NonNull
    Product mockProduct() {
        Product product = new Product();
        product.setId(new Random().nextInt());
        product.setInnerSchemaId(new Random().nextInt());
        product.setName("test-" + String.valueOf(product.getId() + product.getInnerSchemaId()));
        product.setPrice(new Random().nextInt());
        product.setTags(null);
        product.setDescription("Test test test test test test test test test test test test test\n" +
                "test test test test test test test test test test test test test \n" +
                "test test test test test test test test test test test test test test test \n" +
                "test test test test test test test test test test test test test test test test\n" +
                "test test test test test test test test test test test test test test \n" +
                "test test test test test test test test test \n" +
                "test test test test test test test test test test test \n" +
                "\n" +
                "\n" +
                "test test test test test test test test \n" +
                "test test test test test test test test test test test test test \n" +
                "test test test test test test test test test test test test test test test \n" +
                "\n" +
                "test test.");
        return product;
    }

}
