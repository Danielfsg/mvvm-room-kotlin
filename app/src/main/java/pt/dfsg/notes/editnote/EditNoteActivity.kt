package pt.dfsg.notes.editnote

import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_add_note.*
import kotlinx.android.synthetic.main.content_add_note.*
import pt.dfsg.notes.R
import pt.dfsg.notes.db.Note
import pt.dfsg.notes.utils.ID
import java.text.DateFormat
import java.util.*

class EditNoteActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    View.OnClickListener {

    private var calendar = Calendar.getInstance()
    private var date = Date()
    private var color: Int = 0
    private val dateFormat: DateFormat = DateFormat.getDateInstance()
    private var id: String = String()

    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var viewModel: EditNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        id = intent.getStringExtra(ID)

        viewModel = ViewModelProviders.of(this, EditNoteViewModel.Factory(application, id))
            .get(EditNoteViewModel::class.java)

        viewModel.getNoteById()?.observe(this, Observer { note -> note?.let { setNote(it) } })

        datePickerDialog = DatePickerDialog(
            this, this@EditNoteActivity,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        btn_note_date.setOnClickListener { datePickerDialog.show() }

        note_color_white.setOnClickListener(this)
        note_color_red.setOnClickListener(this)
        note_color_green.setOnClickListener(this)
        note_color_blue.setOnClickListener(this)
        note_color_yellow.setOnClickListener(this)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        date = calendar.time

        btn_note_date.setText(dateFormat.format(date), TextView.BufferType.EDITABLE)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_add -> updateNote()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.note_color_white -> {
                setColor(R.color.White)
            }
            R.id.note_color_red -> {
                setColor(R.color.Red)
            }
            R.id.note_color_green -> {
                setColor(R.color.Green)
            }
            R.id.note_color_blue -> {
                setColor(R.color.Blue)
            }
            R.id.note_color_yellow -> {
                setColor(R.color.Yellow)
            }
        }
    }

    private fun setColor(value: Int) {
        color = ContextCompat.getColor(this, value)
        cardView.setCardBackgroundColor(color)
    }

    private fun setNote(note: Note) {
        txt_note_title.setText(note.title, TextView.BufferType.EDITABLE)
        txt_note_content.setText(note.content, TextView.BufferType.EDITABLE)
//        btn_note_date.setText(dateFormat.format(note.date), TextView.BufferType.EDITABLE)
        cardView.setCardBackgroundColor(note.color)
        color = note.color
    }

    private fun updateNote() {
        viewModel.updateNote(
            Note(
                id = id.toLong(),
                title = txt_note_title.text.toString(),
                content = txt_note_content.text.toString(),
                dateModified = date,
                color = color
            )
        )
        finish()
    }
}


