package com.bortolan.iquadriv2.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bortolan.iquadriv2.API.Libri.LibriAPI
import com.bortolan.iquadriv2.Interfaces.Libri.AnnouncementResponse
import com.bortolan.iquadriv2.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_book.*

class AddBook : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        supportActionBar?.title = "Nuovo annuncio"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    fun addAnnouncement() {
        if (isTitle().and(isPrice()).and(isISBN()).and(isSubject()).and(isEdition()).and(isClass()).and(isNotes())) {
            LibriAPI(this).mService.postAnnouncement(
                    title_book.editText?.text?.toString() ?: return,
                    isbn.editText?.text?.toString() ?: return,
                    subject.editText?.text?.toString() ?: return,
                    edition.editText?.text?.toString() ?: return,
                    classi.editText?.text?.toString() ?: return,
                    notes.editText?.text?.toString() ?: return,
                    price.editText?.text?.toString()?.toIntOrNull() ?: return
            ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ t: AnnouncementResponse? ->
                        finish()
                    }, Throwable::printStackTrace)
        }
    }

    fun isTitle(): Boolean {
        if (title_book.editText?.text.isNullOrBlank()) {
            title_book.isErrorEnabled = true
            title_book.error = "Inserire il titolo!"
            title_book.requestFocus()
            return false
        }
        title_book.isErrorEnabled = false
        return true
    }

    fun isPrice(): Boolean {
        if (price.editText?.text.isNullOrBlank()) {
            price.isErrorEnabled = true
            price.error = "Inserire il prezzo!"
            price.requestFocus()
            return false
        }
        price.isErrorEnabled = false
        return true
    }

    fun isISBN(): Boolean {
        if (isbn.editText?.text.isNullOrBlank()) {
            isbn.isErrorEnabled = true
            isbn.error = "Inserire il codice ISBN!"
            isbn.requestFocus()
            return false
        } else if (isbn.editText?.text?.length != 13) {
            isbn.isErrorEnabled = true
            isbn.error = "Il codice ISBN deve essere lungo 13 caratteri!"
            isbn.requestFocus()
            return false
        }
        isbn.isErrorEnabled = false
        return true
    }

    fun isSubject(): Boolean {
        if (subject.editText?.text.isNullOrBlank()) {
            subject.isErrorEnabled = true
            subject.error = "Inserire la materia!"
            subject.requestFocus()
            return false
        }
        subject.isErrorEnabled = false
        return true
    }

    fun isEdition(): Boolean {
        if (edition.editText?.text.isNullOrBlank()) {
            edition.isErrorEnabled = true
            edition.error = "Inserire l'edizione!"
            edition.requestFocus()
            return false
        } else if (edition.editText?.text?.length != 4) {
            edition.isErrorEnabled = true
            edition.error = "L'edizione deve essere lunga 4 caratteri!"
            edition.requestFocus()
            return false
        }
        edition.isErrorEnabled = false
        return true
    }

    fun isClass(): Boolean {
        if (classi.editText?.text.isNullOrBlank()) {
            classi.isErrorEnabled = true
            classi.error = "Inserire la classe!"
            classi.requestFocus()
            return false
        }
        classi.isErrorEnabled = false
        return true
    }

    fun isNotes(): Boolean {
        if (notes.editText?.text.isNullOrBlank()) {
            notes.isErrorEnabled = true
            notes.error = "Inserire maggiori dettagli!"
            notes.requestFocus()
            return false
        }
        notes.isErrorEnabled = false
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId ?: -1) {
            android.R.id.home -> finish()
            R.id.save -> addAnnouncement()
        }
        return super.onOptionsItemSelected(item)
    }
}
