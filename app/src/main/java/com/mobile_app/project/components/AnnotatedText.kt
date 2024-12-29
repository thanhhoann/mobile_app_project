package com.mobile_app.project.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString

data class TextAnnotation(
    val start: Int,
    val end: Int,
    val style: SpanStyle,
    val onClick: (() -> Unit)? = null
)

@Composable
fun AnnotatedText(
    text: String,
    annotations: List<TextAnnotation>,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    val annotatedString = buildAnnotatedStringWithAnnotations(text, annotations)

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            val annotation = annotations.find {
                offset in it.start until it.end
            }
            annotation?.onClick?.invoke()
        },
        style = textStyle,
        modifier = modifier
    )
}


private fun buildAnnotatedStringWithAnnotations(
    text: String,
    annotations: List<TextAnnotation>
): AnnotatedString {
    return buildAnnotatedString {
        append(text)
        annotations.forEach {
            addStyle(
                style = it.style,
                start = it.start,
                end = it.end
            )
        }
    }
}