package com.example.chosa_application

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowActivity
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
class RegisterActivityTest {

    private lateinit var activity: RegisterActivity
    private lateinit var shadowOfActivity: ShadowActivity

    @Before
    fun setUp() {
        activity = Robolectric.buildActivity(RegisterActivity::class.java)
            .create()
            .resume()
            .get()
        shadowOfActivity = Shadows.shadowOf(activity)
    }



    @Test
    fun clickingLoginTextViewStartsLoginActivity() {
        activity.findViewById<TextView>(R.id.textView4).performClick()
        val expectedIntent = Intent(activity, LoginActivity::class.java)
        val actualIntent = shadowOfActivity.nextStartedActivity
        assertEquals(expectedIntent.component, actualIntent.component)
    }

    // Mocking Firebase
    private lateinit var mockFirebaseAuth: FirebaseAuth
    private lateinit var mockFirebaseDatabase: FirebaseDatabase

    @Before
    fun setUpMocks() {
        mockFirebaseAuth = mock(FirebaseAuth::class.java)
        mockFirebaseDatabase = mock(FirebaseDatabase::class.java)

        // Mock Firebase Auth's createUserWithEmailAndPassword method
        `when`(mockFirebaseAuth.createUserWithEmailAndPassword(anyString(), anyString()))
            .thenReturn(mock(Task::class.java) as Task<AuthResult>)

        activity.findViewById<TextView>(R.id.buttonlogin).performClick()
        val expectedIntent = Intent(activity, LoginActivity::class.java)
        val actualIntent = shadowOfActivity.nextStartedActivity
        assertEquals(expectedIntent.component, actualIntent.component)
    }
}

