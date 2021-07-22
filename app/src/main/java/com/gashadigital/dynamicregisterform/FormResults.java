package com.gashadigital.dynamicregisterform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class FormResults extends AppCompatActivity {

    TextView fullName, genderType, age, email, tel, dep, pass;
    ImageView imgProf;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_results);

        fullName = findViewById(R.id.full_name);
        imgProf = findViewById(R.id.img_profile);
        genderType = findViewById(R.id.gender);
        age = findViewById(R.id.birth_date);
        email = findViewById(R.id.email_view);
        tel = findViewById(R.id.tel_view);
        dep = findViewById(R.id.dep);
        pass = findViewById(R.id.pass);

        Intent intent = getIntent();
//        Bundle extras = getIntent().getExtras();

        String filePath = Environment.getDataDirectory() + intent.getStringExtra("imgSrc");
        File imgFile = new File(filePath);
        if(imgFile.exists()){
            bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgProf.setImageBitmap(bitmap);
        }

        Toast.makeText(this, filePath.toString(), Toast.LENGTH_LONG).show();

        fullName.setText(intent.getStringExtra("fullName"));
        genderType.setText(intent.getStringExtra("gender"));
        age.setText(intent.getStringExtra("birth"));
        email.setText(intent.getStringExtra("email"));
        tel.setText(intent.getStringExtra("tel"));
        dep.setText(intent.getStringExtra("dep"));
        pass.setText(intent.getStringExtra("pass"));

    }

}