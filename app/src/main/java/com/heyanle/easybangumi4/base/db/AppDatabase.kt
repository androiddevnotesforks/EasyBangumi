package com.heyanle.easybangumi4.base.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.heyanle.easybangumi4.Migrate
import com.heyanle.easybangumi4.base.db.dao.CartoonHistoryDao
import com.heyanle.easybangumi4.base.db.dao.CartoonStarDao
import com.heyanle.easybangumi4.base.db.dao.SearchHistoryDao
import com.heyanle.easybangumi4.base.entity.CartoonHistory
import com.heyanle.easybangumi4.base.entity.CartoonStar
import com.heyanle.easybangumi4.base.entity.SearchHistory

/**
 * Created by HeYanLe on 2023/1/17 0:26.
 * https://github.com/heyanLE
 */
@Database(
    entities = [
        CartoonStar::class,
        CartoonHistory::class,
        SearchHistory::class,
    ],
    autoMigrations = [],
    version = 4,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun searchHistoryDao(): SearchHistoryDao
    val searchHistory: SearchHistoryDao by lazy { searchHistoryDao() }

    abstract fun cartoonStarDao(): CartoonStarDao
    val cartoonStar: CartoonStarDao by lazy { cartoonStarDao() }

    abstract fun cartoonHistoryDao(): CartoonHistoryDao
    val cartoonHistory: CartoonHistoryDao by lazy { cartoonHistoryDao() }

    companion object {
        fun build(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "easy_cartoon"
            ).apply {
                Migrate.getDBMigration().forEach {
                    addMigrations(it)
                }
            }.build()
        }
    }

}