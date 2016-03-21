package com.material.travel.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class BaseAdapter<T> extends
		RecyclerView.Adapter<RecyclerView.ViewHolder> {
	public List<T> mList = null;

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	public void add(T t) {
		if (t == null)
			return;

		if (mList == null) {
			mList = new ArrayList<T>();
		}
		mList.add(t);
		notifyDataSetChanged();
	}

	public void add(List<T> list) {
		if (list == null)
			return;
		if (mList == null) {
			mList = list;
			notifyDataSetChanged();
			return;
		}
		mList.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public void onBindViewHolder(ViewHolder arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub
		return null;
	}
}
