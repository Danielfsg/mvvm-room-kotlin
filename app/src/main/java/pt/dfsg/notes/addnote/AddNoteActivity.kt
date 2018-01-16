package pt.dfsg.notes.addnote

import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_note.*
import kotlinx.android.synthetic.main.content_add_note.*
import pt.dfsg.notes.R
import pt.dfsg.notes.db.Note
import java.util.*

class AddNoteActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private var calendar: Calendar = Calendar.getInstance()
    private var date: Date = Date()
    private lateinit var datePickerDialog: DatePickerDialog

    private lateinit var viewModel: AddNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(AddNoteViewModel::class.java)
        datePickerDialog = DatePickerDialog(this, this@AddNoteActivity,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))

        btn_note_date.setOnClickListener { datePickerDialog.show() }

        fab.setOnClickListener { addNote() }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        date = calendar.time
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add -> addNote()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    fun addNote() {
        if (txt_note_title.text == null || txt_note_content.text == null)
            Toast.makeText(this, "something", Toast.LENGTH_SHORT).show()
        else {
            viewModel.addNote(Note(
                    title = txt_note_title.text.toString(),
                    content = txt_note_content.text.toString(),
                    date = date))
            finish()
        }
    }
}
