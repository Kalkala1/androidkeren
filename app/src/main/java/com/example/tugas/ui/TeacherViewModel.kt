package com.example.tugas.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tugas.data.Attendance
import com.example.tugas.data.Student
import com.example.tugas.data.TeacherRepository
import com.example.tugas.data.TeachingJournal
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalCoroutinesApi::class)
class TeacherViewModel(private val repository: TeacherRepository) : ViewModel() {

    val students: StateFlow<List<Student>> = repository.allStudents.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val journals: StateFlow<List<TeachingJournal>> = repository.allJournals.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _selectedDate = MutableStateFlow(LocalDate.now().atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000)
    val selectedDate: StateFlow<Long> = _selectedDate

    val attendanceForDate = _selectedDate.flatMapLatest { date ->
        repository.getAttendanceForDate(date)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun selectDate(timestamp: Long) {
        _selectedDate.value = timestamp
    }

    fun addStudent(nis: String, nama: String) {
        viewModelScope.launch {
            repository.insertStudent(Student(nis = nis, nama = nama))
        }
    }

    fun updateAttendance(studentId: Int, status: String, attendanceId: Int?) {
        viewModelScope.launch {
            repository.upsertAttendance(
                Attendance(
                    id = attendanceId ?: 0,
                    studentId = studentId,
                    tanggal = _selectedDate.value,
                    status = status
                )
            )
        }
    }

    fun addJournal(materi: String, catatan: String) {
        viewModelScope.launch {
            repository.insertJournal(
                TeachingJournal(
                    tanggal = System.currentTimeMillis(),
                    materi = materi,
                    catatan = catatan
                )
            )
        }
    }
}

class TeacherViewModelFactory(private val repository: TeacherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeacherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeacherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
