package com.ghalwash.fingerprint.checker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize BiometricPrompt
        Executor newExecutor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this, newExecutor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                showToast("Fingerprint authentication error: " + errString);
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                showToast("Fingerprint authentication succeeded");
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                showToast("Fingerprint authentication failed");
            }
        });

        // Initialize PromptInfo
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

        // Set up button to start fingerprint authentication
        Button checkFingerprintButton = findViewById(R.id.check_fingerprint_button);
        checkFingerprintButton.setOnClickListener(v -> biometricPrompt.authenticate(promptInfo));

        // Set up button to open website
        Button myWebsiteButton = findViewById(R.id.my_website_button);
        myWebsiteButton.setOnClickListener(v -> openWebsite());
    }

    private void openWebsite() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mrfa0gh.line.pm/"));
        startActivity(browserIntent);
    }

    private void showToast(String message) {
        // Inflate the custom toast layout
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, null);

        // Set the text for the toast message
        TextView text = layout.findViewById(R.id.toast_message);
        text.setText(message);

        // Create the toast and set the custom view
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
