package com.heyanle.easybangumi4.compose.main.more

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.HistoryToggleOff
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.outlined.Report
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.heyanle.easybangumi4.ABOUT
import com.heyanle.easybangumi4.LocalNavController
import com.heyanle.easybangumi4.R
import com.heyanle.easybangumi4.SOURCE_MANAGER
import com.heyanle.easybangumi4.compose.common.BooleanPreferenceItem
import com.heyanle.easybangumi4.compose.common.OkImage
import com.heyanle.easybangumi4.compose.setting.SettingPage
import com.heyanle.easybangumi4.navigationSetting
import com.heyanle.easybangumi4.preferences.SettingMMKVPreferences
import com.heyanle.easybangumi4.preferences.SettingPreferences
import com.heyanle.injekt.core.Injekt

/**
 * Created by HeYanLe on 2023/3/22 15:29.
 * https://github.com/heyanLE
 */

@Composable
fun More() {

    val nav = LocalNavController.current

    val settingPreferences: SettingPreferences by Injekt.injectLazy()
    val settingMMKVPreferences: SettingMMKVPreferences by Injekt.injectLazy()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
    ) {
        EasyBangumiCard()
        HorizontalDivider()
        BooleanPreferenceItem(
            title = {
                Text(text = stringResource(id = com.heyanle.easy_i18n.R.string.in_private))
            },
            subtitle = {
                Text(text = stringResource(id = com.heyanle.easy_i18n.R.string.in_private_msg))
            },
            icon = {
                Icon(
                    Icons.Filled.HistoryToggleOff,
                    contentDescription = stringResource(id = com.heyanle.easy_i18n.R.string.in_private)
                )
            },
            preference = settingPreferences.isInPrivate
        )
        HorizontalDivider()

        ListItem(
            modifier = Modifier.clickable {
                nav.navigate(SOURCE_MANAGER)
            },
            headlineContent = { Text(text = stringResource(id = com.heyanle.easy_i18n.R.string.source_manage)) },
            leadingContent = {
                Icon(
                    Icons.Filled.History,
                    contentDescription = stringResource(id = com.heyanle.easy_i18n.R.string.source_manage)
                )
            }
        )

        ListItem(
            modifier = Modifier.clickable {
                nav.navigationSetting(SettingPage.Appearance)
            },
            headlineContent = { Text(text = stringResource(id = com.heyanle.easy_i18n.R.string.appearance_setting)) },
            leadingContent = {
                Icon(
                    Icons.Filled.ColorLens,
                    contentDescription = stringResource(id = com.heyanle.easy_i18n.R.string.appearance_setting)
                )
            }
        )

        ListItem(
            modifier = Modifier.clickable {
                nav.navigationSetting(SettingPage.Player)
            },
            headlineContent = { Text(text = stringResource(id = com.heyanle.easy_i18n.R.string.player_setting)) },
            leadingContent = {
                Icon(
                    Icons.Filled.PlayCircle,
                    contentDescription = stringResource(id = com.heyanle.easy_i18n.R.string.player_setting)
                )
            }
        )



        HorizontalDivider()

        BooleanPreferenceItem(
            title = {
                Text(text = stringResource(id = com.heyanle.easy_i18n.R.string.web_view_compatible))
            },
            subtitle = {
                Text(text = stringResource(id = com.heyanle.easy_i18n.R.string.web_view_compatible_msg))
            },
            icon = {
                Icon(
                    Icons.Filled.Public,
                    contentDescription = stringResource(id = com.heyanle.easy_i18n.R.string.web_view_compatible)
                )
            },
            preference = settingMMKVPreferences.webViewCompatible
        )
        HorizontalDivider()
        ListItem(
            modifier = Modifier.clickable {
                nav.navigate(ABOUT)
            },
            headlineContent = { Text(text = stringResource(id = com.heyanle.easy_i18n.R.string.about)) },
            leadingContent = {
                Icon(
                    Icons.Outlined.Report,
                    contentDescription = stringResource(id = com.heyanle.easy_i18n.R.string.about)
                )
            }
        )

    }

}

@Composable
fun EasyBangumiCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OkImage(
            modifier = Modifier.size(64.dp),
            image = R.mipmap.logo_new,
            contentDescription = stringResource(com.heyanle.easy_i18n.R.string.app_name)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = stringResource(id = com.heyanle.easy_i18n.R.string.app_name))
    }

}

