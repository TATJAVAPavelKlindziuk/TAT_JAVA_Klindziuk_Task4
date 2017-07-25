package com.klindziuk.parsing.runner;

import javax.naming.OperationNotSupportedException;

import com.klindziuk.parsing.reflection.ReflectionCommandName;

public class ReflectionCommandRunner {
	private final static String MOCK_REQUEST= "Mock request";
	
	public static void main(String[] args) throws OperationNotSupportedException  {
		for(String command : ReflectionCommandName.getCommands())
		ReflectionCommandName.getCommandByName(command).execute(MOCK_REQUEST);
	}
}
