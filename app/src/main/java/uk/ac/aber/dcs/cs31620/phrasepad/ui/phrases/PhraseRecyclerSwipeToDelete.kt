package uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases

import android.content.Context
import android.graphics.Paint
import androidx.recyclerview.widget.ItemTouchHelper

abstract class PhraseRecyclerSwipeToDelete : ItemTouchHelper.Callback() {
    private lateinit var adapter: PhrasesRecyclerAdapter


}