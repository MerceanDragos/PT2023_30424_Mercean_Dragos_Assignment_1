abstract public class Utility {

    public static String toFraction ( String real ) {
        if ( real.contains ( "(" ) ) {

            StringBuilder denominatorSB = new StringBuilder ( );

            denominatorSB.append ( "9".repeat ( real.substring ( real.indexOf ( "(" ) + 1, real.indexOf ( ")" ) ).length ( ) ) );
            if ( real.indexOf ( "." ) + 1 < real.indexOf ( "(" ) )
                denominatorSB.append ( "0".repeat ( real.substring ( real.indexOf ( "." ) + 1, real.indexOf ( "(" ) ).length ( ) ) );

            String denominatorString = denominatorSB.toString ( );

            String numeratorString = real.replace ( ".", "" );
            String subtrahendString = numeratorString.substring ( 0, numeratorString.indexOf ( "(" ) );
            numeratorString = numeratorString.replace ( "(", "" );
            numeratorString = numeratorString.replace ( ")", "" );

            Long numerator = Long.parseLong ( numeratorString ) - Long.parseLong ( subtrahendString );
            Long denominator = Long.parseLong ( denominatorString );

            Long gcd = Math.min ( numerator, denominator );

            while ( gcd != 1 ) {
                if ( numerator % gcd == 0 && denominator % gcd == 0 ) {
                    numerator /= gcd;
                    denominator /= gcd;
                    break;
                }
                gcd--;
            }

            if ( denominator == 1 )
                return Long.toString ( numerator );
            else
                return numerator + "/" + denominator;
        }
        else {

            Long denominator = ( long ) Math.pow ( 10, real.substring ( real.indexOf ( "." ) + 1 ).length ( ) );
            Long numerator = Long.parseLong ( real.replace ( ".", "" ) );

            Long gcd = Math.min ( numerator, denominator );

            while ( gcd != 1 ) {
                if ( numerator % gcd == 0 && denominator % gcd == 0 ) {
                    numerator /= gcd;
                    denominator /= gcd;
                    break;
                }
                gcd--;
            }

            if ( denominator == 1 )
                return Long.toString ( numerator );
            else
                return numerator + "/" + denominator;
        }
    }

    public static String toFraction ( Double Real ) {

        double real = ( Real > 0 ) ? Real : -Real;
        StringBuilder fraction = new StringBuilder ( );

        if ( Real < 0 )
            fraction.append ( "-" );

        if(real)

    }
}
