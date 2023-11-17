package com.example.chosa_application

import android.content.Intent
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.Robolectric

@RunWith(MockitoJUnitRunner::class)
class LoginActivityTest {

    @Mock
    private lateinit var mockFirebaseAuth: FirebaseAuth

    @Mock
    private lateinit var mockFirebaseDatabase: FirebaseDatabase

    @Mock
    private lateinit var mockFirebaseUser: FirebaseUser

    @Mock
    private lateinit var mockDatabaseReference: DatabaseReference

    private lateinit var loginActivity: LoginActivity

    @Before
    fun setUp() {
        // Initialize LoginActivity with mocked FirebaseAuth and FirebaseDatabase
        loginActivity = Robolectric.buildActivity(LoginActivity::class.java).create().get()
        loginActivity.firebaseAuth = mockFirebaseAuth
        loginActivity.dbRef = mockFirebaseDatabase
    }

    @Test
    fun testSignInSuccess() {
        // Mock Firebase auth for successful login
        `when`(mockFirebaseAuth.signInWithEmailAndPassword(anyString(), anyString())).thenReturn(
            mock(
                Task::class.java) as Task<AuthResult>?
        )
        `when`(mockFirebaseAuth.currentUser).thenReturn(mockFirebaseUser)
        `when`(mockFirebaseUser.uid).thenReturn("testUserId")

        loginActivity.signIn("test@example.com", "password")

        // Verify that FirebaseAuth.signInWithEmailAndPassword is called
        verify(mockFirebaseAuth).signInWithEmailAndPassword("test@example.com", "password")
        // Further assertions and verifications...
    }

    @Test
    fun testSignInFailure() {
        // Mock Firebase auth for failed login
        val task = mock(Task::class.java)
        `when`(task.isSuccessful).thenReturn(false)
        `when`(mockFirebaseAuth.signInWithEmailAndPassword(anyString(), anyString())).thenReturn(
            task as Task<AuthResult>?
        )

        loginActivity.signIn("test@example.com", "wrongpassword")

        // Verify that FirebaseAuth.signInWithEmailAndPassword is called
        verify(mockFirebaseAuth).signInWithEmailAndPassword("test@example.com", "wrongpassword")
    }
}
