SomaMem MACRO Mem1 Mem2
add Mem1 Mem1
add Mem1 Mem2
sub Mem1 Mem2
add Mem1 Mem2
add Mem1 Mem2
ENDM
DW 8
DW 3
SomaMem MCALL AX DX
DW 9
SomaMem MCALL AX AX
hlt
