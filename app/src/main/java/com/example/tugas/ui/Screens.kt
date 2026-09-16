package com.example.tugas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugas.data.AttendanceWithStudent
import com.example.tugas.data.Student
import com.example.tugas.data.TeachingJournal
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun OceanBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                    )
                )
            )
    ) {
        content()
    }
}

@Composable
fun HomeScreen(viewModel: TeacherViewModel) {
    val students by viewModel.students.collectAsState()
    val journals by viewModel.journals.collectAsState()

    OceanBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                "Halo Guru! 🌊",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                "Semangat mengajar hari ini ya! ✨",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Medium
                )
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                HappySummaryCard("Siswa", students.size.toString(), Icons.Default.Face, Modifier.weight(1f))
                HappySummaryCard("Jurnal", journals.size.toString(), Icons.Default.Face, Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun HappySummaryCard(label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(140.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(32.dp),
        elevation = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface).let { CardDefaults.cardElevation(defaultElevation = 8.dp) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(32.dp))
            Text(value, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black))
            Text(label, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun StudentScreen(viewModel: TeacherViewModel) {
    val students by viewModel.students.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    OceanBackground {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showDialog = true },
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Tambah Siswa")
                }
            },
            containerColor = Color.Transparent
        ) { padding ->
            LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize().padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 24.dp, bottom = 100.dp)
            ) {
                item {
                    Text(
                        "Daftar Siswa 🎒",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                items(students) { student ->
                    HappyStudentCard(student)
                }
            }

            if (showDialog) {
                var nis by remember { mutableStateOf("") }
                var nama by remember { mutableStateOf("") }

                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Registrasi Siswa Baru ➕") },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedTextField(
                                value = nis, 
                                onValueChange = { nis = it }, 
                                label = { Text("NIS") },
                                shape = RoundedCornerShape(16.dp)
                            )
                            OutlinedTextField(
                                value = nama, 
                                onValueChange = { nama = it }, 
                                label = { Text("Nama Lengkap") },
                                shape = RoundedCornerShape(16.dp)
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (nis.isNotBlank() && nama.isNotBlank()) {
                                    viewModel.addStudent(nis, nama)
                                    showDialog = false
                                }
                            },
                            shape = RoundedCornerShape(12.dp)
                        ) { Text("Simpan") }
                    },
                    shape = RoundedCornerShape(28.dp)
                )
            }
        }
    }
}

@Composable
fun HappyStudentCard(student: Student) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.padding(12.dp), tint = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(student.nama, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold))
                Text("NIS: ${student.nis}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Composable
fun AttendanceScreen(viewModel: TeacherViewModel) {
    val attendanceList by viewModel.attendanceForDate.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val sdf = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.forLanguageTag("id-ID"))

    OceanBackground {
        Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Text("Presensi Kelas 📝", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
            Text(sdf.format(Date(selectedDate)), color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Medium)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp), contentPadding = PaddingValues(bottom = 80.dp)) {
                items(attendanceList) { item ->
                    HappyAttendanceItem(item) { status ->
                        viewModel.updateAttendance(item.studentId, status, item.attendanceId)
                    }
                }
            }
        }
    }
}

@Composable
fun HappyAttendanceItem(item: AttendanceWithStudent, onStatusChange: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(item.nama, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Hadir", "Izin", "Sakit", "Alpa").forEach { status ->
                    val isSelected = item.status == status
                    val color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                    val textColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer
                    
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        color = color,
                        onClick = { onStatusChange(status) }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(status.take(1), style = MaterialTheme.typography.labelLarge, color = textColor, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun JournalScreen(viewModel: TeacherViewModel) {
    val journals by viewModel.journals.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val sdf = SimpleDateFormat("dd MMM, HH:mm", Locale.forLanguageTag("id-ID"))

    OceanBackground {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showDialog = true }, 
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Tambah Jurnal")
                }
            },
            containerColor = Color.Transparent
        ) { padding ->
            LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize().padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 24.dp, bottom = 100.dp)
            ) {
                item {
                    Text("Jurnal Mengajar 📖", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                }
                items(journals) { journal ->
                    Card(
                        shape = RoundedCornerShape(32.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    journal.materi,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.weight(1f),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(sdf.format(Date(journal.tanggal)), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(journal.catatan, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            if (showDialog) {
                var materi by remember { mutableStateOf("") }
                var catatan by remember { mutableStateOf("") }

                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Jurnal Mengajar Baru ✍️") },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedTextField(
                                value = materi, 
                                onValueChange = { materi = it }, 
                                label = { Text("Topik/Materi") },
                                shape = RoundedCornerShape(16.dp)
                            )
                            OutlinedTextField(
                                value = catatan, 
                                onValueChange = { catatan = it }, 
                                label = { Text("Catatan Kegiatan") },
                                modifier = Modifier.height(140.dp),
                                shape = RoundedCornerShape(16.dp)
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (materi.isNotBlank()) {
                                    viewModel.addJournal(materi, catatan)
                                    showDialog = false
                                }
                            },
                            shape = RoundedCornerShape(12.dp)
                        ) { Text("Simpan") }
                    },
                    shape = RoundedCornerShape(28.dp)
                )
            }
        }
    }
}
