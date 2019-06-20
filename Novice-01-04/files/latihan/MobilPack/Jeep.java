package MobilPack;

public class Jeep extends Mobil {
    public void tampilOutput() {
        if(kecepatan > 0) { 
            System.out.println("Mobil dengan tipe Jeep warna " + warna + " melaju dengan kecepatan " + kecepatan + " km/jam \n");
        } else {
            System.out.println("Mobil dengan tipe Jeep warna " + warna + " sedang berhenti\n");
        }
    }
}