/*******************************************************************************
 * Copyright (c) 2010, 2024 The Eclipse Foundation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     The Eclipse Foundation - initial API and implementation
 *******************************************************************************/
package org.eclipse.epp.mpc.tests;

/**
 * Retained for source compatibility. The logging functionality has been moved to {@link LoggingExtension}.
 */
public class LoggingSuite {

	/**
	 * @deprecated This always returns {@code false} since the transition to JUnit 5. Use {@link LoggingExtension}
	 *             instead.
	 */
	@Deprecated
	public static boolean isLogging() {
		return false;
	}
}
