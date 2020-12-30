package uk.ac.aber.dcs.cs31620.phrasepad.ui.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.language_list_item.view.*
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language

class SetLanguagesAutocompleteAdapter(context: Context, private val layoutResource: Int, private val languages: MutableList<Language>) : ArrayAdapter<Language>(context, layoutResource, languages) {
    override fun getCount(): Int = languages.size

    override fun getItem(position: Int): Language = languages[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView?: LayoutInflater.from(context).inflate(layoutResource, parent, false)
        view.languageFlag.setImageDrawable(languages[position].getFlag(context))
        view.languageName.text = languages[position].getNativeName()
        return view
    }
}