package ru.fav.moneytrace.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import ru.fav.moneytrace.ui.theme.Providers

@Composable
fun MTEditableListItem(
    title: String? = null,
    value: String,
    trailingTitle: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    inputFilter: (String) -> Boolean = { true },
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    backgroundColor: Color = Providers.color.surface,
    mainColor: Color = Providers.color.onSurface,
    variantColor: Color = Providers.color.onSurfaceVariant,
    contentPadding: PaddingValues = PaddingValues(horizontal = Providers.spacing.m),
    height: Dp = Providers.spacing.xxxl
) {
    Surface(
        modifier = modifier,
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
                .height(height),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                Box(modifier = Modifier.padding(end = Providers.spacing.m)) {
                    it()
                }
            }

            if (title != null) {
                MTText(
                    text = title,
                    maxLines = 1,
                    color = mainColor,
                    modifier = Modifier.weight(1f)
                )
            }

            BasicTextField(
                value = value,
                onValueChange = { newValue ->
                    if (inputFilter(newValue)) {
                        onValueChange(newValue)
                    }
                },
                textStyle = Providers.typography.bodyL.copy(
                    color = mainColor,
                    textAlign = if (title != null) TextAlign.End else TextAlign.Start
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType
                ),
                singleLine = true,
                modifier = Modifier.weight(1f),
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = if (title != null) Alignment.CenterEnd else Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = Providers.typography.bodyL.copy(
                                    color = variantColor,
                                    textAlign = if (title != null) TextAlign.End else TextAlign.Start
                                )
                            )
                        }
                        innerTextField()
                    }
                }
            )

            if (trailingTitle != null) {
                MTText(
                    text = trailingTitle,
                    maxLines = 1,
                    color = mainColor,
                )
            }
        }
    }
}