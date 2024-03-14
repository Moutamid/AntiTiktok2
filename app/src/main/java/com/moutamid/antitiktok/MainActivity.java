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

import com.fxn.stash.Stash;
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

        binding.sensory.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Stash.put("sensory", isChecked);
        });
        binding.counter.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Stash.put("counter", isChecked);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.counter.setChecked(Stash.getBoolean("counter", true));
        binding.sensory.setChecked(Stash.getBoolean("sensory", true));
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