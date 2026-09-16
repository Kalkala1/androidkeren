# Walkthrough - Bug Fix: Attendance Crash (NullPointerException)

I have fixed the crash occurring when loading the attendance list for a date where some students haven't been marked yet.

## The Issue
The application was crashing with a `java.lang.NullPointerException: getString(...) must not be null`. This was caused by the `AttendanceWithStudent` data class expecting a non-nullable `status` and `attendanceId`, but the Room query was using a `LEFT JOIN`. When a student had no attendance record for the selected date, the database returned `NULL` for these fields, causing the crash during mapping.

## Changes Made

### 1. Data Model Update
Modified [Entities.kt](file:///C:/Users/KAYLA/AndroidStudioProjects/tugas/app/src/main/java/com/example/tugas/data/Entities.kt) to make `attendanceId` and `status` nullable in `AttendanceWithStudent`.

```kotlin
data class AttendanceWithStudent(
    val attendanceId: Int?, // Now nullable
    val studentId: Int,
    val nama: String,
    val status: String? // Now nullable
)
```

### 2. ViewModel Logic Improvement
Updated `updateAttendance` in [TeacherViewModel.kt](file:///C:/Users/KAYLA/AndroidStudioProjects/tugas/app/src/main/java/com/example/tugas/ui/TeacherViewModel.kt) to accept the `attendanceId`. This ensures that when a teacher changes the status for an existing record, it updates that specific record instead of potentially creating duplicates.

### 3. UI Integration
Updated [Screens.kt](file:///C:/Users/KAYLA/AndroidStudioProjects/tugas/app/src/main/java/com/example/tugas/ui/Screens.kt) to pass the existing `attendanceId` back to the ViewModel when marking attendance. The UI components already handled the nullable `status` correctly through Kotlin's equality checks.

## Verification Results
- **Build Success**: The project was successfully compiled with `app:assembleDebug`.
- **Logic Check**: Verified that the `LEFT JOIN` results are now safely mapped even when attendance records are missing.
