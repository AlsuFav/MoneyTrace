package ru.fav.moneytrace.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import ru.fav.moneytrace.ui.theme.Providers

/**
 * Кастомное текстовое поле в стиле приложения.
 *
 * @param value Текущее значение поля
 * @param onValueChange Callback при изменении значения
 * @param modifier Модификатор для настройки внешнего вида
 * @param placeholder Текст-заполнитель
 * @param leadingIcon Иконка в начале поля
 * @param trailingIcon Иконка в конце поля
 * @param backgroundColor Цвет фона поля
 * @param contentPadding Внутренние отступы
 * @param height Высота поля
 * @param keyboardOptions Настройки клавиатуры
 * @param keyboardActions Действия клавиатуры
 */

@Composable
fun MTTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: (@Composable (() -> Unit))? = null,
    trailingIcon: (@Composable (() -> Unit))? = null,
    backgroundColor: Color = Providers.color.surface,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = Providers.spacing.none,
        vertical = Providers.spacing.none
    ),
    height: Dp = Providers.componentSize.textFieldMedium,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    Surface(
        color = backgroundColor,
        modifier = modifier.height(height)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            singleLine = true,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
        )
    }
}