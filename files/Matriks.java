class Matriks {

    public static void main(String[] args) {

        int A[][]=new int[2][2];
        int B[][]=new int[2][2];
        int C[][]=new int[2][2];

        A[0][0] = 5;
        A[0][1] = 3;
        A[1][0] = 1;
        A[1][1] = 2;

        B[0][0] = 6;
        B[0][1] = 3;
        B[1][0] = 2;
        B[1][1] = 4;

        /* Melakukan penjumlahan matriks*/
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<2;j++)
            {
                C[i][j]=A[i][j]+B[i][j];
            }
        }
      
        System.out.println("\nHasil penjumlahan Matriks");
        System.out.println("===========================");
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<2;j++)
            {
                System.out.print(+(C[i][j])+" ");
            }
            System.out.println(" ");
        } 
    }
}