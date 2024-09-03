package org.oberon.oss.chess.data.element;

import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@ToString
public class Nag implements Element<Integer> {
    private final Integer data;

    private static final Pattern SPLITTER = Pattern.compile("^\\s*\\$(\\d+)");

    public Nag(String text) {
        Matcher matcher = SPLITTER.matcher(text);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("text is not allowed for pattern " + SPLITTER.pattern());
        }

        int glyph = Integer.parseInt(matcher.group(1));
        if (!NAG_LOOKUP_TABLE.containsKey(glyph)) {
            throw new IllegalArgumentException("Unknown glyph code: $" + glyph);
        }

        data = glyph;
    }

    @Override
    public Integer getElementData() {
        return data;
    }

    private static final Map<Integer, String> NAG_LOOKUP_TABLE;

    static {
        String               nagProperties = "nag-lookup.properties";
        Map<Integer, String> wrk           = new HashMap<>();

        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(nagProperties)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            for (Object o : properties.keySet()) {
                String key = o.toString();
                wrk.put(Integer.parseInt(key), properties.getProperty(key));
                properties.getProperty(o.toString());
            }
        }
        catch (IOException e) {
            throw new IllegalStateException("Failure loading resource " + nagProperties, e);
        }

        NAG_LOOKUP_TABLE = Map.copyOf(wrk);
    }

    public static @Nullable String lookupNagCode(int glyphNumber) {
        return NAG_LOOKUP_TABLE.get(glyphNumber);
    }
}
