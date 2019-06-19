class Variabel {

    // konstanta
    public static final String INTEGER = "Integer";
    public static final String STRING = "String";

    public void outputApp() {
        int angka = 15;
        System.out.println(angka);
    }

    public static void main(String[] args) {
        Variabel variabel = new Variabel();
        variabel.outputApp();    
    }
}