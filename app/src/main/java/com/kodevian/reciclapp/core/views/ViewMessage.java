package com.kodevian.reciclapp.core.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.cons.Messages;
import com.kodevian.reciclapp.util.Util_Fonts;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Leo on 12/01/2016.
 */
public class ViewMessage extends FrameLayout {

    protected View view;
    @Bind(R.id.tv_load)
    TextView tvLoad;
    @Bind(R.id.frame_load)
    FrameLayout frameLoad;
    @Bind(R.id.tv_no_gps)
    TextView tvNoGps;
    @Bind(R.id.tv_active_gps)
    TextView tvActiveGps;
    @Bind(R.id.frame_gps)
    FrameLayout frameGps;
    @Bind(R.id.tv_no_wifi)
    TextView tvNoWifi;
    @Bind(R.id.tv_instructions_wifi)
    TextView tvInstructionsWifi;
    @Bind(R.id.frame_no_network)
    FrameLayout frameNoNetwork;
    @Bind(R.id.tv_no_data)
    TextView tvNoData;
    @Bind(R.id.frame_no_data)
    FrameLayout frameNoData;
    @Bind(R.id.tv_text_message)
    TextView tvTextMessage;
    @Bind(R.id.frame_message)
    FrameLayout frameMessage;

    public ViewMessage(Context context) {
        super(context);
        initView();
    }



    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.layout_message, this, true);
        ButterKnife.bind(this, view);

        frameGps.setVisibility(GONE);
        frameLoad.setVisibility(GONE);
        frameMessage.setVisibility(GONE);
        frameNoData.setVisibility(GONE);
        frameNoNetwork.setVisibility(GONE);

        tvActiveGps.setTypeface(Util_Fonts.setFontNormal(getContext()));
        tvInstructionsWifi.setTypeface(Util_Fonts.setFontNormal(getContext()));
        tvLoad.setTypeface(Util_Fonts.setFontNormal(getContext()));
        tvNoData.setTypeface(Util_Fonts.setFontNormal(getContext()));
        tvNoGps.setTypeface(Util_Fonts.setFontNormal(getContext()));
        tvNoWifi.setTypeface(Util_Fonts.setFontNormal(getContext()));
        tvTextMessage.setTypeface(Util_Fonts.setFontNormal(getContext()));
    }


    public void showMessage(int type){
        frameGps.setVisibility(GONE);
        frameLoad.setVisibility(GONE);
        frameMessage.setVisibility(GONE);
        frameNoData.setVisibility(GONE);
        frameNoNetwork.setVisibility(GONE);
        switch (type){
            case Messages.LOAD:
                frameLoad.setVisibility(VISIBLE);
                break;
            case Messages.NO_DATA:
                frameNoData.setVisibility(VISIBLE);
                break;
            case Messages.NO_GPS:
                frameGps.setVisibility(VISIBLE);
                break;
            case Messages.NO_RED:
                frameNoNetwork.setVisibility(VISIBLE);
                break;
            case Messages.TEXT:
                frameMessage.setVisibility(VISIBLE);
                break;
        }
    }

    public void showMessage(int type, String text){
        frameGps.setVisibility(GONE);
        frameLoad.setVisibility(GONE);
        frameMessage.setVisibility(GONE);
        frameNoData.setVisibility(GONE);
        frameNoNetwork.setVisibility(GONE);
        switch (type){
            case Messages.LOAD:
                frameLoad.setVisibility(VISIBLE);
                break;
            case Messages.NO_DATA:
                frameNoData.setVisibility(VISIBLE);
                break;
            case Messages.NO_GPS:
                frameGps.setVisibility(VISIBLE);
                break;
            case Messages.NO_RED:
                frameNoNetwork.setVisibility(VISIBLE);
                break;
            case Messages.TEXT:
                frameMessage.setVisibility(VISIBLE);
                if(!text.isEmpty())
                    tvTextMessage.setText(text);
                break;
        }
    }

    public void hideMessage() {
        frameGps.setVisibility(GONE);
        frameLoad.setVisibility(GONE);
        frameMessage.setVisibility(GONE);
        frameNoData.setVisibility(GONE);
        frameNoNetwork.setVisibility(GONE);
    }
}
