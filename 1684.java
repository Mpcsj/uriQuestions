import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 *
 * @author mpcsj
 */
public class Main {

    static int n, m;
    static int[] grauDCadaVertice = new int[10000];

    public static void zeraAteN() {
        n--;
        while (n > -1) {
            grauDCadaVertice[n--] = 0;
        }
    }

    public static String retornaSePossivel(){
        for (int i = 0; i < n; i++) {
            if(grauDCadaVertice[i]%2!=0){
                return "No";
            }
        }
        return "Yes";
    }
    public static void main(String[] args) throws IOException {
        //-------------------------------------------------
        InputStream is = System.in;
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        //-------------------------------------------------
        int casosTeste = Integer.parseInt(br.readLine());
        String[] entrada;
        int i, a, b;
        while (casosTeste > 0) {
            entrada = br.readLine().split(" ");
            n = Integer.parseInt(entrada[0]);
            m = Integer.parseInt(entrada[1]);
            // lendo as arestas
            for (i = 0; i < m; i++) {
                entrada = br.readLine().split(" ");
                a = Integer.parseInt(entrada[0]);
                b = Integer.parseInt(entrada[1]);
                grauDCadaVertice[a]++;
                grauDCadaVertice[b]++;
            }
            System.out.println(retornaSePossivel());
            //zero a listaDeGraus de cada vertice ate onde eu usei
            zeraAteN();
            casosTeste--;
        }
    }
}

