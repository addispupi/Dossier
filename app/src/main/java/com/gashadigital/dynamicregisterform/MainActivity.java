package com.gashadigital.dynamicregisterform;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FormResults.class));
            }
        });
    }

    public void setLanguage(Context context, String lan) {
        Locale locale = new Locale(lan);
        Configuration conf = new Configuration();
        conf.locale = locale;
        context.getResources().updateConfiguration(conf, context.getResources().getDisplayMetrics());
        recreate();
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
                setLanguage(this, "am");
                return true;
            case R.id.en:
                Toast.makeText(this,"en", Toast.LENGTH_LONG).show();
                setLanguage(this, "en");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}