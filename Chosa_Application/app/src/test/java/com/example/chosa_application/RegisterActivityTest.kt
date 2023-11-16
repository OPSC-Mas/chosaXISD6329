package com.example.chosa_application

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import androidx.test.core.app.ApplicationProvider
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows

@RunWith(RobolectricTestRunner::class)
class RegisterActivityTest {

    @Mock
    private lateinit var mockFirebaseAuth: FirebaseAuth

    @Mock
    private lateinit var mockFirebaseDatabase: FirebaseDatabase

    @Mock
    private lateinit var mockFirebaseUser: FirebaseUser

    private lateinit var registerActivity: RegisterActivity

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        // Mock the FirebaseAuth and FirebaseUser
        Mockito.`when`(mockFirebaseAuth.currentUser).thenReturn(mockFirebaseUser)
        Mockito.`when`(mockFirebaseUser.uid).thenReturn("testUserId")

        // Set up the activity with Robolectric
        registerActivity = Robolectric.buildActivity(RegisterActivity::class.java)
            .create()
            .resume()
            .get()
    }

    @Test
    fun registerUser_withValidInput_startsLoginActivity() {
        // Arrange: Provide valid input
        val emailField = registerActivity.findViewById<EditText>(R.id.regemail)
        val passwordField = registerActivity.findViewById<EditText>(R.id.regpassword)
        val usernameField = registerActivity.findViewById<EditText>(R.id.username)
        emailField.setText("test@example.com")
        passwordField.setText("password123")
        usernameField.setText("TestUser")

        // Mock Firebase responses for successful registration
        Mockito.`when`(mockFirebaseAuth.createUserWithEmailAndPassword("test@example.com", "password123"))
            .thenReturn(Mockito.mock(Task::class.java)) // Assuming Task is a Firebase Task

        // Act: Trigger the registration process
        registerActivity.findViewById<Button>(R.id.button).performClick()

        // Assert: Verify LoginActivity was started
        val expectedIntent = Intent(registerActivity, LoginActivity::class.java)
        val actualIntent = Shadows.shadowOf(ApplicationProvider.getApplicationContext()).nextStartedActivity
        assertEquals(expectedIntent.component, actualIntent.component)
    }

    // Additional test cases...
}
