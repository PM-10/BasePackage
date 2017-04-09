package kr.pm10.basepackage.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import kr.pm10.basepackage.ui.listener.OnLoadMoreListener;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final int VIEW_TYPE_HEADER = 0;
    protected final int VIEW_TYPE_ITEM = 1;
    protected final int VIEW_TYPE_FOOTER = 2;

    protected Context context;
    protected ArrayList<T> items = new ArrayList<>();
    protected int headerViewCount;

    private OnLoadMoreListener onLoadMoreListener;
    protected boolean isLoading;
    private boolean isLastPage = true;
    private int itemPage = 1;

    public BaseAdapter(Context context) {
        this.context = context;
    }

    public void resetAll(ArrayList<T> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<T> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        int itemCount = items.size() <= 0 ? headerViewCount : items.size() + headerViewCount;
        return itemCount;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    protected void setLoadMore(int position) {
        if (onLoadMoreListener != null && position == items.size() - 1 && !isLoading && !isLastPage) {
            isLoading = true;
            itemPage++;
            onLoadMoreListener.onLoadMore(itemPage);
        }
    }

    public void refreshPage() {
        isLastPage = false;
        itemPage = 1;
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void setLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
        if (isLastPage)
            itemPage = 1;
    }
}
