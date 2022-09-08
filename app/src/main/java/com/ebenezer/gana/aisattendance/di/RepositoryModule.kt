package com.ebenezer.gana.aisattendance.di

import com.ebenezer.gana.aisattendance.data.repository.AdminRepository
import com.ebenezer.gana.aisattendance.data.repository.StaffListRepository
import com.ebenezer.gana.aisattendance.data.repository.StartScreenRepository
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideStaffListRepository(firestore: FirebaseFirestore) =
        StaffListRepository(firestore)

    @Provides
    @Singleton
    fun provideAdminRepository(firestore: FirebaseFirestore) =
        AdminRepository(firestore)

    @Provides
    @Singleton
    fun provideStartScreenRepository(firestore: FirebaseFirestore) =
        StartScreenRepository(firestore)
}