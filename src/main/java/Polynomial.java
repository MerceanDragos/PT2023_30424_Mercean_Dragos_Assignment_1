import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Polynomial {
    private HashMap< Integer, Double > coefficients;

    Polynomial ( ) {
        coefficients = new HashMap<> ( );
    }

    public void setCoefficient ( Integer power, Double coefficient ) {
        if ( coefficient != 0.0d && power >= 0 )
            coefficients.put ( power, coefficient );
    }

    public Double getCoefficient ( Integer power ) {
        return coefficients.get ( power );
    }

    public boolean hasPower ( Integer power ) {
        return coefficients.containsKey ( power );
    }

    public ArrayList< Integer > getPowers ( ) {
        return new ArrayList< Integer > ( coefficients.keySet ( ) );
    }

    public void print ( ) {

        if ( coefficients.isEmpty ( ) ) {
            System.out.println ( "0" );
            return;
        }

        ArrayList< Integer > sortedKeys = new ArrayList< Integer > ( getPowers ( ) );


        sortedKeys.sort ( Collections.reverseOrder ( ) );


        for ( Integer i : sortedKeys ) {

            if ( coefficients.get ( i ) < 0 )
                System.out.print ( "- " );
            else {
                if ( !i.equals ( sortedKeys.get ( 0 ) ) )
                    System.out.print ( "+ " );
            }

            if ( coefficients.get ( i ) != 1.0d && coefficients.get ( i ) != -1.0d )
                System.out.print ( Utility.toFraction ( coefficients.get ( i ).toString ( ) ) );
            else {
                if ( i == 0 )
                    System.out.print ( Utility.toFraction ( coefficients.get ( i ).toString ( ) ) );
            }

            if ( i != 0 )
                System.out.print ( "x" );

            if ( i > 1 )
                System.out.print ( "^" + i );

            System.out.print ( " " );
        }

        System.out.println ( );
    }
}