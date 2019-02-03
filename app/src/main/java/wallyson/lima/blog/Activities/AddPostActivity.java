package wallyson.lima.blog.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import wallyson.lima.blog.Model.Blog;
import wallyson.lima.blog.R;

public class AddPostActivity extends AppCompatActivity {
    private ImageButton mPostImage;
    private EditText mPostTitle, mPostDesc;
    private Button mSubmitButton;
    private DatabaseReference mPostDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        mProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mPostDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");

        mPostImage = findViewById(R.id.imageButton);
        mPostTitle = findViewById(R.id.postTitleEt);
        mPostDesc = findViewById(R.id.descriptionEt);
        mSubmitButton = findViewById(R.id.submitPost);

       mSubmitButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // Posting to our database
               startPosting();
           }
       });
    }

    private void startPosting() {
        mProgress.setMessage("Posting to blog ...");
        mProgress.show();

        String titleVal = mPostTitle.getText().toString().trim();
        String descVal = mPostDesc.getText().toString().trim();

        if ( !TextUtils.isEmpty(titleVal) && !TextUtils.isEmpty(descVal)) {
            // start the uploading...
            Blog blog = new Blog("Title", "Description",
                    "imageUrl", "timestamp", "userid");

            mPostDatabase.setValue(blog).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "Item added", Toast.LENGTH_LONG).show();

                    mProgress.dismiss();
                }
            });
        }
    }
}
