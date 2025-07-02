package tracker.controllers;

import common.models.CLICommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TrackerCommands implements CLICommands {
	END("^\\s*(exit|END_PROGRAM)\\s*$"),
	REFRESH_FILES("^\\s*REFRESH_FILES\\s*$"),
	RESET_CONNECTIONS("^\\s*RESET_CONNECTIONS\\s*$"),
	LIST_PEERS("^\\s*LIST_PEERS\\s*$"),
	LIST_FILES("^\\s*LIST_FILES\\s+(\\S+)\\s+(\\d+)\\s*$"),
	GET_RECEIVES("^\\s*GET_RECEIVES\\s+(\\S+)\\s+(\\d+)\\s*$"),
	GET_SENDS("^\\s*GET_SENDS\\s+(\\S+)\\s+(\\d+)\\s*$");

	private final String regex;

	TrackerCommands(String regex) {
		this.regex = regex;
	}

	@Override
	public Matcher getMatcher(String input) {
		return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(input);
	}
}