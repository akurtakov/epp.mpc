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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.epp.internal.mpc.core.MarketplaceClientCore;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestWatcher;

public class LoggingExtension implements BeforeEachCallback, AfterEachCallback, TestWatcher {

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		log("Starting test " + context.getDisplayName());
	}

	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		log("Finished test " + context.getDisplayName());
	}

	@Override
	public void testFailed(ExtensionContext context, Throwable cause) {
		log(IStatus.ERROR, "Test " + context.getDisplayName() + " failed: " + cause.getMessage(), cause);
	}

	@Override
	public void testAborted(ExtensionContext context, Throwable cause) {
		log(IStatus.INFO, "Assumption failed for " + context.getDisplayName() + ": " + cause.getMessage(), cause);
	}

	@Override
	public void testDisabled(ExtensionContext context, java.util.Optional<String> reason) {
		log("Skipped test " + context.getDisplayName());
	}

	private static void log(String message) {
		log(IStatus.INFO, message, null);
	}

	private static void log(int severity, String message, Throwable ex) {
		MarketplaceClientCore.getLog().log(new Status(severity, "org.eclipse.epp.mpc.tests", message, ex));
		System.out.println(message);
		if (ex != null) {
			ex.printStackTrace();
		}
	}
}
