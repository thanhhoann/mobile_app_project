package com.mobile_app.project.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mobile_app.project.R

val CinzelDecoratvie = FontFamily(
    Font(R.font.cinzel_decorative)
)

val NotoSans = FontFamily(
    Font(R.font.noto_regular),
    Font(R.font.noto_semibold),
    Font(R.font.noto_bold),
    Font(R.font.noto_italic),
)

val Typography = Typography(
    // Used for heading, typically in banner
    headlineLarge = TextStyle(
        fontFamily = CinzelDecoratvie,
        fontWeight = FontWeight.Black,
        fontSize = 36.sp
    ),

    // Used for sub-heading, typically in banner
    headlineMedium = TextStyle(
        fontFamily = CinzelDecoratvie,
        fontWeight = FontWeight.Black,
        fontSize = 30.sp
    ),

    // Used for body text heading
    bodyLarge = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),

    // Used for body text with emphasis
    bodyMedium = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),

    // Used for body text
    bodySmall = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    // Used in buttons, tabs, dialogs, cards
    labelLarge = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    )
)