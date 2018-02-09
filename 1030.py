# -*- coding: utf-8 -*-

casosTeste= int(input())
n = 0
k = 0
res = 0
entrada = None
for i in range(1,casosTeste+1):
    entrada = input().split()
    n,k = int(entrada[0]),int(entrada[1])
    res = 1
    for j in range(2,n+1):
        res = ((res + k - 1) % j) + 1

    print('Case {}: {}'.format(i,res))



'''o que fiz aqui foi transformar a versão padrão da recursao  mostrada abaixo em uma versão bottom-up, comumente utilizada em programação dinamica
pois a partir do momento em que você sabe que a recursão sempre chegará em n==1 e os calculos são feitos a partir daí,
não há a necessidade de descer a recursão até chegar em n==1, podemos simplesmente começar de uma vez em n==1 e subir até chegar
na quantidade de pessoas que existiam originalmente no circulo de pessoas


def funcao(int n,int k):
      if n==1:
      	return 1
      else:
      return (funcao(n-1,k)+k-1)%n+1
''' 
