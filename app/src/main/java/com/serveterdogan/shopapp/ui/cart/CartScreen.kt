package com.serveterdogan.shopapp.ui.cart

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.serveterdogan.shopapp.data.local.Entity.CartEntity
import com.serveterdogan.shopapp.ui.theme.LuxeColors
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.Brush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    state: CartState,
    onIncrement: (Int) -> Unit,
    onDecrement: (Int) -> Unit,
    onRemove: (Int) -> Unit,
    onCheckoutClick: () -> Unit
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
                        text = "SEPETİM",
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
            } else if (state.cartItems.isEmpty()) {
                EmptyCart()
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.cartItems, key = { it.id }) { item ->
                            CartItemCard(
                                item = item,
                                onIncrement = { onIncrement(item.id) },
                                onDecrement = { onDecrement(item.id) },
                                onRemove = { onRemove(item.id) }
                            )
                        }
                    }

                    // Checkout Summary Section
                    CheckoutSummary(
                        totalPrice = state.totalPrice,
                        onCheckoutClick = onCheckoutClick
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    item: CartEntity,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onRemove: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = LuxeColors.SurfaceContainerLow.copy(alpha = 0.6f),
        shape = RoundedCornerShape(24.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(100.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image
            Surface(
                modifier = Modifier.size(100.dp),
                shape = RoundedCornerShape(16.dp),
                color = LuxeColors.SurfaceContainerHighest
            ) {
                AsyncImage(
                    model = item.thumbnail,
                    contentDescription = item.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = LuxeColors.OnSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "$${item.price}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = LuxeColors.Primary
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                // Quantity Controls
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuantityButton(Icons.Default.Remove, onClick = onDecrement)
                    Text(
                        text = "${item.quantity}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = LuxeColors.OnSurface
                    )
                    QuantityButton(Icons.Default.Add, onClick = onIncrement)
                }
            }

            // Remove Button
            IconButton(
                onClick = onRemove,
                modifier = Modifier.align(Alignment.Top)
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = null,
                    tint = LuxeColors.Error,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun QuantityButton(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.size(32.dp).clickable { onClick() },
        shape = CircleShape,
        color = LuxeColors.SurfaceContainerHighest,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = LuxeColors.OnSurface
            )
        }
    }
}

@Composable
fun CheckoutSummary(totalPrice: Double, onCheckoutClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = LuxeColors.GlassHeader,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Ara Toplam", color = LuxeColors.Outline, fontSize = 14.sp)
                Text(text = "$${String.format("%.2f", totalPrice)}", color = LuxeColors.OnSurface, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Kargo", color = LuxeColors.Outline, fontSize = 14.sp)
                Text(text = "ÜCRETSİZ", color = LuxeColors.Primary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
            Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color.White.copy(alpha = 0.1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Toplam", color = LuxeColors.OnSurface, fontSize = 20.sp, fontWeight = FontWeight.Black)
                Text(
                    text = "$${String.format("%.2f", totalPrice)}",
                    color = LuxeColors.Primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onCheckoutClick,
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LuxeColors.Primary)
            ) {
                Text("ÖDEMEYE GEÇ", fontSize = 18.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null)
            }
        }
    }
}

@Composable
fun EmptyCart() {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(120.dp),
            shape = CircleShape,
            color = LuxeColors.Primary.copy(alpha = 0.1f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.ShoppingBag,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = LuxeColors.Primary.copy(alpha = 0.3f)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Sepetiniz Boş", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = LuxeColors.OnSurface)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Anlaşılan henüz bir şey eklememişsiniz. Keşfetmeye başlayın!",
            fontSize = 14.sp,
            color = LuxeColors.Outline,
            textAlign = TextAlign.Center
        )
    }
}