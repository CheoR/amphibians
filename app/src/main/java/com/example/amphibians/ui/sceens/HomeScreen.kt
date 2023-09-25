package com.example.amphibians.ui.sceens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R
import com.example.amphibians.model.AmphibianPhoto

@Composable
fun HomeScreen(
    amphibianUiState: AmphbianUIState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (amphibianUiState) {
        is AmphbianUIState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AmphbianUIState.Success -> PhotosGridScreen(amphibianUiState.photos, modifier)
        is AmphbianUIState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}


@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_correction_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun PhotosGridScreen(photos: List<AmphibianPhoto>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items = photos, key = { photo -> photo.name }) {photo ->
            AmphibianPhotoCard(
                photo,
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()
//                    .aspectRatio(1.5f)
            )
        }

    }
}

@Composable
fun AmphibianPhotoCard(photo: AmphibianPhoto, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = stringResource(R.string.amphibian_title, photo.name, photo.type),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                )

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(photo.imgSrc)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.amphibian_photo),
                contentScale = ContentScale.FillWidth,
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = photo.description,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Justify
            )
        }
    }
}

