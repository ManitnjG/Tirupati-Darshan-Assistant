package com.manitnjg.tirupatidarshan.ui
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
val Navy=Color(0xFF06283D)
val Navy2=Color(0xFF0B3954)
val Gold=Color(0xFFF5B82E)
val GoldSoft=Color(0xFFFFE7A6)
val Cream=Color(0xFFFFF9ED)
val Success=Color(0xFF15803D)
val Warning=Color(0xFFD97706)
private val Scheme=lightColorScheme(primary=Gold,onPrimary=Color(0xFF2A210B),secondary=Navy2,onSecondary=Color.White,background=Cream,onBackground=Navy,surface=Color.White,onSurface=Navy,surfaceVariant=Color(0xFFF3EEE3),outline=Color(0xFFD5CDBE))
@Composable fun TirupatiTheme(content:@Composable()->Unit){MaterialTheme(colorScheme=Scheme,typography=Typography(),content=content)}