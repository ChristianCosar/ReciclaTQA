package com.kodevian.reciclapp.views.fragments.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.model.RecycleItemCategorieTrack;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.views.commons.CategoriesEditView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.DecimalMin;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

/**
 * Created by junior on 24/04/16.
 */
public class EditPriceDialog extends AlertDialog implements Validator.ValidationListener {
    private Validator validator;
    private CategoriesEditView categoriesEditView;

    @NotEmpty(message = "Este campo no puede ser vacío")
    @DecimalMin(value=0,sequence = 1,message = "Valor mínimo no válido")
    EditText editText_parameter;
    Button btn_edit;
    RecycleItemCategorieTrack recycleItemCategorieTrack;

    public EditPriceDialog(Context context, CategoriesEditView categoriesEditView,RecycleItemCategorieTrack recycleItemCategorieTrack){
        super(context);
        this.categoriesEditView = categoriesEditView;
        this.recycleItemCategorieTrack =recycleItemCategorieTrack;
        initDialog();
    }
    private void initDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_edit_price, null);
        setView(view);

        btn_edit = (Button) view.findViewById(R.id.btn_edit);
        editText_parameter = (EditText) view.findViewById(R.id.tv_price_categorie);
        editText_parameter.setTypeface(Util_Fonts.setFontLight(getContext()));
        editText_parameter.setText(recycleItemCategorieTrack.getPrice());
        validator = new Validator(this);
        validator.setValidationListener(this);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });


    }



    @Override
    public void onValidationSucceeded() {
        this.dismiss();
        recycleItemCategorieTrack.setPrice(editText_parameter.getText().toString());
        categoriesEditView.editPrice(recycleItemCategorieTrack);


    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }

}