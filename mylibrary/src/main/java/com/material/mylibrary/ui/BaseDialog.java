/**
 *
 */
package com.material.mylibrary.ui;

import android.app.Dialog;
import android.content.Context;

public class BaseDialog extends Dialog {

	protected Context mContext;
	
	public BaseDialog(Context context) {
		super(context);
		mContext = context;
	}
	
	public BaseDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}
	
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
	}
}
