import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author mpcsj
 */
public class Main {

    public static void main(String[] args) throws IOException {
        InputStream is = System.in;
//        FileInputStream is = new FileInputStream(new File("entradas"));
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String[] entrada = br.readLine().split(" ");
        while (!entrada[0].equals("0") && !entrada[1].equals("0")) {
            int a = Integer.parseInt(entrada[0]);//altura
            int c = Integer.parseInt(entrada[1]);//comprimento
            int alturaDCadaParte[] = new int[c];
            entrada = br.readLine().split(" ");
            for (int i = 0; i < c; i++) {
                alturaDCadaParte[i] = Integer.parseInt(entrada[i]);
            }
            // processando entrada atual:
            int qtdVezesligado = 0;
            boolean laserLigado;
//            int antigoVlr;
            while (a > 0) {// analiso altura por altura
//                antigoVlr = qtdVezesligado;
                laserLigado = false;
                for (int i = 0; i < c; i++) {// analiso na horizontal pelo comprimento da figura
                    if (!laserLigado && alturaDCadaParte[i] < a) {
                        laserLigado = true;
                    } else if (laserLigado && alturaDCadaParte[i] >= a) {
                        laserLigado = false;
                        qtdVezesligado++;
                    }
                }
//                if (antigoVlr == qtdVezesligado) {
//                    qtdVezesligado++;
//                }
                if (laserLigado) {
                    qtdVezesligado++;
                }
                a--;
            }
            System.out.println(qtdVezesligado);
            // lendo proxima entrada
            entrada = br.readLine().split(" ");
        }
    }
}

