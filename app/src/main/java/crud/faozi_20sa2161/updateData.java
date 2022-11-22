package crud.faozi_20sa2161;

import static android.text.TextUtils.isEmpty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class updateData extends AppCompatActivity {
    EditText nimBaru, namaBaru, jurusanBaru;
    String cekNim, cekNama, cekJurusan;
    Button btnUpdate;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        nimBaru = findViewById(R.id.new_nim);
        namaBaru= findViewById(R.id.new_nama);
        jurusanBaru= findViewById(R.id.new_jurusan);
        btnUpdate= findViewById(R.id.btnUpdate);
        getData();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekNim = nimBaru.getText().toString();
                cekNama = namaBaru.getText().toString();
                cekJurusan = jurusanBaru.getText().toString();
                if (isEmpty(cekNim) && isEmpty(cekNama) && isEmpty(cekJurusan)) {
                    Toast.makeText(updateData.this, "ISI DATA", Toast.LENGTH_SHORT).show();
                }else{
                    modelMahasiswa setMahasiswa = new modelMahasiswa();
                    setMahasiswa.setNim(cekNim);
                    setMahasiswa.setNama(cekNama);
                    setMahasiswa.setJurusan(cekJurusan);
                    updatemahasiswa(setMahasiswa);
                }
            }
        });
    }

    private void updatemahasiswa(modelMahasiswa setMahasiswa) {
        String getKey = getIntent().getExtras().getString("getPrimaryKey");
        db.child("Admin").child("Mahasiswa").child(getKey)
                .setValue(setMahasiswa)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(updateData.this, "Data Berubah", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(updateData  .this, MainActivity.class));
                        finish();
                    }
                });

    }

    private void getData() {
        final String getNim = getIntent().getExtras().getString("dataNIM");
        final String getNama = getIntent().getExtras().getString("dataNama");
        final String getJurusan = getIntent().getExtras().getString("dataJurusan");
        nimBaru.setText(getNim);
        namaBaru.setText(getNama);
        jurusanBaru.setText(getJurusan);
    }
}