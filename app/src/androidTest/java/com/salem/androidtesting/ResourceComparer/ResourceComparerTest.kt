package com.salem.androidtesting.ResourceComparer

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.salem.androidtesting.R
import com.salem.androidtesting.philip.ResourceComparer.ResourceComparer
import org.junit.After
import org.junit.Before
import org.junit.Test


class ResourceComparerTest {

    private lateinit var resourceComparer : ResourceComparer


    @Before
    fun setup(){
        resourceComparer = ResourceComparer()
    }


    @Test
    fun stringResourceSameAsGivenString(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context , R.string.app_name , "AndroidTesting" )
        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceAreDifferentAsGivenStringReturnFalse(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context , R.string.app_name , "Hello" )
        assertThat(result).isFalse()
    }



    @After
    fun tearDown(){}

}