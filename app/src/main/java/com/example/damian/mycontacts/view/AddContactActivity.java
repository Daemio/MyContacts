package com.example.damian.mycontacts.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.damian.mycontacts.R;
import com.example.damian.mycontacts.database.DBGateWay;
import com.example.damian.mycontacts.model.UserData;
import com.squareup.picasso.Picasso;

/**
 * Created by Admin on 05.10.2015.
 */
public class AddContactActivity extends Activity implements View.OnClickListener {
    Button btnDeleteText;
    Button btnCancel;
    Button btnSend;
    EditText etEditDescription;
    String imgUrl;
    UserData data;
    int mode; //add or edit
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ImageView imgPhoto = (ImageView) findViewById(R.id.imageView);
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

       mode = getIntent().getIntExtra(MainActivity.BUNDLE_KEY_MODE, 0);


        if(mode==MainActivity.MODE_NEW_CONTACT) {
            imgUrl = getIntent().getStringExtra(MainActivity.BUNDLE_KEY);
            //CameraUtils.setPic(imgPhoto, imgUrl, this);
            Picasso.with(this).load(imgUrl).into(imgPhoto);
        }else{
            data = (UserData) getIntent().getSerializableExtra(MainActivity.BUNDLE_KEY);
            btnSend.setText("Update");
            etEditDescription.setText(data.getDescription());
            imgUrl = data.getImagePath();
            //CameraUtils.setPic(imgPhoto,imgUrl,this);
            Picasso.with(this).load(imgUrl).into(imgPhoto);
        }
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
                if(mode==MainActivity.MODE_NEW_CONTACT) {
                    data = new UserData(etEditDescription.getText().toString(), imgUrl, false);//initially not fav
                    DBGateWay.addContact(data);
                }else{
                    data.setDescription(etEditDescription.getText().toString());
                    DBGateWay.editContact(data);
                }
                finish();
                break;
        }
    }
}
