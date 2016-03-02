package com.kylecorp.RestClientApp;

import java.util.Properties;

import org.python.core.Py;
import org.python.core.PyFile;
import org.python.core.PySystemState;
import org.python.util.JLineConsole;
import org.slf4j.Logger;

import com.kylecorp.RestClientApp.logging.LogService;
import com.kylecorp.RestClientApp.util.ClassNameCompletor;

public class App
{
	private final static Logger	LOGGER	= LogService.getLogger();

	public static void main(final String[] args) throws Exception
	{
		new App(args).run();
	}

	private final String[]				args;
	private final Client				client;
	private final ClassNameCompletor	completor;
	private JLineConsole				console;

	private final String				name;

	public App(final String[] args)
	{
		this.name = this.getClass().getSimpleName();
		this.args = args;
		this.client = new Client();

		completor = new ClassNameCompletor();

		PySystemState.initialize(PySystemState.getBaseProperties(),
				new Properties(), args);

		console = createInterpreter(checkIsInteractive());
	}

	private JLineConsole createInterpreter(boolean interactive)
	{
		JLineConsole console = newInterpreter(interactive);
		Py.getSystemState().__setattr__("_jy_interpreter", Py.java2py(console));

		if (console instanceof JLineConsole)
		{
			JLineConsole j = console;
			j.getReader().addCompletor(completor);
		}

		// imp.load("site");
		return console;
	}

	private JLineConsole newInterpreter(boolean interactiveStdin)
	{
		if (!interactiveStdin)
		{
			return new JLineConsole();
		}

		String interpClass = PySystemState.registry.getProperty(
				"python.console", "");
		if (interpClass.length() > 0)
		{
			try
			{
				return (JLineConsole) Class.forName(interpClass).newInstance();
			} catch (Throwable t)
			{
				// fall through
			}
		}
		return new JLineConsole();
	}

	protected boolean checkIsInteractive()
	{
		final PySystemState systemState = Py.getSystemState();
		final boolean interactive = ((PyFile) Py.defaultSystemState.stdin)
				.isatty();
		if (!interactive)
		{
			systemState.ps1 = systemState.ps2 = Py.EmptyString;
		}
		return interactive;
	}

	public void run()
	{
		console.push("from com.kylecorp.RestClientApp import Client");
		console.push("client = Client.createClient()");

		if (args.length > 0)
		{
			if (args[0].equals("zipcode"))
			{
				if (args.length > 1)
				{
					// console.exec(args[1]);
					// Dump to log for now
					StringBuffer zipCodeSb = new StringBuffer();
					zipCodeSb.append("print(client.getWeather(\"")
							.append(args[1]).append("\"))");
					console.push(zipCodeSb.toString());

				}
			}
		} else
		{

			StringBuffer sb = new StringBuffer();
			sb.append("Welcome to the Weather app");
			console.interact(sb.toString(), null);

			console.write(name + " exiting\n");
		}
	}

}
