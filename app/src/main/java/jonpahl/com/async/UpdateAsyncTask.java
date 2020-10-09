package jonpahl.com.async;

import android.os.AsyncTask;
import android.util.Log;

import jonpahl.com.models.Note;
import jonpahl.com.persistence.NoteDAO;

public class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDAO mNoteDao;
    private static final String TAG = "UpdateAsyncTask";
    public UpdateAsyncTask(NoteDAO dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        Log.d(TAG, "doInBackground: thread: " + Thread.currentThread().getName());
        mNoteDao.update(notes);
        return null;
    }
}
