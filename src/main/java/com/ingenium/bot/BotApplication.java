package com.ingenium.bot;

import com.ingenium.bot.service.EchoBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sx.blah.discord.util.DiscordException;

@SpringBootApplication
public class BotApplication{

	public static void main(String[] args){
		EchoBot echoDiscordBot = new EchoBot();

		try{
			echoDiscordBot.login();
			System.out.println("Бот успешно подключен.");
			SpringApplication.run(BotApplication.class);
		}
		catch(DiscordException e){
			System.err.println("Ошибка при подключении бота к Discord: " + e.getMessage());
		}
	}
}