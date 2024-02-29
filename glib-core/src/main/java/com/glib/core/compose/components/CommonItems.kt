package com.glib.core.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamapp.teamapp.compose.theme.PaddingMedium
import com.teamapp.teamapp.compose.theme.RoundCornerMedium
import com.teamapp.teamapp.compose.theme.ScreenDefaultHorizontalPadding

@Composable
fun CommonErrorScreen(errorText: String, errorIcon: ImageVector? = null, retry: (() -> Unit)? = null) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = ScreenDefaultHorizontalPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (errorIcon != null) {
            Icon(
                modifier = Modifier.size(50.dp),
                imageVector = errorIcon,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(PaddingMedium))
        Text(text = errorText, fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        retry?.let {
            Spacer(modifier = Modifier.height(PaddingMedium))
            Button(
                onClick = retry,
                shape = RoundedCornerShape(RoundCornerMedium),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text(text = "Retry")
            }
        }
    }
}
