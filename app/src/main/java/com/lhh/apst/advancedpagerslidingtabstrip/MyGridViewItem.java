package com.lhh.apst.advancedpagerslidingtabstrip;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by HIBARI_YUYA on 2016/9/6.
 */
public class MyGridViewItem  extends LinearLayout {

    public MyGridViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyGridViewItem(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),getDefaultSize(0, heightMeasureSpec));

        // childWidthSize是自定义布局的宽
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = getMeasuredHeight();

        // 高度和宽度一样；最后面的是RelativeLayout的宽
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}

