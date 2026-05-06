package com.serveterdogan.shopapp.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.serveterdogan.shopapp.domain.model.Product
import com.serveterdogan.shopapp.ui.theme.LuxeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    state: ProductState,
    onSearch: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onFavoriteClick: (Product) -> Unit,
    onProductClick: (Int) -> Unit
) {

    var searchText by remember { mutableStateOf("") }

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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "LUXE",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = LuxeColors.Primary,
                        letterSpacing = (-1).sp
                    )
                }
            }
        },
        containerColor = Color.Transparent,
        modifier = Modifier.background(backgroundGradient)
    ) { paddingValues ->
        
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = LuxeColors.Primary)
                }
            } else if (state.isError != null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.ErrorOutline, null, tint = LuxeColors.Error, modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = state.isError, color = LuxeColors.OnSurface, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { onSearch("") }, colors = ButtonDefaults.buttonColors(containerColor = LuxeColors.Primary)) {
                        Text("Tekrar Dene")
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    contentPadding = PaddingValues(bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    item(span = { GridItemSpan(2) }) {
                        Spacer(modifier = Modifier.height(24.dp))

                        OutlinedTextField(
                            value = searchText,
                            onValueChange = { 
                                searchText = it
                                onSearch(it) 
                            },
                            placeholder = { Text("Premium ürünleri keşfet...", color = LuxeColors.Outline) },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.Search, null, tint = LuxeColors.Outline) },
                            shape = CircleShape,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = LuxeColors.LumeBorder,
                                focusedBorderColor = LuxeColors.Primary.copy(alpha = 0.3f),
                                unfocusedContainerColor = LuxeColors.SurfaceContainerLow,
                                focusedContainerColor = LuxeColors.SurfaceContainerLow
                            )
                        )
                    }

                    item(span = { GridItemSpan(2) }) {
                        Row(
                            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            CategoryChip("Tümü", isSelected = state.selectedCategory == "Tümü") {
                                onCategoryClick("Tümü")
                            }
                            CategoryChip("smartphones", isSelected = state.selectedCategory == "smartphones") {
                                onCategoryClick("smartphones")
                            }
                            CategoryChip("laptops", isSelected = state.selectedCategory == "laptops") {
                                onCategoryClick("laptops")
                            }
                            CategoryChip("fragrances", isSelected = state.selectedCategory == "fragrances") {
                                onCategoryClick("fragrances")
                            }
                            CategoryChip("mens-watches", isSelected = state.selectedCategory == "mens-watches") {
                                onCategoryClick("mens-watches")
                            }
                        }
                    }

                    items(
                        count = state.products.size,
                        span = { index -> if (index == 0) GridItemSpan(2) else GridItemSpan(1) }
                    ) { index ->
                        val product = state.products[index]
                        if (index == 0) {
                            FeaturedProductCard(
                                product = product, 
                                onClick = { onProductClick(product.id) },
                                onFavoriteClick = { onFavoriteClick(product) }
                            )
                        } else {
                            StandardProductCard(
                                product = product, 
                                onClick = { onProductClick(product.id) },
                                onFavoriteClick = { onFavoriteClick(product) }
                            )
                        }
                    }


                    if (state.products.isEmpty() && !state.isLoading) {
                        item(span = { GridItemSpan(2) }) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 64.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    Icons.Default.SearchOff,
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp),
                                    tint = LuxeColors.OutlineVariant
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Üzgünüz, Aradığınız Ürün Bulunamadı",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = LuxeColors.OnSurface
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Farklı anahtar kelimeler veya kategoriler denemeye ne dersiniz?",
                                    fontSize = 14.sp,
                                    color = LuxeColors.OnSurfaceVariant,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 32.dp)
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                Button(
                                    onClick = {
                                        searchText = ""
                                        onSearch("")
                                              },
                                    colors = ButtonDefaults.buttonColors(containerColor = LuxeColors.Primary)
                                ) {
                                    Text("Tüm Ürünleri Gör")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FeaturedProductCard(product: Product, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
            .clickable { onClick() }
            .border(1.dp, LuxeColors.LumeBorder, RoundedCornerShape(32.dp)),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = LuxeColors.GlassBackground)
    ) {
        Column {
            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                AsyncImage(
                    model = product.thumbnail,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Surface(
                    modifier = Modifier.padding(20.dp).size(40.dp).align(Alignment.TopEnd).clickable { onFavoriteClick() },
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.3f),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
                ) {
                    Icon(
                        imageVector = if (product.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder, 
                        contentDescription = null, 
                        modifier = Modifier.padding(10.dp), 
                        tint = if (product.isFavorite) LuxeColors.Secondary else Color.White
                    )
                }
            }
            Column(modifier = Modifier.padding(24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, null, tint = LuxeColors.Primary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${product.rating} ÖNE ÇIKAN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = LuxeColors.Primary, letterSpacing = 2.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = product.title, fontSize = 28.sp, fontWeight = FontWeight.Black, color = LuxeColors.OnSurface, lineHeight = 32.sp)
                Spacer(modifier = Modifier.height(8.dp))
                product.description?.let { Text(text = it, fontSize = 14.sp, color = LuxeColors.OnSurfaceVariant, maxLines = 2, overflow = TextOverflow.Ellipsis) }
                Spacer(modifier = Modifier.height(24.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "$${product.price}", fontSize = 28.sp, fontWeight = FontWeight.Black, color = LuxeColors.Primary)
                    Button(onClick = onClick, colors = ButtonDefaults.buttonColors(containerColor = LuxeColors.Primary), shape = RoundedCornerShape(12.dp)) {
                        Text("DETAY", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun StandardProductCard(product: Product, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(1.dp, LuxeColors.LumeBorder, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = LuxeColors.GlassBackground)
    ) {
        Column {
            Box(modifier = Modifier.aspectRatio(1f).fillMaxWidth()) {
                AsyncImage(model = product.thumbnail, contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                Surface(
                    modifier = Modifier.padding(12.dp).size(32.dp).align(Alignment.TopEnd).clickable { onFavoriteClick() }, 
                    shape = CircleShape, 
                    color = Color.Black.copy(alpha = 0.3f)
                ) {
                    Icon(
                        imageVector = if (product.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder, 
                        contentDescription = null, 
                        modifier = Modifier.padding(8.dp), 
                        tint = if (product.isFavorite) LuxeColors.Secondary else Color.White
                    )
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = product.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = LuxeColors.OnSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "$${product.price}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = LuxeColors.Primary
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, null, tint = LuxeColors.OnSurfaceVariant, modifier = Modifier.size(12.dp))
                            Text(
                                text = product.rating.toString(),
                                fontSize = 12.sp,
                                color = LuxeColors.OnSurfaceVariant,
                                modifier = Modifier.padding(start = 2.dp)
                            )
                        }
                    }
                    // Küçük şık bir detay butonu/simgesi
                    Surface(
                        modifier = Modifier.size(32.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = LuxeColors.Primary
                    ) {
                        Icon(
                            Icons.Default.ArrowForward,
                            null,
                            modifier = Modifier.padding(8.dp).size(16.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryChip(text: String, isSelected: Boolean = false, onClick: () -> Unit) {
    Surface(
        color = if (isSelected) LuxeColors.Primary else LuxeColors.SurfaceContainer,
        shape = CircleShape,
        modifier = Modifier
            .clickable { onClick() }
            .border(width = if (isSelected) 0.dp else 1.dp, color = LuxeColors.LumeBorder, shape = CircleShape)
    ) {
        Text(text = text, modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp), fontSize = 14.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium, color = if (isSelected) Color.White else LuxeColors.OnSurfaceVariant)
    }
}