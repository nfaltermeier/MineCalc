package Blackop778.MineCalc;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Calculate extends CommandBase{

	@Override
	public String getCommandName() {
		//What must be typed in following the / to trigger the command
		return "Calculate";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		//What is shown when "/help Calculate" is typed in
		return "/Calculate <number> <symbol> <number> [symbol] [number]";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] arguments) {
		//What happens when command is entered
		String print = null;
		if((arguments.length - 1) % 2 == 0 && arguments.length > 1)
		{
			double n = Double.valueOf(arguments[0]);
			for(int i = 1; i < arguments.length;i++)
			{
				if(i + 2 == arguments.length && arguments[i].equals("%"))
				{
					print = String.valueOf((int) (n / Double.valueOf(arguments[i + 1]))) + "R" + String.valueOf((int) (n % Double.valueOf(arguments[i + 1])));
					i++;
				}
				else
				{
					if(arguments[i].equals("+"))
					{
						i++;
						n = n + Double.valueOf(arguments[i]);
					}
					else if(arguments[i].equals("-"))
					{
						i++;
						n = n - Double.valueOf(arguments[i]);
					}
					else if(arguments[i].equals("*"))
					{
						i++;
						n = n * Double.valueOf(arguments[i]);
					}
					else if(arguments[i].equals("/"))
					{
						i++;
						n = n / Double.valueOf(arguments[i]);
					}
					else if(arguments[i].equals("%"))
					{
						i++;
						n = n % Double.valueOf(arguments[i]);
					}
					
					if(i + 1 == arguments.length)
					{
						if(n % 1 == 0)
						{
							int b = (int) (n);
							print = String.valueOf(b);
						}
						else
						{
							print = String.valueOf(n);
						}
					}
				}
			}
		}
		else
		{
			print = EnumChatFormatting.RED+"Usage: /Calculate <number> <symbol> <number> [symbol] [number]";
		}
		if(icommandsender instanceof EntityPlayer) // If the sender was a player
		{
			EntityPlayer player = (EntityPlayer) icommandsender;
			player.addChatMessage(new ChatComponentText(print));
		}
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
    {
        return true;
    }

}
