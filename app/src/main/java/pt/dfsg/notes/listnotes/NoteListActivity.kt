package pt.dfsg.notes.listnotes

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import pt.dfsg.notes.utils.ID
import pt.dfsg.notes.utils.timeFormat
import pt.dfsg.notes.viewnote.ViewNoteActivity
import java.util.*


class NoteListActivity : AppCompatActivity(), View.OnClickListener,
    NoteListAdapter.ClickCallBacks {

    private lateinit var noteListAdapter: NoteListAdapter
    private lateinit var viewModel: NoteListViewModel

    private var calendar: Calendar = Calendar.getInstance()
    private var year = 0
    private var month = 0
    private var day = 0
    private var hour = 0
    private var minute = 0

    init {
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
    }

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

    override fun onClick(v: View?) {
        val note = v?.tag as Note
        startActivity<ViewNoteActivity>(ID to note.id.toString())
    }

    override fun onNoteEditClick(note: Note) {
        startActivity<EditNoteActivity>(ID to note.id.toString())
    }

    override fun onShareClick(note: Note) {
        share(note.content, note.title)
    }

    override fun onNoteDeleteClick(note: Note) {
        confirmDelete(note)
    }

    override fun onSetReminderClick(note: Note) {
        showDateDialog(note)
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

    private fun confirmDelete(note: Note) {
        alert("Do you want to delete this note?") {
            yesButton { viewModel.deleteNote(note) }
            noButton { }
        }.show()
    }

    private fun showDateDialog(note: Note) {
        DatePickerDialog(
            this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                showTimeDialog(note)
            }, year, month, day
        ).show()
    }

    private fun showTimeDialog(note: Note) {
        TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
                calendar.set(Calendar.MINUTE, selectedMinute)
                calendar.set(Calendar.SECOND, 0)

                viewModel.setAlarm(
                    this,
                    calendar.timeInMillis,
                    "Reminder",
                    note.title,
                    note.id.toString()
                )
                viewModel.updateNote(note.copy(hasReminder = true, reminder = calendar.time))

                toast("Alarm Set ${timeFormat().format(calendar.time)}")
            }, hour, minute, true
        ).show()
    }
}