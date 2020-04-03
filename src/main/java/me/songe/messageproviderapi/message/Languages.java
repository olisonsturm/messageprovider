package me.songe.messageproviderapi.message;

import org.bukkit.material.Skull;

import java.util.UUID;

public enum Languages {

    GERMAN("de_DE", "Deutsch", "Germany", "NWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ=="),
    ENGLISH("en_GB", "English", "United Kingdom", "YTllZGNkZDdiMDYxNzNkN2QyMjFjNzI3NGM4NmNiYTM1NzMwMTcwNzg4YmI2YTFkYjA5Y2M2ODEwNDM1YjkyYyJ9fX0=");

    private String language_code;
    private String name;
    private String englishName;
    private String idPrefix = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";
    private String textureId;

    Languages(String language_code, String name, String englishName, String textureId) {
        this.name = name;
        this.englishName = englishName;
        this.language_code = language_code;
        this.textureId = textureId;
    }

    public String getName() {
        return name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getTextureId() {
        return idPrefix + textureId;
    }

    public String getLanguageCode() {
        return language_code;
    }

}
