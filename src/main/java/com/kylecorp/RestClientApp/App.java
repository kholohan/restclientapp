package com.kylecorp.RestClientApp;

import java.util.concurrent.ExecutionException;


public class App
{
	protected Console		console;
	private final String	name;
	private String[]		args;
	private Client			client;

	public App(String[] args)
	{
		this.name = this.getClass().getSimpleName();
		this.args = args;
		this.client = new Client();
	}

	public static void main(String[] args) throws Exception
	{
		new App(args).run();
	}

	public void run()
	{
		if (args.length > 0)
		{
			if (args[0].equals("zipcode"))
			{
				if (args.length > 1)
				{
					// console.exec(args[1]);
					client.getWeather(args[1]);

				}
			}
		}
	}

	protected Console createInterpreter(boolean interactive)
	{
		return null;
	}
}
