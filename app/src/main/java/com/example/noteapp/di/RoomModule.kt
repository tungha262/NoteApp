package com.example.noteapp.di

import android.content.Context
import com.example.noteapp.data.room.NoteDao
import com.example.noteapp.data.room.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context) : NoteDatabase{
        return NoteDatabase.getDatabaseInstance(context)
    }

    @Provides
    @Singleton
    fun getNoteDao(noteDatabase: NoteDatabase) : NoteDao {
        return noteDatabase.noteDao()
    }

}