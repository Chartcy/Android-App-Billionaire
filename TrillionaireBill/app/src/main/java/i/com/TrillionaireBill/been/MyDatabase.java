package i.com.TrillionaireBill.been;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {User.class,Classify.class}, version = 2 ,exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase INSTANCE;

    public abstract UserDao user();

    public abstract ClassifyDao classifyDao();

    public static MyDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    MyDatabase.class, "dishes.db")
                    .addMigrations(MIGRATION_1_2 , MIGRATION_2_3)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE IF EXISTS `User`");

            database.execSQL("CREATE TABLE IF NOT EXISTS `User` (`id` text, `name` TEXT, `account` TEXT, `member` TEXT, PRIMARY KEY(`id`))");

        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE IF EXISTS `Classify`");

            database.execSQL("CREATE TABLE IF NOT EXISTS `Classify` (`id` text, `stairTx` TEXT, `secondTx` TEXT, " +
                    "`merchantTx` TEXT,`memberTx` TEXT, PRIMARY KEY(`id`))");

        }
    };
}