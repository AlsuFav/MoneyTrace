package ru.fav.moneytrace.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.ui.theme.Providers
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MTTimePicker(
    selectedTime: Date?,
    onTimeSelected: (Date) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Providers.color.secondaryContainer,
    textColor: Color = Providers.color.onSurface
) {
    val calendar = Calendar.getInstance()
    selectedTime?.let {
        calendar.time = it
    }

    val initialHour = calendar.get(Calendar.HOUR_OF_DAY)
    val initialMinute = calendar.get(Calendar.MINUTE)

    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true
    )

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = Providers.spacing.s,
            modifier = modifier,
            color = containerColor
        ) {
            Column(
                modifier = Modifier.padding(Providers.spacing.l),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimeInput(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        containerColor = containerColor,
                        timeSelectorSelectedContainerColor = Providers.color.primary,
                        timeSelectorUnselectedContainerColor = Providers.color.primaryContainer,
                        timeSelectorSelectedContentColor = Providers.color.onPrimary,
                        timeSelectorUnselectedContentColor = Providers.color.onPrimaryContainer
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Providers.spacing.l),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = stringResource(R.string.exit),
                            color = textColor
                        )
                    }

                    Spacer(modifier = Modifier.width(Providers.spacing.s))

                    TextButton(
                        onClick = {
                            val newCalendar = Calendar.getInstance()
                            if (selectedTime != null) {
                                newCalendar.time = selectedTime
                            }
                            newCalendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                            newCalendar.set(Calendar.MINUTE, timePickerState.minute)
                            newCalendar.set(Calendar.SECOND, 0)
                            newCalendar.set(Calendar.MILLISECOND, 0)

                            onTimeSelected(newCalendar.time)
                            onDismiss()
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.ok),
                            fontWeight = FontWeight.SemiBold,
                            color = textColor
                        )
                    }
                }
            }
        }
    }
}