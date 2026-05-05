package com.serveterdogan.shopapp.ui.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
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
import com.serveterdogan.shopapp.domain.model.Product
import com.serveterdogan.shopapp.domain.model.Review
import com.serveterdogan.shopapp.ui.theme.LuxeColors

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailsScreen(
    product: Product?,
    onBackClick: () -> Unit,
    onAddToCartClick: (Product) -> Unit
) {
    if (product == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = LuxeColors.Primary)
        }
        return
    }

    val displayImages = if (product.images.isNotEmpty()) product.images else listOf(product.thumbnail)
    val pagerState = rememberPagerState(pageCount = { displayImages.size })

    Box(modifier = Modifier.fillMaxSize().background(LuxeColors.Background)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // 1. Slider Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    AsyncImage(
                        model = displayImages[page],
                        contentDescription = product.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                
                if (displayImages.size > 1) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 60.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        repeat(displayImages.size) { iteration ->
                            val color = if (pagerState.currentPage == iteration) LuxeColors.Primary else LuxeColors.Primary.copy(alpha = 0.3f)
                            val width = if (pagerState.currentPage == iteration) 32.dp else 8.dp
                            Box(
                                modifier = Modifier
                                    .width(width)
                                    .height(6.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .animateContentSize()
                            )
                        }
                    }
                }
            }

            // 2. Content Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-40).dp),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                color = LuxeColors.GlassBackground,
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .padding(bottom = 120.dp)
                ) {
                    // Title & Price Section
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(color = LuxeColors.Primary.copy(alpha = 0.1f), shape = CircleShape) {
                                    Text(
                                        text = "AUTHENTIC",
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = LuxeColors.Primary,
                                        letterSpacing = 1.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Star, null, tint = LuxeColors.Secondary, modifier = Modifier.size(14.dp))
                                    Text(text = "${product.rating}", fontSize = 12.sp, color = LuxeColors.OnSurfaceVariant, fontWeight = FontWeight.SemiBold)
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = product.title, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = LuxeColors.OnSurface, lineHeight = 34.sp)
                        }
                        
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "$${product.price}", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = LuxeColors.Primary)
                            if (product.discountPercentage > 0) {
                                Surface(color = LuxeColors.SecondaryContainer, shape = RoundedCornerShape(8.dp)) {
                                    Text(text = "-${product.discountPercentage}%", modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = LuxeColors.OnSecondaryContainer)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Description
                    Text(text = "DESCRIPTION", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = LuxeColors.Outline, letterSpacing = 1.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = product.description, fontSize = 16.sp, color = LuxeColors.OnSurfaceVariant, lineHeight = 24.sp)

                    Spacer(modifier = Modifier.height(32.dp))

                    // Features Bento
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        FeatureItem(Icons.Default.BrandingWatermark, product.brand, Modifier.weight(1f))
                        FeatureItem(Icons.Default.Category, product.category, Modifier.weight(1f))
                        FeatureItem(Icons.Default.VerifiedUser, "Original", Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    // Reviews Section
                    if (product.reviews.isNotEmpty()) {
                        Text(
                            text = "CUSTOMER REVIEWS (${product.reviews.size})",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = LuxeColors.Outline,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        product.reviews.forEach { review ->
                            ReviewItem(review)
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }

        // Floating Back Button
        Surface(
            modifier = Modifier.padding(24.dp).size(48.dp).clickable { onBackClick() },
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.7f),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.4f)),
            shadowElevation = 8.dp
        ) { Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.ArrowBack, null, tint = LuxeColors.Primary) } }

        // Sticky Bottom Bar
        Surface(
            modifier = Modifier.align(Alignment.BottomCenter).padding(24.dp).fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = Color.White.copy(alpha = 0.8f),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)),
            shadowElevation = 16.dp
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Surface(modifier = Modifier.size(56.dp), shape = RoundedCornerShape(16.dp), color = LuxeColors.SurfaceContainerHighest) {
                    Box(contentAlignment = Alignment.Center) { Icon(Icons.Outlined.FavoriteBorder, null, tint = LuxeColors.OnSurfaceVariant) }
                }
                Surface(modifier = Modifier.size(56.dp), shape = RoundedCornerShape(16.dp), color = LuxeColors.SurfaceContainerHighest) {
                    Box(contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Share, null, tint = LuxeColors.OnSurfaceVariant) }
                }
                Button(onClick = { onAddToCartClick(product) }, modifier = Modifier.weight(1f).height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = LuxeColors.Primary), shape = RoundedCornerShape(16.dp)) {
                    Icon(Icons.Default.ShoppingBag, null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Add to Cart", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = LuxeColors.SurfaceContainerLow.copy(alpha = 0.5f),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, LuxeColors.LumeBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.reviewerName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = LuxeColors.OnSurface
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(5) { index ->
                        Icon(
                            Icons.Default.Star,
                            null,
                            tint = if (index < review.rating) LuxeColors.Secondary else LuxeColors.OutlineVariant,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = review.comment,
                fontSize = 13.sp,
                color = LuxeColors.OnSurfaceVariant,
                lineHeight = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = review.date.take(10), // Tarihin sadece gün kısmını alalım
                fontSize = 10.sp,
                color = LuxeColors.Outline,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun FeatureItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, modifier: Modifier) {
    Surface(modifier = modifier, color = LuxeColors.SurfaceContainerLow, shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, LuxeColors.OutlineVariant.copy(alpha = 0.3f))) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, null, tint = LuxeColors.Primary, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text, fontSize = 10.sp, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}
