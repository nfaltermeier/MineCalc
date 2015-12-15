package Blackop778.MineCalc;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import java.lang.NumberFormatException;

public class Calculate extends CommandBase{

	@Override
	public String getCommandName() {
		//What must be typed in following the / to trigger the command
		return "Calculate";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		//What is shown when "/help Calculate" is typed in
		return "Calculates from the arguments and outputs answer";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] arguments) {
		//What happens when command is entered
		try
		{
			double num1 = Double.valueOf(arguments[0]);
			String opperator = arguments[1];
			double num2 = Double.valueOf(arguments[2]);
		}
		catch(NumberFormatException error)
		{
			if(icommandsender instanceof EntityPlayer) // If the sender was a player
			{
				EntityPlayer player = (EntityPlayer) icommandsender;
				player.addChatMessage(new ChatComponentText("Syntax Error"));
				
			}
		}
	}
	
	@Override
	public int getRequiredPermissionLevel()
    {
        return 0;
    }

}
