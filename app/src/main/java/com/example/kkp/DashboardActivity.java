package com.example.kkp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DashboardActivity extends AppCompatActivity {

    CardView assetCard, rentalCard;
    LinearLayout profileButton, logoutButton; // Tambahan

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Inisialisasi card view
        assetCard = findViewById(R.id.assetCard);
        rentalCard = findViewById(R.id.rentalCard);

        // Listener untuk navigasi
        assetCard.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, AssetActivity.class);
            startActivity(intent);
        });

        rentalCard.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, RentalActivity.class);
            startActivity(intent);
        });

        // Inisialisasi tombol toolbar bawah
        profileButton = findViewById(R.id.profileButton); // pastikan id di XML
        logoutButton = findViewById(R.id.logoutButton);   // pastikan id di XML

        LinearLayout profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear backstack
            startActivity(intent);
            finish(); // Tutup Dashboard
        });
    }
}
