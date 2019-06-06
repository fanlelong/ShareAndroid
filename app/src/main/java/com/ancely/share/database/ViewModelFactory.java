//package com.ancely.share.database;
//
//import android.arch.lifecycle.ViewModel;
//import android.arch.lifecycle.ViewModelProvider;
//import android.support.annotation.NonNull;
//
//import com.ancely.share.base.BaseResultVM;
//import com.ancely.share.database.datasouce.ArtriceDataSoucel;
//import com.ancely.share.database.datasouce.DataSouceIml;
//
//public abstract class ViewModelFactory<VM extends BaseResultVM> implements ViewModelProvider.Factory {
//
//    private final DataSouceIml mDataSource;
//
//    public ViewModelFactory(ArtriceDataSoucel dataSource) {
//        mDataSource = dataSource;
//    }
//
//
//    @NonNull
//    @Override
//    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        if (modelClass.isAssignableFrom(initClazz())) {
//            return (T) new UseViewModel(mDataSource);
//        }
//        return null;
//    }
//
//    public abstract Class<VM> initClazz();
//}