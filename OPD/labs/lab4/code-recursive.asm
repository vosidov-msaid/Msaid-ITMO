ORG     0x15B
START:  CLA
        LD N
        PUSH
        CALL RFIB
        POP
        ST RES
        HLT

N:      WORD 0x6
RES:    WORD 0x0000

RFIB:   LD (SP+1)
        BEQ RR
        DEC
        BEQ RR_1

        LD (SP+1)
        DEC
        PUSH
        CALL RFIB
        
        
        POP 
        ST F1
        
        LD (SP+1)
        SUB TWO
        PUSH
        CALL RFIB
        
        POP
        ADD F1
        
        ST (SP+1)
        RET

RR:     CLA
        ST (SP+1)
        RET

RR_1:   LD ONE
        ST (SP+1)
        RET

F1: WORD 0
TWO:     WORD 2
ONE:     WORD 1