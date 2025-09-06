package vpp.vac.skyquill.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import vpp.vac.skyquill.main.Main;
import vpp.vac.skyquill.module.impl.mining.Chestcounter;
import vpp.vac.skyquill.module.impl.mining.CommisionCounter;

public class Skyquill implements ICommand {
	public static String[] Argz;
	private static ArrayList<String> commands = new ArrayList<String>();

	public Skyquill() {
		commands.add("checkcounter");
	}

	@Override
	public int compareTo(ICommand o) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "Skyquill";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Skyquill command";
	}

	@Override
	public List<String> getCommandAliases() {
		List<String> commandAliases = new ArrayList();
		commandAliases.add("sq");
		commandAliases.add("skyq");
		commandAliases.add("squill");
		return commandAliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		Argz = args;
		if (args.length != 0) {
			if (args[0].equalsIgnoreCase("chestcounter")) {
				if (args[1].equalsIgnoreCase("check")) {
					sender.addChatMessage(new ChatComponentText(
							EnumChatFormatting.GREEN + Main.PREFIX + "Current chest counter: " + Chestcounter.count));
				} else {
					if (args[1].equalsIgnoreCase("reset")) {
						sender.addChatMessage(
								new ChatComponentText(EnumChatFormatting.GREEN + Main.PREFIX + "Reset chest counter!"));
						Chestcounter.count = 0;
					}else {
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + Main.PREFIX + "/sq chestcounter check|reset"));
					}
				}
			} else {
				if (args[0].equalsIgnoreCase("help")) {
					int count = 1;
					for (String c : commands) {
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + "" + count + ". " + c));
						count++;
					}
				} else {
					if (args[0].equalsIgnoreCase("devversion") || args[0].equalsIgnoreCase("dev")) {
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Skyquill v"
								+ Main.VERSION + "\n" + EnumChatFormatting.BOLD + EnumChatFormatting.AQUA
								+ "Developer Version: " + Main.DEV_VERSION + "\n made by vaclavak"));
					}else {
						if(args[0].equalsIgnoreCase("fullversion")) {
							sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + Main.VERSION + "-" + Main.DEV_VERSION));
						}else {
							if(args[0].equalsIgnoreCase("commisioncounter")) {
								if(args[1].equalsIgnoreCase("check")) {
									sender.addChatMessage(new ChatComponentText(
											EnumChatFormatting.GREEN + Main.PREFIX + "Current commision counter: " + CommisionCounter.count));
								}else {
									if(args[0].equalsIgnoreCase("reset")) {
										CommisionCounter.count = 0;
										sender.addChatMessage(new ChatComponentText(
												EnumChatFormatting.GREEN + Main.PREFIX + "Reset commision counter!"));
									}else {
										sender.addChatMessage(new ChatComponentText(
												EnumChatFormatting.RED + Main.PREFIX + "/sq commisioncounter check|reset" + Chestcounter.count));
									}
								}
							}
						}
					}
				}
			}
		} else {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + Main.PREFIX
					+ "Invalid argument! Usage: /sq <argument>, use /sq help for command list"));
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		return commands;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

}
