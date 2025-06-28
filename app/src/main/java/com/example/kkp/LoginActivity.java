package com.example.kkp;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private ImageView showPasswordIcon;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inisialisasi view
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        showPasswordIcon = findViewById(R.id.showPasswordIcon);
        Button loginButton = findViewById(R.id.loginButton);

        // Toggle show/hide password saat icon mata diklik
        showPasswordIcon.setOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible) {
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                showPasswordIcon.setImageResource(R.drawable.ic_visibility); // icon mata terbuka
            } else {
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showPasswordIcon.setImageResource(R.drawable.ic_visibility_off); // icon mata tertutup
            }
            // Set cursor tetap di akhir teks
            passwordEditText.setSelection(passwordEditText.length());
        });

        // Handle tombol login
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString();

            if (username.equals("haidar") && password.equals("123")) {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();

                // Pindah ke DashboardActivity
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish(); // agar tidak bisa kembali ke halaman login saat tekan back
            } else {
                Toast.makeText(this, "Username or Password is incorrect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
