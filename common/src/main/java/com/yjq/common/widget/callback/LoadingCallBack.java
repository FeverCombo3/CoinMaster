package com.yjq.common.widget.callback;

import android.content.Context;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.wang.avi.AVLoadingIndicatorView;
import com.yjq.common.R;

public class LoadingCallBack extends Callback{
    private AVLoadingIndicatorView avLoad;

    @Override
    protected int onCreateView() {
        return R.layout.layout_callback_loading;
    }

    @Override
    protected void onViewCreate(Context context, View view) {
        super.onViewCreate(context, view);
        if(view != null && view.findViewById(R.id.avLoad) != null){
            avLoad = view.findViewById(R.id.avLoad);
            avLoad.setIndicator("SquareSpinIndicator");
            avLoad.setIndicatorColor(context.getResources().getColor(R.color.black));
            avLoad.show();
        }
    }
}
