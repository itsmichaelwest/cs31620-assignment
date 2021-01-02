package uk.ac.aber.dcs.cs31620.phrasepad.data

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.phrasepad.data.util.LanguageConverter
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase
import uk.ac.aber.dcs.cs31620.phrasepad.model.PhraseDao

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
                    instance = Room.databaseBuilder(context.applicationContext, PhrasepadDatabase::class.java, "phrasepad_database").allowMainThreadQueries().addCallback(roomDatabaseCallback(context)).build()
                }
                return instance!!
            }
        }

        private fun roomDatabaseCallback(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    coroutineScope.launch(Dispatchers.IO) {
                        populateSampleDatabase(context, getDatabase(context)!!)
                    }
                }
            }
        }

        // Populate the database if we've requested a sample
        private fun populateSampleDatabase(context: Context, instance: PhrasepadDatabase) {
            val sourceLang = "eng"
            val destLang = "cym"

            val phrase1 = Phrase(
                0,
                sourceLang,
                destLang,
                "Thank you",
                "Diolch"
            )

            val phrase2 = Phrase(
                0,
                sourceLang,
                destLang,
                "Excuse me",
                "Esgusodwch fi"
            )

            val phrase3 = Phrase(
                0,
                sourceLang,
                destLang,
                "Fair play",
                "Chwarae teg"
            )

            val phrase4 = Phrase(
                0,
                sourceLang,
                destLang,
                "Merry Christmas",
                "Nadolig Llawen"
            )

            val phrase5 = Phrase(
                0,
                sourceLang,
                destLang,
                "Happy New Year",
                "Blwyddyn Newydd Dda"
            )

            val phrase6 = Phrase(
                0,
                sourceLang,
                destLang,
                "Happy Birthday",
                "Penblwydd Hapus"
            )

            val phrase7 = Phrase(
                0,
                sourceLang,
                destLang,
                "Congratulations",
                "Llongyfarchiadau"
            )

            val phrase8 = Phrase(
                0,
                sourceLang,
                destLang,
                "It's windy",
                "Mae'n wyntog"
            )

            val phrase9 = Phrase(
                0,
                sourceLang,
                destLang,
                "Wales",
                "Cymru"
            )

            val phrase10 = Phrase(
                0,
                sourceLang,
                destLang,
                "Hello!",
                "Su'mae!"
            )

            val phrase11 = Phrase(
                0,
                sourceLang,
                destLang,
                "How are you?",
                "Sut dach chi?"
            )

            val phrase12 = Phrase(
                0,
                sourceLang,
                destLang,
                "Well done!",
                "Da iawn!"
            )

            val phrase13 = Phrase(
                0,
                sourceLang,
                destLang,
                "Thank you very much",
                "Diolch yn fawr iawn"
            )

            val phrase14 = Phrase(
                0,
                sourceLang,
                destLang,
                "Welcome",
                "Croeso"
            )

            val phrase15 = Phrase(
                0,
                sourceLang,
                destLang,
                "What is your name?",
                "Beth ydy eich enw chi?"
            )

            val phrase16 = Phrase(
                0,
                sourceLang,
                destLang,
                "Milk",
                "Llaeth"
            )

            val phrase17 = Phrase(
                0,
                sourceLang,
                destLang,
                "Sugar",
                "Siwgwr"
            )

            val phrase18 = Phrase(
                0,
                sourceLang,
                destLang,
                "Coffee",
                "Coffi"
            )

            val phrase19 = Phrase(
                0,
                sourceLang,
                destLang,
                "Excuse me",
                "Esgusodwch fi"
            )

            val phrase20 = Phrase(
                0,
                sourceLang,
                destLang,
                "Good night",
                "Nos da"
            )

            val phraseList = listOf(
                phrase1,
                phrase2,
                phrase3,
                phrase4,
                phrase5,
                phrase6,
                phrase7,
                phrase8,
                phrase9,
                phrase10,
                phrase11,
                phrase12,
                phrase13,
                phrase14,
                phrase15,
                phrase16,
                phrase17,
                phrase18,
                phrase19,
                phrase20
            )

            val dao = instance.phrasepadDao()
            dao.insertMultiplePhrases(phraseList)
        }
    }
}