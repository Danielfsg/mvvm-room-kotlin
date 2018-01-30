package pt.dfsg.notes.listnotes

import android.content.Context
import android.graphics.drawable.Animatable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.note_item.view.*
import pt.dfsg.notes.R
import pt.dfsg.notes.db.Note
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class NoteListAdapter(
    var context: Context,
    private var noteList: List<Note>,
    private var clickCallBacks: NoteListAdapter.ClickCallBacks,
    private var onClickListener: View.OnClickListener
) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent?.context)
            .inflate(R.layout.note_item, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(noteList[position])

    }

    override fun getItemCount(): Int = noteList.size

    fun addNotes(newNotes: List<Note>) {
        this.noteList = newNotes
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) {
            val dateFormat: DateFormat = DateFormat.getDateInstance()
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            itemView.lbl_note_title.text = note.title
            itemView.lbl_note_content.text = note.content
            itemView.lbl_note_date.text = dateFormat.format(note.date)
            if (note.hasReminder && note.reminder!! > Calendar.getInstance().time)
                itemView.note_reminder_date.text = timeFormat.format(note.reminder)
            else
                itemView.note_reminder_date.text = String()
            itemView.tag = note
            itemView.setOnClickListener(onClickListener)
            itemView.note_share.setOnClickListener { clickCallBacks.onShareClick(note) }
            itemView.note_edit.setOnClickListener { clickCallBacks.onNoteEditClick(note) }
            itemView.note_delete.setOnClickListener { clickCallBacks.onNoteDeleteClick(note) }
            itemView.note_reminder.setOnClickListener { clickCallBacks.onSetReminderClick(note) }

            val animate = itemView.note_anim.drawable as Animatable

            itemView.note_anim.setOnClickListener {
                animate.start()
                animate.stop()
            }

            itemView.cardView.setCardBackgroundColor(note.color)
        }
    }

    interface ClickCallBacks {
        fun onShareClick(note: Note)
        fun onNoteEditClick(note: Note)
        fun onNoteDeleteClick(note: Note)
        fun onSetReminderClick(note: Note)
    }
}