package com.example.noteapp.di

import com.example.noteapp.data.repo.NoteRepositoryImpl
import com.example.noteapp.data.room.NoteDao
import com.example.noteapp.domain.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao) : NoteRepository{
        return NoteRepositoryImpl(noteDao)
    }

}