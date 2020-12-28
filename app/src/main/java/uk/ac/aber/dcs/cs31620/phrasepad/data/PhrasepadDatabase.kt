package uk.ac.aber.dcs.cs31620.phrasepad.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.phrasepad.data.util.LanguageConverter
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase
import uk.ac.aber.dcs.cs31620.phrasepad.model.PhraseDao
import java.util.*

@Database(entities = [Phrase::class], version = 1)
@TypeConverters(LanguageConverter::class)
abstract class PhrasepadDatabase : RoomDatabase() {

    abstract fun phrasepadDao(): PhraseDao

    companion object {
        private var instance: PhrasepadDatabase? = null
        private val coroutineScope = CoroutineScope(Dispatchers.Main)

        fun getDatabase(context: Context): PhrasepadDatabase? {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder<PhrasepadDatabase>(context.applicationContext, PhrasepadDatabase::class.java, "phrasepad_database").allowMainThreadQueries().addCallback(roomDatabaseCallback(context)).build()
                }
                return instance!!
            }
        }

        private fun roomDatabaseCallback(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                }
            }
        }
    }
}