package uk.ac.aber.dcs.cs31620.phrasepad.ui.phrases

import android.content.SharedPreferences
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import uk.ac.aber.dcs.cs31620.phrasepad.R
import uk.ac.aber.dcs.cs31620.phrasepad.databinding.FragmentPhrasesBinding
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language
import uk.ac.aber.dcs.cs31620.phrasepad.model.Phrase
import uk.ac.aber.dcs.cs31620.phrasepad.model.PhraseViewModel
import java.util.*

class PhrasesFragment : Fragment() {

    private var oldPhraseList: LiveData<List<Phrase>>? = null

    private lateinit var phraseRecyclerAdapter: PhrasesRecyclerAdapter
    private lateinit var binding: FragmentPhrasesBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val phraseViewModel: PhraseViewModel by viewModels()

    private lateinit var sourceLang: Language
    private lateinit var destLang: Language

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhrasesBinding.inflate(inflater, container, false)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        sourceLang = Language(Locale(sharedPreferences.getString("source_lang", "eng")!!))
        destLang = Language(Locale(sharedPreferences.getString("dest_lang", "eng")!!))

        binding.phrasesListSwipeRefresh.setOnRefreshListener {
            sourceLang = Language(Locale(sharedPreferences.getString("source_lang", "eng")!!))
            destLang = Language(Locale(sharedPreferences.getString("dest_lang", "eng")!!))

            refreshRecyclerViewData(sourceLang, destLang)

            binding.phrasesListSwipeRefresh.isRefreshing = false
        }

        phraseRecyclerAdapter = PhrasesRecyclerAdapter(context)
        binding.phrasesList.adapter = phraseRecyclerAdapter
        binding.phrasesList.layoutManager = LinearLayoutManager(activity)

        addPhraseRecyclerView()
        refreshRecyclerViewData(sourceLang, destLang)

        return binding.root
    }

    private fun addPhraseRecyclerView() {
        val background = ColorDrawable()
        val backgroundColor = Color.parseColor("#B00020")
        val deleteIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_fluent_delete_24_filled)
        val wrappedDeleteIcon = DrawableCompat.wrap(deleteIcon!!)
        DrawableCompat.setTint(wrappedDeleteIcon, Color.WHITE)
        val intrinsicWidth = deleteIcon?.intrinsicWidth
        val intrinsicHeight = deleteIcon?.intrinsicHeight
        val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position = viewHolder.layoutPosition
                        val phrase: Phrase = phraseRecyclerAdapter.getPhraseAt(position)
                        phraseViewModel.delete(phrase)

                        Snackbar.make(
                            activity!!.findViewById(android.R.id.content),
                            "Deleted",
                            Snackbar.LENGTH_LONG
                        ).apply {
                            setAction("Undo") {
                                phraseViewModel.add(phrase)
                                binding.phrasesList.scrollToPosition(position)
                            }
                        }
                    }

                    override fun onChildDraw(
                        c: Canvas,
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        dX: Float,
                        dY: Float,
                        actionState: Int,
                        isCurrentlyActive: Boolean
                    ) {
                        val itemView = viewHolder.itemView
                        val height = itemView.bottom - itemView.top
                        val isCanceled = dX == 0f && !isCurrentlyActive

                        if (isCanceled) {
                            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            return
                        }

                        background.color = backgroundColor
                        background.setBounds(
                            itemView.right + dX.toInt(),
                            itemView.top,
                            itemView.right,
                            itemView.bottom
                        )
                        background.draw(c)

                        val deleteIconTop = itemView.top + (height - intrinsicHeight!!) / 2
                        val deleteIconMargin = (height - intrinsicHeight) / 2
                        val deleteIconLeft = (itemView.right - deleteIconMargin - intrinsicWidth!!)
                        val deleteIconRight = itemView.right - deleteIconMargin
                        val deleteIconBottom = deleteIconTop + intrinsicHeight

                        wrappedDeleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
                        wrappedDeleteIcon.draw(c)

                        super.onChildDraw(
                            c,
                            recyclerView,
                            viewHolder,
                            dX,
                            dY,
                            actionState,
                            isCurrentlyActive
                        )
                    }

                    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
                        c?.drawRect(left, top, right, bottom, clearPaint)
                    }
                }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.phrasesList)
    }

    private fun refreshRecyclerViewData(sourceLang: Language, destLang: Language) {
        val phraseList = phraseViewModel.getPhrases(sourceLang.getCode(), destLang.getCode())

        val didSetupWithSample = sharedPreferences.getBoolean("sample_database", true)

        // Clear the database if we didn't set up the application to use the sample database
        if (!didSetupWithSample)
            phraseViewModel.deleteAll()

        if (oldPhraseList != phraseList) {
            oldPhraseList?.removeObservers(viewLifecycleOwner)
            oldPhraseList = phraseList
        }

        if (!phraseList.hasObservers()) {
            phraseList.observe(viewLifecycleOwner) { phrases ->
                phraseRecyclerAdapter.changeData(phrases.toMutableList())
            }
        }
    }
}