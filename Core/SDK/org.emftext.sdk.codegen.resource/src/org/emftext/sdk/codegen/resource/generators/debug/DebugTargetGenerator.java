/*******************************************************************************
 * Copyright (c) 2006-2013
 * Software Technology Group, Dresden University of Technology
 * DevBoost GmbH, Berlin, Amtsgericht Charlottenburg, HRB 140026
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany;
 *   DevBoost GmbH - Berlin, Germany
 *      - initial API and implementation
 ******************************************************************************/
package org.emftext.sdk.codegen.resource.generators.debug;

import static de.devboost.codecomposers.java.ClassNameConstants.ARRAY_LIST;
import static de.devboost.codecomposers.java.ClassNameConstants.LIST;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.BUFFERED_READER;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.CONNECT_EXCEPTION;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.CORE_EXCEPTION;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.DEBUG_EXCEPTION;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.DEBUG_PLUGIN;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.INPUT_STREAM_READER;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.IO_EXCEPTION;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.I_BREAKPOINT;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.I_DEBUG_TARGET;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.I_LAUNCH;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.I_MARKER_DELTA;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.I_MEMORY_BLOCK;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.I_PROCESS;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.I_PROGRESS_MONITOR;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.I_STATUS;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.I_THREAD;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.JOB;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.SOCKET;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.STATUS;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.UNKNOWN_HOST_EXCEPTION;

import org.emftext.sdk.codegen.parameters.ArtifactParameter;
import org.emftext.sdk.codegen.resource.GenerationContext;
import org.emftext.sdk.codegen.resource.generators.JavaBaseGenerator;
import org.emftext.sdk.concretesyntax.OptionTypes;

import de.devboost.codecomposers.java.JavaComposite;

// TODO all debug-related classes are also syntax-dependent since their generation can be 
// disabled with 'disableDebugSupport'
public class DebugTargetGenerator extends JavaBaseGenerator<ArtifactParameter<GenerationContext>> {

	public void generateJavaContents(JavaComposite sc) {
		if (!getContext().isDebugSupportEnabled()) {
			generateEmptyClass(sc, null, OptionTypes.DISABLE_DEBUG_SUPPORT);
			return;
		}
		sc.add("package " + getResourcePackageName() + ";");sc.addLineBreak();sc.addImportsPlaceholder();
		sc.addLineBreak();
		sc.add("public class " + getResourceClassName() + " extends " + debugElementClassName +" implements " + I_DEBUG_TARGET(sc) + ", " + iDebugEventListenerClassName + " {");
		sc.addLineBreak();
		addFields(sc);
		addDebugEventDispatcherClass(sc);
		addConstructor(sc);
		addMethods(sc);
		sc.add("}");
	}

	private void addMethods(JavaComposite sc) {
		addAddEventListenerMethod(sc);
		addRemoveEventListenerMethod(sc);
		addGetNameMethod(sc);
		addGetDebugTarget(sc);
		addGetProcessMethod(sc);
		addGetThreadsMethod(sc);
		addHasThreadsMethod(sc);
		addSupportsBreakpointMethod(sc);
		addGetLaunchMethod(sc);
		addCanTerminateMethod(sc);
		addIsTerminatedMethod(sc);
		addTerminateMethod(sc);
		addTerminatedMethod(sc);
		addCanResumeMethod(sc);
		addCanSuspendMethod(sc);
		addIsSuspendedMethod(sc);
		addResumeMethod(sc);
		addGetThreadMethod(sc);
		addSuspendMethod(sc);
		addBreakpointAddedMethod(sc);
		addBreakpointRemovedMethod(sc);
		addBreakpointChangedMethod(sc);
		addCanDisconnectMethod(sc);
		addDisconnectMethod(sc);
		addIsDisconnectedMethod(sc);
		addGetMemoryBlockMethod(sc);
		addSupportsStorageRetrievalMethod(sc);
		addStartedMethod(sc);
		addInstallDeferredBreakpointsMethod(sc);
		addHandleMessageMethod(sc);
		addGetDebugProxyMethod(sc);
	}

	private void addFields(JavaComposite sc) {
		sc.addJavadoc("associated system process");
		sc.add("private " + debugProcessClassName + " process;");
		sc.addLineBreak();
		sc.add("private " + SOCKET(sc) + " eventSocket;");
		sc.addLineBreak();
		sc.add("private " + BUFFERED_READER(sc) + " eventReader;");
		sc.addLineBreak();
		sc.addJavadoc("containing launch object");
		sc.add("private " + I_LAUNCH(sc) + " launch;");
		sc.addLineBreak();
		sc.add("private " + I_THREAD(sc) + "[] threads;");
		sc.addLineBreak();
		sc.add("private " + debugThreadClassName + " thread;");
		sc.addLineBreak();
		sc.add("private boolean terminated = false;");
		sc.addLineBreak();
		sc.addJavadoc("event dispatch job");
		sc.add("private DebugEventDispatcher eventDispatch;");
		sc.addJavadoc("event listeners");
		sc.add("private " + LIST(sc) + "<" + iDebugEventListenerClassName + "> eventListeners = new " + ARRAY_LIST(sc) + "<" + iDebugEventListenerClassName + ">();");
		sc.addLineBreak();
		sc.add("private " + debugProxyClassName + " debugProxy;");
		sc.addLineBreak();
	}

