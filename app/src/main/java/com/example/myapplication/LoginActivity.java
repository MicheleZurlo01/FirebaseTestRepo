package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import android.content.Intent;
import android.content.IntentSender;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends AppCompatActivity {

    private static final int REQ_ONE_TAP = 0;
    private boolean showOneTapUI = true;
    private LoginController controller;

    private Button registraAccountButton;
    private Button accediButton;
    //private Button accediFacebookButton;
    private Button accediGoogleButton;

    private EditText emailEditText, passwordEditText;

    private String email, password;
    //private LoginButton accediFacebookButton;
    //private CallbackManager callbackManager;

    private FirebaseAuth firebaseAuth;

    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;

    private SignInClient oneTapClient;
    private BeginSignInRequest.Builder signInRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ottieniRiferimenti();

        configuraComponenti();

        controller = new LoginController(this);

        firebaseAuth = FirebaseAuth.getInstance();


        //Automatic Login Firebase Credentials //Works for Firebase Login
        if(firebaseAuth.getCurrentUser() != null){
            controller.apriMainActivity();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();

       //Works for Google Login
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            controller.apriMainActivity();
        }


    }

    private void ottieniRiferimenti(){
        registraAccountButton = findViewById(R.id.registraAccountButton);
        accediButton = findViewById(R.id.buttonAccedi);
        //accediFacebookButton = findViewById(R.id.Login_Facebook_Button);
        accediGoogleButton = findViewById(R.id.Login_Google_Button);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
    }

    private void configuraComponenti(){
        configuraAccediButton();
        configuraRegistraButton();
        //configuraAccediFacebookButton();


        configuraAccediGoogleButton();

    }

    private void accediConOneTapClient() {

        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build());

        oneTapClient.beginSignIn(signInRequest.build())
                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult result) {
                        try {
                            int REQ_ONE_TAP = 0;
                            startIntentSenderForResult(
                                    result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                                    null, 0, 0, 0);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e(TAG, "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // No saved credentials found. Launch the One Tap sign-up flow, or
                        // do nothing and continue presenting the signed-out UI.
                        Log.d(TAG, e.getLocalizedMessage());
                    }
                });
    }

    private void configuraAccediButton(){
        accediButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accedi();
            }
        });
    }

    private void configuraAccediGoogleButton(){

/*     OLD CODE FOR SIGN IN WITH GOOGLE

        //Configure Google Sign In
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //Build a sign in client with the google sign in option client
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

 */

        accediGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                accediConOneTapClient();

                //Old code for sign in with google
               // signIn();

            }
        });
    }

    /*    OLD CODE FOR SIGN IN WITH GOOGLE
    private void signIn(){

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);



    }
    */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*    OLD CODE FOR SIGN IN WITH GOOGLE


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                //Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }

         */


        switch (requestCode) {
            case REQ_ONE_TAP:


                    SignInCredential googleCredential = null;
                    try {
                        googleCredential = oneTapClient.getSignInCredentialFromIntent(data);

                    String idToken = googleCredential.getGoogleIdToken();
                    if (idToken !=  null) {
                        // Got an ID token from Google. Use it to authenticate
                        // with Firebase.
                        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
                        firebaseAuth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "signInWithCredential:success");
                                            FirebaseUser user = firebaseAuth.getCurrentUser();

                                            //Print of the user
                                            System.out.println("Google login "+user.getDisplayName());

                                            controller.apriMainActivity();
                                            //  updateUI(user);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                                            // updateUI(null);
                                        }
                                    }
                                });
                    }

                } catch (ApiException e) {
                    switch (e.getStatusCode()) {
                        case CommonStatusCodes.CANCELED:
                            Log.d(TAG, "One-tap dialog was closed.");
                            // Don't re-prompt the user.
                            showOneTapUI = false;
                            break;
                        case CommonStatusCodes.NETWORK_ERROR:
                            Log.d(TAG, "One-tap encountered a network error.");
                            // Try again or just ignore.
                            break;
                        default:
                            Log.d(TAG, "Couldn't get credential from result."
                                    + e.getLocalizedMessage());
                            break;
                    }
                }
                break;
        }


    }

    /*       OLD CODE FOR SIGN IN WITH GOOGLE


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            System.out.println("Google user:    "+user.getEmail() + user.getPhoneNumber() + user.getPhotoUrl() + user.getDisplayName());
                            //updateUI(user);


                            controller.apriMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                           // updateUI(null);
                        }
                    }
                });

    }

     */

    private void configuraRegistraButton(){
        registraAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.apriRegistrazioneAccount();
            }
        });
    }

    private void accedi(){
        if (!campiVuoti()){
            ottieniDati();

            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                controller.apriMainActivity();
                            }else{
                                Toast.makeText(LoginActivity.this, "Authentication Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else {
            Toast.makeText(this, "Campi Richiesti Vuoti. Riempili tutti o registra un account.", Toast.LENGTH_LONG).show();
        }


    }

    private void ottieniDati(){
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
    }

    private boolean campiVuoti(){
        if (TextUtils.isEmpty(emailEditText.getText()))
            return true;
        if (TextUtils.isEmpty(passwordEditText.getText()))
            return true;
        return false;
    }


}
