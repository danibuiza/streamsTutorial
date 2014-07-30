package com.danibuiza.javacodegeeks.streams;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FirstExample
{

    public static void main( String[] args )
    {
        try
        {
            // you can create an Stream directly 
            Stream.of(1,2,"asdfas",4,5,"adsfasa",7,8,9,10).forEach( System.out::println );
            
            // you can use arrays as streams sources
            int[] numbers = { 1, 2, 3, 4 };
            IntStream numbersFromArray = Arrays.stream( numbers );
            numbersFromArray.forEach( System.out::println );

            // you can use a collection as well as Streams sources
            List<String> collectionStr = new ArrayList<>();
            collectionStr.add( "uno" );
            collectionStr.add( "dos" );
            collectionStr.add( "tres" );
            collectionStr.add( "cuatro" );
            collectionStr.add( "cinco" );
            collectionStr.add( "seis" );
            collectionStr.add( "siete" );
            collectionStr.add( "ocho" );
            Stream<String> numbersFromCollection = collectionStr.stream();

            // you can use a directory entries as source (in combination with the nio API)
            Files.list( new File( "." ).toPath() ).forEach( System.out::println );

            // you can use streams for filtering in combination with lambdas
            //numbersFromCollection.filter( ( s ) -> s.startsWith( "s" ) ).forEach( System.out::println ); ->  java.lang.IllegalStateException: stream has already been operated upon or closed
            collectionStr.stream().filter( ( s ) -> s.startsWith( "s" ) ).forEach( System.out::println );
            
            // for sorting
            collectionStr.stream().sorted().forEach( System.out::println );

            // mapping -> convert to upper case
            collectionStr.stream().map( String::toUpperCase ).forEach( System.out::println );

            // for matching purposes
            collectionStr.stream().anyMatch( ( s ) -> s.startsWith( "s" ) );
            collectionStr.stream().noneMatch( ( s ) -> s.startsWith( "z" ) );

            // for counting and retrieving statistics
            collectionStr.stream().filter( ( s ) -> s.startsWith( "s" ) ).count();

            // for reducing the original pipeline
            Optional<String> reduced = collectionStr.stream().sorted().reduce( ( s1, s2 ) -> s1 + "#" + s2 );
            reduced.ifPresent( System.out::println );

        }
        catch( IOException ex )
        {
            ex.printStackTrace();

        }
    }

}
