package com.tarikyasar.curmin.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tarikyasar.curmin.R

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val progress by animateLottieCompositionAsState(composition)

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier
    )
}

@Composable
fun ErrorAnimation(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))
    val progress by animateLottieCompositionAsState(composition)

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier
    )
}

@Composable
fun WarningAnimation(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.warning))
    val progress by animateLottieCompositionAsState(composition)

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier,
        contentScale = ContentScale.FillBounds
    )
}