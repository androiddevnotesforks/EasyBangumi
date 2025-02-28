package com.heyanle.easybangumi4.compose.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.heyanle.easybangumi4.base.preferences.Preference
import kotlinx.coroutines.launch

/**
 * Created by HeYanLe on 2023/3/22 16:06.
 * https://github.com/heyanLE
 */
@Composable
fun BooleanPreferenceItem(
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit),
    subtitle: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    change: Boolean,
    onChange: (Boolean) -> Unit,
) {
    ListItem(
        modifier = modifier.clickable {
            onChange(!change)
        },
        headlineContent = title,
        leadingContent = icon,
        supportingContent = subtitle,
        trailingContent = {
            Switch(checked = change, onCheckedChange = {
                onChange(it)
            })
        }

    )
}


@Composable
fun BooleanPreferenceItem(
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit),
    subtitle: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    preference: Preference<Boolean>
) {
    val value by preference.flow().collectAsState(preference.get())
    val scope = rememberCoroutineScope()
    BooleanPreferenceItem(
        modifier,
        title,
        subtitle,
        icon,
        value
    ) {
        scope.launch {
            preference.set(it)
        }
    }
}

@Composable
inline fun <reified T : Enum<T>> EmumPreferenceItem(
    modifier: Modifier = Modifier,
    noinline title: @Composable (() -> Unit),
    noinline icon: @Composable (() -> Unit)? = null,
    textList: List<String>,
    preference: Preference<T>,
    noinline onChangeListener: (T) -> Unit,
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    val value by preference.flow().collectAsState(preference.get())
    val scope = rememberCoroutineScope()

    ListItem(
        modifier = modifier.clickable {
            showDialog = true
        },
        headlineContent = title,
        leadingContent = icon,
        supportingContent = {
            Text(text = textList[value.ordinal])
        },
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = stringResource(id = com.heyanle.easy_i18n.R.string.cancel))
                }
            },
            title = title,
            text = {
                LazyColumn() {
                    items(textList.size) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                scope.launch {
                                    showDialog = false
                                    val t = enumValues<T>()[it]
                                    preference.set(t)
                                    onChangeListener(t)
                                }
                            }
                        ) {
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(text = textList[it])
                            Spacer(modifier = Modifier.weight(1f))
                            RadioButton(selected = it == value.ordinal, onClick = {
                                scope.launch {
                                    showDialog = false
                                    val t = enumValues<T>()[it]
                                    preference.set(t)
                                    onChangeListener(t)
                                }
                            })
                            Spacer(modifier = Modifier.size(4.dp))
                        }
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StringSelectPreferenceItem(
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit),
    icon: @Composable (() -> Unit)? = null,
    textList: List<String>,
    select: Int,
    onChange: (Int) -> Unit = {},
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    ListItem(
        modifier = modifier.clickable {
            showDialog = true
        },
        headlineContent = title,
        leadingContent = icon,
        supportingContent = {
            Text(text = textList.get(select))
        },
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = stringResource(id = com.heyanle.easy_i18n.R.string.cancel))
                }
            },
            title = title,
            text = {
                LazyColumn() {
                    items(textList.size) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                onChange(it)
                            }
                        ) {
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(text = textList[it])
                            Spacer(modifier = Modifier.weight(1f))
                            RadioButton(selected = it == select, onClick = {
                                onChange(it)
                            })
                            Spacer(modifier = Modifier.size(4.dp))
                        }
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StringSelectPreferenceItem(
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit),
    icon: @Composable (() -> Unit)? = null,
    textList: List<String>,
    preference: Preference<Int>,
    onChange: (Int) -> Unit = {},
) {

    val value by preference.flow().collectAsState(preference.get())
    val scope = rememberCoroutineScope()
    StringSelectPreferenceItem(modifier, title, icon, textList, value) {
        scope.launch {
            preference.set(it)
            onChange(it)
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StringEditPreferenceItem(
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit),
    icon: @Composable (() -> Unit)? = null,
    value: String,
    onEdit: (String) -> Unit = {},
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    ListItem(
        modifier = modifier.clickable {
            showDialog = true
        },
        headlineContent = title,
        leadingContent = icon,
        supportingContent = {
            Text(text = value)
        },
    )

    if (showDialog) {

        var tv by remember {
            mutableStateOf(value)
        }

        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onEdit(tv)
                }) {
                    Text(text = stringResource(id = com.heyanle.easy_i18n.R.string.confirm))
                }
            },
            title = title,
            text = {

                TextField(value = tv, onValueChange = { tv = it })
            }
        )
    }
}