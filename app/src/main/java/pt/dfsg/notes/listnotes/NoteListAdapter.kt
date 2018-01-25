package pt.dfsg.notes.listnotes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.note_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import pt.dfsg.notes.R
import pt.dfsg.notes.db.Note
import java.text.DateFormat


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

            itemView.lbl_note_title.text = note.title
            itemView.lbl_note_content.text = note.content
            itemView.lbl_note_date.text = dateFormat.format(note.date)
            itemView.tag = note
            itemView.setOnClickListener(onClickListener)
            itemView.note_share.setOnClickListener {
                clickCallBacks.onShareClick(note)
            }
            itemView.note_edit.setOnClickListener {
                clickCallBacks.onNoteEditClick(note)
            }
            itemView.note_delete.setOnClickListener {
                clickCallBacks.onNoteDeleteClick(note)
            }

            itemView.cardView.setCardBackgroundColor(note.color)
        }
    }

    interface ClickCallBacks {
        fun onShareClick(note: Note)
        fun onNoteEditClick(note: Note)
        fun onNoteDeleteClick(note: Note)
    }
}