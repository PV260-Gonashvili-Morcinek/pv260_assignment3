package morcinek.assignment3task1;

/**
 *
 * @author Pavel Morcinek (433491@mail.muni.cz)
 */
public class TestCode {

    public void badTooLongMethod() {
        ;
        System.out.println();
        System.out.println();
        ;
        System.out.println();
        if(true) {
            System.out.println();
            System.out.println();
            ;
        }
        ;
        System.out.println();
        System.out.println();
        ;
        System.out.println();
        if(true) {
            System.out.println();
            System.out.println();
            ;
        }
        ;
        System.out.println();
        System.out.println();
        ;
        System.out.println();
        if(true) {
            System.out.println();
            System.out.println();
            ;
        }
        ;
        System.out.println();
        System.out.println();
        ;
        System.out.println();
        if(true) {
            System.out.println();
            System.out.println();
            ;
        }
        ;
        System.out.println();
        System.out.println();
        ;
        System.out.println();
        if(true) {
            System.out.println();
            System.out.println();
            ;
        }
        ;
        System.out.println();
        System.out.println();
        ;
        System.out.println();
        if(true) {
            System.out.println();
            System.out.println();
            ;
        }
        ;
        System.out.println();
        System.out.println();
        ;
        System.out.println();
        if(true) {
            System.out.println();
            System.out.println();
            ;
        }
    }
    
    public void badTooComplexMethod(int param) {
        if(param < 10) {
            System.out.println();
        }
        else if (param > 100){
            System.out.println();
            
            if(param >200) {
                System.out.println();
            }
            else {
                ;
            }  
        }
        
        switch(param) {
            case 0:
                break;
                
            case 1:
                break;
                
            case 2:
                break;
                
            case 3:
                break;
                
            case 4:
                break;
                
            case 5:
                break;
            
            case 6:
                break;
                
            case 7:
                break;
        }
    }
    
    public void badTooNestedMethod() {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 5; ++j){
                if(j < 2) {
                    System.out.println();
                }
                else if (j == 3)    // is not counted to nesting level
                    System.err.println();
                else {
                    ++i;
                    
                    for(int k = 0; k < 20; ++k) {
                    ;
                    ;
                    }
                }
            }
        }
    }
    
    public void badTooMuchVariablesMethod() {
        int what;
        String a;
        long nice;
        char day;
        float we;
        double have;
        boolean here;
        Byte in;
        Character Brno;
        
        Compiler lets;
        Runtime take;
        Integer it;
        Package even;
        Exception further;
        
        System.out.println();
    }
    
    public void goodShortSimpleMethod() {
        int first;
        float second;
        
        System.out.println();
        
        ;
        
        char third;
    }  
}
