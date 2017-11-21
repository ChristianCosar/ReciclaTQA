package com.kodevian.reciclapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.model.CompanyEntity;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.views.activities.MapActivity;
import com.kodevian.reciclapp.views.adapters.CategoriesDetailAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 04/04/16.
 */
public class DetailCompanyFragment extends Fragment {


    @Bind(R.id.tv_company_cellphone)
    TextView tvCompanyCellphone;
    @Bind(R.id.tv_company_cellphone_title)
    TextView tvCompanyCellphoneTitle;
    @Bind(R.id.tv_phone_company)
    TextView tvPhoneCompany;
    @Bind(R.id.tv_phone_company_title)
    TextView tvPhoneCompanyTitle;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_phone_title)
    TextView tvPhoneTitle;
    @Bind(R.id.tv_company_email)
    TextView tvCompanyEmail;
    @Bind(R.id.tv_company_email_title)
    TextView tvCompanyEmailTitle;
    @Bind(R.id.tv_company_m_f)
    TextView tvCompanyMF;
    @Bind(R.id.tv_company_m_f_title)
    TextView tvCompanyMFTitle;
    @Bind(R.id.tv_company_holyday)
    TextView tvCompanyHolyday;
    @Bind(R.id.tv_company_holyday_title)
    TextView tvCompanyHolydayTitle;
    @Bind(R.id.tv_recycle_title)
    TextView tvRecycleTitle;
    @Bind(R.id.tv_recycle_prices)
    TextView tvRecyclePrices;
    @Bind(R.id.btn_go_map)
    Button btnGoMap;
    @Bind(R.id.list_item_recycling)
    RecyclerView listItemRecycling;
    private CompanyEntity companyEntity;
    private LinearLayoutManager linearLayoutManager;
    private CategoriesDetailAdapter categoriesDetailAdapter;


    public static DetailCompanyFragment newInstance(String str) {
        DetailCompanyFragment fragment = new DetailCompanyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("company", str);
        fragment.setArguments(bundle);
        return fragment;
    }

    public DetailCompanyFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        String str = arg.getString("company");
        companyEntity = new Gson().fromJson(str, CompanyEntity.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_info_company, container, false);
        // ButterKnife.bind(this, view);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvCompanyCellphone.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvPhoneCompany.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyEmail.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyHolyday.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyMF.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvRecyclePrices.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvPhone.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvPhoneTitle.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyCellphoneTitle.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvPhoneCompanyTitle.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyEmailTitle.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyHolydayTitle.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvCompanyMFTitle.setTypeface(Util_Fonts.setFontLight(getContext()));
        tvRecycleTitle.setTypeface(Util_Fonts.setFontLight(getContext()));

        tvCompanyEmail.setText(companyEntity.getWeb());
        tvCompanyCellphone.setText(companyEntity.getCelphone());
        tvCompanyMF.setText(companyEntity.getMonday_to_friday());
        tvCompanyHolyday.setText(companyEntity.getSaturday_sunday_holidays());
        tvPhoneCompany.setText(companyEntity.getTelephone());
        categoriesDetailAdapter = new CategoriesDetailAdapter(getContext(),companyEntity.getCategories_companies());
        linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listItemRecycling.setLayoutManager(linearLayoutManager);
        listItemRecycling.setAdapter(categoriesDetailAdapter);

     /*   String pricesCategoires="";
        for (int i = 0; i <companyEntity.getCategories().size() ; i++) {
            pricesCategoires = pricesCategoires + companyEntity.getCategories().get(i);
        }*/


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // ButterKnife.unbind(this);
        ButterKnife.unbind(this);
    }


    @OnClick(R.id.btn_go_map)
    public void onClick() {

        Gson gson = new Gson();

        String str = gson.toJson(companyEntity);
        Intent intent = new Intent(getContext(), MapActivity.class);
        intent.putExtra("company", str);
        startActivity(intent);
    }
}
