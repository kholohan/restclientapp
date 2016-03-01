package com.kylecorp.RestClientApp;


import java.util.Properties;

import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyFile;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.util.JLineConsole;
import org.slf4j.Logger;

import com.kylecorp.RestClientApp.logging.LogService;
import com.kylecorp.RestClientApp.util.ClassNameCompletor;
import com.kylecorp.RestClientApp.util.LocationWeatherInfo;
import com.kylecorp.RestClientApp.util.OutputFormatter;


public class App
{
    private JLineConsole             console;
    private final String             name;
    private String[]                 args;
    private Client                   client;
    private final ClassNameCompletor completor;
    private final static Logger      LOGGER = LogService.getLogger();


    public App(String[] args)
    {
        this.name = this.getClass().getSimpleName();
        this.args = args;
        this.client = new Client();

        completor = new ClassNameCompletor();

        PySystemState.initialize(PySystemState.getBaseProperties(), new Properties(), args);

        // console = createInterpreter(checkIsInteractive());
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
                    // Dump to log for now
                    LocationWeatherInfo locationWeatherInfo = client.getWeather(args[1]);
                    LOGGER.debug(OutputFormatter.output(locationWeatherInfo));

                }
            }
        }
        else
        {
            LocationWeatherInfo locationWeatherInfo = client.getWeather();
            LOGGER.debug(OutputFormatter.output(locationWeatherInfo));
        }
    }


    protected boolean checkIsInteractive()
    {
        PySystemState systemState = Py.getSystemState();
        boolean interactive = ((PyFile) Py.defaultSystemState.stdin).isatty();
        if (!interactive)
        {
            systemState.ps1 = systemState.ps2 = Py.EmptyString;
        }
        return interactive;
    }

}
