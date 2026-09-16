package com.example.tugas.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nis: String,
    val nama: String
)

@Entity(
    tableName = "attendance",
    foreignKeys = [
        ForeignKey(
            entity = Student::class,
            parentColumns = ["id"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["studentId"])]
)
data class Attendance(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val studentId: Int,
    val tanggal: Long, // Timestamp
    val status: String // status_hoki in prompt, assumed as Attendance Status
)

@Entity(tableName = "teaching_journals")
data class TeachingJournal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tanggal: Long,
    val materi: String,
    val catatan: String
)

data class AttendanceWithStudent(
    val attendanceId: Int?,
    val studentId: Int,
    val nama: String,
    val status: String?
)
