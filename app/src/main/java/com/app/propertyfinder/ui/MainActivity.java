package com.app.propertyfinder.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.propertyfinder.Constants;
import com.app.propertyfinder.R;
import com.app.propertyfinder.adapters.PropertyListAdapter;
import com.app.propertyfinder.models.Property;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    final String TAG = "tag";

    private CollectionReference properties; // FirestoreDatabase properties collection;
    private List<Property> mPropertyList = new ArrayList<>();
    private ItemTouchHelper mSwipeDeleteSensor;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private Button addNewProperty;
    PropertyListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fetchPropertyList();

        mAdapter = new PropertyListAdapter(MainActivity.this, mPropertyList);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setVisibility(View.VISIBLE);


        addNewProperty = findViewById(R.id.new_property_btn);
        addNewProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddPropertyActivity.class));
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        DocumentReference df = FirebaseFirestore.getInstance().collection("users").document(userId);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String userLevel = documentSnapshot.getString("UserLevel");
                if ( userLevel.equals("admin")) {
                    addNewProperty.setVisibility(View.VISIBLE);

                } else {
                    Toast.makeText(getApplicationContext(), "Please Contact Admin to be able to add property",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
//        deletePropertyOnSwipe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Helper Functions
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void adminAddProperty(String uid) {
        DocumentReference df = FirebaseFirestore.getInstance().collection("users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String userLevel = documentSnapshot.getString("UserLevel");
                if (userLevel.equals("admin")) {
                    startActivity(new Intent(getApplicationContext(), AddPropertyActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Please Contact Admin to be able to add property", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void fetchPropertyList() {
        properties = FirebaseFirestore.getInstance().collection(Constants.COLLECTION_PROPERTIES);
        properties.addSnapshotListener((value, error) -> {
            if (error != null) {
                Toast.makeText(getApplicationContext(), "Cannot Sync Data now.\nTry later", Toast.LENGTH_LONG).show();
                return;
            }
            if (value != null) {
                mPropertyList.clear();
                for (DocumentSnapshot doc : value.getDocuments()) {
                    mPropertyList.add(doc.toObject(Property.class));
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

//    private void deletePropertyOnSwipe() {
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                Property propertyToDelete = mPropertyList.get(viewHolder.getAdapterPosition());
//                String propertyId = propertyToDelete.getId();
//                // Delete from FirebaseFirestore
//                FirebaseFirestore.getInstance().collection(Constants.COLLECTION_PROPERTIES).document(propertyId)
//                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        //mPropertyList.remove(viewHolder.getAdapterPosition());
//                        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
//                        Toast.makeText(getApplicationContext(),"Property Deleted..",Toast.LENGTH_SHORT).show();
//                    }
//                });
//                // Delete image from storage
//                StorageReference ref =  FirebaseStorage.getInstance().getReference(propertyToDelete.getImageUri());
//
//                ref.delete();
//
//            }
//        }).attachToRecyclerView(mRecyclerView);
//    }
}
