package jonpahl.com;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import jonpahl.com.models.Note;
        import jonpahl.com.persistence.NoteRepository;
        import jonpahl.com.util.Utility;

        import android.app.Activity;
        import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.util.Log;
        import android.view.GestureDetector;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

public class NoteActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, View.OnClickListener, TextWatcher {

    private static final String TAG = "NoteActivity";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    // UI Components
    private LinedEditText mLinedEditText;
    private EditText mEditTitle;
    private TextView mViewTitle;
    private RelativeLayout mCheckContainer, mBackArrowContainer;
    private ImageButton mCheck, mArrow;

    // Vars
    private boolean mIsNewNote;
    private Note mNoteInitial;
    private Note mNoteFinal;
    private GestureDetector mGestureDetector;
    private int mMode;
    private NoteRepository mNoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mLinedEditText = findViewById(R.id.note_text);
        mEditTitle = findViewById(R.id.note_edit_title);
        mViewTitle = findViewById(R.id.note_text_title);
        mCheckContainer = findViewById(R.id.check_container);
        mBackArrowContainer = findViewById(R.id.back_arrow_container);
        mCheck = findViewById(R.id.toolbar_check);
        mArrow = findViewById(R.id.toolbar_back_arrow);

        mNoteRepository = new NoteRepository(this);

        if (getIncomingIntent()) {
            setNewNoteProperties();
            enableEditMode();
        }
        else {
            setNoteProperties();
            disableContentInteraction();
        }

        setListeners();
    }

    private void setListeners(){
        mLinedEditText.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this, this);
        mViewTitle.setOnClickListener(this);
        mCheck.setOnClickListener(this);
        mArrow.setOnClickListener(this);
        mEditTitle.addTextChangedListener(this);
    }
    private boolean getIncomingIntent(){
        if (getIntent().hasExtra("selected_note")) {
            mNoteInitial = getIntent().getParcelableExtra("selected_note");
            Log.d(TAG, "getIncomingIntent : " + mNoteInitial.toString());

            mNoteFinal = new Note();
            mNoteFinal.setTitle(mNoteInitial.getTitle());
            mNoteFinal.setContent(mNoteInitial.getContent());
            mNoteFinal.setTimestamp(mNoteInitial.getTimestamp());
            mNoteFinal.setId(mNoteInitial.getId());
            mMode = EDIT_MODE_DISABLED;

            mIsNewNote= false;
            return false;
        }
        mMode = EDIT_MODE_ENABLED;
        mIsNewNote = true;
        return true;
    }

    private void saveChanges() {
        if(mIsNewNote){
            saveNewNote();
        }
        else {
            updateNote();
        }
    }

    private void updateNote() {
        mNoteRepository.updateNote(mNoteFinal);
    }

    private void saveNewNote() {
        mNoteRepository.insertNoteTask(mNoteFinal);
    }

    private void enableContentInteraction(){
        mLinedEditText.setKeyListener(new EditText(this).getKeyListener());
        mLinedEditText.setFocusable(true);
        mLinedEditText.setFocusableInTouchMode(true);
        mLinedEditText.setCursorVisible(true);
        mLinedEditText.requestFocus();
    }

    private void disableContentInteraction(){
        mLinedEditText.setKeyListener(null);
        mLinedEditText.setFocusable(false);
        mLinedEditText.setFocusableInTouchMode(false);
        mLinedEditText.setCursorVisible(false);
        mLinedEditText.clearFocus();
    }

    private void enableEditMode() {
        mBackArrowContainer.setVisibility(View.GONE);
        mCheckContainer.setVisibility(View.VISIBLE);

        mViewTitle.setVisibility(View.GONE);
        mEditTitle.setVisibility(View.VISIBLE);

        mMode = EDIT_MODE_ENABLED;
        enableContentInteraction();
    }

    private void disableEditMode() {
        mBackArrowContainer.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);

        mViewTitle.setVisibility(View.VISIBLE);
        mEditTitle.setVisibility(View.GONE);
        mEditTitle.setVisibility(View.GONE);

        mMode = EDIT_MODE_DISABLED;
        disableContentInteraction();

        String temp = mLinedEditText.getText().toString();
        temp = temp.replace("\n", "");
        temp = temp.replace(" ", "");
        if(temp.length() > 0) {
            mNoteFinal.setTitle(mEditTitle.getText().toString());
            mNoteFinal.setContent(mLinedEditText.getText().toString());
            String timestamp = Utility.getCurrentTimestamp();
            mNoteFinal.setTimestamp(timestamp);

            if(!mNoteFinal.getContent().equals(mNoteInitial.getContent())
                    || !mNoteFinal.getTitle().equals(mNoteInitial.getTitle())){
                saveChanges();
            }
        }
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setNoteProperties(){
        mViewTitle.setText(mNoteInitial.getTitle());
        mEditTitle.setText(mNoteInitial.getTitle());
        mLinedEditText.setText(mNoteInitial.getContent());
    }

    private void setNewNoteProperties(){
        mViewTitle.setText("Note Title");
        mEditTitle.setText("Note Title");

        mNoteInitial = new Note();
        mNoteFinal = new Note();
        mNoteInitial.setTitle("Note Title");
        mNoteFinal.setTitle("Note Title");
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
        enableEditMode();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.toolbar_check: {
                hideSoftKeyboard();
                disableEditMode();
                break;
            }
            case R.id.note_text_title: {
                enableEditMode();
                mEditTitle.requestFocus();
                mEditTitle.setSelection(mEditTitle.length());
                break;
            }
            case R.id.toolbar_back_arrow: {
                finish();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(mMode == EDIT_MODE_ENABLED){
            onClick(mCheck);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", mMode);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMode = savedInstanceState.getInt("mode");
        if(mMode == EDIT_MODE_ENABLED) {
            enableEditMode();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mViewTitle.setText(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}