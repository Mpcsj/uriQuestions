#include<cstdlib>
#include <bits/stdc++.h>
using namespace std;

const int NEGINF = -1, NOTPROCESSED = -2;
int palitos[101];

void zeraAteOndeUsado(int maxPalitoUsado);
void inicializaVector(int linhas, int colunas, vector<vector<int>>&dp);
int max(int a, int b);
int min(int a, int b);
int absoluto(int a);
int funcao(int palitoAnalisado, int diff, vector<vector<int>>&dp);

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    register int i;
    int n=10, maiorPalito, palito, diferencaMaxima;
    while (cin >> n && n) {
        maiorPalito = -1;
        diferencaMaxima = 0;
        for (i = 0; i < n; i++) {
            cin >> palito;
            palitos[palito]++;
            maiorPalito = max(palito, maiorPalito);
            diferencaMaxima += palito;
        }
        vector<vector<int>>dp(maiorPalito + 1, vector<int>(diferencaMaxima + 1));
        //        vector<vector<int>>dp(qtdLinhas, vector<int>(qtdColunas));
        inicializaVector(maiorPalito + 1, diferencaMaxima + 1, dp);
        cout << funcao(maiorPalito, 0, dp) << endl;
        zeraAteOndeUsado(maiorPalito);
    }
    return 0;
}

int funcao(int palitoAnalisado, int diff, vector<vector<int>>&dp) {
    if (dp[palitoAnalisado][diff] == NOTPROCESSED) // Verifica se essa combinação de entradas já foi calculada.
    {
        if (palitoAnalisado == 1) // Como a função é decrescente, 'indx = 1' representa o último ciclo, e deve ser terminal.
        {
            if (diff > palitos[1]) {// caso base da recursao
                // Se a diferença atual é maior que a quantidade de palitos disponíveis de tamanho 1, não é possível igualar as antenas...
                dp[palitoAnalisado][diff] = NEGINF; // caso impossivel
            } else {
                dp[palitoAnalisado][diff] = (palitos[1] + diff) / 2; // Se for possível igualar as antenas, então calculasse a contribuição dos palitos de tamanho 1 para igualar as antenas. Os palitos restantes podem ser pareados para encher as antenas. A fórmula simplifcada é essa <--
            }
        } else if (palitos[palitoAnalisado] == 0) { // Caso não exista palitos do tamanho especificado...
            dp[palitoAnalisado][diff] = funcao(palitoAnalisado - 1, diff, dp); // ...pula pro próximo e copia a resposta.
        } else {
            int melhorCombinacao = NEGINF;
            for (int d = 0; d <= palitos[palitoAnalisado]; d++) {
                int adicaoGarantida = ((palitos[palitoAnalisado] - d) / 2) * palitoAnalisado;
                // CASO 1: A diferença entre as antenas AUMENTA:
                int resultadoParcial = funcao(palitoAnalisado - 1, diff + d * palitoAnalisado, dp);
                if (resultadoParcial != -1) {
                    melhorCombinacao = max(melhorCombinacao, adicaoGarantida + resultadoParcial);
                }
                // CASO 2: A diferença entre as antenas DIMINUI:
                adicaoGarantida += min(diff, d * palitoAnalisado); // Se estamos diminuido a diferença das antenas, a antena menor está crescendo, então a adição garantida das antenas também aumenta
                resultadoParcial = funcao(palitoAnalisado - 1, absoluto(diff - d * palitoAnalisado), dp); // Chama a recursão para os palitos menores, com a diferença um pouco melhor entre as antenas.
                if (resultadoParcial != -1) {
                    melhorCombinacao = max(melhorCombinacao, adicaoGarantida + resultadoParcial);
                }
            }
            dp[palitoAnalisado][diff] = melhorCombinacao; // Salva o melhor valor encontrado
        }
    }
    return dp[palitoAnalisado][diff];

}

void zeraAteOndeUsado(int maxPalitoUsado) {
    for (; maxPalitoUsado>-1; maxPalitoUsado--)palitos[maxPalitoUsado] = 0;
}

void inicializaVector(int linhas, int colunas, vector<vector<int>>&dp) {
    register int i, j;
    for (i = 0; i < linhas; i++) {
        for (j = 0; j < colunas; j++)dp[i][j] = NOTPROCESSED;
    }
}

int max(int a, int b) {
    return (a >= b) ? a : b;
}

int min(int a, int b) {
    return (a <= b) ? a : b;
}

int absoluto(int a) {
    return (a >= 0) ? a : (a*-1);
}


