package com.example.damian.mycontacts;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        imgPhoto.setImageURI(Uri.parse(imgUlr));

        btnDeleteText = (Button) findViewById(R.id.btnDeleteText);
        btnCancel = (Button) findViewById(R.id.tab_btn_cancel);
        btnSend = (Button) findViewById(R.id.tab_btn_send);
        btnSend.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnDeleteText.setOnClickListener(this);
        etEditDescription = (EditText) findViewById(R.id.etEditDescription);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDeleteText:
                //delete text
                etEditDescription.setText("");
                Toast.makeText(this,"pressed",Toast.LENGTH_LONG).show();
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
