package br.com.cwi.crescer.api.domain.enums;

public enum Visibility {

    PUBLICO("PUBLICO"),
    PRIVADO("PRIVADO"),
    AMIGOS("AMIGOS");

    private String name;

    Visibility(String name) {
        this.name = name;
    }

    public String get() {
        return name;
    }
}
