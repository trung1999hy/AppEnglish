package com.tpk.englishttcm.bookmark.view

import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tpk.englishttcm.base.BaseFragment
import com.tpk.englishttcm.learnzone.vocabulary.OnItemWordClickListener
import com.tpk.englishttcm.learnzone.vocabulary.view.dialog.OnClickListener
import com.tpk.englishttcm.bookmark.adapter.BookmarkAdapter
import com.tpk.englishttcm.bookmark.model.BookmarkWord
import com.tpk.englishttcm.bookmark.view.dialog.DeleteDialog
import com.tpk.englishttcm.bookmark.view.dialog.UpdateWordDialog
import com.tpk.englishttcm.bookmark.viewmodel.BookmarkViewModel
import com.tpk.englishttcm.databinding.FragmentBookmarkBinding

class BookmarkWordFragment: BaseFragment<FragmentBookmarkBinding>(), OnItemWordClickListener {
    private lateinit var viewModel: BookmarkViewModel
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun getLayout(container: ViewGroup?): FragmentBookmarkBinding =
        FragmentBookmarkBinding.inflate(layoutInflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[BookmarkViewModel::class.java]
        viewModel.getBookmarkWordList()
    }

    override fun initViews() {
        viewModel.bookmarkWordList.observe(viewLifecycleOwner){
            bookmarkAdapter = BookmarkAdapter(it, this)
            binding.rcvWordNote.adapter = bookmarkAdapter}

        binding.btnBack.setOnClickListener {
            callback.backToPrevious()
        }
    }

    override fun onItemClick(data: Any?) {
        val word = data as BookmarkWord
        viewModel.speakWord(word.speaker.toString())
    }

    override fun onItemNoteClick(data: Any?) {
        if (data is BookmarkWord) {
            val noteWord = data
            val dialog = DeleteDialog(object: OnClickListener {
                override fun onClick() {
                    viewModel.deleteNote(noteWord.id.toString())
                    val position = bookmarkAdapter.listWord.indexOf(noteWord)
                    bookmarkAdapter.removeItem(position)
                    notify("Successfully deleted")
                }

                override fun onClick(
                    word: String,
                    mean: String,
                    speaker: String,
                    pronounce: String,
                    example: String
                ) {}
            })
            dialog.show(requireActivity().supportFragmentManager, "delete_note_dialog")
        } else {
            notify("Error Data")
        }

    }
    override fun onItemEditClick(data: Any?) {
        if (data is BookmarkWord) {
            val bookWord = data
            val dialog = UpdateWordDialog(bookWord, object: OnClickListener {
                override fun onClick() {
                }

                override fun onClick(
                    word: String,
                    mean: String,
                    speaker: String,
                    pronounce: String,
                    example: String
                ) {
                    val bookmarkWord = BookmarkWord(
                        id = bookWord.id,
                        word = word,
                        speaker = speaker,
                        pronounce = pronounce,
                        mean = mean,
                        example = example
                    )
                    viewModel.updateWord(bookmarkWord)
                    val position = bookmarkAdapter.listWord.indexOf(bookWord)
                    bookmarkAdapter.updateData(position, bookmarkWord)
                    notify("Successfully updated")
                }
            })
            dialog.show(requireActivity().supportFragmentManager, "update_word_dialog")
        } else {
            notify("Error Data")
        }
    }
}