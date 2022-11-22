package crud.faozi_20sa2161;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class tambahMahasiswa extends AppCompatActivity {
    EditText nim, nama, jurusan, instansi;
    Button btnSimpan, btnLihat;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_mahasiswa);

        nim = findViewById(R.id.nim);
        nama = findViewById(R.id.nama);
        jurusan = findViewById(R.id.jurusan);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnLihat= findViewById(R.id.btnLihat);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getNim = nim.getText().toString();
                String getNama = nama.getText().toString();
                String getJurusan =  jurusan.getText().toString();

                if(getNim == null){
                    nim.setText("Masukan NIM!");
                }else if(getNama == null){
                    nama.setText("Masukan Nama!");
                }else if(getJurusan == null){
                    jurusan.setText("Masukan Jurusan!");
                }else {

                    db.child("Admin").child("Mahasiswa").push()
                            .setValue(new modelMahasiswa(getNim, getNama, getJurusan))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(tambahMahasiswa.this, "Data Berhasil Ditambahkan!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(tambahMahasiswa.this, MainActivity.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(tambahMahasiswa.this, "Data Gagal Ditambahkan!", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }
}