// /////////////////////////////////////////////////////////////////////////
//
// Copyright (c) 2011-2015, Plexxi Inc. and its licensors.
//
// All rights reserved.
//
// Use and duplication of this software is subject to a separate license
// agreement between the user and Plexxi or its licensor.
//
// /////////////////////////////////////////////////////////////////////////

package com.kylecorp.RestClientApp.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/*
 * Workaround for ch.qos.logback.classic.filter.ThresholdFilter which lacks any sort of means to retrieve the level
 * 
 * NOTE: For some odd reason, creating an accessor method for level, causes the level to not properly get injected at run-time
 */
public class ThresholdFilter extends Filter<ILoggingEvent>
{
	private Level	level;

	@Override
	public FilterReply decide(final ILoggingEvent event)
	{
		if (!isStarted())
		{
			return FilterReply.NEUTRAL;
		}

		if (event.getLevel().isGreaterOrEqual(level))
		{
			return FilterReply.NEUTRAL;
		} else
		{
			return FilterReply.DENY;
		}
	}

	public Level getLevel()
	{
		return level;
	}

	public void setLevel(final Level level)
	{
		this.level = level;
	}

	@Override
	public void start()
	{
		if (level != null)
		{
			super.start();
		}
	}
}
