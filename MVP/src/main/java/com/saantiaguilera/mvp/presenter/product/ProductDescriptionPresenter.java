package com.saantiaguilera.mvp.presenter.product;

import android.support.annotation.NonNull;

import com.saantiaguilera.mvp.contract.product.ProductDescriptionContract;
import com.saantiaguilera.mvp.model.Product;
import com.saantiaguilera.mvp.presenter.BasePresenter;

public class ProductDescriptionPresenter extends BasePresenter<ProductDescriptionContract.View>
        implements ProductDescriptionContract.Presenter {

    private @NonNull Product product;

    public ProductDescriptionPresenter(@NonNull Product product) {
        this.product = product;
    }

    @Override
    protected void onViewAttached(@NonNull ProductDescriptionContract.View view) {
        view.setDescription(product.getDescription());
    }

    @Override
    public void setTextBold(boolean enabled) {
        ProductDescriptionContract.View view = getView();
        if (view != null) {
            view.setTextBold(enabled);
        }
    }

}
