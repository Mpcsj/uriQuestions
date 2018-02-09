#include <cstdio>
int n, custoDiario, i, receita;
int receitaDCadaDia[50];
int funcao(int diaAtual);
int max(int a, int b);

int main() {
    int res;
    while (scanf("%d", &n) != EOF) {
        // n representa o numero de dias que o circo ficara na cidade
        scanf("%d", &custoDiario); // custo por dia do circo ficar na cidade
        for (i = 0; i < n; i++) {
            scanf("%d", &receitaDCadaDia[i]);
            receitaDCadaDia[i] -= custoDiario;
        }
        res = funcao(0);
        printf("%d\n", (res>=0)?res:0);
    }
    return 0;
}

int funcao(int diaAtual) {// nome criativo
    if (diaAtual >= n) {// caso base
        return 0;
    }
    receita = receitaDCadaDia[diaAtual];
    int maxValor = receita;
    for (i = diaAtual + 1; i < n; i++) {
        receita += receitaDCadaDia[i];
        if (receita > maxValor) {
            maxValor = receita;
        }
    }
    return max(maxValor, funcao(diaAtual + 1)); // dp[atual] recebe o maximo entre comecar no dia atual ou nao comecar no dia atual
}

int max(int a, int b) {
    return (a >= b) ? a : b;
}
