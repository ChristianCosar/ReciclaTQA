package com.kodevian.reciclapp.views.fragments.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.model.RecycleItemEntity;
import com.kodevian.reciclapp.model.RecycleItemTrack;
import com.kodevian.reciclapp.views.adapters.CategoriesSearchAdapter;
import com.kodevian.reciclapp.views.adapters.RecycleItemsAdapter;
import com.kodevian.reciclapp.views.commons.CategoriesEditView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.DecimalMin;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by junior on 24/04/16.
 */
public class AddCategorieDialog extends AlertDialog implements Validator.ValidationListener{


    private CategoriesEditView categoriesEditView;
    public RecyclerView gridItems;
    private GridLayoutManager gridLayoutManager;
    private ImageView closeButon;
    private Button buttonAdd;
    @NotEmpty(message = "Este campo no puede ser vacío")
    @DecimalMin(value=0,sequence = 1,message = "Valor mínimo no válido")
    private EditText editText;
    private Validator validator;
    public CategoriesSearchAdapter categoriesListAdapter;
    private static final int NUM_OF_COLUMNS = 4;

    public AddCategorieDialog(Context context, CategoriesEditView categoriesEditView){
        super(context);
        this.categoriesEditView = categoriesEditView;
        initDialog();
    }
    private void initDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_add_categorie, null);
        setView(view);
        gridItems = (RecyclerView) view.findViewById(R.id.grid_items);
        closeButon = (ImageView) view.findViewById(R.id.btn_close);
        editText= (EditText) view.findViewById(R.id.tv_price_categorie);
        buttonAdd = (Button) view.findViewById(R.id.btn_add);
        categoriesListAdapter=new CategoriesSearchAdapter(getContext(),new ArrayList<RecycleItemEntity>());
        gridLayoutManager = new GridLayoutManager(getContext(),NUM_OF_COLUMNS);
        gridItems.setLayoutManager(gridLayoutManager);
        gridItems.setHasFixedSize(true);
        gridItems.setAdapter(categoriesListAdapter);
        categoriesEditView.populateDialogSearch();
        validator = new Validator(this);
        validator.setValidationListener(this);
        closeButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(categoriesListAdapter.getSelectedItems().size()!=0){
                    validator.validate();
                }else{
                    Toast.makeText(getContext(), "Debe seleccionar una categoria", Toast.LENGTH_SHORT).show();
                }
                ;}
        });

    }


    @Override
    public void onValidationSucceeded() {
        RecycleItemTrack recycleItemTrack= new RecycleItemTrack(categoriesListAdapter.getSelectItem().getId(),
                editText.getText().toString());
        categoriesEditView.addCartegory(recycleItemTrack);
        dismiss();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }
}