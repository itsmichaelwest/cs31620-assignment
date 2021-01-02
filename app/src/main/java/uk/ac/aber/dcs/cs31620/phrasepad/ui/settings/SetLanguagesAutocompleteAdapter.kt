package uk.ac.aber.dcs.cs31620.phrasepad.ui.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import uk.ac.aber.dcs.cs31620.phrasepad.R
import uk.ac.aber.dcs.cs31620.phrasepad.model.Language

class SetLanguagesAutocompleteAdapter(context: Context, private val layoutResource: Int, private val languages: MutableList<Language>) : ArrayAdapter<Language>(context, layoutResource, languages) {
    var isDeviceLangNames: Boolean = false
        get() = field
        set(value) { field = value }

    override fun getCount(): Int = languages.size

    override fun getItem(position: Int): Language = languages[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView?: LayoutInflater.from(context).inflate(layoutResource, parent, false)
        view.findViewById<ImageView>(R.id.languageFlag).setImageDrawable(languages[position].getFlag(context))

        if (isDeviceLangNames)
            view.findViewById<TextView>(R.id.languageName).text = languages[position].getDeviceLangName()
        else
            view.findViewById<TextView>(R.id.languageName).text = languages[position].getNativeName()

        return view
    }
}