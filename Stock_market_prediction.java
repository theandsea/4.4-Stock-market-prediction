import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Stock_market_prediction {
    public static void main(String[] args) throws Exception {
        System.out.println();
        System.out.println();
        System.out.println("===================4.4(a)===================");
        indic_estimate("nasdaq00.txt");

        System.out.println();
        System.out.println();
        System.out.println("===================4.4(b)===================");
        indic_estimate("nasdaq01.txt");
    }


    public static double indic_estimate(String datapath) throws Exception {
        String str = path_str(datapath);
        str_indice(str);
        double[][] A = indice_A();
        double[] X = indice_X();
        System.out.println("================reversed A=====================");
        double[][] reverse = Matrix.matrix_reverse(A);
        Matrix.matrix_printout(reverse);
        // check
        //Matrix.matrix_printout(Matrix.matrix_matrix(A,reverse));
        System.out.println("================solution of a=====================");
        double[] aa = Matrix.matrixvector(reverse, X);
        Matrix.matrix_printout(aa);

        // RMSD
        int T = indice.length - 3;
        double sum = 0;
        double error = 0;
        for (int i = 3; i < indice.length; i++) {
            error = indice[i] - aa[0] * indice[i - 1] + aa[1] * indice[i - 2] + aa[2] * indice[i - 3];
            sum += error * error;
        }
        sum /= T;
        System.out.println("================RMSD=====================");
        System.out.println(sum);

        return 0;
    }

    public static double[] indice = null;

    public static double[] str_indice(String str) {
        String[] indice_str = str.split("\r\n");
        indice = new double[indice_str.length];
        for (int i = 0, il = indice_str.length; i < il; i++) {
            indice[i] = Double.parseDouble(indice_str[i]);
        }
        return indice;
    }

    public static double[][] indice_A() {
        System.out.println("===============A==============");
        double[][] A = new double[3][3];
        double sum = 0;
        int l = indice.length;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sum = 0;
                for (int k = 3; k < l; k++) {
                    sum += indice[k - i - 1] * indice[k - j - 1];
                }
                A[i][j] = sum;
            }
        }
        Matrix.matrix_printout(A);
        return A;
    }

    public static double[] indice_X() {
        System.out.println("===============X==============");
        int l = indice.length;
        double[] X = new double[3];
        double sum = 0;
        for (int i = 0; i < 3; i++) {
            sum = 0;
            for (int k = 3; k < l; k++) {
                sum += indice[k] * indice[k - i - 1];
            }
            X[i] = sum;
        }
        Matrix.matrix_printout(X);
        return X;
    }


    public static String path_str(String path) throws IOException {
        StringBuffer buffer = new StringBuffer();
        BufferedReader bf = new BufferedReader(new FileReader(path));
        String s = null;
        while ((s = bf.readLine()) != null) {//使用readLine方法，一次读一行
            buffer.append(s.trim() + "\r\n");
        }

        return buffer.toString();
    }

}
