package pt.dfsg.notes.utils

import java.text.SimpleDateFormat
import java.util.*


const val ID = "ID"
const val ALERT_TITLE = "ALERT_TITLE"
const val ALERT_TEXT = "ALERT_TEXT"


const val PRIMARY_CHANNEL = "default"

fun timeFormat() = SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.getDefault())