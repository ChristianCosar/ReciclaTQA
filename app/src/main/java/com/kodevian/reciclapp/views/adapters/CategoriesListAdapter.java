package com.kodevian.reciclapp.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.model.RecycleItemCategorieTrack;
import com.kodevian.reciclapp.model.RecycleItemEntity;
import com.kodevian.reciclapp.presenter.commons.CommunicatePresenterRecycleItem;
import com.kodevian.reciclapp.util.CircleTransform;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by junior on 24/02/16.
 */
public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.ViewHolder> {


    private ArrayList<RecycleItemCategorieTrack> recycleItemEntities;
    private Context context;
    private CommunicatePresenterRecycleItem communicatePresenterRecycleItem;

    public CategoriesListAdapter(Context context, ArrayList<RecycleItemCategorieTrack> recycleItemEntities) {
        this.recycleItemEntities = recycleItemEntities;
        this.context = context;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecycleItemCategorieTrack recycleItemEntity = recycleItemEntities.get(position);
        // if (recycleItemEntity.getImg() != null)
        Glide.with(context).load(recycleItemEntity.getCategory().getImg()).bitmapTransform(new CircleTransform(context)).into(holder.ivRecycleCategory);

    }



    @Override
    public int getItemCount() {
        return recycleItemEntities.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_recycle_category)
        ImageView ivRecycleCategory;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}