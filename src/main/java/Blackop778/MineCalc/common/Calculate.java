package Blackop778.MineCalc.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Blackop778.MineCalc.MineCalc;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class Calculate extends CommandBase
{

	static HashMap<String, Double> lastMap = new HashMap<String, Double>();
	static final ChatStyle redStyle = new ChatStyle().setColor(EnumChatFormatting.RED);

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
		return I18n.format("minecalc.calc.help");
	}

	public IChatComponent calculate(ICommandSender icommandsender, String[] arguments)
	{
		// What happens when command is entered
		IChatComponent print = null;
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
					else if(arguments[i].equals("^"))
					{
						i++;
						double next = getDouble(icommandsender, arguments, i);
						if(n < 0)
						{
							double num = next / next / next;
							if(num % 2 == 0)
								throw new ImaginaryNumberException();
						}
						if(getDouble(icommandsender, arguments, i) == 0 && MCConfig.zeroMultWarns)
						{
							zeroPower = true;
						}
						n = Math.pow(n, next);
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
								print = new ChatComponentText(
										String.valueOf((int) (n / getDouble(icommandsender, arguments, i))) + "R"
												+ String.valueOf((int) (n % getDouble(icommandsender, arguments, i))));
							}
						}
						else if(n % 1 == 0)
						{ // Remove unnecessary doubles
							lastMap.put(icommandsender.getCommandSenderName(), n);
							int b = (int) (n);
							print = new ChatComponentText(String.valueOf(b));
						}
						else
						{
							lastMap.put(icommandsender.getCommandSenderName(), n);
							print = new ChatComponentText(String.valueOf(n));
						}

						// Append warnings if needed
						if(zeroPower)
						{
							print.appendSibling(new ChatComponentTranslation("minecalc.calc.powerZeroWarning")
									.setChatStyle(redStyle));
						}
						else if(zeroMult)
						{
							print.appendSibling(new ChatComponentTranslation("minecalc.calc.multZeroWarning")
									.setChatStyle(redStyle));
						}
					}
				}
			}
			catch(NumberFormatException e)
			{
				if(e.getMessage().equals("multiple points"))
					return new ChatComponentTranslation("minecalc.calc.multiplePointsException").setChatStyle(redStyle);
				return new ChatComponentTranslation("minecalc.calc.numberFormatException").setChatStyle(redStyle)
						.appendSibling(new ChatComponentText(e.getMessage().substring(17, e.getMessage().length())));
			}
			catch(ImaginaryNumberException er)
			{
				return new ChatComponentTranslation("minecalc.calc.imaginaryException").setChatStyle(redStyle);
			}
			catch(DivisionException err)
			{
				return new ChatComponentTranslation("minecalc.calc.divZeroException").setChatStyle(redStyle);
			}
			catch(SymbolException erro)
			{
				return new ChatComponentTranslation("minecalc.calc.symbolException").setChatStyle(redStyle)
						.appendSibling(new ChatComponentText(" %"))
						.appendSibling(new ChatComponentTranslation("minecalc.calc.symbolExceptionPartTwo"));
			}
			catch(PreviousOutputException error)
			{
				return new ChatComponentTranslation("minecalc.calc.previousOutputException").setChatStyle(redStyle);
			}
		}
		else
		{ // If the number of arguments is wrong
			return new ChatComponentTranslation("minecalc.calc.usage").setChatStyle(redStyle);
		}

		// Prepend the arguments to the output, if configured to
		if(MCConfig.returnInput)
		{
			String tempPrint;
			tempPrint = arguments[0];
			for(int i = 1; i < arguments.length; i++)
			{
				tempPrint = tempPrint + " " + arguments[i];
			}
			tempPrint = tempPrint + " = ";
			print = new ChatComponentText(tempPrint).appendSibling(print);
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

			IChatComponent output = calculate(sender, args);

			// Send the message back to the user
			if(sender.getCommandSenderName().equals("Server"))
			{
				MineCalc.Logger.info(output.getUnformattedText());
			}
			else
			{
				EntityPlayer player = (EntityPlayer) sender;
				player.addChatMessage(output);
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
