
package MobilApp;

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

class Truk extends Mobil {
    public void tampilOutput() {
        if(kecepatan > 0) { 
            System.out.println("Truk warna " + warna + " melaju dengan kecepatan " + kecepatan + " km/jam \n");
        } else {
            System.out.println("Truk warna " + warna + " sedang berhenti\n");
        }
    }
}

// main class
public class Main {
    public static void main(String[] args) {

        // object mobil pertama
        Mobil mobil1 = new Mobil();
        mobil1.tampilOutput();    

        // object mobil kedua
        Mobil mobil2 = new Mobil();
        mobil2.gantiWarna("Hijau");
        // mobil2.jalan(35);
        mobil2.jalan(-35);
        mobil2.tampilOutput();


        // object truk
        Truk truk = new Truk();

        // exception handling jika kecepatan diisi string
        try{
            int kecepatanTruk = Integer.parseInt("teststring");
            truk.jalan(kecepatanTruk);
            truk.tampilOutput();
        } catch(NumberFormatException e) {
            System.out.println("Format kecepatan truk harus berupa integer\n");
        }

        // object jeep
        Jeep jeep = new Jeep();
        jeep.tampilOutput();
    }
}
