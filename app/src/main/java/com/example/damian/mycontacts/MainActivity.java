package com.example.damian.mycontacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damian.mycontacts.adapter.MyArrayAdapter;
import com.example.damian.mycontacts.dialog.ChooseDialog;
import com.example.damian.mycontacts.model.UserData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public final static String BUNDLE_KEY = "key";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMain = (ListView) findViewById(R.id.listView);
        tvNnumberOfContacts = (TextView) findViewById(R.id.tvContactNumber);

        addPhoto = (Button) findViewById(R.id.btnUploadPhoto);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseDialog();
            }
        });


        // массив данных
        ArrayList data = new ArrayList<UserData>();
        data.add(new UserData("Ann", "1", true, null));
        data.add(new UserData("Andrew", "2", true, null));
        data.add(new UserData("Damian", "3", false, null));
        data.add(new UserData("Den", "4", true, null));
        data.add(new UserData("Sam", "5", false, null));
        data.add(new UserData("Bill", "6", true, null));
        data.add(new UserData("Tom", "7", true, null));
        data.add(new UserData("Caren", "8", false, null));
        data.add(new UserData("Michael", "11", false, null));
        data.add(new UserData("Stew", "13", false, null));
        data.add(new UserData("Rob", "14", true, null));
        data.add(new UserData("Jay", "16", true, null));
        data.add(new UserData("Helen", "17", false, null));
        data.add(new UserData("Michael", "31", false, null));

        // создаем адаптер
        MyArrayAdapter sAdapter = new MyArrayAdapter(this, R.layout.item);
        sAdapter.addAll(data); //добавим элементы из нашего контейнера

        // присваиваем адаптер
        lvMain.setAdapter(sAdapter);
        tvNnumberOfContacts.setText("Contacts(" + sAdapter.getCount() + ")");
    }


    private void showChooseDialog() {
        final ChooseDialog dialog = new ChooseDialog(this,R.style.FullHeightDialog);
        dialog.setClick(new ChooseDialog.OnClick() {
            @Override
            public void onClick(int actionCode) {
                Intent intent;
                if (actionCode == 1) {
                    //call camera
                    //Toast.makeText(getApplicationContext(),"Camera",Toast.LENGTH_LONG).show();
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyContactsPictures");
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                    File file = new File(directory.getPath() + "/" + "photo_"
                            + System.currentTimeMillis() + ".jpg");
                    Uri photoUri = Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intent, SHOOT_PHOTO);

                } else {
                    //call gallery
                    //Toast.makeText(getApplicationContext(),"Gallery",Toast.LENGTH_LONG).show();
                    intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, SELECT_PHOTO);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

//    @Override
//    protected Dialog onCreateDialog(int id) {
//        AlertDialog.Builder adBilder = new AlertDialog.Builder(this);
//        if (id == DIALOG) {
//            adBilder.setTitle("Select image source");
//            final CharSequence items[] = {"From Camera", "From SD Card"};
//            adBilder.setItems(items, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            });
//        }
//        return adBilder.create();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if ((requestCode == SELECT_PHOTO) || (requestCode == SHOOT_PHOTO)) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = imageReturnedIntent.getData();
                if (selectedImage.toString() != null) {
                    Intent myIntent = new Intent(MainActivity.this, AddContactActivity.class);
                    myIntent.putExtra(BUNDLE_KEY, selectedImage.toString());
                    startActivity(myIntent);
                }else {
                    Toast.makeText(this, "selectedImage == null", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

