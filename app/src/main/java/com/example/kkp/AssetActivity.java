package com.example.kkp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AssetActivity extends AppCompatActivity {

    private EditText searchBar;
    private Spinner filterSpinner;
    private Button moreFilterBtn, addAssetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset); // Pastikan nama file layout kamu benar

        // Inisialisasi view
        searchBar = findViewById(R.id.searchBar); // Tambahkan ID ini di layout jika belum ada
        filterSpinner = findViewById(R.id.filterSpinner); // Tambahkan ID di Spinner XML
        moreFilterBtn = findViewById(R.id.moreFilterBtn); // Tambahkan ID di Button XML
        addAssetBtn = findViewById(R.id.addAssetBtn); // Tambahkan ID di Button Add Asset

        // Setup spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.filter_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);

        // Action saat klik tombol More Filter
        moreFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AssetActivity.this, "More Filter Clicked", Toast.LENGTH_SHORT).show();
                // Bisa tampilkan dialog filter di sini
            }
        });

        // Action saat klik Add Asset
        addAssetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AssetActivity.this, "Add Asset Clicked", Toast.LENGTH_SHORT).show();
                // Bisa lanjut ke form tambah asset
            }
        });


    }
}
