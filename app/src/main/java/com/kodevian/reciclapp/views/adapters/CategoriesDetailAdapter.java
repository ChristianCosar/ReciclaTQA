package com.kodevian.reciclapp.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.model.RecycleItemCategorieTrack;
import com.kodevian.reciclapp.util.CircleTransform;
import com.kodevian.reciclapp.util.Util_Fonts;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by junior on 24/02/16.
 */
public class CategoriesDetailAdapter extends RecyclerView.Adapter<CategoriesDetailAdapter.ViewHolder> {



    private ArrayList<RecycleItemCategorieTrack> recycleItemEntities;
    private Context context;

    public CategoriesDetailAdapter(Context context, ArrayList<RecycleItemCategorieTrack> recycleItemEntities) {
        this.recycleItemEntities = recycleItemEntities;
        this.context = context;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_price_company, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecycleItemCategorieTrack recycleItemEntity = recycleItemEntities.get(position);
        holder.tvCategoryTittle.setTypeface(Util_Fonts.setFontLight(context));
        holder.tvCategoriePrice.setTypeface(Util_Fonts.setFontLight(context));
        Glide.with(context).
                load(recycleItemEntity.getCategory().getImg()).
                bitmapTransform(new CircleTransform(context)).into(holder.ivIconCategories);

        holder.tvCategoryTittle.setText(recycleItemEntity.getCategory().getName());
        holder.tvCategoriePrice.setText("S/. "+recycleItemEntity.getPrice());

    }


    @Override
    public int getItemCount() {
        return recycleItemEntities.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_icon_categories)
        ImageView ivIconCategories;
        @Bind(R.id.tv_category_tittle)
        TextView tvCategoryTittle;
        @Bind(R.id.tv_categorie_price)
        TextView tvCategoriePrice;

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