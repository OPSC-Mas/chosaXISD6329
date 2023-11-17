package com.example.chosa_application

import android.content.Intent
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.Robolectric
import org.robolectric.Shadows.shadowOf

@RunWith(MockitoJUnitRunner::class)
class AddCategoryActivityTest {

    @Mock
    private lateinit var mockDatabaseReference: DatabaseReference

    @Mock
    private lateinit var mockTask: Task<Void>

    private lateinit var addCategoryActivity: AddCategoryActivity

    @Before
    fun setUp() {
        addCategoryActivity = Robolectric.buildActivity(AddCategoryActivity::class.java).create().get()
        addCategoryActivity.dbRef = mockDatabaseReference
    }

    @Test
    fun testCategoryAdditionSuccess() {
        // Mocking the database reference and task success
        `when`(mockDatabaseReference.child(anyString())).thenReturn(mockDatabaseReference)
        `when`(mockDatabaseReference.setValue(any())).thenReturn(mockTask)
        `when`(mockTask.isSuccessful).thenReturn(true)

        addCategoryActivity.binding.addcatname.setText("TestCategory")
        addCategoryActivity.binding.addcatdesc.setText("TestDescription")

        addCategoryActivity.binding.addCatButton.performClick()

        verify(mockDatabaseReference).setValue(any(CatModel::class.java))
    }

    @Test
    fun testCategoryAdditionFailure() {
        // Mocking the database reference and task failure
        `when`(mockDatabaseReference.child(anyString())).thenReturn(mockDatabaseReference)
        `when`(mockDatabaseReference.setValue(any())).thenReturn(mockTask)
        `when`(mockTask.isSuccessful).thenReturn(false)

        addCategoryActivity.binding.addcatname.setText("TestCategory")
        addCategoryActivity.binding.addcatdesc.setText("TestDescription")

        addCategoryActivity.binding.addCatButton.performClick()

        verify(mockDatabaseReference).setValue(any(CatModel::class.java))
    }

    @Test
    fun testInputValidation() {
        addCategoryActivity.binding.addCatButton.performClick()

    }

    @Test
    fun testNavigationToCategoriesMenu() {
        addCategoryActivity.binding.backB2.performClick()

        val expectedIntent = Intent(addCategoryActivity, CategoriesMenu::class.java)
        val actualIntent = shadowOf(addCategoryActivity).nextStartedActivity
        assertTrue(actualIntent.filterEquals(expectedIntent))
    }
}
