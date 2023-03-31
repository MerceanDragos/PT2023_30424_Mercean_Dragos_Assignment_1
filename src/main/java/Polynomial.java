import java.text.DecimalFormat;
import java.util.*;

public class Polynomial {

    //Exception class to be thrown when a polynomial cannot be parsed
    static class NotAPolynomialException extends Exception {

    }

    //internal representation of a polynomial
    private final HashMap< Integer, Double > coefficients;

    //constructor
    Polynomial ( ) {
        coefficients = new HashMap<> ( );
    }

    //copy constructor
    Polynomial ( Polynomial P ) {
        coefficients = new HashMap<> ( );

        ArrayList< Integer > powers = P.getPowers ( );

        for ( Integer power : powers )
            coefficients.put ( power, P.getCoefficient ( power ) );
    }

    //method for checking if a polynomial is equal to 0
    public boolean isZeroPolynomial ( ) {
        return coefficients.keySet ( ).isEmpty ( );
    }

    //sets coefficient of certain power
    public void setCoefficient ( Integer power, Double coefficient ) {
        if ( coefficient != 0.0d && power >= 0 ) {
            coefficients.remove ( power );
            coefficients.put ( power, coefficient );
        }
    }

    public void removeCoefficient ( Integer power ) {
        coefficients.remove ( power );
    }

    //adds to already existing coefficient or sets a new one
    public void addCoefficient ( Integer power, Double coefficient ) {
        Double oldCoefficient = ( hasPower ( power ) ) ? getCoefficient ( power ) : 0;
        if ( oldCoefficient + coefficient != 0 )
            setCoefficient ( power, coefficient + oldCoefficient );
        else
            removeCoefficient ( power );
    }

    //gets coefficient of a certain power
    public Double getCoefficient ( Integer power ) {
        return coefficients.get ( power );
    }

    //checks if a polynomial has a non-zero coefficient for a certain power
    public boolean hasPower ( Integer power ) {
        return coefficients.containsKey ( power );
    }

    //gets max power of the polynomial
    public Integer getOrder ( ) {
        if ( coefficients.keySet ( ).isEmpty ( ) )
            return 0;
        else
            return Collections.max ( coefficients.keySet ( ) );
    }

    //gets min power of the polynomial
    public Integer getSmallestPower ( ) {
        return Collections.min ( coefficients.keySet ( ) );
    }

    //returns an ascending sorted array containing the powers of the polynomial without the zero coefficient ones
    public ArrayList< Integer > getPowers ( ) {
        ArrayList< Integer > list = new ArrayList<> ( coefficients.keySet ( ) );
        Collections.sort ( list );
        return list;
    }

    //method for printing a polynomial to System.out
    public void print ( ) {

        if ( coefficients.isEmpty ( ) ) {
            System.out.print ( "0 " );
            return;
        }

        ArrayList< Integer > sortedKeys = new ArrayList<> ( getPowers ( ) );
        sortedKeys.sort ( Collections.reverseOrder ( ) );


        for ( Integer i : sortedKeys ) {

            if ( coefficients.get ( i ) < 0 )
                System.out.print ( "- " );
            else {
                if ( !i.equals ( sortedKeys.get ( 0 ) ) )
                    System.out.print ( "+ " );
            }

            if ( Math.abs ( coefficients.get ( i ) ) != 1 || ( i == 0 && coefficients.get ( i ) != 0 ) ) {
                DecimalFormat df = new DecimalFormat ( "###############0.###" );
                System.out.print ( df.format ( Math.abs ( coefficients.get ( i ) ) ) );
            }

            if ( i != 0 )
                System.out.print ( "x" );

            if ( i > 1 )
                System.out.print ( "^" + i );

            System.out.print ( " " );
        }
    }

    //method for parsing a polynomial to a string
    public String toString ( ) {

        StringBuilder sb = new StringBuilder ( );
        if ( coefficients.isEmpty ( ) ) {
            sb.append ( "0" );
            return sb.toString ( );
        }

        ArrayList< Integer > sortedKeys = new ArrayList<> ( getPowers ( ) );
        sortedKeys.sort ( Collections.reverseOrder ( ) );


        for ( Integer i : sortedKeys ) {

            if ( coefficients.get ( i ) < 0 )
                sb.append ( "- " );
            else {
                if ( !i.equals ( sortedKeys.get ( 0 ) ) )
                    sb.append ( "+ " );
            }

            if ( Math.abs ( coefficients.get ( i ) ) != 1 || ( i == 0 && coefficients.get ( i ) != 0 ) ) {
                DecimalFormat df = new DecimalFormat ( "###############0.###" );
                sb.append ( df.format ( Math.abs ( coefficients.get ( i ) ) ) );
            }

            if ( i != 0 )
                sb.append ( "x" );

            if ( i > 1 )
                sb.append ( "^" ).append ( i );

            sb.append ( " " );
        }
        sb.deleteCharAt ( sb.length ( ) - 1 );

        return sb.toString ( );
    }

    //helper function that parses a string to a polynomial and adds it to the polynomial
    private void addMonomial ( String monomial ) {
        if ( monomial.equals ( "" ) )
            return;

        int power = 1;
        double coefficient = 1.0;

        if ( !monomial.contains ( "x" ) ) {
            power = 0;
            coefficient = Double.valueOf ( monomial );
            addCoefficient ( power, coefficient );
            return;
        }

        String[] parts = monomial.split ( "x" );

        if ( parts.length != 0 && parts[0].length ( ) > 0 ) {
            if ( parts[0].startsWith ( "-" ) ) {
                coefficient = -1.0;
                parts[0] = parts[0].replace ( "-", "" );
            }
            if ( parts[0].length ( ) > 0 )
                coefficient *= Double.parseDouble ( parts[0] );
        }

        if ( parts.length > 1 && parts[1].length ( ) > 0 )
            power = Integer.parseInt ( parts[1].substring ( 1 ) );

        addCoefficient ( power, coefficient );
    }

    //method for parsing a string to a polynomial
    public static Polynomial valueOf ( String polynomialString ) throws NotAPolynomialException {

        if ( !polynomialString.matches ( "^ *(\\+? *-? *([0-9](.[0-9]+)?)* *(\\*?x(\\^[0-9]+)?)?)+ *$" ) )
            throw new NotAPolynomialException ( );

        polynomialString = polynomialString.replace ( " ", "" );
        polynomialString = polynomialString.replace ( "-", "+-" );
        polynomialString = polynomialString.replace ( "*", "" );

        String[] monomials = polynomialString.split ( "\\+" );
        Polynomial polynomial = new Polynomial ( );

        for ( String str : monomials )
            polynomial.addMonomial ( str );

        return polynomial;
    }

    //method for reading a polynomial from System.in
    static public Polynomial read ( ) throws NotAPolynomialException {

        Scanner scanner = new Scanner ( System.in );

        String polynomialString = scanner.nextLine ( );

        return valueOf ( polynomialString );
    }
}