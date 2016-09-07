package Blackop778.MineCalc.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Blackop778.MineCalc.MineCalc;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Calculate extends CommandBase
{

	static HashMap<String, Double> lastMap = new HashMap<String, Double>();

	@Override
	public String getCommandName()
	{
		// What must be typed in following the / to trigger the command
		return "calc";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		// What is shown when "/help Calculate" is typed in
		return "/calc <number><symbol><number>[symbol][number]";
	}

	public String calculate(ICommandSender icommandsender, String[] arguments)
	{
		// What happens when command is entered
		String print = null;
		boolean zeroPower = false;
		boolean zeroMult = false;
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
							throw new DivisionException();
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
								throw new DivisionException();
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
							throw new ImaginaryNumberException();
						}
						else if(n == 0)
						{
							throw new DivisionException();
						}
						else
						{
							boolean neg = false;
							if(n < 0)
							{
								n = -n;
								neg = true;
							}
							n = Math.pow(n, 1.0 / getDouble(icommandsender, arguments, i));
							if(neg)
								n = -n;
						}
					}
					else
					{
						throw new SymbolException();
					}

					if(i + 1 == arguments.length)
					{
						if(arguments[i - 1].equals("%") && MCConfig.fancyRemainders)
						{ // Fancy remainder output
							if(getDouble(icommandsender, arguments, i) == 0)
							{
								throw new DivisionException();
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
			catch(NumberFormatException e)
			{
				print = EnumChatFormatting.RED + "Error: Could not be interpreted as a double:"
						+ e.getMessage().substring(17, e.getMessage().length());
			}
			catch(ImaginaryNumberException er)
			{
				print = EnumChatFormatting.RED + "Error: Imaginary numbers are not supported";
			}
			catch(DivisionException err)
			{
				print = EnumChatFormatting.RED + "Error: Cannot divide by 0";
			}
			catch(SymbolException erro)
			{
				print = EnumChatFormatting.RED + "Error: Valid symbols are '+, -, *, /, %, ^, /-'";
			}
			catch(PreviousOutputException error)
			{
				print = EnumChatFormatting.RED + "Error: There is no previous output to insert";
			}
		}
		else
		{ // If the number of arguments is wrong
			print = EnumChatFormatting.RED + "Usage: /calc <number><symbol><number>[symbol]" + EnumChatFormatting.RED
					+ "[number]";
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
		List<String> aliases = new ArrayList<String>(Arrays.asList("Calc", "calculate", "Calculate"));
		return aliases;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		if(args.length % 2 != 1)
		{
			ArrayList<String> options = new ArrayList<String>(Arrays.asList("+", "-", "*", "/", "%", "^", "/-"));
			return options;
		}
		else
			return null;
	}

	public double getDouble(ICommandSender sender, String[] args, int i)
			throws NumberFormatException, PreviousOutputException
	{
		if(args[i].equalsIgnoreCase("pi"))
			return Math.PI;
		else if(args[i].equalsIgnoreCase("l"))
		{
			if(lastMap.containsKey(sender.getCommandSenderName()))
				return lastMap.get(sender.getCommandSenderName());
			else
			{
				throw new PreviousOutputException();
			}
		}
		else
			return Double.valueOf(args[i]);
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException
	{
		if(sender.getCommandSenderName().equals("@"))
		{
			MineCalc.Logger.warn("Command blocks cannot use /calc");
		}
		else
		{
			ArrayList<String> formattedArgs;
			formattedArgs = new ArrayList<String>();
			for(String arg : args)
			{
				int argStartIndex = 0;
				boolean lastIsNum = false;
				boolean thisIsNum = false;
				Character lastChar = 'z';
				for(int i = 0; i < arg.toCharArray().length + 1; i++)
				{
					if(i == arg.toCharArray().length)
					{
						formattedArgs.add(new String(arg.toCharArray(), argStartIndex, i - argStartIndex));
					}
					else
					{
						lastIsNum = thisIsNum;
						thisIsNum = isNumber(arg.toCharArray()[i], lastIsNum, lastChar);
						lastChar = arg.toCharArray()[i];
						if(thisIsNum != lastIsNum)
						{
							if(i != 0)
							{
								formattedArgs.add(new String(arg.toCharArray(), argStartIndex, i - argStartIndex));
								argStartIndex = i;
							}
						}
					}
				}
			}

			args = formattedArgs.toArray(new String[1]);

			String output = calculate(sender, args);

			// Send the message back to the user
			if(sender.getCommandSenderName().equals("Server"))
			{
				MineCalc.Logger.info(output);
			}
			else
			{
				EntityPlayer player = (EntityPlayer) sender;
				player.addChatMessage(new ChatComponentText(output));
			}
		}
	}

	public static boolean isNumber(Character character, boolean lastIsNum, Character lastChar)
	{
		if(!character.equals('.'))
		{
			if(!character.toString().equalsIgnoreCase("l"))
			{
				if(!character.toString().equalsIgnoreCase("p"))
				{
					if(!character.toString().equalsIgnoreCase("i"))
					{
						if(!(character.equals('-') && (!lastIsNum && !lastChar.toString().equals("/"))))
						{
							try
							{
								Double.valueOf(String.valueOf(character));
							}
							catch(NumberFormatException e)
							{
								return false;
							}
						}
					}
				}
			}
		}

		return true;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender)
	{
		return true;
	}
}

@SuppressWarnings("serial")
class ImaginaryNumberException extends Exception
{

}

@SuppressWarnings("serial")
class DivisionException extends Exception
{

}

@SuppressWarnings("serial")
class SymbolException extends Exception
{

}

@SuppressWarnings("serial")
class PreviousOutputException extends Exception
{

}
