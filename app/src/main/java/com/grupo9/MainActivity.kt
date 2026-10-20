package com.grupo9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.grupo9.ui.navigation.AppNavigation
import com.grupo9.ui.theme.BanqueteriaTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            BanqueteriaTheme {
                AppNavigation()
            }
        }
    }
}

