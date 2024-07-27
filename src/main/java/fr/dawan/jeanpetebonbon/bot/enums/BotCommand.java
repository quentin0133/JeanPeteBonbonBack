package fr.dawan.jeanpetebonbon.bot.enums;

import lombok.Getter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;


@Getter
public enum BotCommand {
    ASSIGN("Assigne le canal de texte au bot discord, il mettra les notifications dans ce canal"),
    SET_HOUR("Active un évènement qui se répètera", List.of(
        new OptionData(OptionType.STRING, "message","Message émit lorsqu'il atteindra la date de son évènement", true),
        new OptionData(OptionType.STRING, "time","temps au format HH:mm:ss", true)
    )),
    SET_DATE_HOUR("Active un évènement qui se répètera", List.of(
        new OptionData(OptionType.STRING, "message","Message émit lorsqu'il atteindra la date de son évènement", true),
        new OptionData(OptionType.STRING, "date","date au format dd-MM-yyyy", true),
        new OptionData(OptionType.STRING, "time","temps au format HH:mm:ss", true)
    )),
    SET_TROLL("Assigne le trolling du bot", List.of(new OptionData(OptionType.BOOLEAN, "is_trolling", "Active ou désactive le trolling du bot")));

    private final String description;
    private final List<OptionData> optionsData;

    BotCommand(String description) {
        this.description = description;
        optionsData = new ArrayList<>();
    }

    BotCommand(String description, List<OptionData> optionsData) {
        this.description = description;
        this.optionsData = optionsData;
    }

    public String getActionName() {return name().toLowerCase();}
}
