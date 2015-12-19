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
												print = String.valueOf((int) (n / Double.valueOf(arguments[i + 1])))
														+ "R"
														+ String.valueOf((int) (n % Double.valueOf(arguments[i + 1])));
												i++;
											} else
											{
												if (arguments[i].equals("+"))
													{
														i++;
														n = n + Double.valueOf(arguments[i]);
													} else if (arguments[i].equals("-"))
													{
														i++;
														n = n - Double.valueOf(arguments[i]);
													} else if (arguments[i].equals("*"))
													{
														i++;
														n = n * Double.valueOf(arguments[i]);
													} else if (arguments[i].equals("/"))
													{
														if (arguments[i + 1].equals("0"))
															{
																divError = true;
															}
														i++;
														n = n / Double.valueOf(arguments[i]);
													} else if (arguments[i].equals("%"))
													{
														if (arguments[i + 1].equals("0"))
															{
																divError = true;
															}
														i++;
														n = n % Double.valueOf(arguments[i]);
													} else
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
															} else
															{
																print = String.valueOf(n);
															}
													}
											}
									}
							} catch (NumberFormatException error)
							{
								print = EnumChatFormatting.RED + "Error: Could not be interpreted as a double:"
										+ error.getMessage().substring(17, error.getMessage().length());
							}
					} else
					{
						print = EnumChatFormatting.RED + "Usage: /Calculate <number> <symbol> <number> [symbol] "
								+ EnumChatFormatting.RED + "[number]";
					}

				if (divError == true && !print.contains("Error"))
					{
						print = EnumChatFormatting.RED + "Error: Cannot divide by zero";
					} else if (symbolError && !print.contains("Error"))
					{
						print = EnumChatFormatting.RED + "Error: Valid symbols are '+,-,*,/,%'";
					}

				if (icommandsender instanceof EntityPlayer) // If the sender was
															// a
															// player
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
