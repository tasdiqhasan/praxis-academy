// mendefinisikan interface
interface Kendaraan {

    void gantiWarna(String warnaBaru);

    void jalan(int lebihCepat);

    void mengerem(int lebihLambat);

    void tampilOutput();
}

// mendefinisikan kelas mobil
class Mobil implements Kendaraan {

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

class Jeep extends Mobil {
    public void tampilOutput() {
        if(kecepatan > 0) { 
            System.out.println("Mobil dengan tipe Jeep warna " + warna + " melaju dengan kecepatan " + kecepatan + " km/jam \n");
        } else {
            System.out.println("Mobil dengan tipe Jeep warna " + warna + " sedang berhenti\n");
        }
    }
}

// main class
class MobilApp {
    public static void main(String[] args) {
        Mobil mobil1 = new Mobil();
        mobil1.tampilOutput();    

        Mobil mobil2 = new Mobil();
        mobil2.gantiWarna("Hijau");
        mobil2.jalan(35);
        mobil2.tampilOutput();

        Jeep jeep = new Jeep();
        jeep.tampilOutput();
    }
}
