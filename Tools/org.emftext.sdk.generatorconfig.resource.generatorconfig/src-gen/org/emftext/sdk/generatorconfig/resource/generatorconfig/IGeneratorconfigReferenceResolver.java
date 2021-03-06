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
package org.emftext.sdk.generatorconfig.resource.generatorconfig;

// A reference resolver tries to resolve a reference to one or many model elements (EObjects).
// It is called by the EMF proxy resolution mechanism.
//
// @param <ContainerType> the type of the container that contains
// the reference that is resolved by this resolver
//
// @param <ReferenceType> the type of the reference that is
// resolved by this resolver
//
public interface IGeneratorconfigReferenceResolver<ContainerType extends org.eclipse.emf.ecore.EObject, ReferenceType extends org.eclipse.emf.ecore.EObject> extends org.emftext.sdk.generatorconfig.resource.generatorconfig.IGeneratorconfigConfigurable {
	// Attempts to resolve a reference string.
	//
	// @param identifier The identifier for the reference.
	// @param container The object that contains the reference.
	// @param reference The reference that points to the target of the reference.
	// @param position The index of the reference (if it has an upper bound greater than 1).
	// @param resolveFuzzy return objects that do not match exactly
	// @param result an object that can be sued to store the result of the resolve operation.
	public void resolve(String identifier, ContainerType container, org.eclipse.emf.ecore.EReference reference, int position, boolean resolveFuzzy, org.emftext.sdk.generatorconfig.resource.generatorconfig.IGeneratorconfigReferenceResolveResult<ReferenceType> result);

	// Reverse of the resolve operation: constructs a representing String of the given
	// object.
	//
	// @param element The referenced model element.
	// @param container The object referencing the element.
	// @param reference The reference that holds the element.
	//
	// @return The identification string for the reference
	public String deResolve(ReferenceType element, ContainerType container, org.eclipse.emf.ecore.EReference reference);
}
