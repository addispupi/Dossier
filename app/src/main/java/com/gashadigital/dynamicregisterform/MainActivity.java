package com.gashadigital.dynamicregisterform;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button btnSubmit;
    ImageButton btnPickImg;
    ImageView imgView;
    Uri userUri;
    Bitmap bitmap;
    EditText firstName, secondName, email, telephone, password;
    RadioGroup genderType;
    RadioButton gender;
    DatePicker dateOfBirth;
    Spinner comboBox;
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);

        // Set Fields
        btnSubmit = findViewById(R.id.btn_submit);
        imgView = findViewById(R.id.img_profile);
        btnPickImg = findViewById(R.id.btn_upload);
        firstName = findViewById(R.id.first_name);
        secondName = findViewById(R.id.second_name);
        genderType = findViewById(R.id.gender);
        dateOfBirth = findViewById(R.id.date_of_birth);
        email = findViewById(R.id.email);
        telephone = findViewById(R.id.tel);
        password = findViewById(R.id.pass);
        comboBox = findViewById(R.id.department);

        btnPickImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkFields() == false){
                    Toast.makeText(MainActivity.this,
                                    "All Fields must be filled correctly, Please review the form again !",
                                    Toast.LENGTH_LONG).show();
                }
                else {
                    String fullName = firstName.getText().toString() + " " + secondName.getText().toString();
                    int selectedType = genderType.getCheckedRadioButtonId();
                    gender = findViewById(selectedType);
                    int month = dateOfBirth.getMonth();
                    int day = dateOfBirth.getDayOfMonth();
                    int year = dateOfBirth.getYear();
                    String birthDte = (day+"-"+month+"-"+year);
                    String selectedDep = comboBox.getSelectedItem().toString();
                    String emailTxt = email.getText().toString();
                    String tel = telephone.getText().toString();
                    String pass = password.getText().toString();

                    Intent intent = new Intent(MainActivity.this, FormResults.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fullName", fullName);
                    bundle.putString("gender", gender.getText().toString());
                    bundle.putString("birth", birthDte);
                    bundle.putString("dep", selectedDep);
                    bundle.putString("email", emailTxt);
                    bundle.putString("tel", tel);
                    bundle.putString("pass", pass);
//                    intent.setData(userUri);
                    bundle.putString("imgSrc", userUri.toString());

                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
    }

    private boolean checkFields() {
        int checkedID = genderType.getCheckedRadioButtonId();
        if (firstName.getText().toString().trim().length() < 2 ||
            secondName.getText().toString().trim().length() < 2 ||
            checkedID < 0 ||
            userUri.toString().length() < 1 ||
            email.getText().toString().trim().length() < 8  ||
            telephone.getText().toString().trim().length() < 11 ||
            password.getText().toString().trim().length() < 9 ){
            return false;
        }
        else
        return true;
    }

    private void openGallery() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Profile Picture");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE){
            userUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), userUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imgView.setImageURI(userUri);
        }
    }

    public void setLanguage(Activity context, String lan) {
        Locale locale = new Locale(lan);
        Configuration conf = new Configuration();
        conf.locale = locale;
        context.getBaseContext().getResources().updateConfiguration(conf, context.getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lan);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences pref = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = pref.getString("My_Lang","am");
        setLanguage(this,language);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.language, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.am:
                Toast.makeText(this,"am", Toast.LENGTH_LONG).show();
                setLanguage(this,"am");
                this.setContentView(R.layout.activity_main);
                return true;
            case R.id.en:
                Toast.makeText(this,"en", Toast.LENGTH_LONG).show();
                setLanguage(this,"en");
                this.setContentView(R.layout.activity_main);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}