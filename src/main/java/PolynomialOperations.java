import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

abstract public class PolynomialOperations {

    static public Polynomial add ( final Polynomial P, final Polynomial Q ) {

        ArrayList< Integer > powersP = P.getPowers ( );
        ArrayList< Integer > powersQ = Q.getPowers ( );

        Collections.sort ( powersP );
        Collections.sort ( powersQ );

        Polynomial R = new Polynomial ( );

        for ( Integer power : powersP ) {
            if ( Q.hasPower ( power ) && P.getCoefficient ( power ) + Q.getCoefficient ( power ) != 0 )
                R.setCoefficient ( power, P.getCoefficient ( power ) + Q.getCoefficient ( power ) );
            else if ( P.getCoefficient ( power ) + Q.getCoefficient ( power ) != 0 )
                R.setCoefficient ( power, P.getCoefficient ( power ) );
        }

        for ( Integer power : powersQ ) {
            if ( !R.hasPower ( power ) && !P.hasPower ( power ) ) {
                R.setCoefficient ( power, Q.getCoefficient ( power ) );
            }
        }

        return R;
    }

    static public Polynomial subtract ( Polynomial P, Polynomial Q ) {

        ArrayList< Integer > powersP = P.getPowers ( );
        ArrayList< Integer > powersQ = Q.getPowers ( );

        Collections.sort ( powersP );
        Collections.sort ( powersQ );

        Polynomial R = new Polynomial ( );

        for ( Integer power : powersP ) {
            if ( Q.hasPower ( power ) && P.getCoefficient ( power ) - Q.getCoefficient ( power ) != 0 )
                R.setCoefficient ( power, P.getCoefficient ( power ) - Q.getCoefficient ( power ) );
            else if ( P.getCoefficient ( power ) - Q.getCoefficient ( power ) != 0 )
                R.setCoefficient ( power, P.getCoefficient ( power ) );
        }

        for ( Integer power : powersQ ) {
            if ( !R.hasPower ( power ) && !P.hasPower ( power ) ) {
                R.setCoefficient ( power, -Q.getCoefficient ( power ) );
            }
        }

        return R;
    }

    static public Polynomial multiply ( Polynomial P, Polynomial Q ) {
        return P;
    }

    static public Polynomial divide ( Polynomial P, Polynomial Q ) {
        return P;
    }

    static public Polynomial differentiate ( Polynomial P ) {
        return P;
    }

    static public Polynomial integrate ( Polynomial P ) {
        return P;
    }
}
