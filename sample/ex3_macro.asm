Dados SEGMENT
Var1 DW 5
Var2 DW 8
Var3 DW 3
Dados ENDS

Codigo SEGMENT
SomaMem MACRO Mem1 MEM2 ;; Soma duas variaveis
mov AX Mem1
push AX
mov AX Mem2
mov DX AX
pop AX
add AX DX
mov Mem1 AX
ENDM
Inicio:
SomaMem MCALL Var1 Var2
SomaMem MCALL Var1 Var3
Codigo ENDS
END Inicio

Pilha SEGMENT
DW ?
DW ?
Pilha ENDS
