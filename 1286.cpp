#include<stdio.h>
#include<stdlib.h>
using namespace std;
int dp[21][31];

struct Pedido {
    int qtdPizzas, tempo;
};
Pedido pedidos[20];

int main() {
    int n, p, i, j;
    // preenchendo o vetor de pedidos e a matriz inicialmente
    for (i = 0; i < 20; i++) {
        pedidos[i].qtdPizzas=0;
        pedidos[i].tempo = 0;
        dp[0][i] = 0;
    }
    // terminando o que falta para a matriz
    for (; i < 31; i++) {// o que faltou na primeira coluna
        dp[0][i] = 0;
    }
    // para o resto da matriz
    for (i = 1; i < 21; i++) {// varrendo por linhas
        for (j = 0; j < 31; j++) {// varrendo por colunas
            dp[i][j] = 0;
        }
    }
    scanf("%d", &n);
    while (n != 0) {
        scanf("%d", &p);
        for (i = 0; i < n; i++) {
            scanf("%d %d", &pedidos[i].tempo, &pedidos[i].qtdPizzas);
        }
        // processando
        for (i = 1; i <= n; i++) {// analisando por linha(pedido a pedido)
            for (j = 1; j <= p && j < pedidos[i - 1].qtdPizzas; j++) {//analisando por coluna( qtas pizzas Roberto pode aceitar
                // primeira parte(menos pizzas do que Roberto pode levar)
                dp[i][j] = dp[i - 1][j];
            }
            for (; j <= p; j++) {// os outros pedidos
                dp[i][j] = (pedidos[i - 1].tempo + dp[i - 1][j - pedidos[i - 1].qtdPizzas] > dp[i - 1][j]) ? pedidos[i - 1].tempo + dp[i - 1][j - pedidos[i - 1].qtdPizzas] : dp[i - 1][j];
            }
        }
        printf("%d min.\n", dp[n][p]);
        // zerando o que foi utilizado
        for (i = 1; i <= n; i++) {
            for (j = 1; j <= p; j++) {
                dp[i][j] = 0;
            }
        }
        scanf("%d", &n);
    }
    return 0;
}

