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
		boolean zeroMult = false;
		boolean imaginaryError = false;
		if ((arguments.length - 1) % 2 == 0 && arguments.length > 1)
		{
			try
			{
				double n = Double.valueOf(arguments[0]);
				for (int i = 1; i < arguments.length; i++)
				{
					if (i + 2 == arguments.length && arguments[i].equals("%"))
					{
						i++;
						if (arguments[i].equals("0"))
						{
							divError = true;
						}
						print = String.valueOf((int) (n / Double.valueOf(arguments[i]))) + "R"
								+ String.valueOf((int) (n % Double.valueOf(arguments[i])));
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
							if (arguments[i].equals("0"))
							{
								zeroMult = true;
							}
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
								zeroPower = true;
							}
							n = Math.pow(n, Double.valueOf(arguments[i]));
						}
						else if (arguments[i].equals("/-"))
						{
							i++;
							if (n < 0 && Double.valueOf(arguments[i]) % 2 == 0)
							{
								imaginaryError = true;
							}
							else
							{
								if (arguments[i].equals("2"))
								{
									n = Math.sqrt(n);
								}
								else if (arguments[i].equals("3"))
								{
									n = Math.cbrt(n);
								}
								else
								{
									@SuppressWarnings("unused")
									boolean test = false;
									double b = 0; // Used to guess what the root
													// is
									double bb = 0; // Used to guess what the
													// root is
									double nn = n; // What the original number
													// was
									for (int x = 0; test = false; x++)
									{
										b = x;
										bb = x + 1;
										b = Math.pow(b, Double.valueOf(arguments[i]));
										bb = Math.pow(bb, Double.valueOf(arguments[i]));
										if (b <= n && bb >= n)
										{
											test = true;
											n = (x + x + 1) / 2; // Make n the
																	// guess
										}
									}
									for (int x = 0; x < MCConfig.rootTimes; x++)
									{
										n = ((Double.valueOf(arguments[i]) - 1) * n
												+ nn / Math.pow(n, Double.valueOf(arguments[i]) - 1))
												/ Double.valueOf(arguments[i]);
									}
								}
							}
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
								print = print + EnumChatFormatting.RED + " Warning: Anything to the power of 0 is 1";
							}
							else if (zeroMult)
							{
								print = print + EnumChatFormatting.RED + " Warning: Anything times 0 is 0";
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

		if (imaginaryError && !print.contains("Error"))
		{
			print = EnumChatFormatting.RED + "Error: Imaginary numbers are not supported";
		}
		else if (divError && !print.contains("Error"))
		{
			print = EnumChatFormatting.RED + "Error: Cannot divide by 0";
		}
		else if (symbolError && !print.contains("Error"))
		{
			print = EnumChatFormatting.RED + "Error: Valid symbols are '+, -, *, /, %, ^, /-'";
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
