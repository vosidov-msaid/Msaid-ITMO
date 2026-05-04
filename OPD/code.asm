ORG 0x15B
START:  CLA
        LD N
        PUSH
        CALL FIB
        POP
        ST RES
        HLT

N:      WORD 0x0003
RES:    WORD 0x0000

FIB:    LD (SP+1)
        BEQ F_0
        DEC
        BEQ F_1
        ST COUNT
        CLA
        ST F0
        LD ONE
        ST F1

F_LP:   LD F0
        ADD F1
        ST NEXT
        LD F1
        ST F0
        LD NEXT
        ST F1

        LD COUNT
        DEC
        ST COUNT
        BNE F_LP
        LD F1
        JUMP EXIT

F_0:    CLA
        JUMP EXIT

F_1:    LD ONE

EXIT:   ST (SP+1)
        RET

COUNT:  WORD 0
F0:     WORD 0
F1:     WORD 0
NEXT:   WORD 0
ONE:    WORD 1