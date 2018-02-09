import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.LinkedList;

class TorreMagica {

    char notaTorre;
    int indiceRaiz;
    boolean analisado = false;

    public TorreMagica(char nota) {
        notaTorre = nota;
        indiceRaiz = -1;
    }
}

class ComponentePConexao {

//    int qtdElementosConectados;
    LinkedList<Integer> conexoes;

    public int qtdElementosConectados() {
        return (conexoes != null) ? conexoes.size() : 0;
    }

    public void add(int ponto) {
        conexoes.add(ponto);
//        qtdElementosConectados++;
    }

    public int removeEl() {
//        qtdElementosConectados--;
        return conexoes.removeFirst();
    }

//    public int contaEl() {
//        return conexoes.removeFirst();
//    }
    public ComponentePConexao() {
        this.conexoes = new LinkedList<>();
//        qtdElementosConectados = 0;
    }
}

public class Main {

    public static boolean analisaComponentesConexos(TorreMagica[] torres, ComponentePConexao[] conexoes) {
        for (int i = 0; i < conexoes.length; i++) {
            if (conexoes[i] != null && !conexoes[i].conexoes.isEmpty()) {
//                System.out.println("Componentes enraizados no vertice "+(i+1));
                int contaB = 0;
                int qtd = conexoes[i].qtdElementosConectados();
                for (int j = 0; j < qtd; j++) {
//                    int aux = conexoes[i].removeEl();
//                    System.out.print((aux+1)+"\t");
                    int pos = conexoes[i].removeEl();
                    torres[pos].analisado = true;
                    if (torres[pos].notaTorre == 'B') {
                        contaB++;
                    }
                }
//                System.out.println("");
                if (contaB % 2 != 0) {
//                    System.out.println("Notas B: "+contaB);
                    return false;
                }
            }
        }
        // analisar as torres isoladas remanescentes
        for (int i = 0; i < torres.length; i++) {
            if (torres[i].analisado == false && torres[i].notaTorre=='B') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        InputStream is = System.in;
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        while (br.ready()) {
            String[] entrada = br.readLine().split(" ");
//        while (entrada != null && !entrada[0].equals("")) {
            String notas[] = br.readLine().split(" ");
            int m = Integer.parseInt(entrada[1]);
            int n = Integer.parseInt(entrada[0]);
            TorreMagica[] torres = new TorreMagica[n];
            ComponentePConexao[] listaDConectividade = new ComponentePConexao[n]; // usando a ideia do Path Compression
            //inicializando as notas de cada torre
            for (int i = 0; i < n; i++) {
                torres[i] = new TorreMagica(notas[i].charAt(0));
            }
            // montando os componentes conexos
            for (; m > 0; m--) {
                entrada = br.readLine().split(" "); // leio dois vertices (vertA -1 e vertB -1)
                int pontoA = Integer.parseInt(entrada[0]) - 1;
                int pontoB = Integer.parseInt(entrada[1]) - 1;
//                if (pontoA > n || pontoB == 0) { // nao sei se e necessario
//                    if (pontoA > n) {
//                        pontoA = pontoA % (n + 1);
//                    }
//                    if (pontoB <= 0) {
//                        pontoB = (pontoB * -1) % (n + 1);
//                    }
//                }
                if (torres[pontoA].indiceRaiz == -1 && torres[pontoB].indiceRaiz == -1) {
                    // adiciono A como conexao de A e B como conexao de A
                    torres[pontoA].indiceRaiz = pontoA;
                    torres[pontoB].indiceRaiz = pontoA;
                    listaDConectividade[pontoA] = new ComponentePConexao();
                    listaDConectividade[pontoA].add(pontoA);
                    listaDConectividade[pontoA].add(pontoB);
                } else if ((torres[pontoA].indiceRaiz != -1) && (torres[pontoB].indiceRaiz != -1)) {
                    // caso ambos os pontos ja estejam conectados
                    // adiciono a subarvore do menor a subarvore do maior
                    if (torres[pontoA].indiceRaiz == torres[pontoB].indiceRaiz) {
                        // caso os dois pontos ja estejam conectados à mesma subarvore
                        // faco nada

                    } else if (listaDConectividade[torres[pontoA].indiceRaiz].qtdElementosConectados() >= listaDConectividade[torres[pontoB].indiceRaiz].qtdElementosConectados()) {
                        // adiciono os componentes de B em A
                        int j = listaDConectividade[torres[pontoB].indiceRaiz].qtdElementosConectados();
                        int antigaRaiz = torres[pontoB].indiceRaiz;
                        for (; j > 0; j--) {
                            int temp = listaDConectividade[antigaRaiz].removeEl();
                            listaDConectividade[torres[pontoA].indiceRaiz].add(temp);
                            torres[temp].indiceRaiz = torres[pontoA].indiceRaiz;
                            // o indice da torre temporaria passa a apontar para quem a torre A aponta

                        }
                    } else {
                        // adiciono os componentes de A em B
                        int j = listaDConectividade[torres[pontoA].indiceRaiz].qtdElementosConectados();
                        int antigaRaiz = torres[pontoA].indiceRaiz;//salvei a raiz de onde os elementos serao retirados
                        for (; j > 0; j--) {
                            int temp = listaDConectividade[antigaRaiz].removeEl();
                            listaDConectividade[torres[pontoB].indiceRaiz].add(temp);
                            torres[temp].indiceRaiz = torres[pontoB].indiceRaiz;
                            // o indice da torre temporaria passa a apontar para quem a torre B aponta
                        }

                    }
                } else { // caso um ou outro esteja conectado
                    if (torres[pontoA].indiceRaiz != -1) {
                        //caso o A esteja conectado e o B nao
                        listaDConectividade[torres[pontoA].indiceRaiz].add(pontoB);
                        torres[pontoB].indiceRaiz = torres[pontoA].indiceRaiz; // raiz da torre B recebe a raiz da torre A
                    } else {
                        //caso o B esteja conectado e o A nao
                        listaDConectividade[torres[pontoB].indiceRaiz].add(pontoA);
                        torres[pontoA].indiceRaiz = torres[pontoB].indiceRaiz;// raiz da torre A recebe a raiz da torre B

                    }
                }
            }
            System.out.println((analisaComponentesConexos(torres, listaDConectividade) ? "Y" : "N"));
//            entrada = br.readLine().split(" ");
//           
        }
    }
}

