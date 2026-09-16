package com.example.tugas.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Query("SELECT * FROM students ORDER BY nama ASC")
    fun getAllStudents(): Flow<List<Student>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)
}

@Dao
interface AttendanceDao {
    @Query("""
        SELECT a.id as attendanceId, s.id as studentId, s.nama, a.status 
        FROM students s 
        LEFT JOIN attendance a ON s.id = a.studentId AND a.tanggal = :date
    """)
    fun getAttendanceForDate(date: Long): Flow<List<AttendanceWithStudent>>

    @Upsert
    suspend fun upsertAttendance(attendance: Attendance)
}

@Dao
interface JournalDao {
    @Query("SELECT * FROM teaching_journals ORDER BY tanggal DESC")
    fun getAllJournals(): Flow<List<TeachingJournal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(journal: TeachingJournal)
}
