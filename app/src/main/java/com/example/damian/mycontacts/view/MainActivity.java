package com.example.damian.mycontacts.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damian.mycontacts.MyCallback;
import com.example.damian.mycontacts.R;
import com.example.damian.mycontacts.database.DBGateWay;
import com.example.damian.mycontacts.model.UserData;
import com.example.damian.mycontacts.view.adapter.MyArrayAdapter;
import com.example.damian.mycontacts.view.dialog.ChooseDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public final static String BUNDLE_KEY = "my bundle key";
    public final static String BUNDLE_KEY_MODE = "mode";

    final int SELECT_PHOTO = 100;
    final int SHOOT_PHOTO = 200;

    public final static int MODE_NEW_CONTACT = 123;
    public final static int MODE_EDIT_CONTACT = 321;

    final int DIALOG = 1;

    ListView lvMain;
    TextView tvNnumberOfContacts;
    Button addPhoto;
    RadioGroup rgBottom;

    MyArrayAdapter sAdapter;
    List data; //data from db

    @Override
    protected void onResume() {
        super.onResume();
        sAdapter.clear();
        data = DBGateWay.getAllContacts();
        sAdapter.addAll(data);
        rgBottom.check(R.id.rbAll);
        tvNnumberOfContacts.setText("Contacts(" + sAdapter.getCount() + ")");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMain = (ListView) findViewById(R.id.listView);

        tvNnumberOfContacts = (TextView) findViewById(R.id.tvContactNumber);
        rgBottom = (RadioGroup) findViewById(R.id.rgBottom);
        rgBottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbAll:
                        sAdapter.clear();
                        data = DBGateWay.getAllContacts();
                        sAdapter.addAll(data);
                        tvNnumberOfContacts.setText("Contacts(" + sAdapter.getCount() + ")");
                        break;
                    case R.id.rbFavorite:
                        sAdapter.clear();
                        data = DBGateWay.getFavoriteContacts();
                        sAdapter.addAll(data);
                        tvNnumberOfContacts.setText("Favorite(" + sAdapter.getCount() + ")");
                        break;
                    case R.id.rbExit:
                        finish();
                        break;
                }
            }
        });

        addPhoto = (Button) findViewById(R.id.btnUploadPhoto);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseDialog();
            }
        });


        // массив данных
        data = new ArrayList<UserData>();

        data = DBGateWay.getAllContacts();

        // создаем адаптер
        sAdapter = new MyArrayAdapter(this, R.layout.item);
        sAdapter.addAll(data); //добавим элементы из нашего контейнера


        sAdapter.setMyCallback(new MyCallback() {
            @Override
            public void callBackItemDeleted(UserData data) {
                sAdapter.remove(data);
                DBGateWay.deleteContact(data);
                String s = tvNnumberOfContacts.getText().toString();
                int numberOfContacts = sAdapter.getCount();
                s = s.replaceFirst("[(][0123456789]*[)]", "(" + numberOfContacts + ")");
                tvNnumberOfContacts.setText(s);
            }

            @Override
            public void callBackItemFavoriteStateChanged(UserData data, boolean isFavorite) {
                data.setFavorite(isFavorite);
                DBGateWay.editContact(data);
            }

            @Override
            public void callBackListItemSelected(UserData data) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                intent.putExtra(BUNDLE_KEY_MODE, MODE_EDIT_CONTACT);
                intent.putExtra(BUNDLE_KEY, data);
                startActivity(intent);
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
                                if (actionCode == 1) {//camera
                                    intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File("/sdcard/tmp")));
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
                    myIntent.putExtra(BUNDLE_KEY_MODE, MODE_NEW_CONTACT);
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

    protected void loadCameraPhoto(Intent imageReturnedIntent) {
        //Bundle bundle = data.getExtras();
        //Bitmap bitmap = (Bitmap) bundle.get("data");
        //String uri;
        //uri = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "", "");

        //Uri full = data.getData();
        //Log.d("mytag",""+(full==null));
        //Log.d("mytag",""+ Utils.hasImageCaptureBug());
        Uri uri=null;
        File fi = new File("/sdcard/tmp");
        try {
            uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), fi.getAbsolutePath(), null, null));
            if (!fi.delete()) {
                Log.i("logMarker", "Failed to delete " + fi);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(uri!=null) {
            Intent myIntent = new Intent(MainActivity.this, AddContactActivity.class);
            myIntent.putExtra(BUNDLE_KEY_MODE, MODE_NEW_CONTACT);
            myIntent.putExtra(BUNDLE_KEY, uri.toString());
            startActivity(myIntent);
        }
    }
}
