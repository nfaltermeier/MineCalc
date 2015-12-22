package Blackop778.MineCalc;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Calculate extends CommandBase
{

	@Override
	public String getCommandName()
	{
		// What must be typed in following the / to trigger the command
		return "Calculate";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		// What is shown when "/help Calculate" is typed in
		return "/Calculate <number> <symbol> <number> [symbol] [number]";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] arguments)
	{
		// What happens when command is entered
		String print = null;
		boolean divError = false;
		boolean symbolError = false;
		boolean zeroPower = false;
		boolean decimalPower = false;
		if ((arguments.length - 1) % 2 == 0 && arguments.length > 1)
		{
			try
			{
				double n = Double.valueOf(arguments[0]);
				for (int i = 1; i < arguments.length; i++)
				{
					if (i + 2 == arguments.length && arguments[i].equals("%"))
					{
						if (arguments[i + 1].equals("0"))
						{
							divError = true;
						}
						print = String.valueOf((int) (n / Double.valueOf(arguments[i + 1]))) + "R"
								+ String.valueOf((int) (n % Double.valueOf(arguments[i + 1])));
						i++;
					}
					else
					{
						if (arguments[i].equals("+"))
						{
							i++;
							n = n + Double.valueOf(arguments[i]);
						}
						else if (arguments[i].equals("-"))
						{
							i++;
							n = n - Double.valueOf(arguments[i]);
						}
						else if (arguments[i].equals("*"))
						{
							i++;
							n = n * Double.valueOf(arguments[i]);
						}
						else if (arguments[i].equals("/"))
						{
							if (arguments[i + 1].equals("0"))
							{
								divError = true;
							}
							i++;
							n = n / Double.valueOf(arguments[i]);
						}
						else if (arguments[i].equals("%"))
						{
							if (arguments[i + 1].equals("0"))
							{
								divError = true;
							}
							i++;
							n = n % Double.valueOf(arguments[i]);
						}
						else if (arguments[1].equals("^"))
						{
							i++;
							if (Double.valueOf(arguments[i]) == 0)
							{
								if (n > 0)
								{
									n = 1;
								}
								else
								{
									n = -1;
								}
								zeroPower = true;
							}
							else if (Double.valueOf(arguments[i]) % 1 != 0)
							{
								int x;
								for (x = 10; x * Double.valueOf(arguments[i]) % 1 != 0; x = x * 10)
								{

								}

							}
							else if (Double.valueOf(arguments[i]) > 0)
							{
								double b = n;
								for (int c = 1; c < Double.valueOf(arguments[i]); c++)
								{
									n = n * b;
								}
							}
							else
							{
								double b = n;
								n = 1 / b;
								for (int c = 2; c < Double.valueOf(arguments[i]); c++)
								{
									n = n / b;
								}
							}
						}
						else if (arguments[i].equals("/-"))
						{
							i++;
							boolean test = false;
							int b = 0;
							int bb = 0;
							for (int x = 0; test = false; x++)
							{
								b = x;
								bb = x + 1;
								for (int y = 1; y < Double.valueOf(arguments[i]); y++)
								{
									b = b * x;
									bb = bb * (x + 1);
								}
								if (b <= n && bb >= n)
								{
									test = true;
								}
							}
							n = (b + bb) / 2;
						}
						else
						{
							symbolError = true;
							i++;
						}

						if (i + 1 == arguments.length)
						{
							if (n % 1 == 0)
							{
								int b = (int) (n);
								print = String.valueOf(b);
							}
							else
							{
								print = String.valueOf(n);
							}
							if (zeroPower)
							{
								print = print + EnumChatFormatting.RED
										+ " Warning: Anything to the power of 0 is one (or " + EnumChatFormatting.RED
										+ "negative one)";
							}
						}
					}
				}
			}
			catch (NumberFormatException error)
			{
				print = EnumChatFormatting.RED + "Error: Could not be interpreted as a double:"
						+ error.getMessage().substring(17, error.getMessage().length());
			}
		}
		else
		{
			print = EnumChatFormatting.RED + "Usage: /Calculate <number> <symbol> <number> [symbol] "
					+ EnumChatFormatting.RED + "[number]";
		}

		if (divError == true && !print.contains("Error"))
		{
			print = EnumChatFormatting.RED + "Error: Cannot divide by 0";
		}
		else if (symbolError && !print.contains("Error"))
		{
			print = EnumChatFormatting.RED + "Error: Valid symbols are '+, -, *, /, %, ^'";
		}
		else if (decimalPower && !print.contains("Error"))
		{
			print = EnumChatFormatting.RED + "Error: Decmial powers are a lot of work. Maybe later. Sorry.";
		}

		if (MCConfig.returnInput && !print.contains("Error"))
		{
			String tempPrint;
			tempPrint = arguments[0];
			for (int i = 1; i < arguments.length; i++)
			{
				tempPrint = tempPrint + " " + arguments[i];
			}
			tempPrint = tempPrint + " = ";
			print = tempPrint + print;
		}

		if (icommandsender.getCommandSenderName().equals("Server"))
		{
			MineCalc.Logger.info(print);
		}
		else if (icommandsender.getCommandSenderName().equals("@"))
		{
			MineCalc.Logger.info("Command blocks cannot use /calculate");
		}
		else
		{
			EntityPlayer player = (EntityPlayer) icommandsender;
			player.addChatMessage(new ChatComponentText(print));
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
	{
		// User can always use this command
		return true;
	}

	@Override
	public List<String> getCommandAliases()
	{
		// A list of alternate command names
		List<String> Aliases = new ArrayList<String>();
		Aliases.add("calculate");
		Aliases.add("calc");
		Aliases.add("Calc");
		return Aliases;
	}
}
