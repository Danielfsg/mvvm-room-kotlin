package pt.dfsg.notes.listnotes

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import pt.dfsg.notes.R
import pt.dfsg.notes.addnote.AddNoteActivity
import pt.dfsg.notes.db.Note
import pt.dfsg.notes.viewnote.ViewNoteActivity
import java.util.*

class NoteListActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener {


    private lateinit var noteListAdapter: NoteListAdapter
    private lateinit var viewModel: NoteListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }


        noteListAdapter = NoteListAdapter(ArrayList(), this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = noteListAdapter

        viewModel = ViewModelProviders.of(this).get(NoteListViewModel::class.java)
        viewModel.getAllNote()?.observe(this,
                Observer { note -> note?.let { noteListAdapter.addNotes(it) } })
    }

    override fun onClick(v: View?) {
        val note = v?.tag as Note
        val intent = Intent(this@NoteListActivity, ViewNoteActivity::class.java)
        intent.putExtra("ID", note.id.toString())
        startActivity(intent)
    }

    override fun onLongClick(v: View?): Boolean {
        viewModel.deleteNote(v?.tag as Note)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
