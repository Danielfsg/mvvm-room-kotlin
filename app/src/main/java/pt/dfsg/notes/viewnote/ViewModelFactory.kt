package pt.dfsg.notes.viewnote

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class ViewModelFactory constructor(private var app: Application, private var params: String) :
        ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewNoteViewModel(params, app) as T
    }
}