package org.bohdan.mlh;

import java.util.Arrays;
import java.util.List;

public class Products {
    double mozz;
    double lava;
    double pepe;
    double szynka;
    double wol;
    double wiep;
    double cezar;
    double grill;
    double kebab;
    double boczek;
    double neap;
    double becham;
    double melts;
    double zupaMarch;
    double zupaBatat;
    double kuku;
    double ananas;
    double oliwki;
    double jalapeno;

    public Products(double mozz, double lava, double pepe, double szynka, double wol, double wiep, double cezar,
                    double grill, double kebab, double boczek, double neap, double melts, double becham,
                    double zupaMarch, double zupaBatat, double kuku, double ananas, double oliwki, double jalapeno) {
        this.mozz = mozz;
        this.lava = lava;
        this.pepe = pepe;
        this.szynka = szynka;
        this.wol = wol;
        this.wiep = wiep;
        this.cezar = cezar;
        this.grill = grill;
        this.kebab = kebab;
        this.boczek = boczek;
        this.neap = neap;
        this.melts = melts;
        this.becham = becham;
        this.zupaMarch = zupaMarch;
        this.zupaBatat = zupaBatat;
        this.kuku = kuku;
        this.ananas = ananas;
        this.oliwki = oliwki;
        this.jalapeno = jalapeno;
    }

    public Products(){
        this.mozz = 0;
        this.lava = 0;
        this.pepe = 0;
        this.szynka = 0;
        this.wol = 0;
        this.wiep = 0;
        this.cezar = 0;
        this.grill = 0;
        this.kebab = 0;
        this.boczek = 0;
        this.neap = 0;
        this.melts = 0;
        this.becham = 0;
        this.zupaMarch = 0;
        this.zupaBatat = 0;
        this.kuku = 0;
        this.ananas = 0;
        this.oliwki = 0;
        this.jalapeno = 0;
    }

    public static Products subtract(Products p1, Products p2) {
        return new Products(
                p1.mozz - p2.mozz,
                p1.lava - p2.lava,
                p1.pepe - p2.pepe,
                p1.szynka - p2.szynka,
                p1.wol - p2.wol,
                p1.wiep - p2.wiep,
                p1.cezar - p2.cezar,
                p1.grill - p2.grill,
                p1.kebab - p2.kebab,
                p1.boczek - p2.boczek,
                p1.neap - p2.neap,
                p1.melts - p2.melts,
                p1.becham - p2.becham,
                p1.zupaMarch - p2.zupaMarch,
                p1.zupaBatat - p2.zupaBatat,
                p1.kuku - p2.kuku,
                p1.ananas - p2.ananas,
                p1.oliwki - p2.oliwki,
                p1.jalapeno - p2.jalapeno
        );
    }

    //Convert List<Double> array to class Products
    public static Products fromList(List<Double> values) {
        if (values.size() != 19) {
            throw new IllegalArgumentException("The list must contain exactly 19 elements.");
        }
        return new Products(
                values.get(0),
                values.get(1),
                values.get(2),
                values.get(3),
                values.get(4),
                values.get(5),
                values.get(6),
                values.get(7),
                values.get(8),
                values.get(9),
                values.get(10),
                values.get(11),
                values.get(12),
                values.get(13),
                values.get(14),
                values.get(15),
                values.get(16),
                values.get(17),
                values.get(18)
        );
    }

    //Convert back to list
    public List<Double> toList() {
        return Arrays.asList(
                this.mozz,
                this.lava,
                this.pepe,
                this.szynka,
                this.wol,
                this.wiep,
                this.cezar,
                this.grill,
                this.kebab,
                this.boczek,
                this.neap,
                this.melts,
                this.becham,
                this.zupaMarch,
                this.zupaBatat,
                this.kuku,
                this.ananas,
                this.oliwki,
                this.jalapeno
        );
    }

    public void replaceNegativeValues() {
        if (this.mozz < 0) this.mozz = 0;
        if (this.lava < 0) this.lava = 0;
        if (this.pepe < 0) this.pepe = 0;
        if (this.szynka < 0) this.szynka = 0;
        if (this.wol < 0) this.wol = 0;
        if (this.wiep < 0) this.wiep = 0;
        if (this.cezar < 0) this.cezar = 0;
        if (this.grill < 0) this.grill = 0;
        if (this.kebab < 0) this.kebab = 0;
        if (this.boczek < 0) this.boczek = 0;
        if (this.neap < 0) this.neap = 0;
        if (this.melts < 0) this.melts = 0;
        if (this.becham < 0) this.becham = 0;
        if (this.zupaMarch < 0) this.zupaMarch = 0;
        if (this.zupaBatat < 0) this.zupaBatat = 0;
        if (this.kuku < 0) this.kuku = 0;
        if (this.ananas < 0) this.ananas = 0;
        if (this.oliwki < 0) this.oliwki = 0;
        if (this.jalapeno < 0) this.jalapeno = 0;
    }
}
