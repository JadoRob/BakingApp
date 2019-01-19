package org.codelab.google.bakingapp.data;

        import android.arch.persistence.room.Database;
        import android.arch.persistence.room.Room;
        import android.arch.persistence.room.RoomDatabase;
        import android.content.Context ;

@Database(entities = Ingredients.class, version = 2, exportSchema = false)
public abstract class IngredientsDatabase extends RoomDatabase {

    private static IngredientsDatabase instance;

    public abstract IngredientDao ingredientDao();

    public static synchronized IngredientsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    IngredientsDatabase.class, "ingredients_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
