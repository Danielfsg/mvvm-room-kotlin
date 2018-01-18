package pt.dfsg.notes.viewnote

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_view_note.*
import kotlinx.android.synthetic.main.content_view_note.*
import pt.dfsg.notes.R
import java.text.DateFormat

class ViewNoteActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_note)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { finish() }

        val id = intent.getStringExtra("ID")

        viewModel = ViewModelProviders.of(this, ViewNoteViewModel.Factory(application, id))
            .get(ViewNoteViewModel::class.java)

        viewModel.getNoteById()?.observe(this, Observer { note ->
            val dateFormat: DateFormat = DateFormat.getDateInstance()
            lbl_note_title.text = note?.title
            lbl_note_content.text = note?.content
            lbl_note_date.text = dateFormat.format(note?.date)
        })

    }
}
