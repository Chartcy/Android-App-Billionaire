package i.com.TrillionaireBill.been;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ClassifyDao {

    @Query("SELECT * FROM Classify WHERE id = :id")
    Classify getClassifyById(String id);

    @Insert(onConflict = IGNORE)
    void insertClassify(Classify User);

    @Query("DELETE FROM User WHERE id = :id")
    void deleteClassify(String id);

    @Update(onConflict = REPLACE)
    void updateClassify(Classify User);

}
