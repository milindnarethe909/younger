package com.youngershopping.view.ratio;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.youngershopping.R;


/**
 * @author Ritesh Shakya
 */

public class RatioRelativeLayout extends RelativeLayout implements RatioBase {
    private float verticalRatio = 1;
    private float horizontalRatio = 1;
    private FixedAttribute fixedAttribute = FixedAttribute.WIDTH;

    public RatioRelativeLayout(Context context) {
        super(context);
    }

    public RatioRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RatioRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public RatioFrameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(attrs);
//    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.RatioRelativeLayout);
        fixedAttribute = FixedAttribute.fromId(typedArray.getInt(R.styleable.RatioRelativeLayout_fixed_attribute, 0));
        horizontalRatio = typedArray.getFloat(R.styleable.RatioRelativeLayout_horizontal_ratio, 1);
        verticalRatio = typedArray.getFloat(R.styleable.RatioRelativeLayout_vertical_ratio, 1);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int originalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int originalHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (fixedAttribute == FixedAttribute.WIDTH) {
            int calculatedHeight = (int) (originalWidth * (verticalRatio / horizontalRatio));
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(calculatedHeight, MeasureSpec.EXACTLY));
        } else {
            int calculatedWidth = (int) (originalHeight * (horizontalRatio / verticalRatio));
            super.onMeasure(MeasureSpec.makeMeasureSpec(calculatedWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
        }
    }

    @Override
    public void setRatio(float horizontalRatio, float verticalRatio) {
        this.setRatio(fixedAttribute, horizontalRatio, verticalRatio);
    }

    @Override
    public void setRatio(FixedAttribute fixedAttribute, float horizontalRatio, float verticalRatio) {
        this.fixedAttribute = fixedAttribute;
        this.horizontalRatio = horizontalRatio;
        this.verticalRatio = verticalRatio;
        this.invalidate();
        this.requestLayout();
    }

    @Override
    public float getHorizontalRatio() {
        return horizontalRatio;
    }

    @Override
    public float getVerticalRatio() {
        return verticalRatio;
    }

    @Override
    public FixedAttribute getFixedAttribute() {
        return fixedAttribute;
    }
}
