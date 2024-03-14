package com.moutamid.antitiktok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.BuildCompat;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.moutamid.antitiktok.databinding.ActivityMainBinding;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Constants.checkApp(this);

        binding.privacy.setOnClickListener(v -> openURL("https://sites.google.com/view/lensappbeta/privacy-policy"));
        binding.website.setOnClickListener(v -> openURL("https://sites.google.com/view/lensappbeta/"));
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            String version = pInfo.versionName;
            binding.version.setText("v" + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
//        findViewById(R.id.layout).setOnClickListener(v -> {
//            if (!Constants.isAccessibilityServiceEnabled(this, getPackageName() + "/.MotionService")){
//                Constants.openAccessibilitySettings(this);
//            }
////            startService();
//        });
        
    }

    private void openURL(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public void startService(){
        // check if the user has already granted
        // the Draw over other apps permission
        if(Settings.canDrawOverlays(this)) {
            // start the service based on the android version
            Intent i = new Intent(this, ForegroundService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(i);
            } else {
                startService(i);
            }
        }else {
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivity(myIntent);
        }
    }

}