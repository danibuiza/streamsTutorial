package com.danibuiza.javacodegeeks.streams;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author dgutierrez-diez This class reads all files from a given directory and show its content in
 *         a different way depending of its nature it uses streams and lambdas for this purpose
 */
public class MenuesAndLyrics
{
    public static String PATH2FILES = "./files";

    public static void main( String[] args )
    {

        try
        {
            Files.list( new File( PATH2FILES ).toPath() ).filter( x -> checkPrefix( x ) )
                    .forEach( path -> handleFile( path ) );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }

    }

    private static Boolean checkPrefix( Path path )
    {
        return path.toString().contains( "song" ) || path.toString().contains( "menu" );
    }

    private static Boolean checkEmpty( String str )
    {
        return str.isEmpty();
    }

    private static void handleFile( Path path )
    {
        try
        {
            boolean isMenu = false, isSong = false;
            // check if total price is present
            if( Files.lines( path ).filter( pathName -> !checkEmpty( pathName ) )
                    .anyMatch( line -> line.contains( "total price:" ) ) )
            {
                isMenu = true;
            }

            if( isMenu )
            {
                // check if the number of lines is between 2 and 10 for menues
                long countLines = Files.lines( path ).filter( pathName -> !checkEmpty( pathName ) ).count();

                isMenu = 2 <= countLines && countLines < 10;
            }
            if( !isMenu )
            {
                // check if the number of lines is larger than 10 for songs
                long countLines = Files.lines( path ).filter( pathName -> !checkEmpty( pathName ) ).count();

                isSong = countLines >= 10;

                if( isSong )
                {
                    // check if the first line is the title in quotation marks
                    String title = Files.lines( path ).filter( pathName -> !checkEmpty( pathName ) ).findFirst().get();
                    if( title.trim().charAt( 0 ) == '\"' && title.trim().charAt( title.length() - 1 ) == '\"' )
                    {
                        isSong = true;
                    }
                    if( isSong )
                    {

                        // get all the appeareances of the word "love"
                        System.out.println( "Love in "
                            + path
                            + "  :"
                            + Files.lines( path ).filter( pathName -> !checkEmpty( pathName ) )
                                    .mapToInt( line -> line.toLowerCase().split( "love" ).length - 1 ).sum() );

                        // get all the appeareances of the word "Tambourine"
                        System.out.println( "Tambourine in "
                            + path
                            + "  :"
                            + Files.lines( path ).filter( pathName -> !checkEmpty( pathName ) )
                                    .mapToInt( line -> line.toLowerCase().split( "tambourine" ).length - 1 ).sum() );

                    }
                }
            }
            else
            {
                Files.lines( path ).filter( pathName -> !checkEmpty( pathName ) )
                        .filter( line -> line.contains( "total price:" ) )
                        .forEach( x -> System.out.println( "total price of menu " + path + " : " + x ) );
            }

        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

}
