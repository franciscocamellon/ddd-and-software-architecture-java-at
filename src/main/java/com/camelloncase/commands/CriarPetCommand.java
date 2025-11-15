package com.camelloncase.commands;

public class CriarPetCommand extends BaseCommand<String> {
    public final String nome;
    public CriarPetCommand(String id, String nome) {
        super(id);
        this.nome = nome;
    }
}
