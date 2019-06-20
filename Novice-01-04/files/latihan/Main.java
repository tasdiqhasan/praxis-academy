import MobilPack.*;

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
