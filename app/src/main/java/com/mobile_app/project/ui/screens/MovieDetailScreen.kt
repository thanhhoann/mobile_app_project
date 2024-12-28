package com.mobile_app.project.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mobile_app.project.R
import com.mobile_app.project.components.StyledButton
import com.mobile_app.project.components.ButtonVariants

// Data class for cast members
data class CastMember(
    val id: Int,
    val name: String,
    val imageResId: Int
)

// Preview for MovieScreen
@Preview(showBackground = true)
@Composable
fun MovieScreenPreview() {
    val navController = rememberNavController() // Mock NavController for preview
    MovieScreen(navController = navController)
}

@Composable
fun MovieScreen(navController: NavController) {
    // Sample cast members - change later when database connect
    val castMembers = remember {
        listOf(
            CastMember(1, "Cast Member 1", R.drawable.cast_member_1),
            CastMember(2, "Cast Member 2", R.drawable.cast_member_2),
            CastMember(3, "Cast Member 3", R.drawable.cast_member_3),
            CastMember(4, "Cast Member 4", R.drawable.cast_member_4),
            CastMember(5, "Cast Member 5", R.drawable.cast_member_5)
        )
    }

    MovieDetail(castMembers, navController)
}

@Composable
fun MovieDetail(castMembers: List<CastMember>, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Movie Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.evil_dead),
                contentDescription = "Movie Banner",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Navigation Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 48.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Back arrow
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back_24dp_e8eaed_fill0_wght400_grad0_opsz24),
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                // Menu button
                IconButton(
                    onClick = { /* Handle Menu */ },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
            }
        }

        // Details Box that overlaps the banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 200.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                color = Color(0xFF121212).copy(alpha = 0.98f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    // Movie Title and Genre
                    Text(
                        text = "EVIL DEAD RISE",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "HORROR 2D.3D.4DX",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )

                        Button(
                            onClick = { /* Handle trailer */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.DarkGray
                            ),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.play_arrow_24dp_e8eaed_fill0_wght400_grad0_opsz24),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Watch Trailer", fontSize = 12.sp)
                        }
                    }

                    // Movie Info Grid
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MovieInfoItem("Censor Rating", "A")
                        MovieInfoItem("Duration", "1hr:38min")
                        MovieInfoItem("Release date", "21 April 2023")
                    }

                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 0.5.dp,
                        color = Color.Gray.copy(alpha = 0.2f)
                    )

                    // Language Section
                    Text(
                        text = "Available in languages",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "English",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    // Cast Section
                    Text(
                        text = "Cast",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        items(castMembers) { castMember ->
                            CastMemberItem(castMember)
                        }
                    }

                    // Book Tickets Button
                    StyledButton(
                        color = ButtonVariants.Primary,
                        onClick = { /* Handle booking */ },
                        text = "Book Tickets",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieInfoItem(title: String, value: String) {
    Column {
        Text(
            text = title,
            color = Color.Gray,
            fontSize = 12.sp
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun CastMemberItem(castMember: CastMember) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(70.dp)
    ) {
        Image(
            painter = painterResource(id = castMember.imageResId),
            contentDescription = "Cast member ${castMember.name}",
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}
