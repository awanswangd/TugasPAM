package org.profile.project.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ProfileData(
    val name: String,
    val title: String,
    val bio: String,
    val email: String,
    val phone: String,
    val location: String,
    val avatarInitials: String
)

// ─────────────────────────────────────────────────────────────
// Composable Reusable #3: ProfileCard
// ─────────────────────────────────────────────────────────────

/**
 * Composable Reusable #3: ProfileCard
 *
 * Card generik yang dapat digunakan ulang untuk membungkus
 * konten apapun dalam tampilan card dengan judul section.
 *
 * @param title   Judul section yang ditampilkan di atas card
 * @param modifier Modifier opsional dari pemanggil
 * @param content  Konten composable di dalam card
 */
@Composable
fun ProfileCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Judul section
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 0.5.sp
            )

            Divider(
                modifier = Modifier.padding(vertical = 6.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // Konten dinamis dari pemanggil
            content()
        }
    }
}

// ─────────────────────────────────────────────────────────────
// BioCard - Menampilkan bio/deskripsi singkat
// ─────────────────────────────────────────────────────────────

/**
 * Card yang menampilkan bio / deskripsi singkat pengguna.
 *
 * @param bio Teks bio pengguna
 */
@Composable
fun BioCard(bio: String) {
    ProfileCard(title = "TENTANG SAYA") {
        Text(
            text = bio,
            fontSize = 14.sp,
            lineHeight = 22.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// ─────────────────────────────────────────────────────────────
// ContactInfoCard - Menampilkan list informasi kontak
// ─────────────────────────────────────────────────────────────

/**
 * Card yang menampilkan informasi kontak:
 * Email, Nomor Telepon, dan Lokasi.
 * Menggunakan komponen InfoItem yang reusable.
 *
 * @param email    Alamat email pengguna
 * @param phone    Nomor telepon pengguna
 * @param location Lokasi pengguna
 */
@Composable
fun ContactInfoCard(
    email: String,
    phone: String,
    location: String
) {
    ProfileCard(title = "INFORMASI KONTAK") {
        InfoItem(
            icon = Icons.Default.Email,
            label = "Email",
            value = email
        )

        Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

        InfoItem(
            icon = Icons.Default.Phone,
            label = "Telepon",
            value = phone
        )

        Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

        InfoItem(
            icon = Icons.Default.LocationOn,
            label = "Lokasi",
            value = location
        )
    }
}

// ─────────────────────────────────────────────────────────────
// ActionButtons - Tombol aksi di bagian bawah
// ─────────────────────────────────────────────────────────────

/**
 * Baris tombol aksi utama: Kirim Pesan dan Bagikan Profil.
 *
 * @param onMessageClick Callback saat tombol "Pesan" ditekan
 * @param onShareClick   Callback saat tombol "Bagikan" ditekan
 */
@Composable
fun ActionButtons(
    onMessageClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Tombol utama: Kirim Pesan (Filled)
        Button(
            onClick = onMessageClick,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Kirim Pesan")
        }

        // Tombol sekunder: Bagikan (Outlined)
        OutlinedButton(
            onClick = onShareClick,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Bagikan")
        }
    }
}