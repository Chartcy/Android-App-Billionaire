package i.com.TrillionaireBill.been;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User WHERE id = :id")
    User getUserById(String id);

    @Insert(onConflict = IGNORE)
    void insertUser(User User);

    @Query("DELETE FROM User WHERE id = :id")
    void deleteUser(String id);

    @Update(onConflict = REPLACE)
    void updateUser(User User);

}
