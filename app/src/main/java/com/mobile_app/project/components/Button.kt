package com.mobile_app.project.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class ButtonVariants {
    Primary,
    Secondary,
}


@Composable
fun StyledButton(
    color: ButtonVariants = ButtonVariants.Primary,
    onClick: () -> Unit,
    text: String,
    icon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    val secondaryButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color.DarkGray,
        contentColor = Color.White,
    )

    val internalColor: ButtonColors = when (color) {
        ButtonVariants.Primary -> {
            ButtonDefaults.buttonColors()
        }
        ButtonVariants.Secondary -> {
            secondaryButtonColors
        }
    }
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        enabled = enabled,
        colors = internalColor,
        border = BorderStroke(0.dp, Color.Transparent),
        modifier = modifier.shadow(elevation = 0.dp),
    ) {
        if (icon != null) {
            icon()
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        }
        Text(
            text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}