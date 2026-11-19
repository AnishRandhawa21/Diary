package com.anish.diary.dao

import androidx.room.*
import com.anish.diary.entity.DiaryEntry
import kotlinx.coroutines.flow.Flow
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Dao
interface DiaryDao {

    @Insert
    suspend fun insertEntry(entry: DiaryEntry)

    @Query("SELECT * FROM diary_entries ORDER BY id DESC")
    fun getAllEntries(): Flow<List<DiaryEntry>>

    @Query("SELECT COUNT(*) FROM diary_entries")
    suspend fun getEntryCount(): Int
}


@Database(
    entities = [DiaryEntry::class],
    version = 1,
    exportSchema = false
)
abstract class DiaryDatabase : RoomDatabase() {

    abstract fun diaryDao(): DiaryDao

    companion object {

        @Volatile
        private var INSTANCE: DiaryDatabase? = null

        fun getDatabase(context: Context): DiaryDatabase {

            return INSTANCE ?: synchronized(this) {

                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    DiaryDatabase::class.java,
                    "diary_database"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}