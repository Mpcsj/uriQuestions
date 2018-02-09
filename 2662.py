# -*- coding: utf-8 -*-

class Nota:
    def __init__(self,valor):
        self.valor = valor



dicionarioNotas = {0:'do',1:'do#',2:'re',3:'re#',4:'mi',5:'fa',6:'fa#',7:'sol',8:'sol#'
                   ,9:'la',10:'la#',11:'si',12:'desafinado'}

notasFundamentais = {0:0,2:2,4:4,5:5,7:7,9:9,11:11}
n = int(input())
incr = 0
aux = 0
notasTocadas= tuple(Nota(0) for i in range(n))

for i in range(n):
    notasTocadas[i].valor = (int(input())-1)%12 # ja saberei qual nota sera no intervalo fundamental


while incr <12:
    aux = incr
    for el in notasTocadas:
        if (el.valor-incr)% 12 not in notasFundamentais:
            incr += 1
            break
    if aux == incr:
        break

print(dicionarioNotas[incr])
