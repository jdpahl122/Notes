package jonpahl.com;

import androidx.appcompat.app.AppCompatActivity;
import jonpahl.com.models.Note;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class NoteActivity extends AppCompatActivity {

    private static final String TAG = "NoteActivity";

    // UI Components
    private LinedEditText mLinedEditText;
    private EditText mEditTitle;
    private TextView mViewTitle;

    // Vars
    private boolean mIsNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mLinedEditText = findViewById(R.id.note_text);
        mEditTitle = findViewById(R.id.note_edit_title);
        mViewTitle = findViewById(R.id.note_text_title);

        if (getIntent().hasExtra("selected_note")){
            Note note = getIntent().getParcelableExtra("selected_note");
            Log.d(TAG, "onCreate: " + note.toString());
        }
        else {
            
        }
    }

    private boolean getIncomingIntent(){
        if (getIntent().hasExtra("selected_note")) {
            mIsNewNote= false;
            return false;
        }
        mIsNewNote = true;
        return true;
    }
}