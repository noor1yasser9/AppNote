package com.nurbk.ps.noteapp.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.nurbk.ps.noteapp.R
import com.nurbk.ps.noteapp.data.models.ImagePath
import com.nurbk.ps.noteapp.data.models.Note
import com.nurbk.ps.noteapp.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.android.synthetic.main.layout_miscellaneous.*
import kotlinx.android.synthetic.main.layout_miscellaneous.view.*
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class CreateNoteFragment : Fragment(R.layout.fragment_create_note) {


    private val REQUEST_IMAGE_CODE = 1

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    private lateinit var selectNoteColor: String

    private val bottomSheetBehavior by lazy {
        BottomSheetBehavior.from(layoutMiscellaneous)
    }

    val contentNote = HashMap<String, String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundel = arguments

        if (bundel != null) {
            val note = bundel.getParcelable<Note>("note")!!

            inputNoteTitle.setText(note.title)
            inputNoteSubtitle.setText(note.subtitle)

            var text = note.noteText
            val arrayText = text!!.split(" ")

            Log.e("tttt", "$arrayText")

            val imagePath = note.imagePath!!.images!!
            val builder = SpannableStringBuilder()
            builder.append(text)
            Log.e("ttttttt", "${imagePath.size}")

            for (data in imagePath) {
                Log.e("tttt key", "${data.key}")
                for (imag in arrayText) {
                    if (data.key == " $imag ") {
                        Log.e("tttt", "Ok Ok ${data.value}")
                        val imageSpan = ImageSpan(requireContext(), Uri.parse(data.value))


                        val selStart: Int = text.indexOf(data.key)
                        builder.replace(
                            text.indexOf(data.key),
                            selStart + data.key.length,
                            data.key
                        )
                        builder.setSpan(
                            imageSpan, selStart, selStart + data.key.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )


                    }
                }
            }
            inputNoteText.text = builder

        }

        imageBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        textDateTime.text = SimpleDateFormat(
            "EEEE, dd MMMM yyyy HH:mm a",
            Locale.getDefault()
        )
            .format(Date())

        imageDone.setOnClickListener {
            saveNote()
        }
        selectNoteColor = "#333333"
        initMiscellaneous()
        setSubtitleIndicator()


    }

    private fun saveNote() {
        if (inputNoteTitle.text.toString().trim().isEmpty()) {
            Toast.makeText(
                requireContext(), "Note title can't be empty",
                Toast.LENGTH_SHORT
            ).show()
            return
        } else if (inputNoteSubtitle.text.toString().trim().isEmpty()
            && inputNoteText.text.toString().trim().isEmpty()
        ) {
            Toast.makeText(
                requireContext(), "Note can't be empty",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        viewModel.insertNote(
            Note(
                inputNoteTitle.text.toString(),
                textDateTime.text.toString(),
                inputNoteSubtitle.text.toString(),
                inputNoteText.text.toString(),
                ImagePath(contentNote), selectNoteColor, ""
            )
        ).also {
            requireActivity().onBackPressed()
        }


    }

    private fun initMiscellaneous() {

        layoutMiscellaneous.textMiscellaneous.setOnClickListener {
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED


            }
        }

        layoutMiscellaneous.imageColor2
        layoutMiscellaneous.imageColor3
        layoutMiscellaneous.imageColor4
        layoutMiscellaneous.imageColor5

        layoutMiscellaneous.viewColor1.setOnClickListener {
            selectNoteColor = "#333333"
            layoutMiscellaneous.imageColor1.visibility = View.VISIBLE
            layoutMiscellaneous.imageColor2.visibility = View.GONE
            layoutMiscellaneous.imageColor3.visibility = View.GONE
            layoutMiscellaneous.imageColor4.visibility = View.GONE
            layoutMiscellaneous.imageColor5.visibility = View.GONE
            setSubtitleIndicator()

        }

        layoutMiscellaneous.viewColor2.setOnClickListener {
            selectNoteColor = "#fdbe3b"
            layoutMiscellaneous.imageColor2.visibility = View.VISIBLE
            layoutMiscellaneous.imageColor1.visibility = View.GONE
            layoutMiscellaneous.imageColor3.visibility = View.GONE
            layoutMiscellaneous.imageColor4.visibility = View.GONE
            layoutMiscellaneous.imageColor5.visibility = View.GONE
            setSubtitleIndicator()

        }
        layoutMiscellaneous.viewColor3.setOnClickListener {
            selectNoteColor = "#ff4842"
            layoutMiscellaneous.imageColor3.visibility = View.VISIBLE
            layoutMiscellaneous.imageColor1.visibility = View.GONE
            layoutMiscellaneous.imageColor2.visibility = View.GONE
            layoutMiscellaneous.imageColor4.visibility = View.GONE
            layoutMiscellaneous.imageColor5.visibility = View.GONE
            setSubtitleIndicator()

        }
        layoutMiscellaneous.viewColor4.setOnClickListener {
            selectNoteColor = "#3a52fc"
            layoutMiscellaneous.imageColor4.visibility = View.VISIBLE
            layoutMiscellaneous.imageColor1.visibility = View.GONE
            layoutMiscellaneous.imageColor2.visibility = View.GONE
            layoutMiscellaneous.imageColor3.visibility = View.GONE
            layoutMiscellaneous.imageColor5.visibility = View.GONE
            setSubtitleIndicator()

        }
        layoutMiscellaneous.viewColor5.setOnClickListener {
            selectNoteColor = "#000000"
            layoutMiscellaneous.imageColor5.visibility = View.VISIBLE
            layoutMiscellaneous.imageColor1.visibility = View.GONE
            layoutMiscellaneous.imageColor2.visibility = View.GONE
            layoutMiscellaneous.imageColor3.visibility = View.GONE
            layoutMiscellaneous.imageColor4.visibility = View.GONE
            setSubtitleIndicator()

        }

        layoutMiscellaneous.addImage.setOnClickListener {
            addImage()
        }
    }

    private fun setSubtitleIndicator() {
        val gradientDrawable = viewSubtitleIndicator.background as GradientDrawable
        gradientDrawable.setColor(Color.parseColor(selectNoteColor))

    }


    fun addImage() {
        Timber.d(" Add Image")
        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {

                    selectImage()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) { /* ... */
                    Toast.makeText(
                        requireContext(),
                        "You must Permission Granted to download",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {

                }
            }).check()
    }

    private fun selectImage() {
        val intit = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intit, REQUEST_IMAGE_CODE)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CODE && resultCode == Activity.RESULT_OK) {

            getImage(data!!.data!!)

            val imageUri = data.data
            val bitmap =
                MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)

            val d = saveToInternalStorage(bitmap)
            Log.e("ttttd", d!!)
        }

    }


    private fun getImage(uri: Uri) {
        val imageSpan = ImageSpan(requireContext(), uri)

        val builder = SpannableStringBuilder()
        builder.append(inputNoteText.text)
        val imgId = " [/*img${uri}*/]${UUID.randomUUID()} "
        contentNote[imgId] = uri.toString()

        val selStart: Int = inputNoteText.selectionStart
        builder.replace(inputNoteText.selectionStart, inputNoteText.selectionEnd, imgId)
        builder.setSpan(
            imageSpan, selStart, selStart + imgId.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        inputNoteText.text = builder

    }


    private fun saveToInternalStorage(bitmapImage: Bitmap): String? {
        val cw = ContextWrapper(requireContext())
        Log.e("ttttcw", cw.toString())

        // path to /data/data/yourapp/app_data/imageDir
        val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
        Log.e("ttttF", directory.path)
        // Create imageDir
        val mypath = File(directory, "profile.jpg")
        Log.e("ttttmy", mypath.path)

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
                Toast.makeText(requireContext(), "asdfasdf", Toast.LENGTH_LONG).show()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.absolutePath
    }

}