package com.material.travel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.material.travel.R;


public class PicsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
	private LayoutInflater mInflater;
	private Context mContext;
	String[] mLists;
	private static final int V_ITEM = 0;
	//private static final int H_ITEM = 1;

	public interface OnItemClickLitener {
		void onItemClick(View view, int position);

		void onItemLongClick(View view, int position);
	}

	private OnItemClickLitener mOnItemClickLitener;

	public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
		this.mOnItemClickLitener = mOnItemClickLitener;
	}

	public PicsAdapter(Context context, String[] lists) {
		mInflater = LayoutInflater.from(context);
		//mList = lists;
		this.mLists=lists;
		mContext=context;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
			int viewType) {
		// Log.e("YM", "onCreateViewHolder");

		//if (viewType == V_ITEM) {
			return new VerticalViewHolder(mInflater.inflate(R.layout.list_photo,
					parent, false));
		/*} else {
			return new HorizViewHolder(mInflater.inflate(R.layout.list_item_h,
					parent, false));
		}*/
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder,
			final int position) {
		VerticalViewHolder holder = (VerticalViewHolder) viewHolder;
		//OkImageLoader.getInstance(mContext).with(mLists[position],holder.photo);
		// 如果设置了回调，则设置点击事件
		if (mOnItemClickLitener != null) {
			viewHolder.itemView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					int pos = viewHolder.getLayoutPosition();
					mOnItemClickLitener.onItemClick(viewHolder.itemView, pos);
				}
			});

			viewHolder.itemView
					.setOnLongClickListener(new OnLongClickListener() {
						@Override
						public boolean onLongClick(View v) {
							int pos = viewHolder.getLayoutPosition();
							mOnItemClickLitener.onItemLongClick(
									viewHolder.itemView, pos);
							// removeData(pos);
							return false;
						}
					});
		}
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return  V_ITEM;

	}

	@Override
	public int getItemCount() {
		return mLists.length;
	}

	public void removeData(int position) {
		//mList.remove(position);
		notifyItemRemoved(position);
	}

	class VerticalViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;

		public VerticalViewHolder(View convertView) {
			super(convertView);
			photo = (ImageView) convertView.findViewById(R.id.photo);

		}
	}



}