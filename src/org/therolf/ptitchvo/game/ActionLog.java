package org.therolf.ptitchvo.game;

import org.therolf.ptitchvo.GameConstants;

import java.awt.*;

public class ActionLog {
    private Color c = GameConstants.TEXT_COLOR;
    private String prefix = "system";
    private final String content;

    @SuppressWarnings("unused")
    public ActionLog(String content) {
        this.content = content;
    }

    public ActionLog(Color c, String prefix, String content) {
        this.c = c;
        this.prefix = prefix;
        this.content = content;
    }

    @Override
    public String toString() {
        return "<div><span style='color: #" + Integer.toHexString(c.getRGB() & 0xFFFFFF) + ";'>" + prefix + " </span> " + content + "</div>";
    }
}
