package com.lunioussky.orirea.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lunioussky.orirea.R;
import com.lunioussky.orirea.utils.ContextUtils;


public class BottomBarTab extends LinearLayout {
    private ImageView mIcon;
    private Context mContext;
    private TextView mTextView;
    private int mTabPosition = -1;
    private int icon;
    private static boolean ifshow = false;

    public BottomBarTab(Context context, @DrawableRes int icon, String title) {
        this(context, null, icon, title);
    }


    public BottomBarTab(Context context, AttributeSet attrs, int icon, String title) {
        this(context, attrs, 0, icon, title);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int defStyleAttr, int icon, String title) {
        super(context, attrs, defStyleAttr);
        init(context, icon, title);
    }

    private void init(Context context, int icon, String title) {
        mContext = context;
        this.icon = icon;

        setOrientation(LinearLayout.VERTICAL);
        mIcon = new ImageView(context);
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, getResources().getDisplayMetrics());
        LayoutParams params = new LayoutParams(size, size);
        params.gravity = Gravity.CENTER;
        params.topMargin = ContextUtils.dip2px(context, 6f);
        mIcon.setImageResource(icon);
        mIcon.setLayoutParams(params);

        LayoutParams textViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textViewParams.gravity = Gravity.CENTER;
        textViewParams.topMargin = ContextUtils.dip2px(context, 2.5f);
        textViewParams.bottomMargin = ContextUtils.dip2px(context, 1.5f);
        mTextView = new TextView(context);
        mTextView.setText(title);
        mTextView.setTextSize(ContextUtils.dip2px(context, 3.2f));
        mTextView.setLayoutParams(textViewParams);
        mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.tab_unselect));
        addView(mIcon);
        addView(mTextView);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));
            mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));

        } else {
            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.tab_unselect));
            mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.tab_unselect));
        }
    }


    public void setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return mTabPosition;
    }
}
