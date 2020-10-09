package jonpahl.com.async;

import android.os.AsyncTask;

import jonpahl.com.models.Note;
import jonpahl.com.persistence.NoteDAO;

public class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDAO mNoteDao;
    private static final String TAG = "DeleteAsyncTask";
    public DeleteAsyncTask(NoteDAO dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.delete(notes);
        return null;
    }
}
