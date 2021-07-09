package noviaasriromadhoni.pmo.aplikasi_wisata.model;

public class DataKonten {
    //Deklarasi Variabel
    private String judul;
    private String artikel;
    private String kategori;

    private String tanggal;
    //private String foto;

    private String gambar;
    private String key;

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getArtikel() {
        return artikel;
    }

    public void setArtikel(String artikel) {
        this.artikel = artikel;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }


    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DataKonten() {
    }

    public DataKonten(String judul, String artikel, String tanggal, String kategori, String gambar) {
        this.judul = judul;
        this.artikel = artikel;
        this.kategori = kategori;
        this.tanggal = tanggal;
        this.gambar = gambar;
    }
}

