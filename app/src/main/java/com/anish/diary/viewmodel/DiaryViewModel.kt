package com.anish.diary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.anish.diary.dao.DiaryDatabase
import com.anish.diary.entity.DiaryEntry
import kotlinx.coroutines.launch

class DiaryViewModel(application: Application) :
    AndroidViewModel(application) {

    private val _dao = DiaryDatabase
        .getDatabase(application)
        .diaryDao()

    val entries = _dao.getAllEntries()

    fun addEntry(title: String, description: String) {

        viewModelScope.launch {

            val count = _dao.getEntryCount()

            if (count < 3) {

                val entry = DiaryEntry(
                    title = title,
                    description = description
                )

                _dao.insertEntry(entry)
            }
        }
    }
}