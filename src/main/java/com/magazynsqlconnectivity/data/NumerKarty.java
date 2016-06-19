/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magazynsqlconnectivity.data;

import java.io.Serializable;
import java.util.Scanner;
import lombok.*;

/**
 *
 * @author xxbar
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class NumerKarty implements Comparable<NumerKarty>, Serializable {

    private Integer NKNUMER;
    private Integer NKFIRMA;
    private Integer NKROK;

    public NumerKarty(String s) {
        Scanner scan = new Scanner(s).useDelimiter("/");
        NKNUMER = scan.hasNextInt() ? scan.nextInt() : null;
        if (NKNUMER == null) {
            scan.next();
        }
        NKFIRMA = scan.hasNextInt() ? scan.nextInt() : null;
        if (NKFIRMA == null) {
            scan.next();
        }
        NKROK = scan.hasNextInt() ? scan.nextInt() : null;
    }

    @Override
    public String toString() {
        return NKNUMER + "/" + NKFIRMA + "/" + NKROK;
    }

    @Override
    public int compareTo(NumerKarty t) {
        if (NKROK.compareTo(t.getNKROK()) != 0) {
            return NKROK.compareTo(t.getNKROK());
        }

        if (NKFIRMA.compareTo(t.getNKFIRMA()) != 0) {
            return NKFIRMA.compareTo(t.getNKFIRMA());
        }

        if (NKNUMER.compareTo(t.getNKNUMER()) != 0) {
            return NKNUMER.compareTo(t.getNKNUMER());
        }
        return 0;
    }

}
