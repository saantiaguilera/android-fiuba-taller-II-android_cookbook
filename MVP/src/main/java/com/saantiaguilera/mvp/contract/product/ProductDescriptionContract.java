package com.saantiaguilera.mvp.contract.product;

import android.support.annotation.NonNull;

import com.saantiaguilera.mvp.presenter.BasePresenterContract;
import com.saantiaguilera.mvp.view.BaseViewContract;

/**
 * The idea of this contracts is to have one for the presenter (which a root will be able to communicate with)
 * and one for the view (the presenter will communicate with the view).
 *
 * Ideally:
 * - The view should be totally agnostic of both the bussiness rules/models and the presenters.
 * - The presenter should know the bussiness rules but not the specific view, it should be bound to a specific contract of it.
 *
 * It can happen, and its not a problem at all, that the presenter contract is empty. This will happen
 * when the root doesnt have any dependency over the presenter, but still the presenter should be the only
 * one in charge of managing its attached 'view'
 *
 * Created by saguilera on 10/25/17.
 */
public interface ProductDescriptionContract {

    /**
     * BasePresenter interface, it has a method to bold or unbold the text, as our parent wants us
     * to change this in runtime
     */
    interface Presenter extends BasePresenterContract {
        void setTextBold(boolean enable);
    }

    /**
     * View interface, check that its not bound to the bussiness models, this lets us
     * decouple from the models and presenter, and make changes smoothly without affecting
     * dependencies.
     *
     * For example if you had a list of MyModel shown, instead of having:
     * - setItems(List< MyModel > items);
     *
     * You should have:
     * - add(String name, String something);
     * - remove(String name);
     * - clear();
     */
    interface View extends BaseViewContract {
        void setDescription(@NonNull String description);

        void setTextBold(boolean enabled);
    }

}

