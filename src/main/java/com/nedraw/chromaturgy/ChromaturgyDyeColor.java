package com.nedraw.chromaturgy;

public record ChromaturgyDyeColor(
        String id,
        int hex,
        String translationKey,
        boolean customTexture
) {
    public static ChromaturgyDyeColor of(String id, String hexString,
                                         String translationKey, boolean customTexture) {
        int parsed = Integer.parseInt(hexString.replace("#", ""), 16);
        return new ChromaturgyDyeColor(id, parsed, translationKey, customTexture);
    }

    public int red()   { return (hex >> 16) & 0xFF; }
    public int green() { return (hex >> 8) & 0xFF; }
    public int blue()  { return hex & 0xFF; }
}