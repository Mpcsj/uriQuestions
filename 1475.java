import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author mpcsj
 */
public class Main {

    static int[] furos = new int[1000];
    static int[] qtdRemendos = new int[1000];//minha dp
    static int n, t1, t2;// o tamanho do pneu é uma informação irrelevante

    public static void main(String[] args) throws IOException {
        InputStream is = System.in;
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String[] entrada;
        int i;
        while (br.ready()) {
            // lendo primeira linha da entrada e atribuindo valores necessarios
            entrada = br.readLine().split(" ");
            n = Integer.parseInt(entrada[0]);
            t1 = Integer.parseInt(entrada[2]);
            t2 = Integer.parseInt(entrada[3]);
            //lendo a segunda linha com o furos
            entrada = br.readLine().split(" ");
            for (i = 0; i < n; i++) {
                qtdRemendos[i] = 0;// zerando a dp para reutilizar para a nova entrada e evitar que dados de uma entrada se misture com outra
                furos[i] = Integer.parseInt(entrada[i]);
            }
            System.out.println(funcao(n - 1));
        }
    }

    static int funcao(int furoAtual) {
        if (furoAtual < 0) {
            return 0;
        } else if (qtdRemendos[furoAtual] > 0) {
            return qtdRemendos[furoAtual];
        }
        // analisando do furo atual com um remendo, até onde consigo remendar utilizando um unico remendo
        int tamA = furoAtual - 1;
        for (; tamA > -1 && furos[furoAtual] - t1 <= furos[tamA]; tamA--) {
        }
        int tamB = furoAtual - 1;
        for (; tamB > -1 && furos[furoAtual] - t2 <= furos[tamB]; tamB--) {
        }
        return qtdRemendos[furoAtual] = Math.min(funcao(tamA) + t1, funcao(tamB) + t2);
        // quantidade de remendos para remendar o furo atual é o minimo entre usar o remendo 1 e o remendo 2
    }
}

