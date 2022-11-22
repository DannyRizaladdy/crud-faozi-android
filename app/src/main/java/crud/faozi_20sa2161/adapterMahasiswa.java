package crud.faozi_20sa2161;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterMahasiswa extends RecyclerView.Adapter<adapterMahasiswa.MyViewHolder> {
    private ArrayList<modelMahasiswa> myList;
    private Context context;

    public void filterList(ArrayList<modelMahasiswa> filteredList) {
        myList = new ArrayList<>();
        myList.addAll(filteredList);
        notifyDataSetChanged();
    }

    public interface dataListener{
        void onDeleteData(modelMahasiswa data, int position);
    }
    dataListener listener;

    public adapterMahasiswa(ArrayList<modelMahasiswa> myList, Context context) {
        this.myList = myList;
        this.context = context;
        listener = (MainActivity)context;
    }

    @NonNull
    @Override
    public adapterMahasiswa.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =inflater.inflate(R.layout.layout_view, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterMahasiswa.MyViewHolder holder, int position) {
        final String NIM = myList.get(position).getNim();
        final String Nama = myList.get(position).getNama();
        final String Jurusan = myList.get(position).getJurusan();
        final String Key = myList.get(position).getKey();

        holder.nim.setText("NIM : "+NIM);
        holder.nama.setText("Nama : "+Nama);
        holder.jurusan.setText("Jurusan : "+Jurusan);

        holder.card_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final String[] action = {"Update", "Delete"};
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setItems(action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i){
                            case 0:
                                Bundle bundle = new Bundle();
                                bundle.putString("dataNIM", NIM);
                                bundle.putString("dataNama", Nama);
                                bundle.putString("dataJurusan", Jurusan);
                                bundle.putString("getPrimaryKey", Key);
                                Intent intent = new Intent(view.getContext(), updateData.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1:
                                listener.onDeleteData(myList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                                break;
                        }

                    }
                });
                alert.create();
                alert.show();
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nim,nama,jurusan;
        CardView card_view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nim = itemView.findViewById(R.id.nim);
            nama = itemView.findViewById(R.id.nama);
            jurusan = itemView.findViewById(R.id.jurusan);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}
