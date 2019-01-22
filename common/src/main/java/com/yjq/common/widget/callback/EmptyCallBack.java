package com.yjq.common.widget.callback;

import com.kingja.loadsir.callback.Callback;
import com.yjq.common.R;

public class EmptyCallBack extends Callback{
    @Override
    protected int onCreateView() {
        return R.layout.layout_callback_empty;
    }
}
