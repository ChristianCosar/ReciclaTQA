package com.kodevian.reciclapp.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.model.RecycleItemCategorieTrack;
import com.kodevian.reciclapp.presenter.commons.CommunicatePresenterEditDefinePrice;
import com.kodevian.reciclapp.util.CircleTransform;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.views.adapters.listeners.OnClickListenerEditCategories;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 12/04/16.
 */
public class CompanyEditCategorieAdapter extends RecyclerView.Adapter<CompanyEditCategorieAdapter.ViewHolder> implements OnClickListenerEditCategories {


    Context context;

    private ArrayList<RecycleItemCategorieTrack> recycleItemEntities;
    private LayoutInflater inflater;
    private CommunicatePresenterEditDefinePrice communicatePresenterDefinePrice;

    public CompanyEditCategorieAdapter(Context context, ArrayList<RecycleItemCategorieTrack> recycleItemEntities, CommunicatePresenterEditDefinePrice communicatePresenterEditDefinePrice) {
        this.context = context;
        this.recycleItemEntities = recycleItemEntities;
        this.inflater = LayoutInflater.from(context);
        this.communicatePresenterDefinePrice = communicatePresenterEditDefinePrice;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_price, parent, false);
        return new ViewHolder(v,this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecycleItemCategorieTrack recycleItemEntity = recycleItemEntities.get(position);
        holder.tvCategoryTittle.setTypeface(Util_Fonts.setFontLight(context));
        holder.textCategoryPrice.setTypeface(Util_Fonts.setFontLight(context));


        holder.tvCategoryTittle.setText(recycleItemEntity.getCategory().getName());
        holder.textCategoryPrice.setText("S/. " + recycleItemEntities.get(position).getPrice());
        Glide.with(context).load(recycleItemEntity.getCategory().getImg()).bitmapTransform(new CircleTransform(context)).into(holder.ivIconCategories);


    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return recycleItemEntities.size();
    }


    public void clear() {
        recycleItemEntities.clear();
        notifyDataSetChanged();
    }

    public void addCategorie(RecycleItemCategorieTrack recycleItemEntity) {
        recycleItemEntities.add(recycleItemEntity);
        notifyDataSetChanged();
    }
    public void deleteCategorie(String id){
        for (int i = 0; i < recycleItemEntities.size() ; i++) {
            if(recycleItemEntities.get(i).getId()==id){
                recycleItemEntities.remove(i);
                notifyItemRemoved(i);

                break;
            }
        }

    }

    public void updateCategory(RecycleItemCategorieTrack recycleItemCategorieTrack){
        for (int i = 0; i < recycleItemEntities.size() ; i++) {
            if(recycleItemEntities.get(i).getId().equals(recycleItemCategorieTrack.getId())){
                recycleItemEntities.get(i).setPrice(recycleItemCategorieTrack.getPrice());
                notifyItemChanged(i);
                break;
            }
        }


    }

    public ArrayList<RecycleItemCategorieTrack> getData() {
        return recycleItemEntities;
    }



    @Override
    public void onItemClickEdit(int position) {
        communicatePresenterDefinePrice.editCategories(position);
    }

    @Override
    public void onItemClickDelete(int position) {
        communicatePresenterDefinePrice.deleteCategories(position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.iv_icon_categories)
        ImageView ivIconCategories;
        @Bind(R.id.tv_category_tittle)
        TextView tvCategoryTittle;
        @Bind(R.id.text_category_price)
        TextView textCategoryPrice;
        @Bind(R.id.iv_edit_categorie)
        ImageView ivEditCategorie;
        @Bind(R.id.iv_delete_categorie)
        ImageView ivDeleteCategorie;

        OnClickListenerEditCategories onClickListenerEditCategories;
        public ViewHolder(View view,OnClickListenerEditCategories onClickListenerEditCategories) {
            super(view);
            ButterKnife.bind(this, view);
            this.onClickListenerEditCategories=onClickListenerEditCategories;
           // ivEditCategorie.setOnClickListener(this);
            //ivDeleteCategorie.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

        }

        @OnClick({R.id.iv_edit_categorie, R.id.iv_delete_categorie})
        public void onClickImageButton(View view) {
            switch (view.getId()) {
                case R.id.iv_edit_categorie:
                    onClickListenerEditCategories.onItemClickEdit(getAdapterPosition());
                    break;
                case R.id.iv_delete_categorie:
                    onClickListenerEditCategories.onItemClickDelete(getAdapterPosition());
                    break;
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}