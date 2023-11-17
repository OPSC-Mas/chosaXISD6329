package com.example.chosa_application

import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import junit.framework.TestCase.assertEquals
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
class AddOrgActivityTest {

    @Mock
    private lateinit var mockDatabaseReference: DatabaseReference

    @Mock
    private lateinit var mockTask: Task<Void>

    @Mock
    private lateinit var mockDataSnapshot: DataSnapshot

    private lateinit var addOrgActivity: AddOrgActivity

    @Before
    fun setUp() {
        addOrgActivity = Robolectric.buildActivity(AddOrgActivity::class.java).create().get()
        addOrgActivity.dbRef = mockDatabaseReference
    }

    @Test
    fun testOrganizationAdditionSuccess() {
        `when`(mockDatabaseReference.child(anyString())).thenReturn(mockDatabaseReference)
        `when`(mockDatabaseReference.setValue(any())).thenReturn(mockTask)
        `when`(mockTask.isSuccessful).thenReturn(true)

        addOrgActivity.binding.addorgname.setText("TestOrganization")
        addOrgActivity.binding.addorgdesc.setText("TestDescription")
        addOrgActivity.selectedCategory = "TestCategory"

        addOrgActivity.binding.addorgButton.performClick()

        verify(mockDatabaseReference).setValue(any(OrgModel::class.java))
        // Assert Toast message for success
    }

    @Test
    fun testOrganizationAdditionFailure() {
        `when`(mockDatabaseReference.child(anyString())).thenReturn(mockDatabaseReference)
        `when`(mockDatabaseReference.setValue(any())).thenReturn(mockTask)
        `when`(mockTask.isSuccessful).thenReturn(false)

        addOrgActivity.binding.addorgname.setText("TestOrganization")
        addOrgActivity.binding.addorgdesc.setText("TestDescription")
        addOrgActivity.selectedCategory = "TestCategory"

        addOrgActivity.binding.addorgButton.performClick()

        verify(mockDatabaseReference).setValue(any(OrgModel::class.java))
    }

    @Test
    fun testInputValidation() {
        addOrgActivity.binding.addorgButton.performClick()

    }

    @Test
    fun testSpinnerSelection() {
        val testCategories = listOf("Category 1", "Category 2")
        val adapter = ArrayAdapter(addOrgActivity, android.R.layout.simple_spinner_item, testCategories)
        addOrgActivity.catSpinner.adapter = adapter

        addOrgActivity.catSpinner.setSelection(1)
        assertEquals("Category 2", addOrgActivity.selectedCategory)
    }

    @Test
    fun testNavigationToCategoriesMenu() {
        addOrgActivity.binding.backB.performClick()

        val expectedIntent = Intent(addOrgActivity, CategoriesMenu::class.java)
        val actualIntent = shadowOf(addOrgActivity).nextStartedActivity
        assertTrue(actualIntent.filterEquals(expectedIntent))
    }
}
