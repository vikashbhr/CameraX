package com.kaagaz.mycamera.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ImageDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ImageDatabase
    private lateinit var dao: ImageDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ImageDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.imageDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertImageTest() = runBlockingTest {
        val imageModel = ImageModel(1, "TestImage", "TestImageUrl")
        dao.insert(imageModel)

        val images = dao.getImages().getOrAwaitValue()
        assertThat(images).contains(imageModel)
    }

    @Test
    fun deleteImageTest() = runBlockingTest {
        val imageModel = ImageModel(1, "TestImage", "TestImageUrl")
        dao.insert(imageModel)
        dao.delete(imageModel)

        val images = dao.getImages().getOrAwaitValue()
        assertThat(images).doesNotContain(imageModel)

    }
}