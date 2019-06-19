class Variabel {

    // konstanta
    public static final String INTEGER = "Integer";
    public static final String STRING = "String";

    public void outputApp() {
        int angka = 15;
        String huruf = "Huruf";

        if(angka > 10) {
            System.out.println("Nilai angka lebih dari 10");
        } else if(angka < 10 ) {
            System.out.println("Nilai angka kurang dari 10");
        } else {
            System.out.println("Akeh");
        }
    }

    public static void main(String[] args) {
        Variabel variabel = new Variabel();
        variabel.outputApp();    
    }
}