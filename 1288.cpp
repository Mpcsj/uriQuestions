#include<stdio.h>
#include<stdlib.h>
using namespace std;
int dp[51][101];

struct Projetil {
    int poderDestruicao, peso;
};
Projetil projeteis[50];

int main() {
    int casosTeste, n, k, r, i, j;
    bool conseguiu;
    // pre-inicializando a matriz de dp
    for (i = 0; i < 51; i++) {
        for (j = 0; j < 101; j++) {
            dp[i][j] = 0;
        }
    }
    scanf("%d", &casosTeste);
    while (casosTeste>0) {
        conseguiu = false;
        scanf("%d", &n);
        for (i = 0; i < n; i++) {
            scanf("%d %d", &projeteis[i].poderDestruicao, &projeteis[i].peso);
        }
        scanf("%d", &k); // capacidade de carga do canhao
        scanf("%d", &r); // resistencia do castelo
        // qtdPizzas = peso, tempo = poderDestruicao
        // processando
        for (i = 1; i <= n; i++) {// analisando por linha
            for (j = 1; j <= k && j < projeteis[i - 1].peso; j++) {//analisando por coluna
                // primeira parte
                dp[i][j] = dp[i - 1][j];
            }
            for (; j <= k; j++) {// remanescentes
                dp[i][j] = (projeteis[i - 1].poderDestruicao + dp[i - 1][j - projeteis[i - 1].peso] > dp[i - 1][j]) ? projeteis[i - 1].poderDestruicao + dp[i - 1][j - projeteis[i - 1].peso] : dp[i - 1][j];
                if (dp[i][j] >= r) {
                    printf("Missao completada com sucesso\n");
                    conseguiu = true;
                    break;
                }
            }
            if(conseguiu){
                break;
            }
        }
        if (!conseguiu) {
            printf("Falha na missao\n");
        }
        // zerando o que foi utilizado
        for (i = 1; i <= n; i++) {
            for (j = 1; j <= k; j++) {
                dp[i][j] = 0;
            }
        }
        casosTeste--;
    }
    return 0;
}

