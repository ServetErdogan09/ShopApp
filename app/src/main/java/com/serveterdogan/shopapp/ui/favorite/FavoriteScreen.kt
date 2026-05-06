package com.serveterdogan.shopapp.ui.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serveterdogan.shopapp.domain.model.Product
import com.serveterdogan.shopapp.ui.home.StandardProductCard
import com.serveterdogan.shopapp.ui.home.FeaturedProductCard
import com.serveterdogan.shopapp.ui.theme.LuxeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    state: FavoriteState,
    onProductClick: (Int) -> Unit,
    onRemoveFavorite: (Int) -> Unit
) {
    val backgroundGradient = Brush.radialGradient(
        colors = listOf(LuxeColors.GradientStart, LuxeColors.GradientEnd),
        radius = 2000f
    )

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                LuxeColors.Background.copy(alpha = 0.85f),
                                Color.Transparent
                            )
                        )
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 24.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "FAVORİLERİM",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = LuxeColors.Primary,
                        letterSpacing = 2.sp
                    )
                }
            }
        },
        containerColor = Color.Transparent,
        modifier = Modifier.background(backgroundGradient)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = LuxeColors.Primary
                )
            } else if (state.favoriteList.isEmpty()) {
                EmptyFavorites()
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    items(state.favoriteList) { product ->
                        Column {
                            StandardProductCard(
                                product = product,
                                onClick = { onProductClick(product.id) },
                                onFavoriteClick = { onRemoveFavorite(product.id) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            // Zaman Bilgisi (Cached X min ago)
                            Text(
                                text = "Önbellek: ${product.lastUpdated?.toTimeAgo() ?: "Az önce"}",
                                fontSize = 10.sp,
                                color = LuxeColors.Outline,
                                modifier = Modifier.padding(start = 8.dp),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

// Zaman damgasını "X dakika önce" formatına çeviren yardımcı fonksiyon
fun Long.toTimeAgo(): String {
    val diff = System.currentTimeMillis() - this
    val minutes = diff / (1000 * 60)
    return when {
        minutes < 1 -> "Az önce"
        minutes < 60 -> "$minutes dk önce"
        minutes < 1440 -> "${minutes / 60} sa önce"
        else -> "${minutes / 1440} gün önce"
    }
}

@Composable
fun EmptyFavorites() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(120.dp),
            shape = androidx.compose.foundation.shape.CircleShape,
            color = LuxeColors.Primary.copy(alpha = 0.1f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = LuxeColors.Primary.copy(alpha = 0.3f)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Henüz Favoriniz Yok",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = LuxeColors.OnSurface
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Beğendiğiniz ürünleri buraya ekleyerek daha sonra kolayca bulabilirsiniz.",
            fontSize = 14.sp,
            color = LuxeColors.OnSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )
    }
}