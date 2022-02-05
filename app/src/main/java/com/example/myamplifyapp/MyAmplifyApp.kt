package com.example.myamplifyapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.api.graphql.GraphQLOperation
import com.amplifyframework.api.graphql.GraphQLRequest
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelPagination
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.generated.model.Post
import com.amplifyframework.datastore.generated.model.Submitted
import com.google.android.material.textfield.TextInputLayout
import org.w3c.dom.Text
import java.util.*

var randomThingy = 0

class MyAmplifyApp : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(applicationContext)
            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }
        setContentView(R.layout.activity_main)

        val submitButton: Button = findViewById(R.id.button)
        submitButton.setOnClickListener {
            submitPost()
        }
        123456
        val signInButton: Button = findViewById(R.id.signin_button)
        signInButton.setOnClickListener {
            signIn()
        }
        val signOutButton: Button = findViewById(R.id.signout_button)
        signOutButton.setOnClickListener {
            signOut()
        }
        val triggerButton: Button = findViewById(R.id.trigger_button)
        triggerButton.setOnClickListener {
            val grabTextView = findViewById<TextView>(R.id.latestPost_textview)
            if(grabTextView.text == "Not Grabbing Posts") {
                grabTextView.text = "Grabbing Latest Posts"
            } else {
                grabTextView.text = "Not Grabbing Posts"
            }

        }
        val thread: Thread = object : Thread() {
            override fun run() {
                try {
                    while (!this.isInterrupted) {
                        sleep(1000)
                        runOnUiThread {
                            queryLatestPost()
                        }
                    }
                } catch (e: InterruptedException) {
                }
            }
        }
        thread.start()

        val signUpButton: Button = findViewById(R.id.signup_button)
        signUpButton.setOnClickListener {
            val email: String = findViewById<TextInputLayout>(R.id.email_signup).editText?.text.toString()
            val username: String = findViewById<TextInputLayout>(R.id.username_signup).editText?.text.toString()
            val password: String = findViewById<TextInputLayout>(R.id.password_signup).editText?.text.toString()
            Log.i("MyAmplifyApp", "$email, $username, $password")
            signUp(email, username, password)
        }

        val confirmationButton: Button = findViewById(R.id.confirm_button)
        confirmationButton.setOnClickListener {
            val username: String = findViewById<TextInputLayout>(R.id.username_signup).editText?.text.toString()
            val confirmCode: String = findViewById<TextInputLayout>(R.id.confirmation_signup).editText?.text.toString()
            signUpConfirm(username, confirmCode)
        }

    }

//        Amplify.DataStore.query(Post::class.java,
//            { posts ->
//                while (posts.hasNext()) {
//                    val post  = posts.next()
//                    Log.i("MyAmplifyApp", "Title: ${post.title}")
//                }
//            },
//            { Log.e("MyAmplifyApp",  "Error retrieving posts", it) }
//        )

//    private fun grabTitle(title: String) {
//        Amplify.API.query(ModelQuery.get(Post::class.java, title),
//            { Log.i("MyAmplifyApp", "Query results = ${(it.data as Post).title}") },
//            { Log.e("MyAmplifyApp", "Query failed", it) }
//        );
//
//        Amplify.API.query(ModelQuery.get(Post::class.java, title),
//            {Log.i("My amplify app", "")},
//            { Log.e("MyAmplifyApp", "Query failed", it) }
//        );
//    }

    private fun signIn() {
        val userName = findViewById<TextInputLayout>(R.id.username).editText?.text.toString()
        val password = findViewById<TextInputLayout>(R.id.password).editText?.text.toString()
        Amplify.Auth.signIn(userName, password,
            { result ->
                if (result.isSignInComplete) {
                    Log.i("AuthQuickStart", "Sign in succeeded")
                } else {
                    Log.i("AuthQuickstart", "Sign in not complete")
                }
            },
            { Log.e("AuthQuickstart", "Failed to sign in", it) })

        val attr = Amplify.Auth.fetchUserAttributes(
            { Log.i("AuthDemo", "User attributes = $it") },
            { Log.e("AuthDemo", "Failed to fetch user attributes", it) }
        )
        if(Amplify.Auth.currentUser != null) {
            Toast.makeText(applicationContext, Amplify.Auth.currentUser.toString(), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Sign in Failed.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signOut() {
        Amplify.Auth.signOut(
            { Log.i("AuthQuickstart", "Signed out successfully") },
            { Log.e("AuthQuickstart", "Sign out failed", it) }
        )
        Toast.makeText(applicationContext, "Signed out.", Toast.LENGTH_SHORT).show()
    }

    private fun submitPost() {
        val submit = Submitted.builder()
            .heartrate(25.0)
            .bloodpressure("Something good")
            .steps(12000)
            .build()

//        Amplify.DataStore.save(submit,
//            { Log.i("MyAmplifyApp", "Created a new post successfully") },
//            { Log.e("MyAmplifyApp", "Error creating post") }
//        )
        Amplify.API.mutate(
            ModelMutation.create(submit),
            { Log.i("MyAmplifyApp", "Submitted post with id: ${it.data.id}") },
            { Log.e("MyAmplifyApp", "Post failed.") }
        )
    }


    private fun signUp(email: String, username: String, password: String) {
        val options = AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.email(), email)
            .build()

        Amplify.Auth.signUp(username, password, options,
            { Log.i("AuthQuickStart", "Sign up succeeded: $it")},
            { Log.i("AuthQuickStart", "Sign up failed")}
        )
    }

    private fun signUpConfirm(username: String, confirmationCode: String) {
        Amplify.Auth.confirmSignUp(
            username, confirmationCode,
            { result ->
                if (result.isSignUpComplete) {
                    Log.i("AuthQuickstart", "Confirm signUp succeeded")
                } else {
                    Log.i("AuthQuickstart","Confirm sign up not complete")
                }
            },
            { Log.e("AuthQuickstart", "Failed to confirm sign up", it) }
        )
    }


    private fun queryLatestPost(){
        Amplify.API.query(ModelQuery.list(Submitted::class.java, ModelPagination.limit(10)),
            { response ->
                if (response.hasData()) {
                    response.data.items.forEach { post ->
                        Log.i("MyAmplifyApp", "Latest Post: ${post.id}")
                    }
                }
            },
            { Log.e("MyAmplifyApp", "Query failed", it) }
        )
    }
}