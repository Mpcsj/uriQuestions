/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.cpp
 * Author: mpcsj
 *
 * Created on 19 de Março de 2018, 11:17
 */

//#include <cstdlib>
#include<iostream>
#include<list>
//--------------------------------------//
using namespace std;
const int MAX = 500;
int grafo[MAX][MAX];
//--------------------------------------//

struct Indice {
    int x, y;
};
//--------------------------------------//

struct DadoDjikstra {
    bool aberto = true;
    int peso = -1;
    int origem = -1;
};
//--------------------------------------/

struct Peso_Origem {
    int peso, origem;
};
//--------------------------------------//
DadoDjikstra tabela[MAX];

/*
 * 
 */
//void insereOrdenado(Peso_Origem &peso_origem, list<Peso_Origem> &indices) {
//    list<Peso_Origem>::iterator it;
//    register int i;
//    for (it = indices.begin(), i = 1; it != indices.end() && it->peso < peso_origem.peso; it++, i++);
//    if (i == indices.size()) {
//        indices.push_back(peso_origem);
//    } else {
//        if (it->peso == peso_origem.peso)it++; // dando preferencia ao que ja existia
//        indices.insert(it, peso_origem);
//    }
//}
bool compare(const Peso_Origem& first, const Peso_Origem& second)
{
//  if (first.peso < second.peso)
//    return true;
//  else
//    return false;
    return (first.peso<second.peso);
}
int djikstra(int a, int b, int tam) {
    //--------------------------------------------------//
    register int i;
    int pontoAnalisado = a, peso, limite, j;
    //--------------------------------------------------//
    for (i = 0; i < tam; i++) {// inicializando a tabela do Djikstra
        tabela[i].aberto = true;
        tabela[i].peso = -1;
        tabela[i].origem = -1;
    }
    //--------------------------------------------------//
    tabela[a].origem = a;
    tabela[a].peso = 0;
    Peso_Origem peso_origem;
    list<Peso_Origem> listaDPontos;
    peso_origem.peso = 0;
    peso_origem.origem = a;
    //--------------------------------------------------//
    listaDPontos.push_back(peso_origem);
    //--------------------------------------------------//
    while (pontoAnalisado != b && !listaDPontos.empty()) {
        // enquanto a lista de pontos nao estiver vazia e o ponto analisado nao for o ponto final
        // vendo se o ponto esta aberto
        if (tabela[pontoAnalisado].aberto) {//se o ponto analisado estiver aberto
            limite = pontoAnalisado;
            i = 0;
            for (j = 0; j < 2; j++) {
                for (; i < limite; i++) {
                    if (grafo[pontoAnalisado][i] != 0) {
                        peso = listaDPontos.front().peso; // recebe o peso do pai
                        if (grafo[i][pontoAnalisado] == 0) {
                            // se nao existe um caminho de ida e volta entre esses dois vertices, a distancia sera o valor encontrado
                            // dentro da posicao da matriz
                            peso += grafo[pontoAnalisado][i];
                        }
                        if (tabela[i].peso == -1 || tabela[i].peso > peso) {
                            tabela[i].peso = peso;
                            tabela[i].origem = pontoAnalisado;
                            peso_origem.peso = peso;
                            peso_origem.origem = i;
                            listaDPontos.push_back(peso_origem);
                            //                            insereOrdenado(peso_origem, listaDPontos);
                            listaDPontos.sort(compare);
                        }

                    }
                }
                limite = tam;
                i++; // ainda nao sei se sera necessario
            }
            // fecho o ponto analisado
            tabela[pontoAnalisado].aberto = false;
        }
        // pego o proximo ponto
        listaDPontos.pop_front();
        if (listaDPontos.empty())return -1;
        peso_origem = listaDPontos.front();
        pontoAnalisado = peso_origem.origem;
    }
    return tabela[b].peso;
}

int main(int argc, char** argv) {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    //    FILE *pfile;
    //    pfile = fopen("res_paises_em_guerra.txt","w");
    int n, e, a, b, c;
    register int i;
    for (cin >> n, cin >> e; n; cin >> n, cin >> e) {
        // usando uma lista encadeada para armazenar os elementos inseridos
        list<Indice>indicesUsados; // para ser mais eficiente ao remover os indices usados
        Indice indice;
        for (i = 0; i < e; i++) {
            //-----------------------------//Reading from console
            cin>>a;
            cin>>b;
            cin>>c;
            //-----------------------------//updating the positions in the matrix
            a--;
            b--;
            //-----------------------------// storing in the matrix
            grafo[a][b] = c;
            //-----------------------------// storing in the list
            indice.x = a;
            indice.y = b;
            indicesUsados.push_back(indice);
            //-----------------------------//
        }
        cin>>c; // c sera usado como k
        for (i = 0; i < c; i++) {
            cin>>a;
            cin>>b;
            a--;
            b--;
            e = djikstra(a, b, n);
            if (e == -1) {
                cout << "Nao e possivel entregar a carta" << endl;
                //                fprintf(pfile,"Nao e possivel entregar a carta\n");
            } else {
                cout << e << endl;
                //                fprintf(pfile,"%d\n",e);
            }
        }
        cout << "\n";
        //        fprintf(pfile,"\n");
        c = indicesUsados.size();
        for (i = 0; i < c; i++) {//zerando o que foi utilizado
            indice = indicesUsados.front();
            grafo[indice.x][indice.y] = 0;
            indicesUsados.pop_front();
        }
    }
//    cout << "\n";
    //    fclose(pfile);
    return 0;
}


