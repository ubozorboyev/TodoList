package ubr.persanal.todolist.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class DataBaseModule {


    @Provides
    fun todoListDao(appDataBase: AppDataBase) = appDataBase.getTodoListDao()


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(appContext, AppDataBase::class.java, "todo")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }


}