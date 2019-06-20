package MobilPack;

// mendefinisikan kelas mobil
public class Mobil implements Kendaraan {

    int kecepatan = 0;
    String warna = "Hitam";

    public void gantiWarna(String warnaBaru) {
        warna = warnaBaru;
    }

    public void jalan(int lebihCepat) {
        kecepatan = kecepatan + lebihCepat;
    }

    public void mengerem(int lebihLambat) {
        kecepatan = kecepatan - lebihLambat;
    }

    public void tampilOutput() {
        if(kecepatan > 0) { 
            System.out.println("Mobil warna " + warna + " melaju dengan kecepatan " + kecepatan + " km/jam \n");
        } else {
            System.out.println("Mobil warna " + warna + " sedang berhenti\n");
        }
    }
}