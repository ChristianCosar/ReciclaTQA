package com.kodevian.reciclapp.views.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.BaseFragment;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.views.activities.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 08/04/16.
 */
public class UsFragment extends BaseFragment {


    @Bind(R.id.action_left)
    ImageButton actionLeft;
    @Bind(R.id.action_middle)
    TextView actionMiddle;
    @Bind(R.id.action_rigth_off)
    ImageButton actionRigthOff;
    @Bind(R.id.action_right)
    ImageButton actionRight;
    @Bind(R.id.background)
    ImageView background;
    @Bind(R.id.tv_us_title)
    TextView tvUsTitle;
    @Bind(R.id.tv_us_info)
    TextView tvUsInfo;
    @Bind(R.id.tv_who_happen_title)
    TextView tvWhoHappenTitle;
    @Bind(R.id.logo)
    ImageView logo;
    @Bind(R.id.tv_who_happen_info)
    TextView tvWhoHappenInfo;

    /**
     * Constructor
     */
    public UsFragment() {
        setIdLayout(R.layout.layout_us);
        setIdContainer(R.id.frame_container);
    }

    /**
     * @return Fragment with container and layout
     */
    public static UsFragment newInstance() {
        UsFragment landingFragment = new UsFragment();
        return landingFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initActionBar() {
        actionMiddle.setText("Información");
        actionRight.setVisibility(View.GONE);
        actionLeft.setImageResource(R.drawable.ic_menu);
    }

    @Override
    protected void onBackActions() {
        active = true;
        activeActionsBack();
    }


    @Override
    protected void initView() {
        tvUsTitle.setTypeface(Util_Fonts.setFontNormal(getMainActivity()));
        tvWhoHappenTitle.setTypeface(Util_Fonts.setFontNormal(getMainActivity()));
        tvUsInfo.setTypeface(Util_Fonts.setFontLight(getMainActivity()));
        tvWhoHappenInfo.setTypeface(Util_Fonts.setFontLight(getMainActivity()));

    }

    @Override
    protected void createEntities() {

    }

    @Override
    protected void requestServices() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void deleteFragment() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
    }




    @OnClick(R.id.action_left)
    public void onClick() {
        ((MainActivity) getMainActivity()).openMenu();
    }
}
