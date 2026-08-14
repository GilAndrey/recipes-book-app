package br.com.fiap.recipes.screens

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fiap.recipes.ui.theme.RecipesTheme

@Composable
fun TopEndCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .size(width = 160.dp, height = 85.dp),
        shape = RoundedCornerShape(
            bottomStart = 85.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) { }
}

@Preview
@Composable
private fun TopEndCardPreview() {
    RecipesTheme() {
        TopEndCard()
    }
}

@Composable
fun BottomStartCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .size(width = 160.dp, height = 85.dp),
        shape = RoundedCornerShape(
            topEnd = 85.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) { }
}

@Preview
@Composable
private fun BottomStartCardPreview() {
    RecipesTheme() {
        BottomStartCard()
    }
}

