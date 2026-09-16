package com.example.tugas.data

import kotlinx.coroutines.flow.Flow

class TeacherRepository(
    private val studentDao: StudentDao,
    private val attendanceDao: AttendanceDao,
    private val journalDao: JournalDao
) {
    val allStudents: Flow<List<Student>> = studentDao.getAllStudents()
    val allJournals: Flow<List<TeachingJournal>> = journalDao.getAllJournals()

    fun getAttendanceForDate(date: Long): Flow<List<AttendanceWithStudent>> = 
        attendanceDao.getAttendanceForDate(date)

    suspend fun insertStudent(student: Student) = studentDao.insertStudent(student)
    suspend fun deleteStudent(student: Student) = studentDao.deleteStudent(student)

    suspend fun upsertAttendance(attendance: Attendance) = attendanceDao.upsertAttendance(attendance)

    suspend fun insertJournal(journal: TeachingJournal) = journalDao.insertJournal(journal)
}