	private void addDebugEventDispatcherClass(JavaComposite sc) {
		sc.add("private class DebugEventDispatcher extends " + JOB(sc) + " {");
		sc.addLineBreak();
		sc.add("private " + debugCommunicationHelperClassName + " communicationHelper = new " + debugCommunicationHelperClassName + "();");
		sc.addLineBreak();
		sc.add("public DebugEventDispatcher() {");
		sc.add("super(new " + metaInformationClassName + "().getSyntaxName() + \" Event Dispatch\");");
		sc.add("setSystem(true);");
		sc.add("}");
		sc.addLineBreak();
		sc.add("protected " + I_STATUS(sc) + " run(" + I_PROGRESS_MONITOR(sc) + " monitor) {");
		sc.add("while (!isTerminated()) {");
		sc.add(debugMessageClassName + " message = communicationHelper.receive(eventReader);");
		sc.add("if (message != null) {");
		sc.add("notifyListeners(message);");
		sc.add("} else {");
		sc.add("terminated();");
		sc.add("break;");
		sc.add("}");
		sc.add("}");
		sc.add("return " + STATUS(sc) + ".OK_STATUS;");
		sc.add("}");
		sc.addLineBreak();
		
		sc.add("private void notifyListeners(" + debugMessageClassName + " message) {");
		sc.add("Object[] listeners = eventListeners.toArray();");
		sc.add("for (int i = 0; i < listeners.length; i++) {");
		sc.add("((" + iDebugEventListenerClassName +") listeners[i]).handleMessage(message);");
		sc.add("}");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addConstructor(JavaComposite sc) {
		sc.add("public " + getResourceClassName() + "(" + debugProcessClassName + " process, " + I_LAUNCH(sc) + " launch, int requestPort, int eventPort) {");
		sc.add("super(launch.getDebugTarget());");
		sc.add("this.process = process;");
		sc.add("this.launch = launch;");
		sc.add("this.thread = new " + debugThreadClassName + "(this);");
		sc.add("this.threads = new " + I_THREAD(sc) + "[] {this.thread};");
		sc.addLineBreak();
		sc.addComment("initialize debug proxy");
		sc.add("try {");
		sc.add("this.debugProxy = new " + debugProxyClassName + "(this, requestPort);");
		sc.add("} catch (" + UNKNOWN_HOST_EXCEPTION(sc) + " e) {");
		// TODO handle exception
		sc.add("e.printStackTrace();");
		sc.add("} catch (" + IO_EXCEPTION(sc) + " e) {");
		// TODO handle exception
		sc.add("e.printStackTrace();");
		sc.add("}");
		sc.addLineBreak();
		sc.addComment("initialize asynchronous event dispatcher");
		sc.add("try {");
		sc.addComment("creating event client socket (trying to connect)...");
		sc.add("this.eventSocket = new " + SOCKET(sc) + "(\"localhost\", eventPort);");
		sc.addComment("creating event client socket - done (connected).");
		sc.add("this.eventReader = new " + BUFFERED_READER(sc) + "(new " + INPUT_STREAM_READER(sc) + "(this.eventSocket.getInputStream()));");
		sc.add("} catch (" + CONNECT_EXCEPTION(sc) + " e) {");
		sc.add("throw new RuntimeException(\"Can't create socket: \" + e.getMessage());");
		sc.add("} catch (" + UNKNOWN_HOST_EXCEPTION(sc) + " e) {");
		sc.add("throw new RuntimeException(\"Can't create socket: \" + e.getMessage());");
		sc.add("} catch (" + IO_EXCEPTION(sc) + " e) {");
		sc.add("throw new RuntimeException(\"Can't create socket: \" + e.getMessage());");
		sc.add("}");
		sc.addLineBreak();
		sc.add("this.eventDispatch = new DebugEventDispatcher();");
		sc.add("this.eventDispatch.schedule();");
		sc.addLineBreak();
		sc.add("addEventListener(this);");
		sc.add("addEventListener(this.thread);");
		sc.add("addEventListener(this.process);");
		sc.addLineBreak();
		sc.add(DEBUG_PLUGIN(sc) + ".getDefault().getBreakpointManager().addBreakpointListener(this);");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addAddEventListenerMethod(JavaComposite sc) {
		sc.addJavadoc(
			"Registers the given event listener. The listener will be notified of " +
			"events in the program being interpreted. Has no effect if the listener " +
			"is already registered.",
			"@param listener event listener"
		);
		sc.add("public void addEventListener(" + iDebugEventListenerClassName + " listener) {");
		sc.add("if (!eventListeners.contains(listener)) {");
		sc.add("eventListeners.add(listener);");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addRemoveEventListenerMethod(JavaComposite sc) {
		sc.addJavadoc(
			"Deregisters the given event listener. Has no effect if the listener is " +
			"not currently registered.",
			"@param listener event listener"
		);
		sc.add("public void removeEventListener(" + iDebugEventListenerClassName + " listener) {");
		sc.add("eventListeners.remove(listener);");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetNameMethod(JavaComposite sc) {
		sc.add("public String getName() throws " + DEBUG_EXCEPTION(sc) + " {");
		sc.add("return new " + metaInformationClassName + "().getSyntaxName() + \" model\";");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetDebugTarget(JavaComposite sc) {
		sc.add("public " + I_DEBUG_TARGET(sc) + " getDebugTarget() {");
		sc.add("return this;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetProcessMethod(JavaComposite sc) {
		sc.add("public " + I_PROCESS(sc) + " getProcess() {");
		sc.add("return process;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetThreadsMethod(JavaComposite sc) {
		sc.add("public " + I_THREAD(sc) + "[] getThreads() throws " + DEBUG_EXCEPTION(sc) + " {");
		sc.add("return threads;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addHasThreadsMethod(JavaComposite sc) {
		sc.add("public boolean hasThreads() throws " + DEBUG_EXCEPTION(sc) + " {");
		sc.add("return true;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addSupportsBreakpointMethod(JavaComposite sc) {
		sc.add("public boolean supportsBreakpoint(" + I_BREAKPOINT(sc) + " breakpoint) {");
		sc.add("return breakpoint.getModelIdentifier().equals(getModelIdentifier());");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetLaunchMethod(JavaComposite sc) {
		sc.add("public " + I_LAUNCH(sc) + " getLaunch() {");
		sc.add("return launch;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addCanTerminateMethod(JavaComposite sc) {
		sc.add("public boolean canTerminate() {");
		sc.add("return getProcess().canTerminate();");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addIsTerminatedMethod(JavaComposite sc) {
		sc.add("public boolean isTerminated() {");
		sc.add("return terminated || getProcess().isTerminated();");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addTerminateMethod(JavaComposite sc) {
		sc.add("public void terminate() throws " + DEBUG_EXCEPTION(sc) + " {");
		sc.add("getThread().terminate();");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addTerminatedMethod(JavaComposite sc) {
		sc.add("private synchronized void terminated() {");
		sc.add("terminated = true;");
		sc.add("threads = new " + I_THREAD(sc) + "[0];");
		sc.add("fireTerminateEvent();");
		sc.add("removeEventListener(this);");
		sc.add("removeEventListener(this.thread);");
		sc.add("removeEventListener(this.process);");
		sc.add(DEBUG_PLUGIN(sc) + " debugPlugin = " + DEBUG_PLUGIN(sc) + ".getDefault();");
		sc.add("try {");
		sc.add("debugPlugin.getBreakpointManager().removeBreakpointListener(this);");
		sc.add("} catch (NullPointerException npe) {");
		sc.addComment("this is a workaround for Eclipse bug 352315 (see https://bugs.eclipse.org/bugs/show_bug.cgi?id=352315)");
		sc.add("}");
		// TODO is this correct?
		sc.add("getDebugProxy().terminate();");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addCanResumeMethod(JavaComposite sc) {
		sc.add("public boolean canResume() {");
		sc.add("return getThread().canResume();");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addCanSuspendMethod(JavaComposite sc) {
		sc.add("public boolean canSuspend() {");
		sc.add("return getThread().canSuspend() && !isTerminated();");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addIsSuspendedMethod(JavaComposite sc) {
		sc.add("public boolean isSuspended() {");
		sc.add("return getThread().isSuspended();");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addResumeMethod(JavaComposite sc) {
		sc.add("public void resume() throws " + DEBUG_EXCEPTION(sc) + " {");
		sc.add("getThread().resume();");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetThreadMethod(JavaComposite sc) {
		sc.add("public " + I_THREAD(sc) + " getThread() {");
		sc.add("return thread;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addSuspendMethod(JavaComposite sc) {
		sc.add("public void suspend() throws " + DEBUG_EXCEPTION(sc) + " {");
		sc.add("getThread().suspend();");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addBreakpointAddedMethod(JavaComposite sc) {
		sc.add("public void breakpointAdded(" + I_BREAKPOINT(sc) + " breakpoint) {");
		sc.add("if (supportsBreakpoint(breakpoint)) {");
		sc.add("try {");
		sc.add("if ((breakpoint.isEnabled() && getBreakpointManager().isEnabled()) || !breakpoint.isRegistered()) {");
		sc.add(lineBreakpointClassName + " lineBreakpoint = (" + lineBreakpointClassName + ") breakpoint;");
		sc.add("lineBreakpoint.install(this);");
		sc.add("}");
		sc.add("} catch (" + CORE_EXCEPTION(sc) + " e) {");
		sc.add("}");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addBreakpointRemovedMethod(JavaComposite sc) {
		sc.add("public void breakpointRemoved(" + I_BREAKPOINT(sc) + " breakpoint, " + I_MARKER_DELTA(sc) + " delta) {");
		sc.add("if (supportsBreakpoint(breakpoint)) {");
		sc.add("try {");
		sc.add("if ((breakpoint.isEnabled() && getBreakpointManager().isEnabled()) || !breakpoint.isRegistered()) {");
		sc.add(lineBreakpointClassName + " lineBreakpoint = (" + lineBreakpointClassName + ") breakpoint;");
		sc.add("lineBreakpoint.remove(this);");
		sc.add("}");
		sc.add("} catch (" + CORE_EXCEPTION(sc) + " e) {");
		sc.add("}");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addBreakpointChangedMethod(JavaComposite sc) {
		sc.add("public void breakpointChanged(" + I_BREAKPOINT(sc) + " breakpoint, " + I_MARKER_DELTA(sc) + " delta) {");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addCanDisconnectMethod(JavaComposite sc) {
		sc.add("public boolean canDisconnect() {");
		sc.addComment("not supported");
		sc.add("return false;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addDisconnectMethod(JavaComposite sc) {
		sc.add("public void disconnect() throws " + DEBUG_EXCEPTION(sc) + " {");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addIsDisconnectedMethod(JavaComposite sc) {
		sc.add("public boolean isDisconnected() {");
		sc.add("return false;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetMemoryBlockMethod(JavaComposite sc) {
		sc.add("public " + I_MEMORY_BLOCK(sc) + " getMemoryBlock(long startAddress, long length) throws " + DEBUG_EXCEPTION(sc) + " {");
		sc.add("return null;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addSupportsStorageRetrievalMethod(JavaComposite sc) {
		sc.add("public boolean supportsStorageRetrieval() {");
		sc.add("return false;");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addStartedMethod(JavaComposite sc) {
		sc.addJavadoc("Notification we have connected to the VM and it has started. Resume the VM.");
		sc.add("private void started() {");
		sc.add("fireCreationEvent();");
		sc.add("installDeferredBreakpoints();");
		sc.add("try {");
		sc.add("resume();");
		sc.add("} catch (" + DEBUG_EXCEPTION(sc) + " e) {");
		// TODO handle exception
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addInstallDeferredBreakpointsMethod(JavaComposite sc) {
		sc.addJavadoc("Install breakpoints that are already registered with the breakpoint manager.");
		sc.add("private void installDeferredBreakpoints() {");
		sc.add(I_BREAKPOINT(sc) + "[] breakpoints = getBreakpointManager().getBreakpoints(getModelIdentifier());");
		sc.add("for (int i = 0; i < breakpoints.length; i++) {");
		sc.add("breakpointAdded(breakpoints[i]);");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addHandleMessageMethod(JavaComposite sc) {
		sc.add("public void handleMessage(" + debugMessageClassName + " message) {");
		sc.add("try {");
		sc.add("if (message.hasType(" + eDebugMessageTypesClassName + ".STARTED)) {");
		sc.add("started();");
		sc.add("} else if (message.hasType(" + eDebugMessageTypesClassName + ".SUSPENDED)) {");
		sc.add("suspend();");
		sc.add("} else if (message.hasType(" + eDebugMessageTypesClassName + ".TERMINATED)) {");
		sc.add("terminated();");
		sc.add("} else if (message.hasType(" + eDebugMessageTypesClassName + ".RESUMED)) {");
		sc.addComment("ignore, this event is handled by the debug thread");
		sc.add("} else {");
		sc.add("System.out.println(\"ERROR in \" + this.getClass().getName() + \".handleMessage(): unknown event \" + message);");
		sc.add("}");
		sc.add("} catch (" + DEBUG_EXCEPTION(sc) + " e) {");
		// TODO handle exception
		sc.add("e.printStackTrace();");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetDebugProxyMethod(JavaComposite sc) {
		sc.add("public " + debugProxyClassName + " getDebugProxy() {");
		sc.add("return this.debugProxy;");
		sc.add("}");
		sc.addLineBreak();
	}
}
