package ru.fav.moneytrace.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.theme.Providers

/**
 * Диалог для отображения ошибок в стиле приложения.
 *
 * @param title Заголовок диалога
 * @param message Текст сообщения об ошибке
 * @param onDismiss Callback при закрытии диалога
 * @param modifier Модификатор для настройки внешнего вида
 * @param confirmButtonText Текст кнопки подтверждения
 * @param dismissButtonText Текст кнопки отмены (если null, кнопка не отображается)
 * @param onConfirm Callback при нажатии кнопки подтверждения
 * @param backgroundColor Цвет фона диалога
 * @param contentColor Цвет текста
 * @param iconTint Цвет иконки
 * @param shape Форма диалога
 */

@Composable
fun MTErrorDialog(
    title: String = stringResource(R.string.error),
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    confirmButtonText: String = stringResource(R.string.ok),
    dismissButtonText: String? = null,
    onConfirm: (() -> Unit)? = null,
    backgroundColor: Color = Providers.color.secondaryContainer,
    contentColor: Color = Providers.color.onSurface,
    iconTint: Color = Providers.color.onSurface,
    shape: Shape = Providers.shape.l
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = modifier,
            color = backgroundColor,
            contentColor = contentColor,
            shape = shape
        ) {
            Column(
                modifier = Modifier.padding(Providers.spacing.l)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MTIcon(
                        painter = painterResource(R.drawable.ic_info),
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.padding(end = Providers.spacing.m)
                    )

                    MTText(
                        text = title,
                        style = Providers.typography.bodyXL,
                        color = contentColor,
                        contentPadding = PaddingValues(
                            vertical = Providers.spacing.m,
                            horizontal = Providers.spacing.none
                        )
                    )
                }

                Spacer(modifier = Modifier.height(Providers.spacing.m))

                MTText(
                    text = message,
                    style = Providers.typography.bodyL,
                    color = contentColor.copy(alpha = 0.8f),
                    contentPadding = PaddingValues(Providers.spacing.none)
                )

                Spacer(modifier = Modifier.height(Providers.spacing.l))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    dismissButtonText?.let { text ->
                        TextButton(onClick = onDismiss) {
                            MTText(
                                text = dismissButtonText,
                                color = contentColor.copy(alpha = 0.7f),
                                style = Providers.typography.bodyL,
                                contentPadding = PaddingValues(Providers.spacing.none)
                            )
                        }
                        Spacer(modifier = Modifier.width(Providers.spacing.s))
                    }

                    TextButton(
                        onClick = { onConfirm?.invoke() ?: onDismiss() },
                    ) {
                        MTText(
                            text = confirmButtonText,
                            color = Providers.color.primary,
                            style = Providers.typography.bodyL,
                            contentPadding = PaddingValues(Providers.spacing.none)
                        )
                    }
                }
            }
        }
    }
}