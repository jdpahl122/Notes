package jonpahl.com.async;

import android.os.AsyncTask;

import jonpahl.com.models.Note;
import jonpahl.com.persistence.NoteDAO;

public class InsertAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDAO mNoteDao;
    private static final String TAG = "InsertAsyncTask";
    public InsertAsyncTask(NoteDAO dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.insertNotes(notes);
        return null;
    }
}
