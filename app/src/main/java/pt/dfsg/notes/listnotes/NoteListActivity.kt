package pt.dfsg.notes.listnotes

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.*
import pt.dfsg.notes.R
import pt.dfsg.notes.addnote.AddNoteActivity
import pt.dfsg.notes.db.Note
import pt.dfsg.notes.editnote.EditNoteActivity
import pt.dfsg.notes.viewnote.ViewNoteActivity
import java.util.*

class NoteListActivity : AppCompatActivity(), View.OnClickListener,
    NoteListAdapter.ClickCallBacks {


    private lateinit var noteListAdapter: NoteListAdapter
    private lateinit var viewModel: NoteListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        noteListAdapter = NoteListAdapter(this, ArrayList(), this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = noteListAdapter

        viewModel = ViewModelProviders.of(this).get(NoteListViewModel::class.java)
        viewModel.getAllNotes()?.observe(this,
            Observer { note -> note?.let { noteListAdapter.addNotes(it) } })
    }

    private fun confirmDelete(note: Note) {
        alert("Do you want to delete this note?") {
            yesButton { viewModel.deleteNoteAnko(note) }
            noButton { }
        }.show()
    }

    override fun onClick(v: View?) {
        val note = v?.tag as Note
        startActivity<ViewNoteActivity>("ID" to note.id.toString())
    }

    override fun onNoteEditClick(note: Note) {
        startActivity<EditNoteActivity>("ID" to note.id.toString())
    }

    override fun onShareClick(note: Note) {
        share(note.content,note.title)
    }

    override fun onNoteDeleteClick(note: Note) {
        confirmDelete(note)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_new_note -> startActivity<AddNoteActivity>()
            R.id.action_settings -> toast("TODO")
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

}
