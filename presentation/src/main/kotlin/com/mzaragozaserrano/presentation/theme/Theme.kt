package com.mzaragozaserrano.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.mzaragozaserrano.presentation.R


private val LightColorScheme = lightColorScheme(
    primary = LightColorTokens.Primary,
    onPrimary = LightColorTokens.OnPrimary,
    primaryContainer = LightColorTokens.PrimaryContainer,
    onPrimaryContainer = LightColorTokens.OnPrimaryContainer,
    secondary = LightColorTokens.Secondary,
    onSecondary = LightColorTokens.OnSecondary,
    secondaryContainer = LightColorTokens.SecondaryContainer,
    onSecondaryContainer = LightColorTokens.OnSecondaryContainer,
    tertiary = LightColorTokens.Tertiary,
    onTertiary = LightColorTokens.OnTertiary,
    tertiaryContainer = LightColorTokens.TertiaryContainer,
    onTertiaryContainer = LightColorTokens.OnTertiaryContainer,
    background = LightColorTokens.Background,
    onBackground = LightColorTokens.OnBackground,
    surface = LightColorTokens.Surface,
    onSurface = LightColorTokens.OnSurface,
    surfaceVariant = LightColorTokens.SurfaceVariant,
    onSurfaceVariant = LightColorTokens.OnSurfaceVariant,
    error = LightColorTokens.Error,
    onError = LightColorTokens.OnError,
    errorContainer = LightColorTokens.ErrorContainer,
    onErrorContainer = LightColorTokens.OnErrorContainer,
    outline = LightColorTokens.Outline,
    outlineVariant = LightColorTokens.OutlineVariant,
    inverseSurface = LightColorTokens.InverseSurface,
    inverseOnSurface = LightColorTokens.InverseOnSurface,
    inversePrimary = LightColorTokens.InversePrimary,
    scrim = LightColorTokens.Scrim,
    surfaceBright = LightColorTokens.SurfaceBright,
    surfaceContainer = LightColorTokens.SurfaceContainer,
    surfaceContainerHigh = LightColorTokens.SurfaceContainerHigh,
    surfaceContainerHighest = LightColorTokens.SurfaceContainerHighest
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkColorTokens.Primary,
    onPrimary = DarkColorTokens.OnPrimary,
    primaryContainer = DarkColorTokens.PrimaryContainer,
    onPrimaryContainer = DarkColorTokens.OnPrimaryContainer,
    secondary = DarkColorTokens.Secondary,
    onSecondary = DarkColorTokens.OnSecondary,
    secondaryContainer = DarkColorTokens.SecondaryContainer,
    onSecondaryContainer = DarkColorTokens.OnSecondaryContainer,
    tertiary = DarkColorTokens.Tertiary,
    onTertiary = DarkColorTokens.OnTertiary,
    tertiaryContainer = DarkColorTokens.TertiaryContainer,
    onTertiaryContainer = DarkColorTokens.OnTertiaryContainer,
    background = DarkColorTokens.Background,
    onBackground = DarkColorTokens.OnBackground,
    surface = DarkColorTokens.Surface,
    onSurface = DarkColorTokens.OnSurface,
    surfaceVariant = DarkColorTokens.SurfaceVariant,
    onSurfaceVariant = DarkColorTokens.OnSurfaceVariant,
    error = DarkColorTokens.Error,
    onError = DarkColorTokens.OnError,
    errorContainer = DarkColorTokens.ErrorContainer,
    onErrorContainer = DarkColorTokens.OnErrorContainer,
    outline = DarkColorTokens.Outline,
    outlineVariant = DarkColorTokens.OutlineVariant,
    inverseSurface = DarkColorTokens.InverseSurface,
    inverseOnSurface = DarkColorTokens.InverseOnSurface,
    inversePrimary = DarkColorTokens.InversePrimary,
    scrim = DarkColorTokens.Scrim,
    surfaceBright = DarkColorTokens.SurfaceBright,
    surfaceContainer = DarkColorTokens.SurfaceContainer,
    surfaceContainerHigh = DarkColorTokens.SurfaceContainerHigh,
    surfaceContainerHighest = DarkColorTokens.SurfaceContainerHighest
)

val fontFamily = FontFamily(
    Font(R.font.nunito_black, FontWeight.Black),
    Font(R.font.nunito_bold, FontWeight.Bold),
    Font(R.font.nunito_light, FontWeight.Light),
    Font(R.font.nunito_medium, FontWeight.Medium),
    Font(R.font.nunito_regular, FontWeight.Normal)
)

val typography = Typography(
    displayLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp
    ),
    titleSmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

@Composable
fun IdealistaAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {

    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current

    if (view.isInEditMode.not()) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )

}