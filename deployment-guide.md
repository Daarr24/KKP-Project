# Panduan Deployment Asset Management App

## Langkah-langkah Deployment Lengkap

### 1. Persiapan Database di Hostinger

1. **Login ke Hostinger Control Panel**
   - Buka https://hpanel.hostinger.com
   - Login dengan akun hosting Anda

2. **Buat Database MySQL**
   - Klik "Databases" → "MySQL Databases"
   - Buat database baru dengan nama: `management-asset`
   - Catat username dan password database

3. **Import Database**
   - Klik "phpMyAdmin" untuk database yang baru dibuat
   - Pilih tab "Import"
   - Upload file `management-asset.sql`
   - Klik "Go" untuk import

### 2. Upload Backend API

1. **Upload File API**
   - Buka File Manager di Hostinger
   - Navigasi ke folder `public_html`
   - Upload folder `api/` ke root directory

2. **Konfigurasi Database**
   - Edit file `api/index.php`
   - Ganti konfigurasi database:
   ```php
   $host = 'localhost';
   $dbname = 'management-asset';
   $username = 'your_username'; // Username database Anda
   $password = 'your_password'; // Password database Anda
   ```

3. **Test API**
   - Akses: `https://your-domain.com/api/`
   - Seharusnya muncul response JSON

### 3. Build Android App

1. **Update API URL**
   - Buka file: `app/src/main/java/com/example/kkp/api/NetworkModule.kt`
   - Ganti BASE_URL:
   ```kotlin
   private const val BASE_URL = "https://your-domain.com/"
   ```

2. **Build APK**
   ```bash
   # Di terminal, navigasi ke folder KKP
   cd KKP
   
   # Build release APK
   ./gradlew assembleRelease
   ```

3. **Lokasi APK**
   - APK akan tersedia di: `app/build/outputs/apk/release/app-release.apk`

### 4. Install di Android

1. **Transfer APK**
   - Transfer file APK ke perangkat Android
   - Bisa via USB, email, atau cloud storage

2. **Enable Unknown Sources**
   - Buka Settings → Security
   - Aktifkan "Unknown Sources" atau "Install unknown apps"

3. **Install App**
   - Buka file APK
   - Klik "Install"
   - Tunggu proses instalasi selesai

### 5. Testing Aplikasi

1. **Test Login**
   - Buka aplikasi
   - Login dengan kredensial:
     - Email: danuprasetya573@gmail.com
     - Password: (cek di database)

2. **Test Fitur**
   - Dashboard
   - Asset Management
   - Project Management
   - Rental Management
   - Pengiriman
   - Tagihan

## Troubleshooting

### Error: "API Connection Failed"
- Cek URL API di NetworkModule.kt
- Pastikan hosting mendukung PHP
- Cek error logs di hosting panel

### Error: "Database Connection Failed"
- Periksa credentials database di api/index.php
- Pastikan database sudah diimport
- Cek apakah database aktif

### Error: "App Crashes on Startup"
- Cek Logcat di Android Studio
- Pastikan semua dependencies terinstall
- Clean dan rebuild project

### Error: "Cannot Install APK"
- Pastikan "Unknown Sources" sudah diaktifkan
- Cek apakah APK corrupt
- Coba build ulang APK

## Konfigurasi Tambahan

### SSL Certificate
- Pastikan hosting menggunakan HTTPS
- Update BASE_URL dengan https://

### Security
- Ganti password default database
- Aktifkan firewall di hosting
- Regular backup database

### Performance
- Enable caching di hosting
- Optimize database queries
- Compress images dan assets

## Monitoring

### Backend Monitoring
- Cek error logs di hosting panel
- Monitor database performance
- Track API usage

### App Monitoring
- Monitor crash reports
- Track user analytics
- Performance monitoring

## Backup Strategy

1. **Database Backup**
   - Export database secara regular
   - Simpan backup di cloud storage
   - Test restore procedure

2. **Code Backup**
   - Version control dengan Git
   - Backup source code
   - Document semua perubahan

## Support

Jika mengalami masalah:
1. Cek error logs
2. Test di environment berbeda
3. Contact support hosting
4. Review documentation

---

**Catatan Penting**: 
- Ganti semua placeholder dengan konfigurasi yang sesuai
- Test di environment development sebelum production
- Backup data sebelum melakukan perubahan besar
- Monitor aplikasi secara regular 