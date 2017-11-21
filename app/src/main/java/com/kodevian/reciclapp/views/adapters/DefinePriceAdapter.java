package com.kodevian.reciclapp.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.model.RecycleItemEntity;
import com.kodevian.reciclapp.presenter.commons.CommunicatePresenterDefinePrice;
import com.kodevian.reciclapp.util.CircleTransform;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.views.activities.MainActivity;
import com.kodevian.reciclapp.views.adapters.listeners.OnClickFocusListenerEditText;
import com.kodevian.reciclapp.views.adapters.listeners.OnClickListenerCompanyItem;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.DecimalMin;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by junior on 12/04/16.
 */
public class DefinePriceAdapter extends RecyclerView.Adapter<DefinePriceAdapter.ViewHolder> implements OnClickFocusListenerEditText {


     Context context;
    private ArrayList<RecycleItemEntity> recycleItemEntities;
    private LayoutInflater inflater;
    private CommunicatePresenterDefinePrice communicatePresenterDefinePrice;

    public DefinePriceAdapter(Context context, ArrayList<RecycleItemEntity> recycleItemEntities,CommunicatePresenterDefinePrice communicatePresenterDefinePrice) {
        this.context = context;
        this.recycleItemEntities = recycleItemEntities;
        this.inflater = LayoutInflater.from(context);
        this.communicatePresenterDefinePrice=communicatePresenterDefinePrice;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_define_price, parent, false);
        return new ViewHolder(v,this,new MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecycleItemEntity recycleItemEntity = recycleItemEntities.get(position);
        holder.tvCategoryTittle.setTypeface(Util_Fonts.setFontLight(context));
        holder.textCategoryPrice.setTypeface(Util_Fonts.setFontLight(context));
        holder.tv_money.setTypeface(Util_Fonts.setFontLight(context));
        holder.tvCategoryTittle.setText(recycleItemEntity.getName());
        holder.myCustomEditTextListener.updatePosition(position);
        holder.textCategoryPrice.setText(recycleItemEntities.get(position).getPrice());
        Glide.with(context).load(recycleItemEntity.getImg()).bitmapTransform(new CircleTransform(context)).into(holder.iv_icon_categories);


    }



    public boolean reviseEditTextNull(){
        for (int i = 0; i <recycleItemEntities.size() ; i++) {
            if(recycleItemEntities.get(i).getPrice().equals("")){
                return false;
            }
        }
        return true;
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

    public void addCategorie(RecycleItemEntity recycleItemEntity){
        recycleItemEntities.add(recycleItemEntity);
        notifyDataSetChanged();
    }
    public ArrayList<RecycleItemEntity> getData(){
        return recycleItemEntities;
    }
    @Override
    public void onItemClick(int position) {
        communicatePresenterDefinePrice.focusRecive();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnTouchListener{
        @Bind(R.id.tv_category_tittle)
        TextView tvCategoryTittle;
        @Bind(R.id.tv_money)
        TextView tv_money;

        @DecimalMin(value=0,sequence = 4,message = "Valor no válido")
        @NotEmpty(message = "Este campo no puede ser vacío")
        @Bind(R.id.text_category_price)
        EditText textCategoryPrice;

        @Bind(R.id.iv_icon_categories)
        ImageView iv_icon_categories;
        public MyCustomEditTextListener myCustomEditTextListener;

        OnClickFocusListenerEditText onClickFocusListenerEditText;
        public ViewHolder(View view, OnClickFocusListenerEditText onClickFocusListenerEditText,
                          MyCustomEditTextListener myCustomEditTextListener) {
            super(view);
            ButterKnife.bind(this, view);
            this.onClickFocusListenerEditText=onClickFocusListenerEditText;
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.textCategoryPrice.addTextChangedListener(myCustomEditTextListener);
            textCategoryPrice.setOnTouchListener(this);


        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusableInTouchMode(true);
            v.requestFocus();
            v.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            onClickFocusListenerEditText.onItemClick(getAdapterPosition());
                            return true;
                        }
                    }
                    return false;
                }


            });
            return false;
        }


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            //recycleItemEntities.get(position).setPrice(charSequence.toString());
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            recycleItemEntities.get(position).setPrice(charSequence.toString());

        }

        @Override
        public void afterTextChanged(Editable editable) {


            // no op
        }
    }

}