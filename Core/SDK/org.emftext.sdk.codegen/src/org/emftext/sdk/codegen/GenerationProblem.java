/*******************************************************************************
 * Copyright (c) 2006-2012
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
package org.emftext.sdk.codegen;

import org.eclipse.emf.ecore.EObject;

/**
 * Eclipse independent Error/Problem representation for EMFText generators.
 * 
 * @author Sven Karol (Sven.Karol@tu-dresden.de)  
 */
public class GenerationProblem {
	
	/**
	 * An enumeration of all available severity levels.
	 */
	public static enum Severity {WARNING, ERROR}
	
	private GenerationProblem.Severity severity ; 
	private String message ;
	private Exception exceptionThrown;
	private EObject cause;
	
	public GenerationProblem(String message, EObject cause, GenerationProblem.Severity severity, Exception e){
		this.message = message;
		this.cause = cause;
		this.severity = severity;
		this.exceptionThrown = e;
	}
	
	public GenerationProblem(String message, EObject cause, GenerationProblem.Severity severity){
		this(message, cause, severity, null);
	}
	
	public GenerationProblem(String message, EObject cause){
		this(message, cause, Severity.ERROR);
	}
	
	public GenerationProblem.Severity getSeverity() {
		return severity;
	}

	public String getMessage() {
		return message;
	}

	public Exception getExceptionThrown() {
		return exceptionThrown;
	}
	
	public EObject getCause(){
		return cause;
	}

}
