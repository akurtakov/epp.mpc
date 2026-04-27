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
 *     Yatta Solutions - initial API and implementation
 *******************************************************************************/
package org.eclipse.epp.mpc.tests;

import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.junit.platform.commons.JUnitException;

import java.lang.reflect.Method;

/**
 * JUnit 5 extension that runs test methods on the SWT UI thread when the workbench is available.
 */
public class UISuite implements InvocationInterceptor {

	@Override
	public void interceptTestMethod(Invocation<Void> invocation,
			ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
		runInUIThread(invocation);
	}

	@Override
	public void interceptBeforeEachMethod(Invocation<Void> invocation,
			ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
		runInUIThread(invocation);
	}

	@Override
	public void interceptAfterEachMethod(Invocation<Void> invocation,
			ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
		runInUIThread(invocation);
	}

	private static void runInUIThread(Invocation<Void> invocation) throws Throwable {
		if (!PlatformUI.isWorkbenchRunning()) {
			throw new JUnitException("Workbench not running");
		}
		if (Display.getCurrent() != null) {
			// already on the UI thread
			invocation.proceed();
			return;
		}
		AtomicReference<Throwable> error = new AtomicReference<>();
		PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
			try {
				invocation.proceed();
			} catch (Throwable t) {
				error.set(t);
			}
		});
		if (error.get() != null) {
			throw error.get();
		}
	}
}
