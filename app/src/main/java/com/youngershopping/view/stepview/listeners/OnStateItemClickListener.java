package com.youngershopping.view.stepview.listeners;


import com.youngershopping.view.stepview.StateProgressBar;
import com.youngershopping.view.stepview.components.StateItem;

/**
 * Created by Kofi Gyan on 2/20/2018.
 */

public interface OnStateItemClickListener {

    void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState);

}
