package jonpahl.com;

import androidx.appcompat.app.AppCompatActivity;
import jonpahl.com.models.Note;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NoteActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private static final String TAG = "NoteActivity";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    // UI Components
    private LinedEditText mLinedEditText;
    private EditText mEditTitle;
    private TextView mViewTitle;

    // Vars
    private boolean mIsNewNote;
    private Note mNoteInitial;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mLinedEditText = findViewById(R.id.note_text);
        mEditTitle = findViewById(R.id.note_edit_title);
        mViewTitle = findViewById(R.id.note_text_title);

        if (getIncomingIntent()) {
            setNewNoteProperties();
        }
        else {
            setNoteProperties();
        }

        setListeners();
    }

    private void setListeners(){
        mLinedEditText.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this, this);
    }
    private boolean getIncomingIntent(){
        if (getIntent().hasExtra("selected_note")) {
            mNoteInitial = getIntent().getParcelableExtra("selected_note");
            Log.d(TAG, "getIncomingIntent : " + mNoteInitial.toString());

            mIsNewNote= false;
            return false;
        }
        mIsNewNote = true;
        return true;
    }

    private void setNoteProperties(){
        mViewTitle.setText(mNoteInitial.getTitle());
        mEditTitle.setText(mNoteInitial.getTitle());
        mLinedEditText.setText(mNoteInitial.getContent());
    }

    private void setNewNoteProperties(){
        mViewTitle.setText("Note Title");
        mEditTitle.setText("Note Title");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: double tapped!");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}