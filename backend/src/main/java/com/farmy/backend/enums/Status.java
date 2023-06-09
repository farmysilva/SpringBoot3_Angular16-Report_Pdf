package com.farmy.backend.enums;

public enum Status {
    ATIVO("Ativo"), INATIVO("Inativo");
        
    private String value;

    Status(String value) {
            this.value = value;
    }

    public String getValue(){
            return value;
    }
    //tostring
    @Override
    public String toString() {
        return value;
    }
}
