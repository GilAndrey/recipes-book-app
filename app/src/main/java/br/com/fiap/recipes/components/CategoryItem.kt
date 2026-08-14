package br.com.fiap.recipes.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fiap.recipes.model.Category
import br.com.fiap.recipes.ui.theme.RecipesTheme

@Composable
fun CategoryItem(
    category: Category = Category(),
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(90.dp)
            .clickable(
                onClick = {
                    onClick()
                }
            )
    ) {
        Card(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .size(90.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = category.background
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(category.image!!),
                    contentDescription = "Chicken Image",
                    modifier = Modifier
                        .size(45.dp)
                )
            }
        }
        Text(
            text = category.name,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview
@Composable
private fun CategoryItemPreview() {
    RecipesTheme() {
        CategoryItem(onClick = {})
    }
}