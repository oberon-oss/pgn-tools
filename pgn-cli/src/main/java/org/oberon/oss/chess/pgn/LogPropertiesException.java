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

/**
 * Thrown by the LogProperties class to indicate a problem or error while loading/setting up log properties.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class LogPropertiesException extends Exception {
	/**
	 * Constructs a new exception with {@code null} as its detail message. The cause is not initialized, and may
	 * subsequently be initialized by a call to {@link #initCause}.
	 *
	 * @since 8.0.0
	 */
	public LogPropertiesException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message. The cause is not initialized, and may subsequently
	 * be initialized by a call to {@link #initCause}.
	 *
	 * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()}
	 *                method.
	 *
	 * @since 8.0.0
	 */
	public LogPropertiesException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * <p>
	 * Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated in this
	 * exception's detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).  (A
	 *                <i>null</i> value is permitted, and indicates that the cause is nonexistent or unknown.)
	 *
	 * @since 8.0.0
	 */
	public LogPropertiesException(
		String message,
		Throwable cause
	) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail message of <i>(cause==null ? null :
	 * cause.toString())</i> (which typically contains the class and detail message of <i>cause</i>).
	 *
	 * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).  (A <i>null</i>
	 *              value is permitted, and indicates that the cause is nonexistent or unknown.)
	 *
	 * @since 8.0.0
	 */
	public LogPropertiesException(Throwable cause) {
		super(cause);
	}
}