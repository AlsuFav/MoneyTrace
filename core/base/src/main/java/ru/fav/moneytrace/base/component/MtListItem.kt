package ru.fav.moneytrace.base.component

import androidx.collection.emptyLongSet
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.fav.moneytrace.base.theme.Providers

@Composable
fun MTListItem(
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    title: String,
    subtitle: String? = null,
    trailingTitle: String? = null,
    trailingSubtitle: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    backgroundColor: Color = Providers.color.surface,
    onClick: (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = Providers.spacing.m, vertical = Providers.spacing.xs),
    textPadding: PaddingValues = PaddingValues(
        horizontal = Providers.spacing.none,
        vertical = Providers.spacing.l
    )
) {
    val interactionSource = remember { MutableInteractionSource() }
    val clickableModifier = if (onClick != null) {
        Modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick
        )
    } else {
        Modifier
    }

    Surface(
        modifier = modifier.then(clickableModifier),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                Box(modifier = Modifier.padding(end = Providers.spacing.m)) {
                    it()
                }
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {

                if (subtitle.isNullOrEmpty()) {
                    MTText(
                        text = title,
                        contentPadding = textPadding
                    )
                } else {
                    MTText(
                        text = title,
                        contentPadding = PaddingValues(
                            horizontal = Providers.spacing.none,
                            vertical = Providers.spacing.xxs
                        )
                    )

                    MTText(
                        text = subtitle,
                        style = Providers.typography.bodyM,
                        color = Providers.color.onSurfaceVariant,
                        contentPadding = PaddingValues(
                            horizontal = Providers.spacing.none,
                            vertical = Providers.spacing.xxs
                        )
                    )
                }
            }

            if (!trailingTitle.isNullOrEmpty()) {
                if (trailingSubtitle.isNullOrEmpty()) {
                    MTText(
                        text = trailingTitle,
                        contentPadding = textPadding
                    )
                } else {
                    MTText(trailingTitle)

                    MTText(
                        text = trailingSubtitle,
                        style = Providers.typography.bodyM,
                        color = Providers.color.onSurfaceVariant
                    )
                }
            }


            trailingIcon?.let {
                Box(modifier = Modifier.padding(start = Providers.spacing.s)) {
                    it()
                }
            }
        }
    }
}