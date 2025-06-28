# KKP Asset Management Mobile App

A modern Android mobile application for asset management built with Kotlin and Jetpack Compose.

## 🚀 Features

- **Modern UI**: Futuristic neon-themed interface with glass morphism effects
- **Authentication**: Secure login system with session management
- **Asset Management**: Complete CRUD operations for assets and detail assets
- **Project Management**: Manage projects and contracts
- **Rental Management**: Track rental agreements and status
- **Pengiriman Tracking**: Monitor deliveries and shipments
- **Invoice Management**: Handle billing and invoices
- **Real-time Data**: Live updates with modern architecture

## 📱 Screens

### 1. Login Screen
- Modern authentication interface
- Demo credentials: `danuprasetya573@gmail.com`
- Auto-navigation to dashboard on successful login

### 2. Dashboard Screen
- Welcome section with user info
- Statistics cards showing active rentals, upcoming rentals, and returns
- Menu grid for navigation to different modules
- Real-time data visualization placeholders

### 3. Asset Management
- List all assets with detailed information
- Add new assets with brand selection (Asus, Acer, Dell, HP, Lenovo)
- View asset specifications and creation dates
- Link to detail assets

### 4. Rental Management
- View all rental agreements
- Filter by status (All, Ready, Rent, In Use, Maintenance)
- Search functionality
- Statistics overview

### 5. Profile Screen
- User information display
- Profile management options
- Logout functionality

## 🗄️ Database Structure

### Updated Tables (as of 2025-06-28)

#### Users
- **ID**: 1
- **Name**: Danu Febri Andi Prasetyo
- **Email**: danuprasetya573@gmail.com
- **Password**: Hashed (check database)

#### Assets
- **Lenovo Thinkpad E14** (ID: 1) - I5 Gen11|16 GB|256 GB|
- **Dell Latitude 5420** (ID: 2) - I5 Gen11|16 GB|256 GB|

#### Detail Assets
- **Serial**: MJ0GHALK (Asset 1) - Normal condition
- **Serial**: 9MZCNQ3 (Asset 2) - Normal condition

#### Projects
- **iForte** (ID: 1) - 2 months contract, Rp 550,000/month

#### Pengiriman
- **Delivery 1**: Asset 1 to Acep (Danu → Acep)
- **Delivery 2**: Asset 2 to Acep (Danu → Acep)

#### Rental
- **Rental 1**: Project iForte, 29 Jun - 29 Aug 2025, Rp 550,000
- **Rental 2**: Project iForte, 29 Jun - 29 Aug 2025, Rp 550,000

## 🛠️ Technical Stack

### Frontend
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Repository pattern
- **Navigation**: Compose Navigation
- **State Management**: StateFlow and LiveData

### Backend Integration
- **API**: RESTful API with Retrofit
- **Authentication**: Token-based with SessionManager
- **Database**: MySQL with Laravel backend

### Dependencies
```kotlin
// Core Android
implementation("androidx.core:core-ktx:1.12.0")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
implementation("androidx.activity:activity-compose:1.8.2")

// Compose
implementation(platform("androidx.compose:compose-bom:2024.02.00"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.ui:ui-graphics")
implementation("androidx.compose.ui:ui-tooling-preview")
implementation("androidx.compose.material3:material3")

// Navigation
implementation("androidx.navigation:navigation-compose:2.7.7")

// Network
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

// JSON
implementation("com.google.code.gson:gson:2.10.1")

// Charts (for future use)
implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- Android SDK 34+
- JDK 17+

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd KKP
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the KKP folder and select it

3. **Sync Project**
   - Wait for Gradle sync to complete
   - Resolve any dependency issues if they occur

4. **Configure API**
   - Update `ApiService.kt` with your backend URL
   - Ensure the Laravel backend is running

5. **Run the App**
   - Connect an Android device or start an emulator
   - Click the "Run" button in Android Studio

### Database Setup

1. **Import SQL**
   ```sql
   -- Use the management-asset.sql file
   -- Import into your MySQL database
   ```

2. **Configure Laravel Backend**
   - Set up the Laravel project in the `management-asset` folder
   - Configure database connection
   - Run migrations and seeders

## 📊 Data Flow

1. **Authentication**: User logs in → Token stored → Auto-navigate to dashboard
2. **Asset Management**: View assets → Add/Edit assets → Real-time updates
3. **Rental Tracking**: Monitor rentals → Filter by status → View details
4. **Profile Management**: View user info → Edit profile → Logout

## 🎨 UI/UX Features

- **Glass Morphism**: Translucent backgrounds with blur effects
- **Neon Colors**: Blue, Green, Purple, and Pink accent colors
- **Smooth Animations**: Material 3 design with fluid transitions
- **Responsive Design**: Adapts to different screen sizes
- **Dark Theme**: Optimized for low-light environments

## 🔧 Configuration

### API Configuration
```kotlin
// In NetworkModule.kt
private const val BASE_URL = "http://your-backend-url.com/"
```

### Theme Customization
```kotlin
// In Color.kt
val NeonBlue = Color(0xFF00D4FF)
val NeonGreen = Color(0xFF00FF88)
val NeonPurple = Color(0xFF8A2BE2)
val NeonPink = Color(0xFFFF1493)
```

## 📱 Screenshots

The app features a modern, futuristic interface with:
- Neon-themed color scheme
- Glass morphism effects
- Smooth animations
- Intuitive navigation
- Real-time data display

## 🔄 Recent Updates (2025-06-28)

### Database Updates
- ✅ Updated user information (Danu Febri Andi Prasetyo)
- ✅ Added new assets (Dell Latitude 5420)
- ✅ Updated project information (iForte)
- ✅ Added rental data with proper relationships
- ✅ Updated table structures (removed tagihan_id from rental, added rental_id to tagihan)

### App Updates
- ✅ Updated model classes to match new database structure
- ✅ Enhanced UI with more detailed information display
- ✅ Improved data visualization in tables
- ✅ Updated user profile information
- ✅ Enhanced asset cards with creation dates and detail counts

## 🐛 Troubleshooting

### Common Issues

1. **Gradle Sync Failed**
   - Clean and rebuild project
   - Invalidate caches and restart
   - Check internet connection for dependencies

2. **API Connection Issues**
   - Verify backend URL in NetworkModule
   - Check Laravel backend is running
   - Ensure CORS is properly configured

3. **Database Connection**
   - Verify MySQL server is running
   - Check database credentials in Laravel .env
   - Import the latest SQL file

### Build Commands
```bash
# Windows PowerShell
.\gradlew assembleDebug
.\gradlew build

# Linux/Mac
./gradlew assembleDebug
./gradlew build
```

## 📞 Support

For technical support or questions:
- Check the deployment guide in `deployment-guide.md`
- Review the Laravel backend documentation
- Ensure all dependencies are properly installed

## 📄 License

This project is part of the KKP Asset Management System. 