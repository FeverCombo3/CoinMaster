package com.yjq.common.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.jess.arms.base.BaseApplication;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.integration.ConfigModule;
import com.kingja.loadsir.core.LoadSir;
import com.yjq.common.constant.Constant;
import com.yjq.common.glide.GlideImageLoaderStrategy;
import com.yjq.common.widget.callback.EmptyCallBack;
import com.yjq.common.widget.callback.ErrorCallBack;
import com.yjq.common.widget.callback.LoadingCallBack;
import com.yjq.common.widget.callback.NoNetworkCallBack;
import com.yjq.common.widget.callback.TimeOutCallBack;

import java.util.List;

import javax.security.auth.callback.Callback;

public class CommonConfiguration implements ConfigModule{
    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        builder.imageLoaderStrategy(new GlideImageLoaderStrategy());
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        lifecycles.add(new AppLifecycles() {
            @Override
            public void attachBaseContext(@NonNull Context base) {

            }

            @Override
            public void onCreate(@NonNull Application application) {
                AppComponent appComponent = ((BaseApplication)application).getAppComponent();
                appComponent.extras().put(Constant.LoadSir.ERROR,new ErrorCallBack());
                appComponent.extras().put(Constant.LoadSir.EMPTY,new EmptyCallBack());
                appComponent.extras().put(Constant.LoadSir.TIMEOUT,new TimeOutCallBack());
                appComponent.extras().put(Constant.LoadSir.NO_NETWORK,new NoNetworkCallBack());
                appComponent.extras().put(Constant.LoadSir.LOADING,new LoadingCallBack());

                LoadSir.beginBuilder()
                        .addCallback(new ErrorCallBack())
                        .addCallback(new EmptyCallBack())
                        .addCallback(new TimeOutCallBack())
                        .addCallback(new LoadingCallBack())
                        .addCallback(new NoNetworkCallBack())
                        .commit();
            }

            @Override
            public void onTerminate(@NonNull Application application) {

            }
        });
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {

    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {

    }
}
