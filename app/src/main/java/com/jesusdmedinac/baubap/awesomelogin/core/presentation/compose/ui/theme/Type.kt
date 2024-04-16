package com.jesusdmedinac.baubap.awesomelogin.core.presentation.compose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.baubap.awesomelogin.R

val montserrat = FontFamily(
    Font(R.font.montserrat_black),
    Font(R.font.montserrat_black_italic),
    Font(R.font.montserrat_bold),
    Font(R.font.montserrat_bold_italic),
    Font(R.font.montserrat_extra_bold),
    Font(R.font.montserrat_extra_bold_italic),
    Font(R.font.montserrat_extra_light),
    Font(R.font.montserrat_extra_light_italic),
    Font(R.font.montserrat_italic),
    Font(R.font.montserrat_italic_variable_font_wght),
    Font(R.font.montserrat_light),
    Font(R.font.montserrat_light_italic),
    Font(R.font.montserrat_medium),
    Font(R.font.montserrat_medium_italic),
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_semi_bold),
    Font(R.font.montserrat_semi_bold_italic),
    Font(R.font.montserrat_thin),
    Font(R.font.montserrat_thin_italic),
    Font(R.font.montserrat_variable_font_wght),
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = montserrat,
        fontSize = 28.sp,
        fontWeight = FontWeight.Normal
    ),
    labelMedium = TextStyle(
        fontFamily = montserrat,
        fontSize = 14.sp,
        fontWeight = FontWeight.ExtraLight
    ),
    labelSmall = TextStyle(
        fontFamily = montserrat,
        fontSize = 12.sp,
        fontWeight = FontWeight.ExtraLight
    )
)
