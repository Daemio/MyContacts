package com.example.damian.mycontacts.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damian.mycontacts.MyCallback;
import com.example.damian.mycontacts.R;
import com.example.damian.mycontacts.database.DBGateWay;
import com.example.damian.mycontacts.view.adapter.MyArrayAdapter;
import com.example.damian.mycontacts.view.dialog.ChooseDialog;
import com.example.damian.mycontacts.model.UserData;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public final static String BUNDLE_KEY = "my bundle key";

    final String ATTRIBUTE_NUMBER = "name";
    final String ATTRIBUTE_NAME = "description";
    final String ATTRIBUTE_FAVORITE = "favorite";
    final String ATTRIBUTE_IMAGE = "image";
    final int SELECT_PHOTO = 100;
    final int SHOOT_PHOTO = 200;

    final int DIALOG = 1;

    ListView lvMain;
    TextView tvNnumberOfContacts;
    Button addPhoto;
    Button btnShowAllContacts;
    Button btnShowFavoriteContacts;

    MyArrayAdapter sAdapter;
    List data; //data from db

    @Override
    protected void onResume() {
        super.onResume();
        data = DBGateWay.getAllContacts();
        sAdapter.clear();
        sAdapter.addAll(data);
        tvNnumberOfContacts.setText("Contacts(" + sAdapter.getCount() + ")");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMain = (ListView) findViewById(R.id.listView);
        tvNnumberOfContacts = (TextView) findViewById(R.id.tvContactNumber);

        btnShowAllContacts = (Button) findViewById(R.id.btnAll);
        btnShowFavoriteContacts = (Button) findViewById(R.id.btnFavorite);
        BottomButtonsListener bottomButtonsListener = new BottomButtonsListener();
        btnShowAllContacts.setOnClickListener(bottomButtonsListener);
        btnShowFavoriteContacts.setOnClickListener(bottomButtonsListener);


        addPhoto = (Button) findViewById(R.id.btnUploadPhoto);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseDialog();
            }
        });


        // массив данных
        data = new ArrayList<UserData>();
       /*
        data.add(new UserData("Ann", null, true));
        data.add(new UserData("Andrew", null, true));
        data.add(new UserData("Damian", null, false));
        data.add(new UserData("Den", null, true));
        data.add(new UserData("Sam",null, true));
        data.add(new UserData("Bill", null, false));
        data.add(new UserData("Tom", null, true));
        data.add(new UserData("Caren", null, false));
        data.add(new UserData("Michael",null, false));
        data.add(new UserData("Stew", null, true));
        data.add(new UserData("Rob", null, false));
        data.add(new UserData("Jay", null, true));
        data.add(new UserData("Helen", null, false));
        data.add(new UserData("Michael", null, true));
        */


        data = DBGateWay.getAllContacts();

        // создаем адаптер
        sAdapter = new MyArrayAdapter(this, R.layout.item);
        sAdapter.addAll(data); //добавим элементы из нашего контейнера


        sAdapter.setMyCallback(new MyCallback() {
            @Override
            public void callBackItemDeleted(UserData data) {
                sAdapter.remove(data);
                DBGateWay.deleteContact(data);
            }

            @Override
            public void callBackItemFavoriteStateChanged(UserData data) {
                DBGateWay.editContact(data);
            }
        });
        // присваиваем адаптер
        lvMain.setAdapter(sAdapter);
        tvNnumberOfContacts.setText("Contacts(" + sAdapter.getCount() + ")");
    }

    private void showChooseDialog() {
        final ChooseDialog dialog = new ChooseDialog(this, R.style.FullHeightDialog);
        dialog.setClick(new ChooseDialog.OnClick() {
                            @Override
                            public void onClick(int actionCode) {
                                Intent intent;
                                if (actionCode == 1) {
                                    intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(intent, SHOOT_PHOTO);
                                } else {
                                    //call gallery
                                    intent = new Intent(Intent.ACTION_PICK);
                                    intent.setType("image/*");
                                    startActivityForResult(intent, SELECT_PHOTO);
                                }
                                dialog.dismiss();
                            }
                        }

        );
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = imageReturnedIntent.getData();
                if (selectedImage.toString() != null) {
                    Intent myIntent = new Intent(MainActivity.this, AddContactActivity.class);
                    myIntent.putExtra(BUNDLE_KEY, selectedImage.toString());
                    startActivity(myIntent);
                } else {
                    Toast.makeText(this, "selectedImage == null", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (requestCode == SHOOT_PHOTO && resultCode == RESULT_OK) {
            loadCameraPhoto(imageReturnedIntent);
        }
    }

    protected void loadCameraPhoto(Intent data) {
        Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");
        String uri;
        if (bitmap != null) {
            uri = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "", "");
        } else {
            uri = String.valueOf(data.getData());
        }
        Intent myIntent = new Intent(MainActivity.this, AddContactActivity.class);
        myIntent.putExtra(BUNDLE_KEY, uri);
        startActivity(myIntent);
    }

    private class BottomButtonsListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnAll:
                    data = DBGateWay.getAllContacts();
                    sAdapter.clear();
                    sAdapter.addAll(data);
                    tvNnumberOfContacts.setText("Contacts(" + sAdapter.getCount() + ")");
                    break;
                case R.id.btnFavorite:
                    data = DBGateWay.getFavoriteContacts();
                    sAdapter.clear();
                    sAdapter.addAll(data);
                    tvNnumberOfContacts.setText("Contacts(" + sAdapter.getCount() + ")");
                    break;
            }
        }
    }
}

