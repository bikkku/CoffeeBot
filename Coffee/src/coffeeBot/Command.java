package coffeeBot;

import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * This class is used by the main class for a bunch of different commands that can be utilised
 * by discord users through sending messages with the designated prefix to access the bot.
 * @author Jack Chu
 *
 */

public class Command extends ListenerAdapter {

	public void onMessageReceived(MessageReceivedEvent event) {
		
		String[] helloResponses = {"Yo wassup", 
				"Hello there!", 
				"Hey, how you doin [member]", 
				"Hey",
				"Ok but like when?"
		};
		
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		String[] pollArgs = event.getMessage().getContentRaw().split(" :");
		String[] spamArgs = event.getMessage().getContentRaw().split("'");
		int colour = 0x5a8d3e;

		/**
		 * This command is used to display what the bot can do using an embed.
		 * Should be updated with every new addition of functions.
		 */
		if (args[0].equalsIgnoreCase(Coffee.prefix + "help")) {
			EmbedBuilder help = new EmbedBuilder();
			help.setTitle("I am Coffee Bot");
			help.setDescription("Here are my commands: ");
			help.addField("`" + Coffee.prefix + "hello" + "`", "I will personally greet you :)", false);
			help.addField("`" + Coffee.prefix + "2poll" + "`", "create a two choice poll \n *Format: ~2poll :[title] :[choice1] :[choice2]*", false);
			help.addField("`" + Coffee.prefix + "spam" + "`", "sends a message a specified amount of times (20 max, works with pings)\n *Format: ~spam '[message]' [number of times]*", false);
			help.addField("`" + Coffee.prefix + "opgg" + "`", "gives op.gg link of specified IGN, currently only works for OCE accounts \n *Format: ~opgg [ign]*", false);
			help.addField("`" + Coffee.prefix + "mock" + "`", "turns an input message from 'hello' to 'HeLlO'", false);
			help.setFooter("imagine needing help", "https://cdn.frankerfacez.com/emoticon/249216/4");
			help.setAuthor("For the homies by ya boi bikku");
			help.setColor(colour);
			event.getChannel().sendMessage(help.build()).queue();
		}

		/**
		 * Responds to 'hello' with a random message out of an array of responses.
		 */
		if (args[0].equalsIgnoreCase(Coffee.prefix + "hello")) {
			Random rand = new Random();
			int num = rand.nextInt(helloResponses.length);
			event.getChannel().sendMessage(helloResponses[num].replace("[member]", event.getMember().getAsMention())).queue();
		}


		/**
		 * Creates a two choice poll, where the first argument is the command,
		 * each argument is separated by a ":" using the pollArgs array.
		 * 2nd argument is the title or statement for the poll
		 * 3rd and 4th argument are the two options.
		 * Arguments trimmed to keep integrity.
		 */
		if (pollArgs[0].equalsIgnoreCase(Coffee.prefix + "2poll")) {
			EmbedBuilder poll = new EmbedBuilder();
			poll.setTitle(pollArgs[1]);
			poll.addField(":a:", pollArgs[2].trim(), true);
			poll.addField(":b:", pollArgs[3].trim(), true);
			poll.setColor(colour);
			event.getChannel().sendMessage(poll.build()).queue(message -> {
				message.addReaction("🅰️").queue();
				message.addReaction("🅱️").queue();
			});
		}

		/**
		 * Sends a message a specified amount of times, capped at 20. Should work with pings and whatever emojis are available to the bot.
		 * Using the spamArgs array, the "'" character is the separator instead of a space, 
		 * so that sentences can be sent.
		 * The arguments are also trimmed to keep integrity, as well as making the number readable by the compiler.
		 */
		if (args[0].equalsIgnoreCase(Coffee.prefix + "spam")) {
			int i = 0;
			int value;

			try {
				value = Integer.parseInt(spamArgs[2].trim());
			} catch (NumberFormatException e) {
				value = 0;
			}

			if (value <= 20) {
				while (i < value) {
					event.getChannel().sendMessage(spamArgs[1].trim()).queue();
					i++;
				}
			} else {
				event.getChannel().sendMessage("20 max you dingus").queue();
			}
		}

		/**
		 * Gives op.gg link for specifed IGN, only works for OCE accounts.
		 */
		if (args[0].equalsIgnoreCase(Coffee.prefix + "opgg")) {
			String link = "https://oce.op.gg/summoner/userName=" + args[1];
			if (args.length > 2) {
				for (int i = 2; i < args.length; i++) {
					link = link.concat("+" + args[i]);
				}
			}
			event.getChannel().sendMessage(link).queue();
		}

		/**
		 * This command capitalises every second character from a given phrase,
		 * e.g. 'Hello nice to meet you' turns into 'HeLlO NiCe tO MeEt yOu'.
		 */
		if (args[0].equalsIgnoreCase(Coffee.prefix + "mock")) {
			String phrase = "";
			for (int i = 1; i < args.length; i++) {
				phrase = phrase.concat(args[i].toLowerCase() + " ");
			}
			char[] chars = phrase.toCharArray();
			for (int j = 0; j < chars.length; j+=2) {
				chars[j] = Character.toUpperCase(chars[j]);
			}
			event.getChannel().sendMessage(new String (chars)).queue();

			//			The code below was a first attempt at this command, did not work,
			//			keeping it here because it was a different concept, might come back later.
			//			
			//			String output = "";
			//			String[] words = new String[args.length - 1];
			//			boolean previousLetterCapitalised = false;
			//			
			//			for (int i = 1; i < args.length; i++) {
			//				args[i].toLowerCase();
			//				String[] letterArray = args[i].split("(?!^)");
			//				for (int j = 0; j < letterArray.length; j++) {
			//					if (previousLetterCapitalised == false) {
			//						if (j % 2 != 0) {
			//							letterArray[j].toUpperCase();
			//							words[i - 1] = words[i - 1].concat(letterArray[j]);
			//							if (j == letterArray.length) {
			//								previousLetterCapitalised = true;
			//							}
			//						}
			//					} else {
			//						if (j % 2 == 0) {
			//							letterArray[j].toUpperCase();
			//							if (j == letterArray.length) {
			//								previousLetterCapitalised = true;
			//							}
			//						}
			//					}
			//				}
			//			}
			//			for (int k = 0; k < words.length; k++) {
			//				output = output.concat(words[k] + " ");
			//			}
			//			event.getChannel().sendMessage(output).queue();
		}
	}
}
