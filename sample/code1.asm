five EQU 5
five1 EQU a
EXTERN label:NEAR
EXTERN label:NEAR,label2:WORD,num:ABS
PUBLIC plbl,plbl1,plbl2
four DW 4
DW 4
char DW c
DW c
ques DW ?
DW ?
DW 10 DUP 10
EQU 5
sum1 add AX AX
add AX AX
sum2 add AX DX
add AX DX
sum3 add AX 10
add AX 10
sum4 add AX five
add AX five
dif1 sub AX AX
sub AX AX
dif2 sub AX DX
sub AX DX
dif4 sub AX five
sub AX five
END M1
