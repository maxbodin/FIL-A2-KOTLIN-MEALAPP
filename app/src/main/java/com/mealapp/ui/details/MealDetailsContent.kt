package com.mealapp.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mealapp.domain.model.MealDetails
import com.mealapp.ui.components.YoutubePlayer

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MealDetailsContent(details: MealDetails) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            AsyncImage(
                model = details.thumbnail,
                contentDescription = details.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                details.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                Badge(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                    Text(details.category, modifier = Modifier.padding(4.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Badge(containerColor = MaterialTheme.colorScheme.secondaryContainer) {
                    Text(details.area, modifier = Modifier.padding(4.dp))
                }
            }
        }

        if (details.tags.isNotEmpty()) {
            item {
                Text(
                    "Tags",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    details.tags.forEach { tag ->
                        SuggestionChip(onClick = {}, label = { Text(tag) })
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        details.youtubeUrl?.let { url ->
            item {
                Text(
                    "Video Tutorial",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                YoutubePlayer(youtubeUrl = url)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        item {
            Text(
                "Ingredients",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    details.ingredients.forEach { ingredient ->
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)) {
                            Text("•", color = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(ingredient.name, modifier = Modifier.weight(1f))
                            Text(ingredient.measure, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                "Instructions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = details.instructions,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}