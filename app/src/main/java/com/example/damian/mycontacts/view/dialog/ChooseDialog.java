package com.example.damian.mycontacts.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.damian.mycontacts.R;

/**
 * Created by Admin on 05.10.2015.
 */
public class ChooseDialog extends Dialog implements View.OnClickListener {

    private OnClick click;

    public ChooseDialog(Context context) {
        super(context);
    }

    public ChooseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public void setClick(OnClick click){
        this.click = click;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_dialog);
        findViewById(R.id.tv_gallery).setOnClickListener(this);
        findViewById(R.id.tv_camera).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_gallery:
                if (click != null) {
                    click.onClick(0);
                }
                break;
            case R.id.tv_camera:
                if (click != null) {
                    click.onClick(1);
                }
                break;
        }
    }

    public interface OnClick{
        public void onClick(int actionCode);
    }
}
