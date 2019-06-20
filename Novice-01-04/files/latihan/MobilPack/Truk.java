package MobilPack;

public class Truk extends Mobil {
    public void tampilOutput() {
        if(kecepatan > 0) { 
            System.out.println("Truk warna " + warna + " melaju dengan kecepatan " + kecepatan + " km/jam \n");
        } else {
            System.out.println("Truk warna " + warna + " sedang berhenti\n");
        }
    }
}