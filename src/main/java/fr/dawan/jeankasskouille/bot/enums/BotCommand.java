package fr.dawan.jeankasskouille.bot.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

@RequiredArgsConstructor
@Getter
public enum BotCommand {
    ASSIGN("Assigne le canal de texte au bot discord, il mettra les notifications dans ce canal");

    private final String description;
    private final OptionData[] optionsData;

    BotCommand(String description) {
        this.description = description;
        optionsData = new OptionData[0];
    }

    public String getActionName() {return name().toLowerCase();}
}
