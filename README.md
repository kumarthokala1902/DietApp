<<<<<<< HEAD
# DietApp
This is an Gym diet planning app
=======
# 🏋️ FitFuel Pro - Gym Diet App
### A Complete, Production-Grade Android Gym & Diet App in Java

---

## 📱 App Overview

**FitFuel Pro** is a beautifully designed gym diet tracker app built for Android Studio with Java.
It features a dark, vibrant UI with orange/pink gradients, smooth animations, and complete functionality.

---

## 🚀 Setup in Android Studio

### Step 1: Open Project
1. Launch **Android Studio**
2. Click `File > Open`
3. Navigate to the `GymDietApp` folder
4. Click **OK**

### Step 2: Sync Gradle
1. Android Studio will prompt to sync Gradle
2. Click **"Sync Now"** in the notification bar
3. Wait for dependencies to download (~2-3 minutes)

### Step 3: Run the App
1. Connect an Android device (API 24+) OR use the emulator
2. Click the **▶ Run** button (or `Shift + F10`)
3. Select your device and click **OK**

---

## 🎨 Features & Screens

### 1. Splash Screen
- Animated logo with scale + fade entrance
- Gradient progress bar loading animation
- Smooth transition to onboarding

### 2. Onboarding (3 Slides)
- Animated ViewPager2 with emoji illustrations
- Auto-animating text and icons per page
- Custom pill indicator dots that morph active/inactive
- "Get Started" CTA button

### 3. Home Dashboard
- Personalized greeting (morning/afternoon/evening)
- **Calorie summary card** with orange-pink gradient
- Animated count-up numbers for calories
- Macro breakdown (Protein, Carbs, Fats)
- Water intake + Steps cards
- Today's meals list with RecyclerView
- Today's workout preview card

### 4. Diet Plans
- Filterable diet plans by category (Bulking, Cutting, Vegan, Keto, etc.)
- Plan cards with gradient headers (orange, green, purple)
- Per-plan: calories/day, meals/day, duration in weeks
- "Start Plan" button with haptic feedback

### 5. Workouts 🏋️ (Center FAB)
- Filter by muscle group (Full Body, Chest, Back, Legs, Arms, Core)
- Exercise cards with emoji icons
- Sets, reps, difficulty badges
- Color-coded difficulty (Green=Beginner, Yellow=Medium, Red=Advanced)

### 6. Nutrition Tracker
- Daily calorie balance (Goal vs Eaten vs Remaining)
- Animated progress bars for Protein, Carbs, Fats
- **Interactive Water Tracker** with glass-by-glass visualization
- "+250ml" button with bounce animation

### 7. Profile
- Profile header with gradient banner
- Stats row (Weight, Height, BMI)
- Weekly calorie bar chart (visual bars)
- Achievements (Streak, Workouts, Progress)
- Settings menu items

---

## 🎞️ Animations

| Animation | Used In |
|-----------|---------|
| Scale + Fade Entrance | Splash Screen |
| Count-Up Number | Calories, Steps |
| Progress Bar Fill | All progress indicators |
| Stagger Slide-In | RecyclerView items |
| Slide-In from Bottom | Dashboard cards |
| Pulse on Tap | All buttons & cards |
| Overshoot Scale | Dialog entries |
| Emoji Bounce | Onboarding slides |

---

## 🎨 Color Palette

| Color | Hex | Use |
|-------|-----|-----|
| Orange (Primary) | `#FF6B35` | Buttons, accents |
| Pink | `#E91E8C` | Gradients |
| Teal (Accent) | `#00D4AA` | Water, success |
| Dark BG | `#0D0D0D` | Background |
| Card BG | `#1A1A2E` | Cards |

---

## 📦 Dependencies

```gradle
implementation 'com.google.android.material:material:1.11.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
implementation 'androidx.recyclerview:recyclerview:1.3.2'
implementation 'androidx.cardview:cardview:1.0.0'
implementation 'com.airbnb.android:lottie:6.1.0'
implementation 'de.hdodenhof:circleimageview:3.1.0'
implementation 'androidx.viewpager2:viewpager2:1.0.0'
```

---

## 📂 Project Structure

```
app/src/main/java/com/gymdiet/app/
├── activities/
│   ├── SplashActivity.java       ← Animated splash
│   ├── OnboardingActivity.java   ← 3-page intro
│   └── MainActivity.java         ← Main hub with bottom nav
├── fragments/
│   ├── HomeFragment.java         ← Dashboard
│   ├── DietFragment.java         ← Diet plans
│   ├── WorkoutFragment.java      ← Exercises
│   ├── TrackerFragment.java      ← Nutrition tracker
│   └── ProfileFragment.java      ← User profile
├── adapters/
│   ├── MealAdapter.java
│   ├── DietPlanAdapter.java
│   ├── WorkoutAdapter.java
│   └── OnboardingAdapter.java
├── models/
│   ├── Meal.java
│   ├── DietPlan.java
│   └── Workout.java
└── utils/
    ├── AnimUtils.java            ← All animation helpers
    └── DataProvider.java         ← Sample data

app/src/main/res/
├── layout/         ← All XML layouts
├── drawable/       ← Gradients, shapes, ripples
├── anim/           ← XML animation files
└── values/         ← Colors, strings, themes, dimens
```

---

## 🔧 Minimum Requirements

- **Android Studio**: Flamingo or later
- **Min SDK**: API 24 (Android 7.0)
- **Target SDK**: API 34 (Android 14)
- **Java Version**: 1.8
- **Gradle**: 8.x

---

## 💡 Future Enhancements

- [ ] Room Database for persistent data
- [ ] Barcode food scanner
- [ ] Rest timer for workouts  
- [ ] Push notifications for meal reminders
- [ ] BMR/TDEE calculator
- [ ] Progress photos gallery
- [ ] Social sharing of achievements

---

*Built with ❤️ by your dev team. Get that hike! 🚀*
>>>>>>> c4dee62 (an ainitial commit of push)
