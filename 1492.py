# -*- coding: utf-8 -*-

'''
Escreva a sua solução aqui
Code your solution here
Escriba su solución aquí
'''
from math import ceil,log

def meuRange(a,b,intervalo = 1):
    while a<b:
        yield a
        a += intervalo


def calcula_qtd_um(num):
    def calculaColunas():
        qtd = 0
        indice = 1
        while num >= 2**indice:
            qtd += 1
            indice +=1
        qtd +=1
        if num == 2**qtd:
            qtd +=1
        return  qtd

    # para saber quantas colunas existem num numero, faco o log do numero na base 2
    # e arredondo para cima(caso o num nao seja potencia de 2)
    qtdColunas = 0


    qtdColunas =calculaColunas()

    # agora itero por colunas do numero

    resultado = 0
    for i in meuRange(1,qtdColunas+1):
        # a quantidade de uns existente num numero de 0 ate esse numero e igual
        # a quantidade de ciclos existentes de 0s e 1s pra cada coluna do numero
        # vezes a quantidade de uns existente dentro de um ciclo completo
        # mais quantos restaram do proximo ciclo nao concluido

        resultado += (num//2**i)*(2**(i-1)) # primeira parte (ciclos inteiros)
        # segunda parte ( remanescentes do ciclo atual)
        if (num%(2**i))>((2**i -1)//2):
            # se a pos do num dentro do ciclo atual for maior do que a metade do ciclo
            # tem numeros 1 para somar
            a = (num%(2**i))
            b = ((2**i)-1)//2
            resultado += ((num%(2**i)) - ((2**i -1)//2))


    return resultado


# -----------------------------------------------------------------------------#
while True:
    try:
        valores = input('').split()
        #try:
        valores = [int(valores[0]), int(valores[1])]
        print(calcula_qtd_um(valores[1]) - calcula_qtd_um(valores[0] - 1))
        #except ValueError:
           # break
    except EOFError:
        break


#print('Primeiro numero: {}'.format(calcula_qtd_um(valores[0])))
#print('Segundo numero: {}'.format(calcula_qtd_um(valores[1])))

