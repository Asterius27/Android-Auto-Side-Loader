package com.example.androidautosideloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button selectAPK = findViewById(R.id.select_apk_button);
        Button install = findViewById(R.id.install_button);

        selectAPK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, 200);
            }
        });

        install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                installAPK();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
        }
    }

    private void installAPK() {
        if (uri != null) {
            Intent intent;
            intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
            intent.setData(uri); // getUri(file)
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
            intent.putExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME, "com.android.vending");
            getApplicationContext().startActivity(intent);
        }
    }

    private Uri getUri(File file) {
        return FileProvider.getUriForFile(getApplicationContext(), "sksa.aa.customapps.fileProvider", file);
    }

}