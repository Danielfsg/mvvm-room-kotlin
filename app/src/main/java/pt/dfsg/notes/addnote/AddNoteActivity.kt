package pt.dfsg.notes.addnote

import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_add_note.*
import kotlinx.android.synthetic.main.content_add_note.*
import pt.dfsg.notes.R
import pt.dfsg.notes.db.Note
import java.text.DateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    View.OnClickListener {


    private var calendar = Calendar.getInstance()
    private var date = Date()
    private var color: Int = 0
    private lateinit var datePickerDialog: DatePickerDialog

    private lateinit var viewModel: AddNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel = ViewModelProviders.of(this).get(AddNoteViewModel::class.java)
        datePickerDialog = DatePickerDialog(
            this, this@AddNoteActivity,
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

        val dateFormat: DateFormat = DateFormat.getDateInstance()
        btn_note_date.setText(dateFormat.format(date), TextView.BufferType.EDITABLE)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_add -> addNote()
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
        cardView.setCardBackgroundColor(value)
    }

    private fun addNote() {
        if (txt_note_title.text == null || txt_note_content.text == null)
            Toast.makeText(this, "something", Toast.LENGTH_SHORT).show()
        else {
            if (color == 0) color = ContextCompat.getColor(this, R.color.White)
            viewModel.addNote(
                Note(
                    title = txt_note_title.text.toString(),
                    content = txt_note_content.text.toString(),
                    date = date,
                    color = color
                )
            )
            FancyToast.makeText(
                this,
                "Note Added",
                FancyToast.LENGTH_SHORT,
                FancyToast.SUCCESS,
                false
            ).show()
            finish()
        }
    }




}
