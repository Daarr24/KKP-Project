# Asset Management Mobile App

Aplikasi mobile Android untuk sistem manajemen aset yang terhubung dengan database MySQL. Dibangun menggunakan Jetpack Compose dan modern Android development practices.

## Fitur

- 🔐 **Authentication** - Login dengan email dan password
- 📱 **Modern UI** - Menggunakan Material 3 Design System
- 🗄️ **Database Integration** - Terhubung dengan MySQL database
- 📊 **Asset Management** - Kelola aset dan inventaris
- 📋 **Project Management** - Kelola proyek dan kontrak
- 🚚 **Delivery Tracking** - Lacak pengiriman dan pengiriman
- 💰 **Billing System** - Kelola tagihan dan faktur
- 🔄 **Rental Management** - Kelola perjanjian sewa

## Struktur Database

Aplikasi ini menggunakan database MySQL dengan tabel berikut:

- `users` - Data pengguna
- `assets` - Data aset (merk, type, spesifikasi)
- `detailasset` - Detail aset (serial number, kondisi)
- `projects` - Data proyek dan kontrak
- `pengiriman` - Data pengiriman aset
- `tagihan` - Data tagihan dan faktur
- `rental` - Data perjanjian sewa

## Setup dan Deployment

### 1. Backend Setup (Hostinger)

1. **Upload Database**
   - Import file `management-asset.sql` ke database MySQL di Hostinger
   - Pastikan nama database sesuai dengan konfigurasi

2. **Upload API Files**
   - Upload folder `api/` ke hosting Anda
   - Edit file `api/index.php` dan sesuaikan konfigurasi database:
     ```php
     $host = 'localhost';
     $dbname = 'management-asset';
     $username = 'your_username'; // Ganti dengan username database Anda
     $password = 'your_password'; // Ganti dengan password database Anda
     ```

3. **Test API**
   - Akses `https://your-domain.com/api/` untuk memastikan API berjalan
   - Test endpoint login: `POST https://your-domain.com/api/login`

### 2. Android App Setup

1. **Update API URL**
   - Buka file `app/src/main/java/com/example/kkp/api/NetworkModule.kt`
   - Ganti `BASE_URL` dengan URL hosting Anda:
     ```kotlin
     private const val BASE_URL = "https://your-domain.com/"
     ```

2. **Build dan Deploy**
   ```bash
   # Build APK
   ./gradlew assembleRelease
   
   # APK akan tersedia di: app/build/outputs/apk/release/app-release.apk
   ```

3. **Install di Android**
   - Transfer file APK ke perangkat Android
   - Aktifkan "Install from Unknown Sources" di settings
   - Install aplikasi

## Teknologi yang Digunakan

### Android App
- **Kotlin** - Bahasa pemrograman utama
- **Jetpack Compose** - Modern UI toolkit
- **Material 3** - Design system
- **Retrofit** - HTTP client untuk API calls
- **Coroutines** - Asynchronous programming
- **ViewModel & LiveData** - Architecture components
- **Navigation Compose** - Navigation

### Backend
- **PHP** - Server-side scripting
- **MySQL** - Database
- **PDO** - Database abstraction layer
- **JSON** - Data format

## Struktur Project

```
KKP/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/kkp/
│   │   │   ├── api/           # Network layer
│   │   │   ├── model/         # Data models
│   │   │   ├── repository/    # Data repositories
│   │   │   ├── viewmodel/     # ViewModels
│   │   │   ├── ui/screens/    # UI screens
│   │   │   └── ui/theme/      # App theme
│   │   └── res/               # Resources
│   └── build.gradle.kts       # App dependencies
├── api/
│   └── index.php              # Backend API
└── README.md
```

## Demo Credentials

Untuk testing, gunakan kredensial berikut:
- **Email**: danuprasetya573@gmail.com
- **Password**: (cek di database)

## Troubleshooting

### Common Issues

1. **API Connection Error**
   - Pastikan URL API sudah benar
   - Cek koneksi internet
   - Pastikan hosting mendukung PHP

2. **Database Connection Error**
   - Periksa konfigurasi database di `api/index.php`
   - Pastikan database sudah diimport
   - Cek credentials database

3. **Build Error**
   - Pastikan Android Studio sudah ter-update
   - Sync project dengan Gradle
   - Clean dan rebuild project

### Logs

Untuk debugging, cek logs di:
- Android: Logcat di Android Studio
- Backend: Error logs di hosting panel

## Contributing

1. Fork repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Create Pull Request

## License

This project is licensed under the MIT License.

## Support

Untuk bantuan dan pertanyaan:
- Email: danuprasetya573@gmail.com
- GitHub Issues: [Create Issue](https://github.com/your-repo/issues)

---

**Note**: Pastikan untuk mengganti semua placeholder (your-domain.com, your_username, your_password) dengan konfigurasi yang sesuai dengan hosting Anda. 