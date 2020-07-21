package coffeeBot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Coffee {

	public static JDA jda;
	public static String prefix = "~";

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws LoginException {//Main method
		jda = new JDABuilder(AccountType.BOT).setToken("Your-Token-Here").build();
		
		//Online status
		jda.getPresence().setStatus(OnlineStatus.ONLINE);
		
		//Activity status
		jda.getPresence().setActivity(Activity.watching("you scream for ~help"));
		
		jda.addEventListener(new Command());
	}
}
