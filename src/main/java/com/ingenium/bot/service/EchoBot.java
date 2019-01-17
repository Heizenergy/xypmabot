package com.ingenium.bot.service;

import com.ingenium.bot.controller.PhraseController;
import com.ingenium.bot.controller.RaidController;
import com.ingenium.bot.entities.Phrase;
import com.ingenium.bot.entities.Raid;
import org.springframework.stereotype.Service;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class EchoBot implements IListener<MessageReceivedEvent> {

    public static final String ECHO_BOT_TOKEN = "NTM0ODg5ODQxMTQ2NTkzMjgz.DyAM0A.2JEfjA7teitQgtb0X0DT8OVxoT8";
    private IDiscordClient dscordClient;
    private boolean isConnected;

    public EchoBot() {
        this.isConnected = false;
    }

    private void regBot() {
        EventDispatcher dispatcher = dscordClient.getDispatcher();
        dispatcher.registerListener(this);
    }

    public void login() throws DiscordException {
        ClientBuilder cBuilder = new ClientBuilder();
        cBuilder.withToken(EchoBot.ECHO_BOT_TOKEN);
        dscordClient = cBuilder.login();
        regBot();
        this.isConnected = true;
    }

    public void handle(MessageReceivedEvent event) {
        IMessage message = event.getMessage();
        IChannel channel = message.getChannel();
        String inputMsgStr = message.getContent();

        switch (inputMsgStr){
            case "!фраза" :  {
                phraseGenerator(channel);
                break;
            }
            case "!хурма помоги" : {
                xypmaHelp(channel);
                break;
            }
            case "!расписание" :
            case "!рейд" : {
                raid(message,channel);
                break;
            }
        }
    }

    public void phraseGenerator(IChannel channel){
        try {
            List<String> temp = new ArrayList<>();
            for (Phrase ph : PhraseController.repository.findAll()) {
                temp.add(ph.getText());
            }
            new MessageBuilder(this.dscordClient).withChannel(channel).withContent(random(temp)).build();
        } catch (RateLimitException | DiscordException | MissingPermissionsException e) {
            System.err.println("Ошибка при отправке сообщения: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void raid(IMessage message, IChannel channel){
        try {
            List<Raid> temp = new ArrayList<>(RaidController.raidRepo.findAll());
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat formatNextRaid = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            formatNextRaid.setTimeZone(TimeZone.getTimeZone("GMT"));
            LocalDateTime ldt = LocalDateTime.now();
            String td = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(ldt);
            Date today = format.parse(td);
            StringBuilder raidScheduler = new StringBuilder("Следующих 7 рейдов:\n");
            int count = 0;
            for (Raid ra : temp) {
                Date myDate = ra.getTime();
                String rd = format.format(myDate);
                Date raid = format.parse(rd);
                if (today.compareTo(raid) < 0) {
                    count++;
                    if(message.toString().equals("!расписание") && count <= 7){
                        raidScheduler.append(ra.getName()).append(" ").append(ra.getDifficulty()).append(" ").append(formatNextRaid.format(myDate)).append(" мск\n");
                    }
                    if(message.toString().equals("!рейд")){
                        new MessageBuilder(this.dscordClient)
                                .withChannel(channel)
                                .withContent("Следующий рейд\n" +
                                        ra.getName() + "\n" +
                                        ra.getDifficulty() + "\n" +
                                        formatNextRaid.format(myDate) + " мск" + "\n" +
                                        "И отебитесь от меня пидары вонючие (с)")
                                .build();
                        break;
                    }
                }
            }
            if(raidScheduler.length() > 22){
                new MessageBuilder(this.dscordClient)
                        .withChannel(channel)
                        .withContent(raidScheduler.toString())
                        .build();
            }
        } catch (RateLimitException | DiscordException | MissingPermissionsException e) {
            System.err.println("Ошибка при отправке сообщения: " + e.getMessage());
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void xypmaHelp(IChannel channel){
        try {
            String answer = "Доступные команды:\n" +
                    "!фраза - крылатые фразы гильдии\n" +
                    "!рейд - информация о ближайшем рейде\n" +
                    "!расписание - расписание рейдов на неделю\n";
            new MessageBuilder(this.dscordClient).withChannel(channel).withContent(answer).build();
        } catch (RateLimitException | DiscordException | MissingPermissionsException e) {
            System.err.println("Ошибка при отправке сообщения: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String random(List<String> phrases) {
        int minimum = 0;
        int maximum = phrases.size() -1;
        int result = ((int) (Math.random()*(maximum - minimum))) + minimum;
        System.out.println(result);
        return phrases.get(result);
    }
}