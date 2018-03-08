import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
//import testes.TestaCodigo;

/**
 *
 * @author mpcsj
 */
class VerticeImpostoReal {

    int indice, dist;

    public VerticeImpostoReal(int a, int b) {
        indice = a;
        dist = b;
    }
}

public class Main {

    /**
     * @param args the command line arguments
     */
    static LinkedList<VerticeImpostoReal>[] listaAdj;//acho que nao vale a pena alocar dinamicamente pois se trata de apenas
    // um caso de teste por vez
    static int[] impostoPorCidade;//idem
    static int i, carga, n;

    public static void main(String[] args) throws IOException {
//        TestaCodigo testador = new TestaCodigo("Prob2666/MinhaSaida", "Prob2666/Entradas", "Prob2666/Saidas", true);
//        String[] in_out;
//        float caso;
//        while ((in_out = testador.enquantoTiverCasosDeTeste(true)) != null) {
        int a, b, c;
//            int res;
        String entrada[];
        InputStream in = System.in;
//            FileInputStream in = new FileInputStream(new File(in_out[0]));
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);
        entrada = br.readLine().split(" ");
        n = Integer.parseInt(entrada[0]);
        carga = Integer.parseInt(entrada[1]);
        listaAdj = new LinkedList[n];
        impostoPorCidade = new int[n];
        // segunda parte da leitura
        entrada = br.readLine().split(" ");
        for (i = 0; i < n; i++) {
            impostoPorCidade[i] = Integer.parseInt(entrada[i]);
        }
        // terceira parte da leitura
        for (i = 0; i < n - 1; i++) {
            entrada = br.readLine().split(" ");
            a = Integer.parseInt(entrada[0]) - 1;
            b = Integer.parseInt(entrada[1]) - 1;
            c = Integer.parseInt(entrada[2]);
            if (listaAdj[a] == null) {
                listaAdj[a] = new LinkedList<>();
            }
            if (listaAdj[b] == null) {
                listaAdj[b] = new LinkedList<>();
            }
            listaAdj[a].add(new VerticeImpostoReal(b, c));
            listaAdj[b].add(new VerticeImpostoReal(a, c));// pois e um grafo bidirecional

        }
//            res = dfs();
        System.out.println(dfs());
//            System.out.println(res);
//            testador.constroiArquivoDResposta(res + "");
//            caso = testador.comparaResposta(in_out[1],false,false);
//            if(caso!=1){
//                System.out.println("Caso de teste incorreto: "+in_out[0]);
//            }
//        }

//        dfs();
    }

    static int dfs() {
        if (n == 1) {
            return 0;
        }
        impostoPorCidade[0] = 0;
        int res = 0;
        LinkedList<VerticeImpostoReal> pilhaDCaminho = new LinkedList<>();
        VerticeImpostoReal aux1, aux2;
        pilhaDCaminho.add(new VerticeImpostoReal(0, 0));
        while (!pilhaDCaminho.isEmpty()) {// enquanto tiver caminho
            // se nao for folha, eu continuo avancando, se for folha, imprimo seu valor e removo da lista
            do {// enquanto a raiz tem caminhos disponiveis pra seguir
                // essas chamadas de funcoes abaixo podem me dar problemas
                if (listaAdj[pilhaDCaminho.getLast().indice].isEmpty()) {
                    // ultimo item da pilha virou folha
                    aux2 = pilhaDCaminho.getLast();
                    break;
                }
                aux1 = listaAdj[pilhaDCaminho.getLast().indice].getFirst();
                aux2 = pilhaDCaminho.removeLast();
                if (pilhaDCaminho.isEmpty() || aux1.indice != pilhaDCaminho.getLast().indice) {
                    // nao é uma folha
                    pilhaDCaminho.add(aux2);// devolvo para a pilha
                    pilhaDCaminho.add(aux1);// empilhei esse elemento
//                    dist += aux1.dist;
                } else {
                    listaAdj[aux2.indice].removeFirst();//removo a redundancia
                    // pode ser uma folha
                    pilhaDCaminho.add(aux2);
                    if (listaAdj[pilhaDCaminho.getLast().indice].size() >= 1) {
                        // nao e uma folha
                        pilhaDCaminho.add(listaAdj[pilhaDCaminho.getLast().indice].getFirst());
//                        dist += pilhaDCaminho.getLast().dist;
                    } else {
                        // e uma folha
                        break;
                    }
                }
            } while (!listaAdj[0].isEmpty());
            // aux2 é a folha
            if (impostoPorCidade[aux2.indice] != 0) {// so faz sentido visitar essa cidade se ela tem alguma coisa pra pagar
//                    dist -= aux2.dist;// volto um nivel na arvore
                pilhaDCaminho.removeLast();// que é o aux2
                if (pilhaDCaminho.isEmpty()) {
                    break;
                }
                res += 2 * aux2.dist * ((int) impostoPorCidade[aux2.indice] / carga);
                if (impostoPorCidade[aux2.indice] % carga != 0) {
                    res += 2 * aux2.dist;
                }
                impostoPorCidade[pilhaDCaminho.getLast().indice] += impostoPorCidade[aux2.indice];
//                impostoPorCidade[aux2.indice]=0; // desnecessario
                if (!listaAdj[pilhaDCaminho.getLast().indice].isEmpty()) {
                    listaAdj[pilhaDCaminho.getLast().indice].removeFirst();
                }
            } else {
                // so desempilho e removo esse elemento da arvore
//                dist -= aux2.dist;
                pilhaDCaminho.removeLast();
                if (pilhaDCaminho.isEmpty()) {
                    break;
                }
                listaAdj[pilhaDCaminho.getLast().indice].removeFirst();
            }
//            dist -= aux2.dist;
        }
        return res;
    }
}

