package br.com.cwi.crescer.api.domain.enums;

public enum Priority {

    URGENTE("URGENTE"),
    IMPORTANTE("IMPORTANTE"),
    NORMAL("NORMAL");

    private String name;

    Priority(String name) {
        this.name = name;
    }

    public String get() {
        return name;
    }


}
