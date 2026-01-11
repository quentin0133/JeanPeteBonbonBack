package fr.quentin.jeanpetebonbon.core.runner;

import fr.quentin.jeanpetebonbon.auth.user.User;
import fr.quentin.jeanpetebonbon.auth.user.UserRepository;
import fr.quentin.jeanpetebonbon.bot.guild.GuildRepository;
import fr.quentin.jeanpetebonbon.message.clash.MessageClashTroll;
import fr.quentin.jeanpetebonbon.message.clash.MessageClashTrollRepository;
import fr.quentin.jeanpetebonbon.message.reaction.MessageReactionTroll;
import fr.quentin.jeanpetebonbon.message.reaction.MessageReactionTrollRepository;
import fr.quentin.jeanpetebonbon.message.response.MessageResponseTroll;
import fr.quentin.jeanpetebonbon.message.response.MessageResponseTrollRepository;
import fr.quentin.jeanpetebonbon.message.trigger.MessageTriggerTroll;
import fr.quentin.jeanpetebonbon.message.trigger.MessageTriggerTrollRepository;
import fr.quentin.jeanpetebonbon.schedule.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final MessageTriggerTrollRepository messageTriggerTrollRepository;
    private final MessageResponseTrollRepository messageResponseTrollRepository;
    private final MessageReactionTrollRepository messageReactionTrollRepository;
    private final MessageClashTrollRepository messageClashTrollRepository;
    private final ScheduleService scheduleService;
    private final GuildRepository guildRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${app.default.email}")
    private String defaultUsername;

    @Value("${app.default.password}")
    private String defaultPassword;

    @Value("${app.default.firstname}")
    private String defaultFirstName;

    @Value("${app.default.lastname}")
    private String defaultLastName;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) return;

        userRepository.saveAndFlush(new User(0, defaultUsername, passwordEncoder.encode(defaultPassword)));

        List<MessageResponseTroll> quoi = messageResponseTrollRepository.saveAllAndFlush(
            List.of(
                new MessageResponseTroll("ffeur"),
                new MessageResponseTroll("ffeuse"),
                new MessageResponseTroll("ffeur"),
                new MessageResponseTroll("coubeh")
            )
        );

        List<MessageResponseTroll> qui = messageResponseTrollRepository.saveAllAndFlush(
            List.of(
                new MessageResponseTroll("kette bicyclette"),
                new MessageResponseTroll("ki"),
                new MessageResponseTroll("rikou"),
                new MessageResponseTroll("kou")
            )
        );

        List<MessageResponseTroll> comment = messageResponseTrollRepository.saveAllAndFlush(
            List.of(
                new MessageResponseTroll("dant"),
                new MessageResponseTroll("cement")
            )
        );

        List<MessageResponseTroll> allo = messageResponseTrollRepository.saveAllAndFlush(
            List.of(
                new MessageResponseTroll("à l'huile")
            )
        );

        List<MessageResponseTroll> aDemain = messageResponseTrollRepository.saveAllAndFlush(
            List.of(
                new MessageResponseTroll("à deux pieds")
            )
        );

        List<MessageResponseTroll> ah = messageResponseTrollRepository.saveAllAndFlush(
            List.of(
                new MessageResponseTroll("beille"),
                new MessageResponseTroll("ladin"),
                new MessageResponseTroll("méli"),
                new MessageResponseTroll("dam")
            )
        );

        List<MessageResponseTroll> mais = messageResponseTrollRepository.saveAllAndFlush(
            List.of(
                new MessageResponseTroll("son"),
                new MessageResponseTroll("tre"),
                new MessageResponseTroll("tresse"),
                new MessageResponseTroll("tisse"),
                new MessageResponseTroll("ssi le goat")
            )
        );

        List<MessageResponseTroll> roh = messageResponseTrollRepository.saveAllAndFlush(
            List.of(
                new MessageResponseTroll("naldo la fraude")
            )
        );

        List<MessageResponseTroll> oui = messageResponseTrollRepository.saveAllAndFlush(
            List.of(
                new MessageResponseTroll("stiti")
            )
        );

        List<MessageResponseTroll> non = messageResponseTrollRepository.saveAllAndFlush(
            List.of(
                new MessageResponseTroll("bril")
            )
        );

        List<MessageResponseTroll> dit = messageResponseTrollRepository.saveAllAndFlush(
            List.of(
                new MessageResponseTroll("recteur"),
                new MessageResponseTroll("rectrice"),
                new MessageResponseTroll("rection"),
                new MessageResponseTroll("vertissement"),
                new MessageResponseTroll("rective"),
                new MessageResponseTroll("van"),
                new MessageResponseTroll("vin")
            )
        );

        List<MessageResponseTroll> dire = messageResponseTrollRepository.saveAllAndFlush(
            List.of(
                new MessageResponseTroll("cteur"),
                new MessageResponseTroll("ctrice"),
                new MessageResponseTroll("ction"),
                new MessageResponseTroll("ctive"),
                new MessageResponseTroll("scours")
            )
        );

        List<MessageResponseTroll> go = messageResponseTrollRepository.saveAllAndFlush(
            List.of(
                new MessageResponseTroll("at")
            )
        );

        List<MessageResponseTroll> genre = messageResponseTrollRepository.saveAllAndFlush(
            List.of(
                new MessageResponseTroll("dan"),
                new MessageResponseTroll("gette"),
                new MessageResponseTroll("ge")
            )
        );

        List<MessageResponseTroll> dame = messageResponseTrollRepository.saveAllAndFlush(
            List.of(
                new MessageResponseTroll("so"),
                new MessageResponseTroll("ier")
            )
        );

        List<MessageResponseTroll> mec = messageResponseTrollRepository.saveAllAndFlush(
            List.of(
                new MessageResponseTroll("anique")
            )
        );

        List<MessageResponseTroll> bro = messageResponseTrollRepository.saveAllAndFlush(
            List.of(
                new MessageResponseTroll("chette"),
                new MessageResponseTroll("derie"),
                new MessageResponseTroll("sser"),
                new MessageResponseTroll("sse"),
                new MessageResponseTroll("ute")
            )
        );

        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("quoi", quoi));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("quoa", quoi));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("koi", quoi));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("pourquoi", quoi));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("pourquoa", quoi));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("pourkoi", quoi));

        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("qui", qui));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("ki", qui));

        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("comment", comment));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("commant", comment));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("coment", comment));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("comant", comment));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("commen", comment));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("comman", comment));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("comen", comment));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("coman", comment));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("komment", comment));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("kommant", comment));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("koment", comment));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("komant", comment));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("kommen", comment));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("komman", comment));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("komen", comment));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("koman", comment));

        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("allo", allo));

        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("a demain", aDemain));

        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("ah", ah));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("ha", ah));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("a", ah));

        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("mé", mais));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("mai", mais));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("mais", mais));

        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("roh", roh));

        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("oui", oui));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("ui", oui));

        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("non", non));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("nom", non));

        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("dit", dit));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("dis", dit));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("di", dit));

        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("dire", dire));

        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("go", go));

        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("genre", genre));

        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("dame", dame));

        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("mec", mec));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("mek", mec));

        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("bro", bro));

        messageClashTrollRepository.saveAndFlush(new MessageClashTroll("chouine"));
        messageClashTrollRepository.saveAndFlush(new MessageClashTroll("pleure"));
        messageClashTrollRepository.saveAndFlush(new MessageClashTroll("pa lu"));
        messageClashTrollRepository.saveAndFlush(new MessageClashTroll("ratio"));
        messageClashTrollRepository.saveAndFlush(new MessageClashTroll("ok bozo"));
        messageClashTrollRepository.saveAndFlush(new MessageClashTroll("gênant"));
        messageClashTrollRepository.saveAndFlush(new MessageClashTroll("trop long"));
        messageClashTrollRepository.saveAndFlush(new MessageClashTroll("abrège"));
        messageClashTrollRepository.saveAndFlush(new MessageClashTroll("supprime"));
        messageClashTrollRepository.saveAndFlush(new MessageClashTroll("me parle pas"));

        messageReactionTrollRepository.saveAndFlush(new MessageReactionTroll("\uD83E\uDD13", "*alors je suis obligé d'intervenir...*"));
        messageReactionTrollRepository.saveAndFlush(new MessageReactionTroll("\uD83D\uDE2B", "gênant"));
        messageReactionTrollRepository.saveAndFlush(new MessageReactionTroll("\uD83D\uDE43", "\"Peux mieux faire\""));
        messageReactionTrollRepository.saveAndFlush(new MessageReactionTroll("\uD83E\uDD76", "Frisson de gêne"));
        messageReactionTrollRepository.saveAndFlush(new MessageReactionTroll("\uD83D\uDE10", "mon honnête réaction face à ton message"));
        messageReactionTrollRepository.saveAndFlush(new MessageReactionTroll("👎", "finito"));
        messageReactionTrollRepository.saveAndFlush(new MessageReactionTroll("\uD83E\uDD28", "soit normal"));
        messageReactionTrollRepository.saveAndFlush(new MessageReactionTroll("\uD83E\uDD22", "ne dis plus jamais ça"));
        messageReactionTrollRepository.saveAndFlush(new MessageReactionTroll("🇱", "prend ton L, victime"));
        messageReactionTrollRepository.saveAndFlush(new MessageReactionTroll("\uD83D\uDE44", "qui a demandé ?"));

        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("paraître",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("disparaître")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("pute",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("dispute")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("séction",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("dissection")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("torsion",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("distorsion")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("traction",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("distraction")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("traction",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("distraction")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("continuer",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("discontinuer")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("continuité",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("discontinuite")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("convenable",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("disconvenable")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("cours",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("discours")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("créditer",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("discrediter")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("fonctionnement",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("disfonctionnement")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("harmonieux",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("disharmonieux")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("jonction",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("disjonction")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("muter",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("dismuter")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("proportionné",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("disproportionne")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("semblance",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("dissemblance")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("sembler",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("dissembler")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("signe",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("dissigne")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("similaire",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("dissimilaire")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("symétrie",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("dissymetrie")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("enterie",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("dysenterie")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("cerner",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("discerner")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("cerne",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("discerne")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("tinguer",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("distinguer")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("ciplinaire",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("disciplinaire")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("cipline",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("discipline")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("co",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("disco")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("cord",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("discord")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("corde",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("discorde")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("crimination",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("discrimination")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("criminatif",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("discriminatif")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("criminatoire",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("discriminatoire")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("crètement",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("discretement")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("cussion",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("discussion")))));
        messageTriggerTrollRepository.saveAndFlush(new MessageTriggerTroll("joncter",
            List.of(messageResponseTrollRepository.saveAndFlush(new MessageResponseTroll("disjoncter")))));
    }
}
