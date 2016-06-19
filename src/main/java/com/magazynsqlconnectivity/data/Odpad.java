/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magazynsqlconnectivity.data;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import lombok.*;

/**
 *
 * @author xxbar
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Odpad implements Serializable {

    private Long ID;
    private Integer GRUPA;
    private Integer PODGRUPA;
    private Integer RODZAJ;
    private String TYP;
    
    private String OPIS;

    @Getter
    private static String[] propTym = {"GRUPA", "PODGRUPA", "RODZAJ", "TYP", "OPIS", "NR_ODPADU"};
    @Getter
    private static String[] colTym = {"GRUPA", "PODGRUPA", "RODZAJ", "TYP", "OPIS", "NR_ODPADU"};

//    public Slownik(String GRUPA, String PODGRUPA, String RODZAJ, String TYP, String OPIS, String NR_ODPADU) {
//        this.GRUPA = Integer.parseInt(GRUPA.replaceAll("[^\\d.]", ""));
//        this.PODGRUPA = Integer.parseInt(PODGRUPA.replaceAll("[^\\d.]", ""));
//        this.RODZAJ = Integer.parseInt(RODZAJ.replaceAll("[^\\d.]", ""));
//        this.TYP = TYP;
//        this.OPIS = OPIS;
//        this.NR_ODPADU = Integer.parseInt(NR_ODPADU.replaceAll("[^\\d.]", ""));
//
//    }
    public Odpad(String row) {
        Scanner scan = new Scanner(row).useDelimiter(";");
        GRUPA = scan.hasNextInt() ? scan.nextInt() : null;
        if (GRUPA == null) {
            scan.next();
        }
        PODGRUPA = scan.hasNextInt() ? scan.nextInt() : null;
        if (PODGRUPA == null) {
            scan.next();
        }
        RODZAJ = scan.hasNextInt() ? scan.nextInt() : null;
        if (RODZAJ == null) {
            scan.next();
        }

        TYP = scan.hasNext() ? scan.next() : null;
        if (TYP == null) {
            scan.next();
        }

        OPIS = scan.hasNext() ? scan.next() : null;
        if (OPIS == null) {
            scan.next();
        }
        ID = scan.hasNextLong() ? scan.nextLong() : null;

    }

    public static List<Odpad> Open(Path path) {

        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
            String firstLine = lines.remove(0);
            return lines.stream().map(row -> new Odpad(row)).collect(Collectors.toList());
        } catch (IOException ex) {
            System.out.println("Błąd odczytu pliku: \n" + ex);
            return null;
        }
    }

}
