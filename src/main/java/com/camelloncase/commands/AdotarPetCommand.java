package com.camelloncase.commands;

public class AdotarPetCommand extends BaseCommand<String> {
    public final String ownerId;
    public AdotarPetCommand(String id, String ownerId) {
        super(id);
        this.ownerId = ownerId;
    }
}
