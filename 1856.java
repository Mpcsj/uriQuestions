import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author mpcsj
 */
public class Main {

    public static void main(String[] args) throws IOException {
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(in);

        int n, tam_bloco, q, i, j, pos, aux, a, b, qtd;

        n = Integer.parseInt(br.readLine());
        tam_bloco = 1000;
        qtd = (int) Math.sqrt(n) + 1;
        String[] entrada = br.readLine().split(" ");
        q = Integer.parseInt(br.readLine());

        LinkedList<Integer> listaDListas[] = new LinkedList[tam_bloco];
        HashMap<Integer, Integer> mapeamento = new HashMap<>();// mapea em qual grupo cada numero esta
        LinkedList<String[]> listaOpPendentes = new LinkedList<>();

        j = 0;
        for (i = 0; i < n; i++) {
            if (listaDListas[j] == null) {
                listaDListas[j] = new LinkedList();
            }
            aux = Integer.parseInt(entrada[i]);
            //se o tamanho da lista atual esta dentro do esperado, insere
            if (listaDListas[j].size() >= qtd) {
                j++;
                listaDListas[j] = new LinkedList();
            }
            listaDListas[j].add(aux);
            mapeamento.put(aux, j);
        }
        ListIterator<Integer> it;
        for (; q > 0; q--) {
            entrada = br.readLine().split(" ");
            listaOpPendentes.add(entrada);
            if (entrada[0].equals("Q")) {
//                operacoesPendentes(tam_bloco, listaOpPendentes, listaDListas, mapeamento, it);
                //------------------------------------------------------------------------------------//
                while (listaOpPendentes.size() > 1) {
                    entrada = listaOpPendentes.removeFirst();
                    a = Integer.parseInt(entrada[1]);
                    if (entrada[0].equals("I")) {
                        b = Integer.parseInt(entrada[2]);
                        it = listaDListas[mapeamento.get(b)].listIterator();
                        while (it.hasNext() && it.next() != b) {
                        }
                        it.add(a);
                        mapeamento.put(a, mapeamento.get(b));

                        // tentativa de otimizacao
                        // pego os elementos da lista atual e jogo para a lista seguinte
                        pos = mapeamento.get(a);
                        if (listaDListas[pos + 1] == null) {
                            listaDListas[pos + 1] = new LinkedList<>();
                        }
                        while (listaDListas[pos].size() > tam_bloco) {
                            aux = listaDListas[pos].removeLast();
                            listaDListas[(pos + 1)].addFirst(aux);
                            mapeamento.replace(aux, (pos + 1));
                        }
                    } else {
                        it = listaDListas[mapeamento.get(a)].listIterator();
                        while (it.hasNext() && it.next() != a) {
                        }
                        it.remove();
                    }
                }
                //------------------------------------------------------------------------------------//
                entrada = listaOpPendentes.removeFirst();
                a = Integer.parseInt(entrada[1]);
                b = Integer.parseInt(entrada[2]);
                if (a == b) {
                    System.out.println("-1");
                } else {
                    if (mapeamento.get(a) > mapeamento.get(b)) {
                        aux = a;
                        a = b;
                        b = aux;
                    }
                    pos = 0;
                    if (mapeamento.get(a).equals(mapeamento.get(b))) {
                        // procuro numa unica lista
                        it = listaDListas[mapeamento.get(a)].listIterator();
                        while (it.hasNext()) {
                            aux = it.next();
                            if (aux == a || aux == b) {
                                break;
                            }
                            pos++;
                        }
                        j = pos;
                        while (it.hasNext()) {
                            aux = it.next();
                            if (aux == a || aux == b) {
                                System.out.println((j - pos));
                                break;
                            }
                            j++;
                        }
                    } else {
                        // procuro em multiplas listas
                        aux = mapeamento.get(a);
                        i = mapeamento.get(b);
                        it = listaDListas[aux].listIterator();
                        while (it.hasNext() && it.next() != a) {
                            pos++;
                        }
                        qtd = listaDListas[aux].size() - pos - 1;
                        for (j = (aux + 1); j < i; j++) {
                            qtd += listaDListas[j].size();
                        }
                        it = listaDListas[i].listIterator();
                        pos = 0;
                        while (it.hasNext() && it.next() != b) {
                            pos++;
                        }
                        System.out.println(qtd + (pos));
                    }
                }
            }
        }
    }
}

