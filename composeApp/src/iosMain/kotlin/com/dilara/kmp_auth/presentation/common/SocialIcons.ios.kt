package com.dilara.kmp_auth.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp

actual object SocialIcons {
    @Composable
    actual fun Google(): Painter {
        // Android'deki XML path verilerini kullanarak Google ikonu
        val googleIcon = ImageVector.Builder(
            name = "Google",
            defaultWidth = 800.dp,
            defaultHeight = 800.dp,
            viewportWidth = 262f,
            viewportHeight = 262f
        ).apply {
            // Mavi path (#4285F4)
            // Path: M258.88,133.45c0,-10.73 -0.87,-18.57 -2.76,-26.69L133.55,106.76v48.45h71.95c-1.45,12.04 -9.28,30.17 -26.69,42.36l-0.24,1.62 38.76,30.02 2.68,0.27c24.66,-22.77 38.88,-56.28 38.88,-96.03
            path(
                fill = SolidColor(Color(0xFF4285F4)),
                fillAlpha = 1f,
                stroke = null,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(258.88f, 133.45f)
                // c0,-10.73 -0.87,-18.57 -2.76,-26.69 (c = relative cubic bezier: dx1,dy1 dx2,dy2 dx,dy)
                curveToRelative(0f, -10.73f, -0.87f, -18.57f, -2.76f, -26.69f)
                lineTo(133.55f, 106.76f)
                verticalLineToRelative(48.45f)
                horizontalLineToRelative(71.95f)
                // c-1.45,12.04 -9.28,30.17 -26.69,42.36
                curveToRelative(-1.45f, 12.04f, -9.28f, 30.17f, -26.69f, 42.36f)
                lineToRelative(-0.24f, 1.62f)
                lineToRelative(38.76f, 30.02f)
                lineToRelative(2.68f, 0.27f)
                // c24.66,-22.77 38.88,-56.28 38.88,-96.03
                curveToRelative(24.66f, -22.77f, 38.88f, -56.28f, 38.88f, -96.03f)
                close()
            }
            
            // Yeşil path (#34A853)
            // Path: M133.55,261.1c35.25,0 64.84,-11.6 86.45,-31.62l-41.2,-31.91c-11.02,7.69 -25.82,13.06 -45.26,13.06 -34.52,0 -63.82,-22.77 -74.27,-54.25l-1.53,0.13 -40.3,31.19 -0.53,1.47C38.39,231.8 82.49,261.1 133.55,261.1
            path(
                fill = SolidColor(Color(0xFF34A853)),
                fillAlpha = 1f,
                stroke = null,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(133.55f, 261.1f)
                curveToRelative(35.25f, 0f, 64.84f, -11.6f, 86.45f, -31.62f)
                lineToRelative(-41.2f, -31.91f)
                curveToRelative(-11.02f, 7.69f, -25.82f, 13.06f, -45.26f, 13.06f)
                curveToRelative(-34.52f, 0f, -63.82f, -22.77f, -74.27f, -54.25f)
                lineToRelative(-1.53f, 0.13f)
                lineToRelative(-40.3f, 31.19f)
                lineToRelative(-0.53f, 1.47f)
                // C38.39,231.8 82.49,261.1 133.55,261.1 (absolute cubic bezier)
                curveTo(38.39f, 231.8f, 82.49f, 261.1f, 133.55f, 261.1f)
                close()
            }
            
            // Sarı path (#FBBC05)
            // Path: M59.28,156.37c-2.76,-8.12 -4.35,-16.83 -4.35,-25.82 0,-8.99 1.6,-17.7 4.21,-25.82l-0.07,-1.73L18.26,71.31l-1.34,0.63C8.08,89.64 3,109.52 3,130.55s5.08,40.9 13.93,58.6l42.36,-32.78
            path(
                fill = SolidColor(Color(0xFFFBBC05)),
                fillAlpha = 1f,
                stroke = null,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(59.28f, 156.37f)
                curveToRelative(-2.76f, -8.12f, -4.35f, -16.83f, -4.35f, -25.82f)
                curveToRelative(0f, -8.99f, 1.6f, -17.7f, 4.21f, -25.82f)
                lineToRelative(-0.07f, -1.73f)
                lineToRelative(-40.95f, -31.75f)
                lineToRelative(-1.34f, 0.63f)
                // C8.08,89.64 3,109.52 3,130.55 (absolute cubic bezier)
                curveTo(8.08f, 89.64f, 3f, 109.52f, 3f, 130.55f)
                // s5.08,40.9 13.93,58.6 (smooth relative cubic bezier - s komutu için ilk kontrol noktası önceki eğrinin son kontrol noktasının yansıması)
                // Önceki eğrinin son kontrol noktası: (3, 109.52), son nokta: (3, 130.55)
                // Yansıma: dx2 = 3 - 3 = 0, dy2 = 130.55 - 109.52 = 21.03
                // s komutu: s dx2,dy2 dx,dy -> curveToRelative(0f, 21.03f, 5.08f, 40.9f, 13.93f, 58.6f)
                curveToRelative(0f, 21.03f, 5.08f, 40.9f, 13.93f, 58.6f)
                lineToRelative(42.36f, -32.78f)
                close()
            }
            
            // Kırmızı path (#EB4335)
            // Path: M133.55,50.48c24.51,0 41.05,10.59 50.48,19.44l36.84,-35.97C198.24,12.91 168.8,0 133.55,0 82.49,0 38.39,29.3 16.92,71.95l42.21,32.78c10.59,-31.48 39.89,-54.25 74.41,-54.25
            path(
                fill = SolidColor(Color(0xFFEB4335)),
                fillAlpha = 1f,
                stroke = null,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(133.55f, 50.48f)
                curveToRelative(24.51f, 0f, 41.05f, 10.59f, 50.48f, 19.44f)
                lineToRelative(36.84f, -35.97f)
                // C198.24,12.91 168.8,0 133.55,0 (absolute cubic bezier: x1,y1 x2,y2 x,y)
                curveTo(198.24f, 12.91f, 168.8f, 0f, 133.55f, 0f)
                // C82.49,0 38.39,29.3 16.92,71.95 (absolute cubic bezier - C harfi eksik ama önceki komut devam ediyor)
                curveTo(82.49f, 0f, 38.39f, 29.3f, 16.92f, 71.95f)
                lineToRelative(42.21f, 32.78f)
                curveToRelative(10.59f, -31.48f, 39.89f, -54.25f, 74.41f, -54.25f)
                close()
            }
        }.build()
        
        return rememberVectorPainter(googleIcon)
    }
    
    @Composable
    actual fun Apple(): Painter {
        val appleIcon = ImageVector.Builder(
            name = "Apple",
            defaultWidth = 800.dp,
            defaultHeight = 800.dp,
            viewportWidth = 560.03f,
            viewportHeight = 560.03f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1f,
                stroke = null,
                pathFillType = PathFillType.NonZero
            ) {
                // Main apple body path
                moveTo(432.85f, 297.53f)
                curveToRelative(0.79f, 84.75f, 74.35f, 112.96f, 75.16f, 113.31f)
                curveToRelative(-0.62f, 1.99f, -11.75f, 40.19f, -38.76f, 79.65f)
                curveToRelative(-23.34f, 34.12f, -47.57f, 68.11f, -85.73f, 68.81f)
                curveToRelative(-37.5f, 0.69f, -49.56f, -22.24f, -92.43f, -22.24f)
                curveToRelative(-42.86f, 0f, -56.26f, 21.53f, -91.75f, 22.93f)
                curveToRelative(-36.84f, 1.39f, -64.89f, -36.89f, -88.42f, -70.88f)
                curveToRelative(-48.09f, -69.53f, -84.85f, -196.48f, -35.5f, -282.17f)
                curveToRelative(24.52f, -42.55f, 68.33f, -69.5f, 115.88f, -70.19f)
                curveToRelative(36.17f, -0.69f, 70.32f, 24.34f, 92.43f, 24.34f)
                curveToRelative(22.1f, 0f, 63.59f, -30.1f, 107.21f, -25.68f)
                curveToRelative(18.26f, 0.76f, 69.52f, 7.38f, 102.43f, 55.55f)
                curveToRelative(-2.65f, 1.64f, -61.16f, 35.7f, -60.52f, 106.56f)
                
                // Leaf path
                moveTo(362.38f, 89.42f)
                curveToRelative(19.56f, -23.67f, 32.72f, -56.63f, 29.13f, -89.42f)
                curveToRelative(-28.19f, 1.13f, -62.28f, 18.78f, -82.5f, 42.44f)
                curveToRelative(-18.12f, 20.95f, -33.99f, 54.49f, -29.71f, 86.63f)
                curveToRelative(31.42f, 2.43f, 63.52f, -15.97f, 83.08f, -39.65f)
                close()
            }
        }.build()
        
        return rememberVectorPainter(appleIcon)
    }
}

