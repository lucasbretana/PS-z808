Dados SEGMENT
V1 DW ?
V2 DW ?
Dados ENDS
Codigo SEGMENT
Inicio:
mov AX V2
mov DX AX
mov AX V1
mov V2 AX
mov AX DX
mov V1 AX
hlt
Codigo ENDS
END Inicio
