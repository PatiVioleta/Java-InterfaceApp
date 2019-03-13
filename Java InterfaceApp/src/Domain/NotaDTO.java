package Domain;

import java.util.List;

public class NotaDTO {
    private String nume;
    private String t1="-";
    private String t2="-";
    private String t3="-";
    private String t4="-";
    private String t5="-";
    private String t6="-";
    private String t7="-";
    private String t8="-";
    private String t9="-";
    private String t10="-";
    private String t11="-";
    private String t12="-";
    private String t13="-";
    private String t14="-";

    public NotaDTO(String nume, List<String> l) {
        this.nume = nume;
        this.t1 = l.get(0);
        this.t2 = l.get(1);
        this.t3 = l.get(2);
        this.t4 = l.get(3);
        this.t5 = l.get(4);
        this.t6 = l.get(5);
        this.t7 = l.get(6);
        this.t8 = l.get(7);
        this.t9 = l.get(8);
        this.t10 = l.get(9);
        this.t11 = l.get(10);
        this.t12 = l.get(11);
        this.t13 = l.get(12);
        this.t14 = l.get(13);
    }

    public String getNume() {
        return nume;
    }

    public String getT1() {
        return t1;
    }

    public String getT2() {
        return t2;
    }

    public String getT3() {
        return t3;
    }

    public String getT4() {
        return t4;
    }

    public String getT5() {
        return t5;
    }

    public String getT6() {
        return t6;
    }

    public String getT7() {
        return t7;
    }

    public String getT8() {
        return t8;
    }

    public String getT9() {
        return t9;
    }

    public String getT10() {
        return t10;
    }

    public String getT11() {
        return t11;
    }

    public String getT12() {
        return t12;
    }

    public String getT13() {
        return t13;
    }

    public String getT14() {
        return t14;
    }
}
