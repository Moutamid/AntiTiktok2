package com.moutamid.antitiktok;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.moutamid.antitiktok.databinding.ActivityPermissionBinding;

public class PermissionActivity extends AppCompatActivity {
    ActivityPermissionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPermissionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.next.setEnabled(false);
        
        if (Stash.getBoolean("perm", false) && Constants.isAccessibilityServiceEnabled(this, getPackageName() + "/.MotionService")){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        binding.overlay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.next.setEnabled(isChecked && binding.accessibility.isChecked());
            if (isChecked) {
                if (!Constants.isServiceRunningInForeground(this, ForegroundService.class)) {
                    Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                    startActivity(myIntent);
                }
            }
        });
        binding.accessibility.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.next.setEnabled(isChecked && binding.overlay.isChecked());
            if (isChecked) {
                if (!Constants.isAccessibilityServiceEnabled(this, getPackageName() + "/.MotionService")) {
                    Constants.openAccessibilitySettings(this);
                }
            }
        });

        binding.next.setOnClickListener(v -> {
            Stash.put("perm", true);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

    }
}