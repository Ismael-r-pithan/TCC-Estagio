package br.com.cwi.crescer.api.domain.enums;

public enum Category {

    LIMPEZA("LIMPEZA"),
    HIGIENE("HIGIENE"),
    ESTUDO("ESTUDO"),
    LAZER("LAZER"),
    SAUDE("SAUDE"),
    TRABALHO("TRABALHO");

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String get() {
        return name;
    }

}
