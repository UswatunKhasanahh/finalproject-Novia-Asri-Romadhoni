package noviaasriromadhoni.pmo.aplikasi_wisata.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import noviaasriromadhoni.pmo.aplikasi_wisata.R;
import noviaasriromadhoni.pmo.aplikasi_wisata.model.DataKonten;
import noviaasriromadhoni.pmo.aplikasi_wisata.ui.user.BacaKonten;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.ViewHolder> implements Filterable {

    private ArrayList<DataKonten> listKonten2;
    private ArrayList<DataKonten> listKonten2_full;
    private Context context;

    public interface dataListener{
        void onDeleteData(DataKonten data, int position);
    }

    dataListener listener2;

    public AdapterUser(ArrayList<DataKonten> listKonten2, Context context) {
        this.listKonten2 = listKonten2;
        listKonten2_full = new ArrayList<>(listKonten2);
        this.context = context;
        listener2 = (dataListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_artikel_view_design, parent, false);
        return new ViewHolder(V);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final String Judul = listKonten2.get(position).getJudul();
        final String Artikel = listKonten2.get(position).getArtikel();
        final String Kategori = listKonten2.get(position).getKategori();
        final String Tanggal = listKonten2.get(position).getTanggal();
        final String Gambar = listKonten2.get(position).getGambar();

        //FUNGSI REPLACE PADA URL GAMBAR
        String sumber = Gambar;
        String a1 = sumber.replace("o?name=", "o/");
        String a2 = a1.replace("&uploadType=resumable", "?alt=media&uploadType=resumable");
        String a3 = a2.replace("&upload_id", "&upload_ids");
        String a4 = a3.replace("&upload_protocol=resumable", "");
        String imgsumber = a4;

        holder.Judul.setText(Judul);
        holder.Artikel.setText(Artikel);
        holder.Kategori.setText(Kategori);
        holder.Tanggal.setText(Tanggal);
        Glide.with(context).load(imgsumber).into(holder.Gambar);

        holder.ListItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("dataJudul", listKonten2.get(position).getJudul());
                bundle.putString("dataArtikel", listKonten2.get(position).getArtikel());
                bundle.putString("dataKategori", listKonten2.get(position).getKategori());
                bundle.putString("dataTanggal", listKonten2.get(position).getTanggal());
                bundle.putString("dataGambar", listKonten2.get(position).getGambar());
                bundle.putString("getPrimaryKey", listKonten2.get(position).getKey());
                Intent intent = new Intent(v.getContext(), BacaKonten.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listKonten2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Judul, Artikel, Kategori, Tanggal;
        private ImageView Gambar;
        private LinearLayout ListItem2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Judul = itemView.findViewById(R.id.judul);
            Artikel = itemView.findViewById(R.id.artikel);
            Kategori = itemView.findViewById(R.id.kategori);
            Tanggal = itemView.findViewById(R.id.tanggal);
            Gambar = itemView.findViewById(R.id.imageView);
            ListItem2 = itemView.findViewById(R.id.list_item2);
        }
    }

    @Override
    public Filter getFilter() {
        return dataFilter;
    }

    private Filter dataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DataKonten> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length()==0){
                filteredList.addAll(listKonten2_full);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (DataKonten item : listKonten2_full){
                    if(item.getJudul().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        };
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listKonten2.clear();
            listKonten2.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}

