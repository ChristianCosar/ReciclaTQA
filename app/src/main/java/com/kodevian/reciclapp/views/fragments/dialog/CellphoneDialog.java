package com.kodevian.reciclapp.views.fragments.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kodevian.reciclapp.R;
import com.kodevian.reciclapp.util.Util_Fonts;
import com.kodevian.reciclapp.views.commons.EditCompanyView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

/**
 * Created by junior on 24/04/16.
 */
public class CellphoneDialog extends AlertDialog implements Validator.ValidationListener {
    private Validator validator;
    private EditCompanyView editCompanyView;
    private String field;

    @NotEmpty(message = "Este campo no puede ser vacío", sequence = 1)
    @Length(min = 9, max = 9, message = "Número de celular inválido")
    EditText editText_parameter;
    Button btn_add_control;
    public CellphoneDialog(Context context, EditCompanyView editCompanyView,String field){
        super(context);
        this.editCompanyView = editCompanyView;
        this.field=field;
        initDialog();
    }
    private void initDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_cellphone, null);
        setView(view);

        btn_add_control = (Button) view.findViewById(R.id.btn_edit);
        editText_parameter = (EditText) view.findViewById(R.id.tv_company_cellphone);
        editText_parameter.setTypeface(Util_Fonts.setFontLight(getContext()));
        validator = new Validator(this);
        validator.setValidationListener(this);
        editText_parameter.setText(field.substring(3,field.length()));
        btn_add_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });


    }



    @Override
    public void onValidationSucceeded() {
        this.dismiss();
        editCompanyView.editCellphone(editText_parameter.getText().toString());


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