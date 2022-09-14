package com.tarikyasar.curmin.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tarikyasar.curmin.presentation.currency_watchlist_item.CurrencyWatchlistItem
import com.tarikyasar.curmin.presentation.ui.theme.CurminTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            CurminTheme {
                var changeValue1 by remember { mutableStateOf(Random.nextDouble(-0.25, 0.25)) }
                var changeValue2 by remember { mutableStateOf(Random.nextDouble(-0.25, 0.25)) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black,
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        CurrencyWatchlistItem(
                            base = "USD",
                            target = "TRY",
                            value = 10.0,
                            change = (changeValue1 * 100.0).roundToInt() / 100.0,
                            date = "14.09.2022"
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        CurrencyWatchlistItem(
                            base = "USD",
                            target = "EUR",
                            value = 0.99,
                            change = (changeValue2 * 100.0).roundToInt() / 100.0,
                            date = "14.09.2022"
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                changeValue1 = Random.nextDouble(-0.25, 0.25)
                                changeValue2 = Random.nextDouble(-0.25, 0.25)
                            },
                            content = {
                                Text("Randomize")
                            }
                        )
                    }
                }
            }
        }
    }
}