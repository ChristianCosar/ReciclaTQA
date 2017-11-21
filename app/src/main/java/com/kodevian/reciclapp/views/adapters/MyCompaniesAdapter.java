package com.kodevian.reciclapp.views.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.presenter.commons.CommunicatePresenterCompanyItem;
import com.kodevian.reciclapp.util.UtilString;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.views.adapters.listeners.OnClickListenerCompanyItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Leo on 13/01/2016.
 */
public class MyCompaniesAdapter extends RecyclerView.Adapter<MyCompaniesAdapter.ViewHolder> implements OnClickListenerCompanyItem {


    private static final int NUM_OF_COLUMNS = 6;

    private Context context;
    private ArrayList<CompanyEntity> companyEntities;
    private LayoutInflater inflater;
    CommunicatePresenterCompanyItem communicatePresenterCompanyItem;

    LinearLayout linearLayout;
    LinearLayout normalLayout;

    public MyCompaniesAdapter(Context context, ArrayList<CompanyEntity> companyEntities, CommunicatePresenterCompanyItem communicatePresenterCompanyItem) {
        this.context = context;
        this.companyEntities = companyEntities;
        this.inflater = LayoutInflater.from(context);
        this.communicatePresenterCompanyItem = communicatePresenterCompanyItem;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, parent, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CompanyEntity companyEntity = companyEntities.get(position);
        holder.tvNameCompany.setText(UtilString.capitalizeFirstLetter(companyEntity.getName()));
        holder.tvDetailCompany.setTypeface(Util_Fonts.setFontLight(context));
        holder.tvNameCompany.setTypeface(Util_Fonts.setFontNormal(context));
        holder.tvNumLikes.setTypeface(Util_Fonts.setFontLight(context));
        holder.tvWeRecycle.setTypeface(Util_Fonts.setFontLight(context));
        holder.tv_s_d_f.setTypeface(Util_Fonts.setFontLight(context));
        holder.tv_l_m.setTypeface(Util_Fonts.setFontLight(context));
        holder.tvPhone.setTypeface(Util_Fonts.setFontLight(context));

        if (companyEntity.getPhoto() != null)
            Glide.with(context).load(companyEntity.getPhoto()).into(holder.logoCompany);
        else{
           // Glide.clear(holder.logoCompany);
            // remove the placeholder (optional); read comments below
            holder.logoCompany.setImageDrawable(null);
        }

        if (companyEntity.getTelephone() != null ) {
            if(!companyEntity.getTelephone().equals("")){
                holder.tvPhone.setText(companyEntity.getTelephone() + " / " + companyEntity.getCelphone());
            }else{
                holder.tvPhone.setText(companyEntity.getCelphone());
            }

        } else {
            holder.tvPhone.setText(companyEntity.getCelphone());
        }

        holder.tv_l_m.setText("Lu. - Vi. "+companyEntity.getMonday_to_friday());
        holder.tv_s_d_f.setText("Sa. - Do. - Fe. "+companyEntity.getSaturday_sunday_holidays());
        holder.tvDetailCompany.setText(companyEntity.getAddress());
        if (companyEntity.getC_type().equals("R")) {
            holder.tvTypeCompany.setText("R");
            holder.cornerCard.setBackgroundResource(R.drawable.triangle_shape);
        } else {
            holder.tvTypeCompany.setText("J");
            holder.cornerCard.setBackgroundResource(R.drawable.triangle_shape_off);
        }

        CategoriesListAdapter categoriesListAdapter = new CategoriesListAdapter(context, companyEntity.getCategories_companies());
        categoriesListAdapter.notifyDataSetChanged();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, NUM_OF_COLUMNS);
        holder.listItemRecycling.setLayoutManager(gridLayoutManager);
        holder.listItemRecycling.setHasFixedSize(true);
        holder.listItemRecycling.setAdapter(categoriesListAdapter);

        if (!companyEntity.isVerify()) {
            holder.ivReciclappAprobed.setVisibility(View.GONE);
        } else {
            holder.ivReciclappAprobed.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return companyEntities.size();
    }

    public void addCompany(CompanyEntity companyEntity) {
        companyEntities.add(0,companyEntity);
        notifyDataSetChanged();
    }


    public void clear() {
        companyEntities.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        CompanyEntity companyEntity = companyEntities.get(position);
        communicatePresenterCompanyItem.clickItemCompanyItem(companyEntity);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.logo_company)
        ImageView logoCompany;
        @Bind(R.id.tv_name_company)
        TextView tvNameCompany;
        @Bind(R.id.tv_detail_company)
        TextView tvDetailCompany;
        @Bind(R.id.list_item_recycling)
        RecyclerView listItemRecycling;
        @Bind(R.id.tv_type_company)
        TextView tvTypeCompany;
        @Bind(R.id.corner_card)
        RelativeLayout cornerCard;

        @Bind(R.id.tv_we_recycle)
        TextView tvWeRecycle;
        @Bind(R.id.tv_num_likes)
        TextView tvNumLikes;
        @Bind(R.id.tv_l_m)
        TextView tv_l_m;

        @Bind(R.id.tv_s_d_f)
        TextView tv_s_d_f;
        @Bind(R.id.tv_phone)
        TextView tvPhone;

        @Bind(R.id.layout_like)
        LinearLayout layoutLike;

        @Bind(R.id.iv_reciclapp_aprobed)
        ImageView ivReciclappAprobed;

        OnClickListenerCompanyItem onClickListenerCompanyItem;

        public ViewHolder(View view, OnClickListenerCompanyItem onClickListenerCompanyItem) {
            super(view);
            this.onClickListenerCompanyItem = onClickListenerCompanyItem;
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListenerCompanyItem.onItemClick(getAdapterPosition());
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
