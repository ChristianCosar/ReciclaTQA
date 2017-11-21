package com.kodevian.reciclapp.core;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.core.cons.Messages;
import com.kodevian.reciclapp.core.views.ViewMessage;


/**
 * Created by Leo on 12/01/2016.
 */
public abstract class BaseFragment extends Fragment {

    public int idContainer; //container
    public int idContainerLoader; //container
    public int idLayout; //layout
    //for paginations
    public String next = "";
    public String previous = "";
    //name last fragment
    public String lastTag = "";
    //for show loading
    public boolean isLoading = false;
    public boolean isReady = false;
    public boolean active = true;

    private BaseActivity mainActivity;
    public ViewMessage viewMessage;

    //abstract methods

    /**
     * Actions to top bar
     */
    protected abstract void initActionBar();

    /**
     * Actions when returns to show fragment
     */
    protected abstract void onBackActions();


    /**
     * Actions to view
     */
    protected abstract void initView();

    /**
     * Actions to view
     */
    protected abstract void createEntities();


    /**
     * Comunicate with services to presenter
     */
    protected abstract void requestServices();

    //override methods


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mainActivity = (BaseActivity) activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View localView = inflater.inflate(getIdLayout(), viewGroup, false);
        localView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        return localView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createEntities();
        initActionBar();
        initView();
        ViewGroup viewGroup = getLayoutLoader();
        if (viewGroup != null) {
            if (viewMessage != null) {
                viewGroup.addView(viewMessage);
            } else {
                viewMessage = new ViewMessage(getMainActivity());
                viewGroup.addView(viewMessage);
            }
            if (!isOnline()) {
                viewMessage.showMessage(Messages.NO_RED);
            }
        }
        activeActionsBack();
        requestServices();
    }


    public void searchEditTextAndImplementsKey(final View view) {
        if ((view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View view, MotionEvent event) {
                    view.setFocusableInTouchMode(true);
                    view.requestFocus();
                    view.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                                if (keyCode == KeyEvent.KEYCODE_BACK) {
                                    onBackActions();
                                    deleteFragment();
                                    return true;
                                }
                            }
                            return false;
                        }


                    });
                    return false;
                }
            });
            ((EditText) view).setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        EditText editText= (EditText) getLayoutLoader().findViewById(v.getNextFocusForwardId()) ;
                        //EditText editText = (EditText) v.focusSearch(View.FOCUS_DOWN);
                        editText.setFocusableInTouchMode(true);
                        editText.requestFocus();
                        editText.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                                        onBackActions();
                                        deleteFragment();
                                        return true;
                                    }
                                }
                                return false;
                            }


                        });
                        return false;
                    }
                    return false;
                }
            });

        }else{
            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    View innerView = ((ViewGroup) view).getChildAt(i);
                    searchEditTextAndImplementsKey(innerView);
                }
            }
        }



    }
    //our methods


    /**
     * Check network status
     *
     * @return true if it's online
     */
    public boolean isOnline() {
        NetworkInfo localNetworkInfo = ((ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return (localNetworkInfo != null) && (localNetworkInfo.isConnectedOrConnecting());
    }


    public void OnbackAc(BaseFragment baseFragment){
        baseFragment.onBackActions();
    }
    /**
     * Close keyboard
     */
    public void closeKeyboard() {
        getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * Delete a fragment
     */
    public void deleteFragment() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.setCustomAnimations(R.anim.left_right, R.anim.right_left);
        trans.remove(this);
        trans.commit();
        active = true;
        closeKeyboard();
        if (!lastTag.isEmpty()) {
            BaseFragment baseFragment = (BaseFragment) manager.findFragmentByTag(lastTag);
            if (baseFragment != null)
                baseFragment.onBackActions();
        }
        manager.popBackStack();
    }


    /**
     * layout with container to Message
     */
    public ViewGroup getLayoutLoader() {
        return (ViewGroup) getView().findViewById(getIdContainer());
    }


    /**
     * Activate button back native
     */

    public void activeActionsBack() {
        this.getView().setFocusableInTouchMode(true);
        this.getView().requestFocus();
        this.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        deleteFragment();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    //getters and setters

    public int getIdContainer() {
        return idContainer;
    }

    public void setIdContainer(int idContainer) {
        this.idContainer = idContainer;
    }

    public int getIdContainerLoader() {
        return idContainerLoader;
    }

    public void setIdContainerLoader(int idContainerLoader) {
        this.idContainerLoader = idContainerLoader;
    }

    public int getIdLayout() {
        return idLayout;
    }

    public void setIdLayout(int idLayout) {
        this.idLayout = idLayout;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getLastTag() {
        return lastTag;
    }

    public void setLastTag(String lastTag) {
        this.lastTag = lastTag;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public BaseActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(BaseActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
