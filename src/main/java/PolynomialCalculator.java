import java.util.ArrayList;

abstract public class PolynomialCalculator {

    //Calculate methods are meant for easier usage of the PolynomialCalculator class
    //All operations except division return a signal polynomial, so we wrap the results of these methods in appropriately sized Polynomial arrays
    static public Polynomial[] calculate ( Polynomial P, String operation, Polynomial Q ) throws IllegalArgumentException {
        switch ( operation ) {
            case "+" -> {
                return new Polynomial[]{ add ( P, Q ) };
            }
            case "-" -> {
                return new Polynomial[]{ subtract ( P, Q ) };
            }
            case "*" -> {
                return new Polynomial[]{ multiply ( P, Q ) };
            }
            case "/" -> {
                return divide ( P, Q );
            }
            default -> {
                System.out.println ( "Something went wrong" );
                return new Polynomial[]{ P };
            }
        }
    }

    static public Polynomial calculate ( String operation, Polynomial P ) {
        if ( operation.equals ( "d/dx" ) )
            return differentiate ( P );
        else
            return integrate ( P );
    }

    //adds P and Q
    static private Polynomial add ( final Polynomial P, final Polynomial Q ) {

        ArrayList< Integer > powersP = P.getPowers ( );
        ArrayList< Integer > powersQ = Q.getPowers ( );

        Polynomial R = new Polynomial ( );

        for ( Integer power : powersP ) {
            if ( !Q.hasPower ( power ) )
                R.setCoefficient ( power, P.getCoefficient ( power ) );
            else if ( P.getCoefficient ( power ) + Q.getCoefficient ( power ) != 0 )
                R.setCoefficient ( power, P.getCoefficient ( power ) + Q.getCoefficient ( power ) );
        }

        for ( Integer power : powersQ )
            if ( !R.hasPower ( power ) && !P.hasPower ( power ) )
                R.setCoefficient ( power, Q.getCoefficient ( power ) );

        return R;
    }

    //subtracts subtrahend from minuend
    static private Polynomial subtract ( final Polynomial minuend, final Polynomial subtrahend ) {

        ArrayList< Integer > powersMinuend = minuend.getPowers ( );
        ArrayList< Integer > powersSubtrahend = subtrahend.getPowers ( );

        Polynomial difference = new Polynomial ( );

        for ( Integer power : powersMinuend ) {
            if ( !subtrahend.hasPower ( power ) )
                difference.setCoefficient ( power, minuend.getCoefficient ( power ) );
            else if ( minuend.getCoefficient ( power ) - subtrahend.getCoefficient ( power ) != 0 )
                difference.setCoefficient ( power, minuend.getCoefficient ( power ) - subtrahend.getCoefficient ( power ) );
        }

        for ( Integer power : powersSubtrahend ) {
            if ( !difference.hasPower ( power ) && !minuend.hasPower ( power ) ) {
                difference.setCoefficient ( power, -subtrahend.getCoefficient ( power ) );
            }
        }

        return difference;
    }

    //helper method for actual multiplication method,
    //multiplies a polynomial with a monomial
    static private Polynomial multiply ( final Polynomial P, final Integer power, final Double coefficient ) {
        ArrayList< Integer > powers = P.getPowers ( );
        Polynomial Q = new Polynomial ( );

        for ( Integer i : powers )
            Q.setCoefficient ( power + i, coefficient * P.getCoefficient ( i ) );


        return Q;
    }

    //multiplies P by Q
    static private Polynomial multiply ( final Polynomial P, final Polynomial Q ) {

        ArrayList< Integer > powersP = P.getPowers ( );

        Polynomial product = new Polynomial ( );

        for ( Integer power : powersP ) {
            product = add ( product, multiply ( Q, power, P.getCoefficient ( power ) ) );
        }

        return product;
    }

    //helper method for actual division method,
    //divides two monomials
    static private Polynomial divide ( final Integer powerP, final Double coefficientP, final Integer powerQ, final Double coefficientQ ) {
        Polynomial R = new Polynomial ( );
        R.setCoefficient ( powerP - powerQ, coefficientP / coefficientQ );
        return R;
    }

    //divides dividend by divisor
    static private Polynomial[] divide ( final Polynomial dividend, final Polynomial divisor ) throws IllegalArgumentException {
        Polynomial quotient = new Polynomial ( );
        Polynomial rest = new Polynomial ( dividend );

        if ( divisor.isZeroPolynomial ( ) )
            throw new IllegalArgumentException ( );
        if ( dividend.isZeroPolynomial ( ) )
            return new Polynomial[]{ quotient, rest };

        Integer orderOfB = divisor.getOrder ( );
        Double coefficientOfHighestPowerOfB = divisor.getCoefficient ( orderOfB );
        Integer orderOfrest;

        while ( rest.getOrder ( ) >= divisor.getOrder ( ) && !rest.isZeroPolynomial () ) {
            orderOfrest = rest.getOrder ( );
            quotient = add ( quotient, divide ( orderOfrest, rest.getCoefficient ( orderOfrest ), orderOfB, coefficientOfHighestPowerOfB ) );
            rest = subtract ( rest, multiply ( divisor, quotient.getSmallestPower ( ), quotient.getCoefficient ( quotient.getSmallestPower ( ) ) ) );
        }

        return new Polynomial[]{ quotient, rest };
    }

    //differentiates P at point x
    static private Polynomial differentiate ( final Polynomial P ) {

        ArrayList< Integer > powers = P.getPowers ( );
        Polynomial R = new Polynomial ( );

        for ( Integer power : powers )
            if ( power != 0 )
                R.setCoefficient ( power - 1, P.getCoefficient ( power ) * power );

        return R;
    }

    //integrates P
    static private Polynomial integrate ( final Polynomial P ) {

        ArrayList< Integer > powers = P.getPowers ( );
        Polynomial R = new Polynomial ( );

        for ( Integer power : powers )
            R.setCoefficient ( power + 1, P.getCoefficient ( power ) / ( power + 1 ) );

        if ( P.hasPower ( 0 ) )
            R.setCoefficient ( 1, P.getCoefficient ( 0 ) );

        return R;
    }
}
