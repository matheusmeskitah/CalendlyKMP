package com.meskitah.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import calendlykmp.composeapp.generated.resources.Lato_Black
import calendlykmp.composeapp.generated.resources.Lato_Bold
import calendlykmp.composeapp.generated.resources.Lato_Light
import calendlykmp.composeapp.generated.resources.Lato_Regular
import calendlykmp.composeapp.generated.resources.Lato_Thin
import calendlykmp.composeapp.generated.resources.OpenSans_Bold
import calendlykmp.composeapp.generated.resources.OpenSans_ExtraBold
import calendlykmp.composeapp.generated.resources.OpenSans_Light
import calendlykmp.composeapp.generated.resources.OpenSans_Medium
import calendlykmp.composeapp.generated.resources.OpenSans_Regular
import calendlykmp.composeapp.generated.resources.OpenSans_SemiBold
import calendlykmp.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun bodyFontFamily() = FontFamily(
    Font(resource = Res.font.OpenSans_Bold, weight = FontWeight.Bold),
    Font(resource = Res.font.OpenSans_ExtraBold, weight = FontWeight.ExtraBold),
    Font(resource = Res.font.OpenSans_Light, weight = FontWeight.Light),
    Font(resource = Res.font.OpenSans_Medium, weight = FontWeight.Medium),
    Font(resource = Res.font.OpenSans_Regular, weight = FontWeight.Normal),
    Font(resource = Res.font.OpenSans_SemiBold, weight = FontWeight.SemiBold)
)

@Composable
fun displayFontFamily() = FontFamily(
    Font(resource = Res.font.Lato_Black, weight = FontWeight.Black),
    Font(resource = Res.font.Lato_Bold, weight = FontWeight.Bold),
    Font(resource = Res.font.Lato_Light, weight = FontWeight.Light),
    Font(resource = Res.font.Lato_Regular, weight = FontWeight.Normal),
    Font(resource = Res.font.Lato_Thin, weight = FontWeight.Thin)
)

// Default Material 3 typography values
val baseline = Typography()

@Composable
fun AppTypography() = Typography().run {
    val bodyFontFamily = bodyFontFamily()
    val displayFontFamily = displayFontFamily()
    copy(
        displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
        displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
        displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
        headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
        headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
        headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
        titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
        titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily),
        titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
        bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
        bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
        bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
        labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
        labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
        labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),
    )
}

