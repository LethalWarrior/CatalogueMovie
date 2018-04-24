package com.lethalizer.robby.cataloguemovie.etc;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lethalizer.robby.cataloguemovie.R;

/**
 * Created by robby on 4/23/18.
 */

public class ItemClickSupport {

    private final RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    private ItemClickSupport(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        recyclerView.setTag(R.id.item_click_support, this);
        recyclerView.addOnChildAttachStateChangeListener(onChildAttachStateChangeListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(onItemClickListener != null) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                onItemClickListener.onItemClicked(recyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if(onItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                return onItemLongClickListener.onItemLongClicked(recyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener onChildAttachStateChangeListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if(onItemClickListener != null) {
                view.setOnClickListener(onClickListener);
            }
            if(onLongClickListener != null) {
                view.setOnLongClickListener(onLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {

        }
    };

    public static ItemClickSupport addTo(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if(support == null) {
            support = new ItemClickSupport(view);
        }
        return support;
    }

    public static ItemClickSupport removeFrom(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if(support != null) {
            support.detach(view);
        }
        return support;
    }

    public ItemClickSupport setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
        return this;
    }

    public ItemClickSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
        onItemLongClickListener = listener;
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(onChildAttachStateChangeListener);
        view.setTag(R.id.item_click_support, this);
    }


    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }

}
