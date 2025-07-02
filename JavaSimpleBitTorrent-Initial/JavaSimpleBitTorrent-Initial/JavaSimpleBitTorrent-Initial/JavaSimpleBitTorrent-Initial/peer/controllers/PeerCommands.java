package peer.controllers;

import common.models.CLICommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum PeerCommands implements CLICommands {
	END("(?i)^\\s*(END_PROGRAM|EXIT|QUIT)\\s*$"),
	LIST("(?i)^\\s*LIST\\s*$"),
	DOWNLOAD("(?i)^\\s*DOWNLOAD\\s+(.+)\\s*$");
	// TODO: You can add more commands and patterns here if needed

	private final String regex;

	PeerCommands(String regex) {
		this.regex = regex;
	}

	@Override
	public Matcher getMatcher(String input) {
		return Pattern.compile(regex).matcher(input);
	}
}