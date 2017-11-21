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
import com.kodevian.reciclapp.model.RecycleItemEntity;
import com.kodevian.reciclapp.presenter.commons.CommunicatePresenterRecycleItem;
import com.kodevian.reciclapp.util.CircleTransform;
import com.kodevian.reciclapp.util.SelectableAdapter;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.views.adapters.listeners.OnClickListenerRecycleItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by junior on 24/02/16.
 */
public class RecycleItemsAdapter extends SelectableAdapter<RecycleItemsAdapter.ViewHolder> implements OnClickListenerRecycleItem {

    private ArrayList<RecycleItemEntity> recycleItemEntities;
    private Context context;
    private CommunicatePresenterRecycleItem communicatePresenterRecycleItem;

    public RecycleItemsAdapter(Context context,ArrayList<RecycleItemEntity> recycleItemEntities, CommunicatePresenterRecycleItem communicatePresenterRecycleItem) {
        this.recycleItemEntities = recycleItemEntities;
        this.communicatePresenterRecycleItem =communicatePresenterRecycleItem;
        this.context=context;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle, parent, false);
        return new ViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecycleItemEntity recycleItemEntity = recycleItemEntities.get(position);
        holder.title.setText(recycleItemEntity.getName());
        holder.title.setTypeface(Util_Fonts.setFontLight(context));
        Glide.with(context).load(recycleItemEntity.getImg()).bitmapTransform(new CircleTransform(context)).into(holder.imgView);
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        return recycleItemEntities.size();
    }

    public void selectableItems( ArrayList<RecycleItemEntity> listCompany){
        for (int j = 0; j < listCompany.size(); j++) {
            for (int i = 0; i < recycleItemEntities.size(); i++) {
                if(recycleItemEntities.get(i).getId().equals(listCompany.get(j).getId())){
                    toggleSelection(i);
                }

            }
        }

    }

    @Override
    public void onItemClick(int position) {
           communicatePresenterRecycleItem.clickItemRecycle(recycleItemEntities.get(position));
            toggleSelection(position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        @Bind(R.id.iv_recycle_category)
        ImageView imgView;
        @Bind(R.id.tv_recycle_category)
        TextView title;
        @Bind(R.id.selected_overlay)
        View selectedOverlay;

        OnClickListenerRecycleItem onClickListenerRecycleItem;

        public ViewHolder(View view, OnClickListenerRecycleItem onClickListenerRecycleItem) {
            super(view);
            this.onClickListenerRecycleItem = onClickListenerRecycleItem;
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onClickListenerRecycleItem.onItemClick(getAdapterPosition());
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}