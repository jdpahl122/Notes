package jonpahl.com.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import jonpahl.com.models.Note;

@Dao
public interface NoteDAO {

    @Insert
    long[] insertNotes(Note... notes);

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

    @Query("SELECT * FROM notes WHERE title LIKE :title")
    List<Note> getNotesWithCustomQuery(String title);

    @Delete
    int delete(Note... notes);

    @Update
    int update(Note... notes);
}
