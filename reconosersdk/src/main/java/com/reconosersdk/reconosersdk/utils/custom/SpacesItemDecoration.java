package com.reconosersdk.reconosersdk.utils.custom;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    /***
     * defines default margin space will have the item of recyclerview
     */
    public static final int DEFAULT_SPACE = 14;
    /***
     * if it is to be used in different recyclerview
     */
    public static final int DEFAULT_TYPE = 0;
    public static final int TYPE_30 = 1;
    public static final int SPACE_30 = 30;
    private int space;
    private int type;

    public SpacesItemDecoration(int space, int type) {
        this.space = space;
        this.type = type;
    }

    /***
     * a rectangle is generated with the spaces defined
     * @param outRect Rect holds four integer coordinates for a rectangle.
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        switch (type) {
            case DEFAULT_TYPE:

                outRect.right = space;
                outRect.bottom = space;
                outRect.left = space;

                // Add top margin only for the first item to avoid double space between items
                if(parent.getChildLayoutPosition(view) == 0)
                    outRect.top = space;

                break;
            case TYPE_30:

                outRect.right = space;
                outRect.bottom = SPACE_30;
                outRect.left = space;

                // Add top margin only for the first item to avoid double space between items
                if(parent.getChildLayoutPosition(view) == 0)
                    outRect.top = space;

                break;

            default:
                break;
        }
    }
}
