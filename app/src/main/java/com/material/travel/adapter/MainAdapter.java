package com.material.travel.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.material.travel.R;
import com.material.travel.WebViewActivity;
import com.material.travel.model.TravelInfo;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MainAdapter extends BaseAdapter<TravelInfo.Book> {
	private LayoutInflater mInflater;
	private Context mContext;

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

	public MainAdapter(Context context, List<TravelInfo.Book> lists) {
		mInflater = LayoutInflater.from(context);
		mList = lists;
		mContext=context;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
			int viewType) {
		// Log.e("YM", "onCreateViewHolder");

		//if (viewType == V_ITEM) {
			return new VerticalViewHolder(mInflater.inflate(R.layout.list_item,
					parent, false));
		/*} else {
			return new HorizViewHolder(mInflater.inflate(R.layout.list_item_h,
					parent, false));
		}*/
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder,
			final int position) {
		// Log.e("YM", "onBindViewHolder");
		    TravelInfo.Book book = mList.get(position);
		//if (viewHolder instanceof VerticalViewHolder) {
			VerticalViewHolder holder = (VerticalViewHolder) viewHolder;
			holder.trav_text.setText(book.text);
			holder.trav_title.setText(book.title);
		    holder.trav_likenum.setText(String.valueOf(book.likeCount));
		    holder.trav_commentnum.setText(String.valueOf(book.commentCount));
		    Picasso.with(mContext)
				.load(book.userHeadImg)
				.placeholder(R.drawable.user_head)
				.error(R.drawable.user_head)
				.into(holder.trav_head);
		/*} else {
			HorizViewHolder holder = (HorizViewHolder) viewHolder;
			holder.goods_name.setText(person.getName());
			holder.goods_left.setText(String.valueOf(person.getLeftTime()));

		}*/

		// 如果设置了回调，则设置点击事件
		if (mOnItemClickLitener != null) {
			viewHolder.itemView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					//int pos = viewHolder.getLayoutPosition();
					//mOnItemClickLitener.onItemClick(viewHolder.itemView, pos);
					Intent i=new Intent(mContext, WebViewActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.putExtra("load_url",book.bookUrl);
					i.putExtra("load_subtitle",book.userName);
					i.putExtra("load_title",book.title);
					mContext.startActivity(i);

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

	public void removeData(int position) {
		mList.remove(position);
		notifyItemRemoved(position);
	}

	class VerticalViewHolder extends RecyclerView.ViewHolder {
        ImageView trav_head;

		TextView trav_title;
		TextView trav_text;
		TextView trav_likenum;
		TextView trav_commentnum;

		public VerticalViewHolder(View convertView) {
			super(convertView);
			trav_head = (ImageView) convertView.findViewById(R.id.trav_head);
			trav_title = (TextView) convertView.findViewById(R.id.trav_title);
			trav_text = (TextView) convertView.findViewById(R.id.trav_text);
			trav_likenum = (TextView) convertView.findViewById(R.id.trav_likenum);
			trav_commentnum = (TextView) convertView.findViewById(R.id.trav_commentnum);


		}
	}

	/*class HorizViewHolder extends VerticalViewHolder {

		public HorizViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
		}

	}*/

}