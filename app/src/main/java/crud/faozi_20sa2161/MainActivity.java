package crud.faozi_20sa2161;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements crud.faozi_20sa2161.adapterMahasiswa.dataListener {
    FloatingActionButton btn;
    adapterMahasiswa adapterMahasiswa;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    ArrayList<modelMahasiswa> listMahasiswa;
    RecyclerView data_tampil;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data_tampil = findViewById(R.id.data_tampil);

        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, tambahMahasiswa.class));
                finish();
            }
        });

        search = findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        MyRecycler();

        tampilData();

    }

    private void MyRecycler() {
        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);
        data_tampil.setLayoutManager(mLayout);
        data_tampil.setItemAnimator(new DefaultItemAnimator());
    }

    private void tampilData() {
        db.child("Admin").child("Mahasiswa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listMahasiswa = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()){
                    modelMahasiswa mhs = item.getValue(modelMahasiswa.class);

                    mhs.setKey(item.getKey());
                    listMahasiswa.add(mhs);
                }
                adapterMahasiswa = new adapterMahasiswa(listMahasiswa, MainActivity.this);
                data_tampil.setAdapter(adapterMahasiswa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filter(String text) {
        ArrayList<modelMahasiswa> filteredList = new ArrayList<>();

        for (modelMahasiswa item : listMahasiswa){
            if (item.getNama().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        adapterMahasiswa.filterList(filteredList);
    }

    @Override
    public void onDeleteData(modelMahasiswa data, int position) {
        if (db != null){
            db.child("Admin").child("Mahasiswa").child(data.getKey())
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MainActivity.this, "Data Terhapus!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}