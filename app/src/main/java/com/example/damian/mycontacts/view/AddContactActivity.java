package com.example.damian.mycontacts.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.damian.mycontacts.CameraUtils;
import com.example.damian.mycontacts.R;

/**
 * Created by Admin on 05.10.2015.
 */
public class AddContactActivity extends Activity implements View.OnClickListener {
    Button btnDeleteText;
    Button btnCancel;
    Button btnSend;
    EditText etEditDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ImageView imgPhoto = (ImageView) findViewById(R.id.imageView);
        String imgUlr = getIntent().getStringExtra(MainActivity.BUNDLE_KEY);
        CameraUtils.setPic(imgPhoto, imgUlr, this);

        btnDeleteText = (Button) findViewById(R.id.btnDeleteText);
        btnCancel = (Button) findViewById(R.id.tab_btn_cancel);
        btnSend = (Button) findViewById(R.id.tab_btn_send);
        btnSend.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnDeleteText.setOnClickListener(this);
        etEditDescription = (EditText) findViewById(R.id.etEditDescription);
        etEditDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etEditDescription.getText().toString()!=""){
                    btnDeleteText.setEnabled(true);
                }else{
                    btnDeleteText.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDeleteText:
                //delete text
                etEditDescription.setText("");
                btnDeleteText.setEnabled(false);
                //Toast.makeText(this,"pressed",Toast.LENGTH_LONG).show();
                break;
            case R.id.tab_btn_cancel:
                finish();
                break;
            case R.id.tab_btn_send:
                //add data todatabase
                finish();
                break;
        }
    }
}
