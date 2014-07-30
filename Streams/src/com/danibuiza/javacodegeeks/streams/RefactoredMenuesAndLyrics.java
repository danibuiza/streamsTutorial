package com.danibuiza.javacodegeeks.streams;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author dgutierrez-diez This class reads all files from a given directory and show its content in
 *         a different way depending of its nature it uses streams and lambdas for this purpose
 */
public class RefactoredMenuesAndLyrics
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

    private static void handleFile( Path path )
    {
        try

        {
            long count = Files.lines( path ).filter( pathName -> !checkEmpty( pathName ) ).count();
            if( 2 <= count && count < 10 )
            {
                Files.lines( path ).filter( pathName -> !checkEmpty( pathName ) )
                        .filter( line -> line.contains( "total price:" ) )
                        .forEach( x -> System.out.println( "total price of menu " + path + " : " + x ) );
            }
            else
            {
                String title = Files.lines( path ).filter( pathName -> !checkEmpty( pathName ) ).findFirst().get();
                if( title.trim().charAt( 0 ) == '\"' && title.trim().charAt( title.length() - 1 ) == '\"' )
                {
                    System.out.println( "Love in "
                        + path
                        + "  :"
                        + Files.lines( path ).filter( pathName -> !checkEmpty( pathName ) )
                                .mapToInt( line -> line.toLowerCase().split( "Tambourine" ).length - 1 ).sum() );
                }
            }

        }
        catch( IOException e )
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

}
