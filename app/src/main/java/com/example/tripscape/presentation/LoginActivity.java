package com.example.tripscape.presentation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tripscape.R;
import com.example.tripscape.model.TripUser;
import com.example.tripscape.model.Users;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.auth.User;

public class LoginActivity extends AppCompatActivity  implements GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_GOOGLE_SIGN_IN = 123;
    private String email, password;
    private EditText etEmail, etPassword;
    private TextInputLayout tilEmail, tilPassowrd;
    private CallbackManager callbackManager;
    private LoginButton btnFacebook;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private ViewGroup contenedor;
    private boolean join;
    private ProgressDialog dialogo;
    private GoogleApiClient googleApiClient;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        FacebookSdk.sdkInitialize(this);

        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        tilEmail = (TextInputLayout) findViewById(R.id.txt_email);
        tilPassowrd = (TextInputLayout) findViewById(R.id.txt_password);
        contenedor = (ViewGroup) findViewById(R.id.contenedor);
        dialogo = new ProgressDialog(this);
        dialogo.setTitle("Verifying user");
        dialogo.setMessage("Please wait...");

        //Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail() .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        //Facebook
        callbackManager = CallbackManager.Factory.create();
        btnFacebook = (LoginButton) findViewById(R.id.facebook);
        btnFacebook.setReadPermissions("email", "public_profile");
        btnFacebook.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override public void onSuccess(LoginResult loginResult) {
                        facebookAuth(loginResult.getAccessToken());
                    }
                    @Override public void onCancel() {
                        message("Facebook autentication cancelled");
                    }
                    @Override public void onError(FacebookException error) {
                        message(error.getLocalizedMessage());
                    }
                });
    }

    public void loginEmail(View v) {
        if (verifyFields()) {
            dialogo.show();
            if (join) {
                AuthCredential credential= EmailAuthProvider.getCredential(email,password);
                joinWith(credential);
            }
            else {
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    verifyUserValidated();
                                } else {
                                    dialogo.dismiss();
                                    message(task.getException().getLocalizedMessage());
                                }
                            }
                        });
            }
        }
    }

    private void joinWith(AuthCredential credential) {
        auth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            join = false;
                            verifyUserValidated();
                        } else {
                            Log.w("Tripscape", "Error at linkWithCredential", task.getException());
                            message("Error when joining accounts.");
                        }
                    }
                });
    }

    private void facebookAuth(AccessToken accessToken) {
        final AuthCredential credential = FacebookAuthProvider.getCredential( accessToken.getToken());
            auth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    LoginManager.getInstance().logOut();
                                }
                                message(task.getException().getLocalizedMessage());
                            } else {
                                verifyUserValidated();
                            }
                        }
                    });
    }

    private void verifyUserValidated() {
        FirebaseUser usuario = auth.getCurrentUser();
        boolean usesEmailPassword = false;
        if (!join &&  usuario != null) {
            TripUser tripUser = TripUser.getInstance();
            tripUser.setName(usuario.getDisplayName());
            tripUser.setEmail(usuario.getEmail());
            tripUser.setUid(usuario.getUid());
            Users.saveUser(tripUser);
            for (UserInfo info : usuario.getProviderData()) {
                if (info.getProviderId().equals("password")) {
                    usesEmailPassword = true;
                }
            }

            if(usesEmailPassword && !usuario.isEmailVerified()) {
                sendVerificationEmail();
            }
            else {
                //Create User instance
                Intent i = new Intent(this, ActionActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        }
    }

    private void message(String msg) {
        Snackbar.make(contenedor, msg, Snackbar.LENGTH_LONG).show();
    }

    public void registerEmail(View v) {
        if (verifyFields()) {
            dialogo.show();
            if (join) {
                AuthCredential credential= EmailAuthProvider.getCredential(email, password);
                joinWith(credential);
            }
            else {
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    verifyUserValidated();
                                } else {
                                    dialogo.dismiss();
                                    message(task.getException().getLocalizedMessage());
                                }
                            }
                        });
            }
        }
    }

    public void GoogleAut(View v) {
        Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(i, RC_GOOGLE_SIGN_IN);
    }

    private void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText(this, "Please, verify email: " + user.getEmail(), Toast.LENGTH_LONG).show();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent

                            // after email is sent just logout the user and finish this activity
                            FirebaseAuth.getInstance().signOut();
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do

                            //restart this activity
                            overridePendingTransition(0, 0);
                            finish();
                        }
                    }
                });
    }

    private boolean verifyFields() {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        tilEmail.setError("");
        tilPassowrd.setError("");
        if (email.isEmpty()) {
            etEmail.setError("Enter email");
        } else if (!email.matches(".+@.+[.].+")) {
            etEmail.setError("Email not valid");
        } else if (password.isEmpty()) {
            tilPassowrd.setError("Enter password");
        } else if (password.length()<6) {
            tilPassowrd.setError("Password minimal length is 6 characteres");
        } else if (!password.matches(".*[0-9].*")) {
            tilPassowrd.setError("It must contain at least a number");
        } else if (!password.matches(".*[A-Z].*")) {
            tilPassowrd.setError("It must contain at least an upper case letter");
        } else {
            return true;
        }
        return false;
    }


    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            if (resultCode == RESULT_OK ) {
                GoogleSignInResult result = Auth.GoogleSignInApi .getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    googleAuth(result.getSignInAccount());
                } else {
                    message("Error de autentificación con Google");
                }
            }
            else {
                message("Error de autentificación con Google: resultCode = " + resultCode );
            }
        }
        else if (requestCode == btnFacebook.getRequestCode()) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        else {
            anonymousAutentication(null);
        }
    }

    private void googleAuth(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(
                acct.getIdToken(), null);
        if (join) {
            joinWith(credential);
        }
        else {
            auth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                message(task.getException().getLocalizedMessage());
                            } else {
                                verifyUserValidated();
                            }
                        }
                    });
        }
    }

    public void anonymousAutentication(View v) {
        dialogo.show();
        auth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override public void onComplete(@NonNull Task<AuthResult> task){
                        if (task.isSuccessful()) {
                            verifyUserValidated();
                            dialogo.dismiss();
                        } else {
                            dialogo.dismiss();
                            Log.w("Tripscape", "Error en signInAnonymously", task.getException());
                            message( "Error at anynimous login");
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        message("Error at Google autentication");
    }
}
