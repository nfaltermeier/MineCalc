package Blackop778.MineCalc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class Calculate extends CommandBase
{

	static HashMap<String, Double> lastMap = new HashMap<String, Double>();
	private boolean lastError;
	public static String redStyle;

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

	public String calculate(ICommandSender icommandsender, String[] arguments)
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
					else if(arguments[i].equals("*") || arguments[i].equalsIgnoreCase("x"))
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
								lastMap.put(icommandsender.getName(), n % getDouble(icommandsender, arguments, i));
								print = String.valueOf((int) (n / getDouble(icommandsender, arguments, i))) + "R"
										+ String.valueOf((int) (n % getDouble(icommandsender, arguments, i)));
							}
						}
						else if(n % 1 == 0)
						{ // Remove unnecessary doubles
							lastMap.put(icommandsender.getName(), n);
							int b = (int) (n);
							print = String.valueOf(b);
						}
						else
						{
							lastMap.put(icommandsender.getName(), n);
							print = String.valueOf(n);
						}

						// Append warnings if needed
						if(zeroPower)
						{
							print = print + redStyle + " Warning: Anything to the power of 0 is 1";
						}
						else if(zeroMult)
						{
							print = print + redStyle + " Warning: Anything times 0 is 0";
						}
					}
				}
			}
			catch(NumberFormatException error)
			{
				print = redStyle + "Error: Could not be interpreted as a double:"
						+ error.getMessage()/**
											 * .substring(17,
											 * error.getMessage().length())
											 */
						;
			}
		}
		else
		{ // If the number of arguments is wrong
			print = redStyle + "Usage: /Calculate <number> <symbol> <number> [symbol] " + redStyle + "[number]";
		}

		// Change print to an error message if an error was thrown
		if(imaginaryError && !print.contains("Error"))
		{
			print = redStyle + "Error: Imaginary numbers are not supported";
		}
		else if(divError && !print.contains("Error"))
		{
			print = redStyle + "Error: Cannot divide by 0";
		}
		else if(symbolError && !print.contains("Error"))
		{
			print = redStyle + "Error: Valid symbols are '+, -, *, /, %, ^, /-'";
		}
		else if(lastError && !print.contains("Error"))
		{
			print = redStyle + "Error: There is no previous output to insert";
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

		return print;
	}

	@Override
	public List<String> getCommandAliases()
	{
		// A list of alternate command names
		List<String> aliases = new ArrayList<String>(Arrays.asList("Calc", "calculate", "calc"));
		return aliases;
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args,
			@Nullable BlockPos pos)
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
			if(lastMap.containsKey(sender.getName()))
				return lastMap.get(sender.getName());
			else
			{
				lastError = true;
				return 7;
			}
		}
		else
			return Double.valueOf(args[i]);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		ArrayList<String> formattedArgs;
		formattedArgs = new ArrayList<String>();
		for(String arg : args)
		{
			int numberStartIndex = 0;
			boolean lastIsNum = false;
			for(int i = 0; i < arg.toCharArray().length + 1; i++)
			{
				if(i == arg.toCharArray().length)
				{
					if(lastIsNum)
					{
						formattedArgs.add(new String(arg.toCharArray(), numberStartIndex, i - numberStartIndex));
					}
				}
				else if(lastIsNum)
				{
					lastIsNum = isNumber(arg.toCharArray()[i], lastIsNum);
					if(!lastIsNum)
					{
						formattedArgs.add(new String(arg.toCharArray(), numberStartIndex, i - numberStartIndex));
					}
				}
				else
				{
					lastIsNum = isNumber(arg.toCharArray()[i], lastIsNum);
					if(lastIsNum)
					{
						numberStartIndex = i;
					}
					else
					{
						formattedArgs.add(String.valueOf(arg.toCharArray()[i]));
					}
				}
			}
		}

		args = formattedArgs.toArray(new String[1]);

		String output = calculate(sender, args);

		// Send the message back to the user
		if(sender.getName().equals("Server"))
		{
			MineCalc.Logger.info(output);
		}
		else if(sender.getName().equals("@"))
		{
			MineCalc.Logger.warn("Command blocks cannot use /Calculate");
		}
		else
		{
			EntityPlayer player = (EntityPlayer) sender;
			player.addChatMessage(new TextComponentString(output));
		}
	}

	public static boolean isNumber(Character character, boolean lastIsNum)
	{
		if(!character.equals('.'))
		{
			if(!(character.equals('-') && !lastIsNum))
			{
				try
				{
					Double.valueOf(String.valueOf(character));
				}
				catch(NumberFormatException e)
				{
					System.out.println("f");
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return true;
	}
}
