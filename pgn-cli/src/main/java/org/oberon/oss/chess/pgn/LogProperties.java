/*
     Copyright © 2000 and beyond by Fabien H. Dumay

  	Licensed under the Apache License, Version 2.0 (the "License");
  	you may not use this file except in compliance with the License.
  	You may obtain a copy of the License at

  	http://www.apache.org/licenses/LICENSE-2.0

  	Unless required by applicable law or agreed to in writing, software
  	distributed under the License is distributed on an "AS IS" BASIS,
  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  	See the License for the specific language governing permissions and
  	limitations under the License.
 */
package org.oberon.oss.chess.pgn;

import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Loading log properties.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
public class LogProperties {
	private LogProperties() {

	}
	private static final Object Lock = new Object();

	public static void loadLoggingProperties() throws LogPropertiesException {
		synchronized (Lock) {
			try {
				ConfigurationSource source = getSource(System.getProperty("log4j2.configurationFile"));
				if (source == null) {
					source = getSource(ClassLoader.getSystemClassLoader().getResource("log4j2.xml"));
				}
				if (source != null) {
					Configurator.initialize(null, source);
				} else {
					throw new IllegalStateException("No logging configuration could be loaded.");
				}
			}
			catch (Exception e) {
				throw new LogPropertiesException("Failed to load logging properties due to : " + e.getClass().getSimpleName());
			}
		}
	}

	private static ConfigurationSource getSource(String configurationFile) throws IOException {
		ConfigurationSource source = null;
		if (configurationFile != null) {
			try (InputStream inputStream = new FileInputStream(configurationFile)) {
				source = new ConfigurationSource(inputStream);
			}
			catch (IOException e) {
				throw new IOException("Failed to load configuration from: " + configurationFile, e);
			}
		}
		return source;
	}

	private static ConfigurationSource getSource(URL resource) throws URISyntaxException, IOException {
		ConfigurationSource source = null;
		if (resource != null) {
			try (InputStream inputStream = new FileInputStream(new File(resource.toURI()))) {
				source = new ConfigurationSource(inputStream);
			}
			catch (IOException e) {
				throw new IOException("Failed to load default console logger configuration", e);
			}

		}
		return source;
	}
}
