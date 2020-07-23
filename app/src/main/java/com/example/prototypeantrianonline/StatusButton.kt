package com.example.prototypeantrianonline

import android.view.View
import android.widget.Button
import android.widget.ProgressBar

class StatusButton {
    fun disable(button: Button, progressBar: ProgressBar){
        button.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }
    fun enable(button: Button, progressBar: ProgressBar){
        button.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }
}