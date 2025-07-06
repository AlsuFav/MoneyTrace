package ru.fav.moneytrace.account.impl.ui.update.component

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
import ru.fav.moneytrace.ui.component.MTText
import ru.fav.moneytrace.ui.theme.Providers

@Composable
fun EditableAccountItem(
    title: String,
    value: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    backgroundColor: Color = Providers.color.surface,
    mainColor: Color = Providers.color.onSurface,
    variantColor: Color = Providers.color.onSurfaceVariant,
    contentPadding: PaddingValues = PaddingValues(horizontal = Providers.spacing.m),
    height: Dp = Providers.spacing.xxl
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

            MTText(
                text = title,
                maxLines = 1,
                color = mainColor,
                modifier = Modifier.weight(1f)
            )

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = Providers.typography.bodyL.copy(
                    color = mainColor,
                    textAlign = TextAlign.End
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType
                ),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(contentAlignment = Alignment.CenterEnd) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = Providers.typography.bodyL.copy(
                                    color = variantColor,
                                    textAlign = TextAlign.End
                                )
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}