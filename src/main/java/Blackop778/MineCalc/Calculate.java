package Blackop778.MineCalc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Calculate extends CommandBase
{

	static HashMap<String, Double> lastMap = new HashMap<String, Double>();
	private boolean lastError;

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
		if((arguments.length - 1) % 2 == 0 && arguments.length > 1)
		{
			try
			{
				// The loop looks at the symbol so we have to progress to a
				// symbol
				double n = getDouble(icommandsender, arguments, 0);
				// Process all the inputs, check for errors, and print back to
				// user
				for(int i = 1; i < arguments.length; i++)
				{

					if(arguments[i].equals("+"))
					{
						i++;
						n = n + getDouble(icommandsender, arguments, i);
					}
					else if(arguments[i].equals("-"))
					{
						i++;
						n = n - getDouble(icommandsender, arguments, i);
					}
					else if(arguments[i].equals("*"))
					{
						i++;
						n = n * getDouble(icommandsender, arguments, i);
						if(getDouble(icommandsender, arguments, i) == 0 && MCConfig.zeroMultWarns)
						{
							zeroMult = true;
						}
					}
					else if(arguments[i].equals("/"))
					{
						i++;
						if(getDouble(icommandsender, arguments, i) == 0)
						{
							divError = true;
						}
						else
						{
							n = n / getDouble(icommandsender, arguments, i);
						}
					}
					else if(arguments[i].equals("%"))
					{
						i++;
						if(i + 1 == arguments.length && MCConfig.fancyRemainders)
						{
							// Skip the math to be done later for fancy
							// remainder output
						}
						else
						{
							if(getDouble(icommandsender, arguments, i) == 0)
							{
								divError = true;
							}
							else
							{
								n = n % getDouble(icommandsender, arguments, i);
							}
						}
					}
					else if(arguments[1].equals("^"))
					{
						i++;
						if(getDouble(icommandsender, arguments, i) == 0 && MCConfig.zeroMultWarns)
						{
							zeroPower = true;
						}
						n = Math.pow(n, getDouble(icommandsender, arguments, i));
					}
					else if(arguments[i].equals("/-"))
					{
						i++;
						if(n < 0 && getDouble(icommandsender, arguments, i) % 2 == 0)
						{
							imaginaryError = true;
						}
						else
						{
							if(arguments[i].equals("2"))
							{
								n = Math.sqrt(n);
							}
							else if(arguments[i].equals("3"))
							{
								n = Math.cbrt(n);
							}
							else
							{
								@SuppressWarnings("unused")
								boolean test = false;
								double b = 0; // Used to guess what the root is
								double bb = 0; // Used to guess what the root is
								double nn = n; // What the original number was
								for(int x = 0; test = false; x++)
								{ // Find what set of nth powers 'n' falls
									// between, and set n to the average of them
									// pre-powering
									b = x;
									bb = x + 1;
									b = Math.pow(b, getDouble(icommandsender, arguments, i));
									bb = Math.pow(bb, getDouble(icommandsender, arguments, i));
									if(b <= n && bb >= n)
									{
										test = true;
										n = (x + x + 1) / 2;
									}
								}
								for(int x = 0; x < MCConfig.rootTimes; x++)
								{ // Each time this is run it gets more
									// accurate
									n = ((getDouble(icommandsender, arguments, i) - 1) * n
											+ nn / Math.pow(n, getDouble(icommandsender, arguments, i) - 1))
											/ getDouble(icommandsender, arguments, i);
								}
							}
						}
					}
					else
					{
						symbolError = true;
						i++;
					}

					if(i + 1 == arguments.length)
					{
						if(arguments[i - 1].equals("%") && MCConfig.fancyRemainders)
						{ // Fancy remainder output
							if(getDouble(icommandsender, arguments, i) == 0)
							{
								divError = true;
							}
							else
							{
								lastMap.put(icommandsender.getCommandSenderName(),
										n % getDouble(icommandsender, arguments, i));
								print = String.valueOf((int) (n / getDouble(icommandsender, arguments, i))) + "R"
										+ String.valueOf((int) (n % getDouble(icommandsender, arguments, i)));
							}
						}
						else if(n % 1 == 0)
						{ // Remove unnecessary doubles
							lastMap.put(icommandsender.getCommandSenderName(), n);
							int b = (int) (n);
							print = String.valueOf(b);
						}
						else
						{
							lastMap.put(icommandsender.getCommandSenderName(), n);
							print = String.valueOf(n);
						}

						// Append warnings if needed
						if(zeroPower)
						{
							print = print + EnumChatFormatting.RED + " Warning: Anything to the power of 0 is 1";
						}
						else if(zeroMult)
						{
							print = print + EnumChatFormatting.RED + " Warning: Anything times 0 is 0";
						}
					}
				}
			}
			catch(NumberFormatException error)
			{
				print = EnumChatFormatting.RED + "Error: Could not be interpreted as a double:"
						+ error.getMessage().substring(17, error.getMessage().length());
			}
		}
		else
		{ // If the number of arguments is wrong
			print = EnumChatFormatting.RED + "Usage: /Calculate <number> <symbol> <number> [symbol] "
					+ EnumChatFormatting.RED + "[number]";
		}

		// Change print to an error message if an error was thrown
		if(imaginaryError && !print.contains("Error"))
		{
			print = EnumChatFormatting.RED + "Error: Imaginary numbers are not supported";
		}
		else if(divError && !print.contains("Error"))
		{
			print = EnumChatFormatting.RED + "Error: Cannot divide by 0";
		}
		else if(symbolError && !print.contains("Error"))
		{
			print = EnumChatFormatting.RED + "Error: Valid symbols are '+, -, *, /, %, ^, /-'";
		}
		else if(lastError && !print.contains("Error"))
		{
			print = EnumChatFormatting.RED + "Error: There is no previous output to insert";
			lastError = false;
		}

		// Prepend the arguments to the output, if configured to
		if(MCConfig.returnInput && !print.contains("Error") && !print.contains("Usage"))
		{
			String tempPrint;
			tempPrint = arguments[0];
			for(int i = 1; i < arguments.length; i++)
			{
				tempPrint = tempPrint + " " + arguments[i];
			}
			tempPrint = tempPrint + " = ";
			print = tempPrint + print;
		}

		// Send the message back to the user
		if(icommandsender.getCommandSenderName().equals("Server"))
		{
			MineCalc.Logger.info(print);
		}
		else if(icommandsender.getCommandSenderName().equals("@"))
		{
			MineCalc.Logger.warn("Command blocks cannot use /Calculate");
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
		List<String> aliases = new ArrayList<String>(Arrays.asList("Calc", "calculate", "calc"));
		return aliases;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
	{
		if(args.length % 2 != 1)
		{
			ArrayList<String> options = new ArrayList<String>(Arrays.asList("+", "-", "*", "/", "%", "^", "/-"));
			return options;
		}
		else
			return null;
	}

	public double getDouble(ICommandSender sender, String[] args, int i) throws NumberFormatException
	{
		if(args[i].equalsIgnoreCase("pi"))
			return Math.PI;
		else if(args[i].equalsIgnoreCase("l"))
		{
			if(lastMap.containsKey(sender.getCommandSenderName()))
				return lastMap.get(sender.getCommandSenderName());
			else
			{
				lastError = true;
				return 7;
			}
		}
		else
			return Double.valueOf(args[i]);
	}
}
